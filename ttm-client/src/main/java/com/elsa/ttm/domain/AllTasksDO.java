package com.elsa.ttm.domain;

import com.elsa.ttm.domain.BaseDO;

import java.util.Date;

/**
 * 
 * @author haisheng.long Fri Mar 27 17:06:10 CST 2015
 */

public class AllTasksDO extends BaseDO {

	private static final long serialVersionUID = 1002281077645924761L;

	/** 主键 */
	private Integer id;

	/** job名称 */
	private String jobName;

	/** job组名 */
	private String jobGroupName;

	/** trigger名称 */
	private String triggerName;

	/** trigger组名 */
	private String triggerGroupName;

	/** job介绍 */
	private String jobMemo;

	/** simple:cron */
	private String jobTimeType;

	/** single:distribute */
	private String jobEnvironmentType;

	/** simpleTrigger 时间次数等信息 */
	private String simpleTriggerJson;

	/** 时间表达式 */
	private String cronExpression;

	/** 任务是否只运行一次 */
	private boolean onlyRunOnce;

	/** 目标对象 */
	private String targetObject;

	/** 目标方法 */
	private String targetMethod;

	/** 方法参数 */
	private String arguments;

	/** 多节点任务 ips */
	private String distIps;

	/** 节点运行状态 pause resume normal */
	private String runStatus;

	/** 上次操作编号 */
	private String lastActionNo;

	/** 上次操作结果 */
	private String lastActionResult;

	/** 上一次运行时间 */
	private Date lastRunTime;

	/** 开始时间 */
	private Date startTime;

	/** 结束时间 */
	private Date endTime;

	/** 记录创建时间 */
	private Date createTime;

	/** 记录修改时间 */
	private Date modifyTime;
	// //////////////simpleTrigger/////////////////////
	/** 延时毫秒数 */
	private int startDelay;

	/** 重复次数 */
	private int repeatCount;

	/** 重复间隔 */
	private int repeatInterval;

	/**
	 * 设置 主键
	 * 
	 * @param id
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * 设置 job名称
	 * 
	 * @param jobName
	 */
	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	/**
	 * 设置 job组名
	 * 
	 * @param jobGroupName
	 */
	public void setJobGroupName(String jobGroupName) {
		this.jobGroupName = jobGroupName;
	}

	/**
	 * 设置 trigger名称
	 * 
	 * @param triggerName
	 */
	public void setTriggerName(String triggerName) {
		this.triggerName = triggerName;
	}

	/**
	 * 设置 trigger组名
	 * 
	 * @param triggerGroupName
	 */
	public void setTriggerGroupName(String triggerGroupName) {
		this.triggerGroupName = triggerGroupName;
	}

	/**
	 * 设置 job介绍
	 * 
	 * @param jobMemo
	 */
	public void setJobMemo(String jobMemo) {
		this.jobMemo = jobMemo;
	}

	/**
	 * 设置 simple:cron
	 * 
	 * @param jobTimeType
	 */
	public void setJobTimeType(String jobTimeType) {
		this.jobTimeType = jobTimeType;
	}

	/**
	 * 设置 single:distribute
	 * 
	 * @param jobEnvironmentType
	 */
	public void setJobEnvironmentType(String jobEnvironmentType) {
		this.jobEnvironmentType = jobEnvironmentType;
	}

	/**
	 * 设置 时间表达式
	 * 
	 * @param cronExpression
	 */
	public void setCronExpression(String cronExpression) {
		this.cronExpression = cronExpression;
	}

	/**
	 * 设置 上一次运行时间
	 * 
	 * @param lastRunTime
	 */
	public void setLastRunTime(Date lastRunTime) {
		this.lastRunTime = lastRunTime;
	}

	/**
	 * 设置 开始时间
	 * 
	 * @param startTime
	 */
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	/**
	 * 设置 结束时间
	 * 
	 * @param endTime
	 */
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	/**
	 * 设置 记录创建时间
	 * 
	 * @param createTime
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * 设置 记录修改时间
	 * 
	 * @param modifyTime
	 */
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	/**
	 * 获取 主键
	 * 
	 * @return id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * 获取 job名称
	 * 
	 * @return jobName
	 */
	public String getJobName() {
		return jobName;
	}

	/**
	 * 获取 job组名
	 * 
	 * @return jobGroupName
	 */
	public String getJobGroupName() {
		return jobGroupName;
	}

	/**
	 * 获取 trigger名称
	 * 
	 * @return triggerName
	 */
	public String getTriggerName() {
		return triggerName;
	}

	/**
	 * 获取 trigger组名
	 * 
	 * @return triggerGroupName
	 */
	public String getTriggerGroupName() {
		return triggerGroupName;
	}

	/**
	 * 获取 job介绍
	 * 
	 * @return jobMemo
	 */
	public String getJobMemo() {
		return jobMemo;
	}

	/**
	 * 获取 simple:cron
	 * 
	 * @return jobTimeType
	 */
	public String getJobTimeType() {
		return jobTimeType;
	}

	/**
	 * 获取 single:distribute
	 * 
	 * @return jobEnvironmentType
	 */
	public String getJobEnvironmentType() {
		return jobEnvironmentType;
	}

	/**
	 * 获取 时间表达式
	 * 
	 * @return cronExpression
	 */
	public String getCronExpression() {
		return cronExpression;
	}

	/**
	 * 获取 上一次运行时间
	 * 
	 * @return lastRunTime
	 */
	public Date getLastRunTime() {
		return lastRunTime;
	}

	/**
	 * 获取 开始时间
	 * 
	 * @return startTime
	 */
	public Date getStartTime() {
		return startTime;
	}

	/**
	 * 获取 结束时间
	 * 
	 * @return endTime
	 */
	public Date getEndTime() {
		return endTime;
	}

	/**
	 * 获取 记录创建时间
	 * 
	 * @return createTime
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * 获取 记录修改时间
	 * 
	 * @return modifyTime
	 */
	public Date getModifyTime() {
		return modifyTime;
	}

	public String getRunStatus() {
		return runStatus;
	}

	public void setRunStatus(String runStatus) {
		this.runStatus = runStatus;
	}

	public String getSimpleTriggerJson() {
		return simpleTriggerJson;
	}

	public void setSimpleTriggerJson(String simpleTriggerJson) {
		this.simpleTriggerJson = simpleTriggerJson;
	}

	public String getDistIps() {
		return distIps;
	}

	public void setDistIps(String distIps) {
		this.distIps = distIps;
	}

	public boolean isOnlyRunOnce() {
		return onlyRunOnce;
	}

	public void setOnlyRunOnce(boolean onlyRunOnce) {
		this.onlyRunOnce = onlyRunOnce;
	}

	public String getTargetObject() {
		return targetObject;
	}

	public void setTargetObject(String targetObject) {
		this.targetObject = targetObject;
	}

	public String getTargetMethod() {
		return targetMethod;
	}

	public void setTargetMethod(String targetMethod) {
		this.targetMethod = targetMethod;
	}

	public String getArguments() {
		return arguments;
	}

	public void setArguments(String arguments) {
		this.arguments = arguments;
	}

	public String getLastActionNo() {
		return lastActionNo;
	}

	public void setLastActionNo(String lastActionNo) {
		this.lastActionNo = lastActionNo;
	}

	public String getLastActionResult() {
		return lastActionResult;
	}

	public void setLastActionResult(String lastActionResult) {
		this.lastActionResult = lastActionResult;
	}

	public int getStartDelay() {
		return startDelay;
	}

	public void setStartDelay(int startDelay) {
		this.startDelay = startDelay;
	}

	public int getRepeatCount() {
		return repeatCount;
	}

	public void setRepeatCount(int repeatCount) {
		this.repeatCount = repeatCount;
	}

	public int getRepeatInterval() {
		return repeatInterval;
	}

	public void setRepeatInterval(int repeatInterval) {
		this.repeatInterval = repeatInterval;
	}

	@Override
	public String toString() {
		return "AllTasksDO [id=" + id + ", jobName=" + jobName + ", jobGroupName=" + jobGroupName + ", triggerName=" + triggerName + ", triggerGroupName=" + triggerGroupName + ", jobMemo=" + jobMemo
				+ ", jobTimeType=" + jobTimeType + ", jobEnvironmentType=" + jobEnvironmentType + ", simpleTriggerJson=" + simpleTriggerJson + ", cronExpression=" + cronExpression + ", onlyRunOnce="
				+ onlyRunOnce + ", targetObject=" + targetObject + ", targetMethod=" + targetMethod + ", arguments=" + arguments + ", distIps=" + distIps + ", runStatus=" + runStatus
				+ ", lastActionNo=" + lastActionNo + ", lastActionResult=" + lastActionResult + ", lastRunTime=" + lastRunTime + ", startTime=" + startTime + ", endTime=" + endTime + ", createTime="
				+ createTime + ", modifyTime=" + modifyTime + ", startDelay=" + startDelay + ", repeatCount=" + repeatCount + ", repeatInterval=" + repeatInterval + "]";
	}

}