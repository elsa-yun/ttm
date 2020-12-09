package com.elsa.ttm.domain;

import com.elsa.ttm.domain.BaseDO;

import java.util.Date;

/**
 * 
 * @author haisheng.long Fri Mar 27 17:06:10 CST 2015
 */

public class UsersDO extends BaseDO {

	private static final long serialVersionUID = -2305749466036862467L;

	/** 主键 */
	private Integer id;

	/** 用户名 */
	private String userName;

	/** 密码 */
	private String passWord;

	/** 可操作groupNames */
	private String groupNames;

	/** admin tl */
	private String role;

	/** 是否删除 */
	private Integer isDelete;

	/** 创建时间 */
	private Date createTime;

	/** 修改时间 */
	private Date modifyTime;

	/**
	 * 设置 主键
	 * 
	 * @param id
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * 设置
	 * 
	 * @param userName
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * 设置
	 * 
	 * @param passWord
	 */
	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}

	/**
	 * 设置 admin tl
	 * 
	 * @param role
	 */
	public void setRole(String role) {
		this.role = role;
	}

	/**
	 * 设置
	 * 
	 * @param isDelete
	 */
	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
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
	 * 设置
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
	 * 获取
	 * 
	 * @return userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * 获取
	 * 
	 * @return passWord
	 */
	public String getPassWord() {
		return passWord;
	}

	/**
	 * 获取 admin tl
	 * 
	 * @return role
	 */
	public String getRole() {
		return role;
	}

	/**
	 * 获取
	 * 
	 * @return isDelete
	 */
	public Integer getIsDelete() {
		return isDelete;
	}

	/**
	 * 获取
	 * 
	 * @return createTime
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * 获取
	 * 
	 * @return modifyTime
	 */
	public Date getModifyTime() {
		return modifyTime;
	}

	public String getGroupNames() {
		return groupNames;
	}

	public void setGroupNames(String groupNames) {
		this.groupNames = groupNames;
	}

}