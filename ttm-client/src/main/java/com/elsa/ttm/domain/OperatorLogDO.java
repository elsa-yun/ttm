package com.elsa.ttm.domain;

import com.elsa.ttm.domain.BaseDO;

import java.util.Date;

/**
 * 
 * @author haisheng.long Fri Mar 27 17:06:10 CST 2015
 */

public class OperatorLogDO extends BaseDO {

	private static final long serialVersionUID = -5941520524671996231L;

	/** 主键 */
	private Integer id;

	/** 操作人 */
	private String operator;

	/** 操作内容 */
	private String content;

	/** 类型：pause resume remove run_at_once dynamic_create_job */
	private String type;

	/** 记录创建时间 */
	private Date createTime;

	/** job名称 */
	private String jobName;

	/** job组名 */
	private String jobGroupName;

	/** 操作编号 */
	private String actionNo;

	/**
	 * 设置 主键
	 * 
	 * @param id
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * 设置 操作人
	 * 
	 * @param operator
	 */
	public void setOperator(String operator) {
		this.operator = operator;
	}

	/**
	 * 设置 操作内容
	 * 
	 * @param content
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * 设置 类型：pause resume cancle start dynamic
	 * 
	 * @param type
	 */
	public void setType(String type) {
		this.type = type;
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
	 * 获取 主键
	 * 
	 * @return id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * 获取 操作人
	 * 
	 * @return operator
	 */
	public String getOperator() {
		return operator;
	}

	/**
	 * 获取 操作内容
	 * 
	 * @return content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * 获取 类型：pause resume cancle start dynamic
	 * 
	 * @return type
	 */
	public String getType() {
		return type;
	}

	/**
	 * 获取 记录创建时间
	 * 
	 * @return createTime
	 */
	public Date getCreateTime() {
		return createTime;
	}

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public String getJobGroupName() {
		return jobGroupName;
	}

	public void setJobGroupName(String jobGroupName) {
		this.jobGroupName = jobGroupName;
	}

	public String getActionNo() {
		return actionNo;
	}

	public void setActionNo(String actionNo) {
		this.actionNo = actionNo;
	}

}