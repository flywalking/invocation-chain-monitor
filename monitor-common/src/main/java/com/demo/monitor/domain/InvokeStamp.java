package com.demo.monitor.domain;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Calendar;

public class InvokeStamp implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6267242114300156557L;
	public static final String stampValueFormatOfDate = "yyyy-MM-dd hh:mm:ss.sss";
	private String id;
	private String invokeSpanId;
	/**
	 * 1:客户端服务调用的cs（client send）,
	 * 2:cr（client receive）,
	 * 3:服务端的sr（server receive）,
	 * 4:ss（server send）。
	 * 5:exception
	 */
	private int  stampType;
	/**
	 * 对应stampType 值
	 */
	private String stampValue;
	
	private String ip;
	private int port;
	
	private Calendar createTime;
	private Calendar updateTime;
	
	
	
	public InvokeStamp() {
		super();
	}
	
	public static InvokeStamp fromJsonStr(String jsonStr) {
		InvokeStamp invokeStamp = com.alibaba.fastjson.JSON.parseObject(jsonStr, InvokeStamp.class);
		return invokeStamp ;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getInvokeSpanId() {
		return invokeSpanId;
	}
	public void setInvokeSpanId(String invokeSpanId) {
		this.invokeSpanId = invokeSpanId;
	}
	public int getStampType() {
		return stampType;
	}
	public void setStampType(int stampType) {
		this.stampType = stampType;
	}
	public String getStampValue() {
		return stampValue;
	}
	public void setStampValue(String stampValue) {
		this.stampValue = stampValue;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public Calendar getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Calendar createTime) {
		this.createTime = createTime;
	}
	public Calendar getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Calendar updateTime) {
		this.updateTime = updateTime;
	}
	
	public String toString(){
		String jsonStr = com.alibaba.fastjson.JSON.toJSONString(this);
		return jsonStr;
	}
	
	public static void main(String[] args){
		InvokeStamp s = new InvokeStamp();
		s.setId("123");
		s.setCreateTime(Calendar.getInstance());
		s.setStampValue("{'time':'2014-04-01 12:01:01.012'}");
		System.out.println(s);
		String jsonStr = s.toString();
		s = InvokeStamp.fromJsonStr(jsonStr);
		System.out.println(s);
	}
	
	
}
