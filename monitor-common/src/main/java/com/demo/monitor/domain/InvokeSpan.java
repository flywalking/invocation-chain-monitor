package com.demo.monitor.domain;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class InvokeSpan implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	/**
	 * 调用链跟踪标识
	 */
	private String traceId ;
	/**
	 * 父InvokeSpanid
	 */
	private String parentId;
	/**
	 * 根InvokeSpanid
	 */
	private String rootId;
	/**
	 * 远程调用类型：dubbo,hessin,http,
	 */
	private int rpcType;
	
	/**
	 * 应用名
	 */
	private String appName;
	/**
	 * 应用Id
	 */
	private String appId;
	/**
	 * 应用集群组名
	 */
	private String clusterGroupName;
	/**
	 * 调用的 服务接口名
	 */
	private String interfaceName;
	/**
	 * 调用的服务方法全名
	 */
	private String mothedName;
	
	/**
	 * 进程号
	 */
	private long pid;
	/**
	 * SampleFlagConstant
	 * 采样标记，0表示未知，1 标示不打印，2表示打印
	 */
	private int sampleFlag;
	/**
	 * 调用戳记录了调用发起时刻，调用返回时刻，调用响应时刻，响应结束时刻 等信息
	 */
	private List<InvokeStamp> stampList = new ArrayList<InvokeStamp>();
	
	private Calendar createTime;
	private Calendar updateTime;
	
	
	//下面冗余信息，可insert InvokeStamp 时update填入
	/**
	 * 在Server端调用开始时间
	 */
	private Long startTimeInServer;
	/**
	 * 在clinet端调用开始时间
	 */
	private Long startTimeInClient;
	
	/**
	 * 方法处理的时间（不包括网络）
	 * 单位毫秒数
	 */
	private Long costTime;
	/**
	 * costTime 减去下一级全部的span的costTimeIncludeNet
	 */
	private Long costTimeExceptChide;
	/**
	 * costTime占根span的时间比例（不包括网络）
	 */
	private Double costRaceByRoot;
	/**
	 * 相对根span的开始时间的开始时间
	 * （startTimeInServer - root的startTimeInServer）/root的costTime
	 */
	private Double startTimeInServerByRoot;
	/**
	 * 方法处理的时间（包括网络）
	 * 单位毫秒数
	 */
	private Long costTimeIncludeNet ;
	
	private String exceptionMessage;
	
	private String ipInServer;
	
	private Integer portInServer;
	/**
	 * 调用状态，1正常，2失败
	 */
	private Integer rpcStatus;
	
	
	public InvokeSpan() {
		super();
	}
	
	public InvokeSpan(String id, String traceId, String parentId,
			String rootId, int rpcType, String appName, String appId,
			String clusterGroupName, String interfaceName, String mothedName,
			int sampleFlag, Calendar createTime, Calendar updateTime,
			Long costTime, Long costTimeExceptChide, Double costRaceByRoot,
			Double startTimeInServerByRoot, Long costTimeIncludeNet,
			String exceptionMessage, String ipInServer, Integer portInServer,
			Integer rpcStatus) {
		super();
		this.id = id;
		this.traceId = traceId;
		this.parentId = parentId;
		this.rootId = rootId;
		this.rpcType = rpcType;
		this.appName = appName;
		this.appId = appId;
		this.clusterGroupName = clusterGroupName;
		this.interfaceName = interfaceName;
		this.mothedName = mothedName;
		this.sampleFlag = sampleFlag;
		this.createTime = createTime;
		this.updateTime = updateTime;
		this.costTime = costTime;
		this.costTimeExceptChide = costTimeExceptChide;
		this.costRaceByRoot = costRaceByRoot;
		this.startTimeInServerByRoot = startTimeInServerByRoot;
		this.costTimeIncludeNet = costTimeIncludeNet;
		this.exceptionMessage = exceptionMessage;
		this.ipInServer = ipInServer;
		this.portInServer = portInServer;
		this.rpcStatus = rpcStatus;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
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
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTraceId() {
		return traceId;
	}

	public void setTraceId(String traceId) {
		this.traceId = traceId;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getRootId() {
		return rootId;
	}

	public void setRootId(String rootId) {
		this.rootId = rootId;
	}
	
	public long getPid() {
		return pid;
	}

	public void setPid(long pid) {
		this.pid = pid;
	}

	public int getSampleFlag() {
		return sampleFlag;
	}

	public void setSampleFlag(int sampleFlag) {
		this.sampleFlag = sampleFlag;
	}

	public List<InvokeStamp> getStampList() {
		return stampList;
	}

	public void setStampList(List<InvokeStamp> stampList) {
		this.stampList = stampList;
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

	public Long getStartTimeInServer() {
		return startTimeInServer;
	}

	public void setStartTimeInServer(Long startTimeInServer) {
		this.startTimeInServer = startTimeInServer;
	}

	public Long getStartTimeInClient() {
		return startTimeInClient;
	}

	public void setStartTimeInClient(Long startTimeInClient) {
		this.startTimeInClient = startTimeInClient;
	}

	public Long getCostTime() {
		return costTime;
	}

	public void setCostTime(Long costTime) {
		this.costTime = costTime;
	}

	public Long getCostTimeIncludeNet() {
		return costTimeIncludeNet;
	}

	public void setCostTimeIncludeNet(Long costTimeIncludeNet) {
		this.costTimeIncludeNet = costTimeIncludeNet;
	}

	public String getExceptionMessage() {
		return exceptionMessage;
	}

	public void setExceptionMessage(String exceptionMessage) {
		this.exceptionMessage = exceptionMessage;
	}

	public String getIpInServer() {
		return ipInServer;
	}

	public void setIpInServer(String ipInServer) {
		this.ipInServer = ipInServer;
	}

	public void setPortInServer(Integer portInServer){
		this.portInServer = portInServer;
	}
	public Integer getPortInServer() {
		return portInServer;
	}
	
	public int getRpcType() {
		return rpcType;
	}

	public void setRpcType(int rpcType) {
		this.rpcType = rpcType;
	}

	public Long getCostTimeExceptChide() {
		return costTimeExceptChide;
	}

	public void setCostTimeExceptChide(Long costTimeExceptChide) {
		this.costTimeExceptChide = costTimeExceptChide;
	}

	public Double getCostRaceByRoot() {
		return costRaceByRoot;
	}

	public void setCostRaceByRoot(Double costRaceByRoot) {
		this.costRaceByRoot = costRaceByRoot;
	}

	public Double getStartTimeInServerByRoot() {
		return startTimeInServerByRoot;
	}

	public void setStartTimeInServerByRoot(Double startTimeInServerByRoot) {
		this.startTimeInServerByRoot = startTimeInServerByRoot;
	}

	public Integer getRpcStatus() {
		return rpcStatus;
	}

	public void setRpcStatus(Integer rpcStatus) {
		this.rpcStatus = rpcStatus;
	}
	
	public static InvokeSpan fromJsonStr(String jsonStr) {
		InvokeSpan invokeSpan = com.alibaba.fastjson.JSON.parseObject(jsonStr, InvokeSpan.class);
		return invokeSpan ;
	}
	
	public String toString(){
		String jsonStr = com.alibaba.fastjson.JSON.toJSONString(this);
		return jsonStr;
	}
	
	
	
}
