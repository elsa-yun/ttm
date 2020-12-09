package com.elsa.ttm.domain;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author longhaisheng
 * 
 */
public class DynamicActionDO {

	/** simpleTrigger */
	public final static String SCHEDULE_TYPE_SIMPLE_TRIGGER = "simpleTrigger";

	/** cronTrigger */
	public final static String SCHEDULE_TYPE_CRON_TRIGGER = "cronTrigger";

	/** single node */
	public final static String JOB_ENVIRONMENT_TYPE_SINGLE = "single";

	/** distribute node*/
	public final static String JOB_ENVIRONMENT_TYPE_DISTRIBUTE = "distribute";

	/** time_type_cron */
	public final static String JOB_TIME_TYPE_CRON = "cron";

	/** time_type_simple */
	public final static String JOB_TIME_TYPE_SIMPLE = "simple";

	/** 目标对象 */
	private String targetObject;

	/** 目标方法 */
	private String targetMethod;

	/** job所属组 */
	private String jobGroupName;

	/** job名 */
	private String jobName;

	/** job描述 */
	private String jobDesc;

	/** trigger名 */
	private String triggerName;

	/** trigger组名 */
	private String triggerGroupName;

	private Date startTime = null;// 暂时不使用

	private Date endTime = null;// 暂时不使用

	/** 延时毫秒数 */
	private long startDelay = 1000L;

	/** 重复间隔 */
	private long repeatInterval = 0L;

	/** 重复次数 */
	private long repeatCount = 0L;

	/** 时间表达式 */
	private String cronExpression;

	/** trigger类型 */
	private String triggerType;

	/** bean名称 */
	private String beanName;

	/** action动作名称 */
	private String action;

	/** 每次操作时的动作编号 */
	private String actionNo;

	/** 参数列表 */
	private List<String> paramList;

	/** 运行节点列表 */
	private List<String> ipList;

	/** 节点运行环境 单节点/多节点 */
	private String jobEnvironmentType;

	/** 任务是否只运行一次 */
	private boolean onlyRunOnce = false;

	/** 任务是否是动态添加 */
	private boolean isDynamicAdd = false;

	/** job时间类型 */
	private String jobTimeType;

	/** simpleTrigger任务所有属性 */
	private Map<String, Object> simpleTriggerMap = new HashMap<String, Object>();

	public String getJobEnvironmentType() {
		return jobEnvironmentType;
	}

	public void setJobEnvironmentType(String jobEnvironmentType) {
		this.jobEnvironmentType = jobEnvironmentType;
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

	public String getJobDesc() {
		return jobDesc;
	}

	public void setJobDesc(String jobDesc) {
		this.jobDesc = jobDesc;
	}

	public long getStartDelay() {
		return startDelay;
	}

	public void setStartDelay(long startDelay) {
		this.startDelay = startDelay;
	}

	public long getRepeatInterval() {
		return repeatInterval;
	}

	public void setRepeatInterval(long repeatInterval) {
		this.repeatInterval = repeatInterval;
	}

	public long getRepeatCount() {
		return repeatCount;
	}

	public void setRepeatCount(long repeatCount) {
		this.repeatCount = repeatCount;
	}

	public String getCronExpression() {
		return cronExpression;
	}

	public void setCronExpression(String cronExpression) {
		this.cronExpression = cronExpression;
	}

	public String getTriggerType() {
		return triggerType;
	}

	public void setTriggerType(String triggerType) {
		this.triggerType = triggerType;
	}

	public String getBeanName() {
		return beanName;
	}

	public void setBeanName(String beanName) {
		this.beanName = beanName;
	}

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

	public List<String> getParamList() {
		return paramList;
	}

	public void setParamList(List<String> paramList) {
		this.paramList = paramList;
	}

	public List<String> getIpList() {
		return ipList;
	}

	public void setIpList(List<String> ipList) {
		this.ipList = ipList;
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

	public Map<String, Object> getSimpleTriggerMap() {
		return simpleTriggerMap;
	}

	public void setSimpleTriggerMap(Map<String, Object> simpleTriggerMap) {
		this.simpleTriggerMap = simpleTriggerMap;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public boolean isOnlyRunOnce() {
		return onlyRunOnce;
	}

	public void setOnlyRunOnce(boolean onlyRunOnce) {
		this.onlyRunOnce = onlyRunOnce;
	}

	public String getTriggerGroupName() {
		return triggerGroupName;
	}

	public void setTriggerGroupName(String triggerGroupName) {
		this.triggerGroupName = triggerGroupName;
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

	public boolean isDynamicAdd() {
		return isDynamicAdd;
	}

	public void setDynamicAdd(boolean isDynamicAdd) {
		this.isDynamicAdd = isDynamicAdd;
	}

	@Override
	public String toString() {
		return "DynamicActionDO [targetObject=" + targetObject + ", targetMethod=" + targetMethod + ", jobGroupName=" + jobGroupName + ", jobName=" + jobName + ", jobDesc=" + jobDesc
				+ ", triggerName=" + triggerName + ", triggerGroupName=" + triggerGroupName + ", startTime=" + startTime + ", endTime=" + endTime + ", startDelay=" + startDelay + ", repeatInterval="
				+ repeatInterval + ", repeatCount=" + repeatCount + ", cronExpression=" + cronExpression + ", triggerType=" + triggerType + ", beanName=" + beanName + ", action=" + action
				+ ", actionNo=" + actionNo + ", paramList=" + paramList + ", ipList=" + ipList + ", jobEnvironmentType=" + jobEnvironmentType + ", onlyRunOnce=" + onlyRunOnce + ", isDynamicAdd="
				+ isDynamicAdd + ", jobTimeType=" + jobTimeType + ", simpleTriggerMap=" + simpleTriggerMap + "]";
	}

}
