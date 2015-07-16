package com.demo.monitor.domain;

import java.io.Serializable;

public class ServiceAttribute implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int rpcType ;
	private String appName ;
	private String appId;
	private String clusterGroupName ;
	private String interfaceName ;
	private String mothedName ;
	private String ip;
	private int port;
	private long pid;
	
	public int getRpcType() {
		return rpcType;
	}
	public void setRpcType(int rpcType) {
		this.rpcType = rpcType;
	}
	public String getAppName() {
		return appName;
	}
	public void setAppName(String appName) {
		this.appName = appName;
	}
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getClusterGroupName() {
		return clusterGroupName;
	}
	public void setClusterGroupName(String clusterGroupName) {
		this.clusterGroupName = clusterGroupName;
	}
	public String getInterfaceName() {
		return interfaceName;
	}
	public void setInterfaceName(String interfaceName) {
		this.interfaceName = interfaceName;
	}
	public String getMothedName() {
		return mothedName;
	}
	public void setMothedName(String mothedName) {
		this.mothedName = mothedName;
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
	public long getPid() {
		return pid;
	}
	public void setPid(long pid) {
		this.pid = pid;
	}
	
}
