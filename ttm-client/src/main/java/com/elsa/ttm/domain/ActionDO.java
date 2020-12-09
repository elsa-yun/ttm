package com.elsa.ttm.domain;

import java.util.Date;

/**
 * @author longhaisheng
 * 
 */
public class ActionDO {

	/** 主键 */
	private Integer id;

	/** job组名 */
	private String jobGroupName;

	/** job名 */
	private String jobName;

	/** action动作名称 */
	private String action;

	/** 每次操作时的动作编号 */
	private String actionNo;

	/** IP */
	private String ip;

	/** 时间表达式 */
	private String cronExpression;

	/** 记录创建时间 */
	private Date createTime;

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getActionNo() {
		return actionNo;
	}

	public void setActionNo(String actionNo) {
		this.actionNo = actionNo;
	}

	public String getJobGroupName() {
		return jobGroupName;
	}

	public void setJobGroupName(String jobGroupName) {
		this.jobGroupName = jobGroupName;
	}

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getCronExpression() {
		return cronExpression;
	}

	public void setCronExpression(String cronExpression) {
		this.cronExpression = cronExpression;
	}

}
