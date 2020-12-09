package com.elsa.ttm.domain;

import com.elsa.ttm.domain.BaseDO;

import java.util.Date;

/**
 * 任务运行日志
 * 
 * @author haisheng.long Fri Mar 27 17:06:10 CST 2015
 */

public class TriggerLogDO extends BaseDO {

	private static final long serialVersionUID = 6458464486008822535L;

	/** 主键 */
	private Integer id;

	/** job名 */
	private String jobName;

	/** job所属组 */
	private String jobGroupName;

	/** 时间表达式 */
	private String cronExpression;

	/** 方法运行时信息 */
	private String runMemo;

	/** 方法开始运行时间 */
	private long startTime;

	/** 方法运行结束时间 */
	private long endTime;

	/** job运行是否成功 */
	private Integer runStatus;

	/** 记录创建时间 */
	private Date createTime;

	/** 创建记录IP */
	private String ip;

	/**
	 * 设置 主键
	 * 
	 * @param id
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * 设置 job名
	 * 
	 * @param jobName
	 */
	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	/**
	 * 设置 job所属组
	 * 
	 * @param jobGroupName
	 */
	public void setJobGroupName(String jobGroupName) {
		this.jobGroupName = jobGroupName;
	}

	/**
	 * 设置
	 * 
	 * @param cronExpression
	 */
	public void setCronExpression(String cronExpression) {
		this.cronExpression = cronExpression;
	}

	/**
	 * 设置 任务运行开始时间
	 * 
	 * @param startTime
	 */
	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	/**
	 * 设置 任务运行结束时间
	 * 
	 * @param endTime
	 */
	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}

	/**
	 * 设置 job运行是否成功
	 * 
	 * @param runStatus
	 */
	public void setRunStatus(Integer runStatus) {
		this.runStatus = runStatus;
	}

	/**
	 * 设置
	 * 
	 * @param createTime
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
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
	 * 获取 job名
	 * 
	 * @return jobName
	 */
	public String getJobName() {
		return jobName;
	}

	/**
	 * 获取 job所属组
	 * 
	 * @return jobGroupName
	 */
	public String getJobGroupName() {
		return jobGroupName;
	}

	/**
	 * 获取
	 * 
	 * @return cronExpression
	 */
	public String getCronExpression() {
		return cronExpression;
	}

	/**
	 * 获取 任务运行开始时间
	 * 
	 * @return startTime
	 */
	public long getStartTime() {
		return startTime;
	}

	/**
	 * 获取 任务运行结束时间
	 * 
	 * @return endTime
	 */
	public long getEndTime() {
		return endTime;
	}

	/**
	 * 获取 job运行是否成功
	 * 
	 * @return runStatus
	 */
	public Integer getRunStatus() {
		return runStatus;
	}

	/**
	 * 获取
	 * 
	 * @return createTime
	 */
	public Date getCreateTime() {
		return createTime;
	}

	public String getRunMemo() {
		return runMemo;
	}

	public void setRunMemo(String runMemo) {
		this.runMemo = runMemo;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

}