package com.demo.monitor.dubbo;

import org.apache.commons.lang3.time.DateFormatUtils;

import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.extension.Activate;
import com.alibaba.dubbo.rpc.Filter;
import com.alibaba.dubbo.rpc.Invocation;
import com.alibaba.dubbo.rpc.Invoker;
import com.alibaba.dubbo.rpc.Result;
import com.alibaba.dubbo.rpc.RpcContext;
import com.alibaba.dubbo.rpc.RpcException;
import com.alibaba.dubbo.rpc.RpcInvocation;
import com.demo.monitor.constant.InvokeStampTypeConstant;
import com.demo.monitor.constant.RpcTypeConstant;
import com.demo.monitor.domain.InvokeSpan;
import com.demo.monitor.domain.InvokeStamp;
import com.demo.monitor.domain.ServiceAttribute;
import com.demo.monitor.manager.PhotoscopeTracer;

@Activate(group = {Constants.PROVIDER, Constants.CONSUMER})
public class PhotoscopeFilter implements Filter {

	@Override
	public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        RpcContext rpcContext = RpcContext.getContext();
        String ip = rpcContext.getLocalHost();
        int port =  rpcContext.getLocalPort();
        InvokeSpan invokeSpan = null;
       
        if(Constants.CONSUMER_SIDE.equals(invoker.getUrl().getParameter(Constants.SIDE_KEY))) {//服务消费方
        	invokeSpan = PhotoscopeTracer.createSpanInRpcClient();
       	 	RpcInvocation rpcInvocation = (RpcInvocation) invocation;
            setAttachment(invokeSpan, rpcInvocation);//设置需要向下游传递的参数
            String stampValue = String.format("{'time':'%s'}",new Object[]{DateFormatUtils.format(System.currentTimeMillis(), InvokeStamp.stampValueFormatOfDate)});
            
            PhotoscopeTracer.createInvokeStampAndSet(invokeSpan, InvokeStampTypeConstant.cs,stampValue, ip, port);
            try{
	           	 //调用invoke链
	           	Result result = invoker.invoke(rpcInvocation);
	            if (result.getException() != null){
	                catchException(result.getException(),invokeSpan,ip,port);
	            }
	            return result;
           }catch (RpcException e){        	   
        	   catchException(e,invokeSpan,ip,port);
        	   throw e;
           }finally {
               stampValue = String.format("{'time':'%s'}",new Object[]{DateFormatUtils.format(System.currentTimeMillis(), InvokeStamp.stampValueFormatOfDate)});
        	   PhotoscopeTracer.createInvokeStampAndSet(invokeSpan, InvokeStampTypeConstant.cr, stampValue, ip, port);
        	   PhotoscopeTracer.printStamp(invokeSpan);
           }
            
        }else{
        	//服务提供方
        	String traceId = invocation.getAttachment(PhotoscopeTracer.TRACE_ID);
            String parentId = invocation.getAttachment(PhotoscopeTracer.PARENT_ID); 
            String spanId = invocation.getAttachment(PhotoscopeTracer.SPAN_ID);
            String rootId = invocation.getAttachment(PhotoscopeTracer.ROOT_ID);
            String sampleFlagStr= invocation.getAttachment(PhotoscopeTracer.SAMPLE_FLAG);
            int sampleFlag = sampleFlagStr==null?0:Integer.parseInt (sampleFlagStr);
        	invokeSpan = PhotoscopeTracer.buildSpanFromRpcParamAndPutNextRpc(spanId, traceId, parentId, rootId, sampleFlag);
        	String  stampValue = String.format("{'time':'%s'}",new Object[]{DateFormatUtils.format(System.currentTimeMillis(), InvokeStamp.stampValueFormatOfDate)});
        	PhotoscopeTracer.createInvokeStampAndSet(invokeSpan, InvokeStampTypeConstant.sr, stampValue, ip, port);
        	try{
           	 	//调用invoke链
        		Result result = invoker.invoke(invocation);
        		if (result.getException() != null){
        			 catchException(result.getException(),invokeSpan,ip,port);
                }
        		return result;
           }catch (RpcException e){
        	   catchException(e,invokeSpan,ip,port);
        	   throw e;
           }finally {
                String appName = invoker.getUrl().getParameter(Constants.APPLICATION_KEY);
               	String clusterGroupName = invoker.getUrl().getParameter(Constants.GROUP_KEY);
                   ServiceAttribute serviceAttribute = new ServiceAttribute();
                   serviceAttribute.setAppId(appName);//暂时设置appName 为appId
                   serviceAttribute.setAppName(appName);
                   serviceAttribute.setClusterGroupName(clusterGroupName);
                   serviceAttribute.setInterfaceName(invocation.getInvoker().getInterface().getName());
                   serviceAttribute.setMothedName(rpcContext.getMethodName());
                   serviceAttribute.setRpcType(RpcTypeConstant.dubbo);
                   PhotoscopeTracer.setServiceAttributeAndPrint(invokeSpan, serviceAttribute);
                   stampValue = String.format("{'time':'%s'}",new Object[]{DateFormatUtils.format(System.currentTimeMillis(), InvokeStamp.stampValueFormatOfDate)});
                   PhotoscopeTracer.createInvokeStampAndSet(invokeSpan, InvokeStampTypeConstant.ss, stampValue, ip, port);
                   PhotoscopeTracer.printStamp(invokeSpan);
               }
          }
     }
	
	private void setAttachment(InvokeSpan span, RpcInvocation invocation){
        invocation.setAttachment(PhotoscopeTracer.TRACE_ID, span.getTraceId());
        invocation.setAttachment(PhotoscopeTracer.PARENT_ID, span.getParentId());
        invocation.setAttachment(PhotoscopeTracer.SPAN_ID, span.getId());
        invocation.setAttachment(PhotoscopeTracer.ROOT_ID, span.getRootId());
        invocation.setAttachment(PhotoscopeTracer.SAMPLE_FLAG, String.valueOf(span.getSampleFlag()));
        
	}

	
	private void catchException(Throwable exception,InvokeSpan span,String ip,int port) {
		String causeMsg = exception.getCause()==null ? "":exception.getCause().getMessage();
		String stampValue = String.format("{'exMsg':'%s';'cause':'%s';'exClass':'%s'}", new Object[]{exception.getMessage(),causeMsg,exception.getClass().getName()});
	 	PhotoscopeTracer.createInvokeStampAndSet(span, InvokeStampTypeConstant.ex, stampValue, ip, port);
	}

}
