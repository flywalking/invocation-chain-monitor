package com.demo.monitor.manager;

import java.util.Calendar;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.demo.monitor.constant.SampleFlagConstant;
import com.demo.monitor.domain.InvokeSpan;
import com.demo.monitor.domain.InvokeStamp;
import com.demo.monitor.domain.ServiceAttribute;


public final class PhotoscopeTracer {
	static protected transient final Logger invokeSpanLogger = LoggerFactory.getLogger(InvokeSpan.class);
	static protected transient final Logger invokeStampLogger = LoggerFactory.getLogger(InvokeStamp.class);
	
	public static final String TRACE_ID = "traceId";
    public static final String SPAN_ID = "spanId";
    public static final String PARENT_ID = "parentId";
    public static final String SAMPLE_FLAG = "sampleFlag";
    public static final String ROOT_ID = "rootId";
    
     /**
      * 在rpc客户端,SPAN_HOLDER保持的是当前span的父span
      * 在rpc服务端，SPAN_HOLDER保持的是当前span
      */
	private static final ThreadLocal<InvokeSpan> SPAN_HOLDER = new ThreadLocal<InvokeSpan>();
	
	public static InvokeSpan getCurrentSpan() {
		return SPAN_HOLDER.get();
	}
	
	public static void setCurrentSpan(InvokeSpan InvokeSpan) {
		SPAN_HOLDER.set(InvokeSpan);
	}
	/**
	 * 在rpc客户端发起调用时，调用这个方法来创建InvokeSpan
	 * 产生rootId的时候来决定是否采样
	 * @return
	 */
	public static InvokeSpan createSpanInRpcClient(){
		String id = UUID.randomUUID().toString();
		String traceId =  UUID.randomUUID().toString();
		String parentId = null;
		String rootId = null;
		int sampleFlag = 0;
		InvokeSpan parentSpan = SPAN_HOLDER.get();
		if(parentSpan == null){
			rootId = id;
			parentId = null;
			sampleFlag = SampleFlagHandler.getSampleFlag();
		}else{
			rootId = parentSpan.getRootId();
			parentId = parentSpan.getId();
		}
		Calendar createTime = Calendar.getInstance();
		Calendar updateTime = Calendar.getInstance();
		InvokeSpan span = new InvokeSpan();
		span.setCreateTime(createTime);
		span.setUpdateTime(updateTime);
		span.setId(id);
		span.setTraceId(traceId);
		span.setParentId(parentId);
		span.setSampleFlag(sampleFlag);
		span.setRootId(rootId);
		return span;
	}
	
	public static InvokeSpan createInvokeStampAndSet(InvokeSpan span,int stampType, String stampValue,String ip,int port){
		InvokeStamp invokeStamp = new InvokeStamp();
		String id = UUID.randomUUID().toString();
		invokeStamp.setId(id);
		invokeStamp.setInvokeSpanId(span.getId());
		invokeStamp.setCreateTime(Calendar.getInstance());
		invokeStamp.setUpdateTime(Calendar.getInstance());
		invokeStamp.setStampType(stampType);
		invokeStamp.setStampValue(stampValue);
		invokeStamp.setIp(ip);
		invokeStamp.setPort(port);
		span.getStampList().add(invokeStamp);
		return span;
	}
	
	/**
	 * 在rpc 服务端或客户端 处理完请求后打印InvokeSpan
	 * @param span
	 */
	public static void printStamp(InvokeSpan span){
		if(span.getSampleFlag() != SampleFlagConstant.sample){
			return ;
		}
		StringBuilder sb = new StringBuilder("\n");
		for(InvokeStamp stamp:span.getStampList()){
			sb.append(stamp.toString());
			sb.append("\n");
		}
		sb.append("\n");
		invokeStampLogger.info(sb.toString());
	}
	/**
	 * 在服务端从Rpc获得Span参数来还原Span对象放到ThreadLocal，
	 * 方便在发起rpc请求是可以从ThreadLocal那出来作为父Span，
	 * 产生rootId的时候来决定是否采样
	 * @param id
	 * @param traceId
	 * @param parentId
	 * @param rootId
	 * @return
	 */
	public static InvokeSpan buildSpanFromRpcParamAndPutNextRpc(String id,
			String traceId ,String parentId,String rootId,int sampleFlag){
		if(id == null){
			id = UUID.randomUUID().toString();
			traceId =  UUID.randomUUID().toString();
			parentId = null;
			rootId = id;
			sampleFlag = SampleFlagHandler.getSampleFlag();
		}
		InvokeSpan span = new InvokeSpan();
		span.setCreateTime(Calendar.getInstance());
		span.setUpdateTime(Calendar.getInstance());
		span.setId(id);
		span.setTraceId(traceId);
		span.setParentId(parentId);
		span.setSampleFlag(sampleFlag);
		span.setRootId(rootId);
		SPAN_HOLDER.set(span);
		return span;
	}
	/**
	 * rpc 服务端处理请求后调用，打印InvokeSpan
	 * @param span
	 * @param serviceAttribute
	 * @param sampleFlag
	 * @return
	 */
	public static InvokeSpan setServiceAttributeAndPrint(InvokeSpan span ,ServiceAttribute serviceAttribute){
		span.setRpcType(serviceAttribute.getRpcType());
		span.setAppName(serviceAttribute.getAppName());
		span.setAppId(serviceAttribute.getAppId());
		span.setAppName(serviceAttribute.getAppName());
		span.setClusterGroupName(serviceAttribute.getClusterGroupName());
		span.setInterfaceName(serviceAttribute.getInterfaceName());
		span.setMothedName(serviceAttribute.getMothedName());
		if(span.getSampleFlag() != SampleFlagConstant.sample){
			return span;
		}
		List<InvokeStamp> stampList = span.getStampList();
		span.setStampList(null);//stampList 不打印
		
		StringBuilder sb = new StringBuilder("\n");
		sb.append(span.toString());
		sb.append("\n");
		sb.append("\n");
		invokeSpanLogger.info(sb.toString());
		
		span.setStampList(stampList);//stampList 放回
		return span;
	}
	
	
}
