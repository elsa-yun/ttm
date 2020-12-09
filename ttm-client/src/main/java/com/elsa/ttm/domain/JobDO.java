package com.elsa.ttm.domain;

/**
 * @author longhaisheng
 * 
 */
public class JobDO {

	private String jobName;

	private String cronExpression;

	private String jobDesc;

	private String triggerStatus;

	private String jobGroupName;

	private String triggerName;

	private String triggerGroupName;

	/** simple:cron */
	private String jobTimeType;

	/** single:distribute */
	private String jobEnvironmentType;

	// //////////////simple////////////////

	private int isSimple;

	private String startTime;

	private String endTime;

	private String repeatCount;

	private String repeatInterval;

	private String startDelay;

	// //////////////simple////////////////

	/** 节点运行状态 pause resume normal */
	private String status;

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public String getCronExpression() {
		return cronExpression;
	}

	public void setCronExpression(String cronExpression) {
		this.cronExpression = cronExpression;
	}

	public String getJobDesc() {
		return jobDesc;
	}

	public void setJobDesc(String jobDesc) {
		this.jobDesc = jobDesc;
	}

	public String getTriggerStatus() {
		return triggerStatus;
	}

	public void setTriggerStatus(String triggerStatus) {
		this.triggerStatus = triggerStatus;
	}

	public String getJobGroupName() {
		return jobGroupName;
	}

	public void setJobGroupName(String jobGroupName) {
		this.jobGroupName = jobGroupName;
	}

	public String getTriggerGroupName() {
		return triggerGroupName;
	}

	public void setTriggerGroupName(String triggerGroupName) {
		this.triggerGroupName = triggerGroupName;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getRepeatCount() {
		return repeatCount;
	}

	public void setRepeatCount(String repeatCount) {
		this.repeatCount = repeatCount;
	}

	public String getRepeatInterval() {
		return repeatInterval;
	}

	public void setRepeatInterval(String repeatInterval) {
		this.repeatInterval = repeatInterval;
	}

	public String getStartDelay() {
		return startDelay;
	}

	public void setStartDelay(String startDelay) {
		this.startDelay = startDelay;
	}

	public int getIsSimple() {
		return isSimple;
	}

	public void setIsSimple(int isSimple) {
		this.isSimple = isSimple;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTriggerName() {
		return triggerName;
	}

	public void setTriggerName(String triggerName) {
		this.triggerName = triggerName;
	}

	public String getJobTimeType() {
		return jobTimeType;
	}

	public void setJobTimeType(String jobTimeType) {
		this.jobTimeType = jobTimeType;
	}

	public String getJobEnvironmentType() {
		return jobEnvironmentType;
	}

	public void setJobEnvironmentType(String jobEnvironmentType) {
		this.jobEnvironmentType = jobEnvironmentType;
	}

}
