/*
 * Copyright 2002-2012 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.elsa.ttm.schedule;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.zookeeper.KeeperException;
import org.quartz.CronTrigger;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleTrigger;
import org.quartz.StatefulJob;
import org.quartz.Trigger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.ArgumentConvertingMethodInvoker;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.util.MethodInvoker;
import org.springframework.util.ReflectionUtils;

import com.elsa.ttm.constant.TaskConstant;
import com.elsa.ttm.domain.AllTasksDO;
import com.elsa.ttm.domain.DynamicActionDO;
import com.elsa.ttm.domain.TriggerLogDO;
import com.elsa.ttm.manager.ClientManager;
import com.elsa.ttm.manager.DBManager;
import com.elsa.ttm.manager.ScheduleManager;
import com.elsa.ttm.util.ScheduleUtil;

/**
 * {@link org.springframework.beans.factory.FactoryBean} that exposes a
 * {@link org.quartz.JobDetail} object which delegates job execution to a
 * specified (static or non-static) method. Avoids the need for implementing a
 * one-line Quartz Job that just invokes an existing service method on a
 * Spring-managed target bean.
 *
 * <p>
 * Inherits common configuration properties from the {@link MethodInvoker} base
 * class, such as {@link #setTargetObject "targetObject"} and
 * {@link #setTargetMethod "targetMethod"}, adding support for lookup of the
 * target bean by name through the {@link #setTargetBeanName "targetBeanName"}
 * property (as alternative to specifying a "targetObject" directly, allowing
 * for non-singleton target objects).
 *
 * <p>
 * Supports both concurrently running jobs and non-currently running jobs
 * through the "concurrent" property. Jobs created by this
 * MethodInvokingJobDetailFactoryBean are by default volatile and durable
 * (according to Quartz terminology).
 *
 * <p>
 * <b>NOTE: JobDetails created via this FactoryBean are <i>not</i> serializable
 * and thus not suitable for persistent job stores.</b> You need to implement
 * your own Quartz Job as a thin wrapper for each case where you want a
 * persistent job to delegate to a specific service method.
 *
 * <p>
 * Compatible with Quartz 1.5+ as well as Quartz 1.8.6, as of Spring 3.1. edit
 * by longhaisheng
 *
 * @author Juergen Hoeller
 * @author Alef Arendsen
 * @since 18.02.2004
 * @see #setTargetBeanName
 * @see #setTargetObject
 * @see #setTargetMethod
 * @see #setConcurrent
 * @author longhaisheng
 * 
 */
public class MethodInvokingJobDetailFactoryBean extends ArgumentConvertingMethodInvoker implements BeanNameAware, BeanClassLoaderAware, BeanFactoryAware, InitializingBean {

	private static Class<?> jobDetailImplClass;

	private static Method setResultMethod;

	static {
		try {
			jobDetailImplClass = Class.forName("org.quartz.impl.JobDetailImpl");
		} catch (ClassNotFoundException ex) {
			jobDetailImplClass = null;
		}
		try {
			Class jobExecutionContextClass = QuartzJobBean.class.getClassLoader().loadClass("org.quartz.JobExecutionContext");
			setResultMethod = jobExecutionContextClass.getMethod("setResult", Object.class);
		} catch (Exception ex) {
			throw new IllegalStateException("Incompatible Quartz API: " + ex);
		}
	}

	private String name;

	private String group = Scheduler.DEFAULT_GROUP;

	private boolean concurrent = true;

	private String targetBeanName;

	private String[] jobListenerNames;

	private String beanName;

	private ClassLoader beanClassLoader = ClassUtils.getDefaultClassLoader();

	private BeanFactory beanFactory;

	private JobDetail jobDetail;

	private String cronExpression;

	private String jobDesc;

	private Map<String, Object> simpleTriggerMap = new HashMap<String, Object>();

	private volatile boolean dynamicAddJob = false;

	public final static String SIMPLE_TRIGGER_STARTTIME = "startTime";

	public final static String SIMPLE_TRIGGER_ENDTIME = "endTime";

	public final static String SIMPLE_TRIGGER_REPEATCOUNT = "repeatCount";

	public final static String SIMPLE_TRIGGER_REPEATINTERVAL = "repeatInterval";

	public final static String SIMPLE_TRIGGER_START_DELAY = "startDelay";

	private static final Log logger = LogFactory.getLog(MethodInvokingJobDetailFactoryBean.class);

	private Map<String, List<Long>> customParams;

	public Map<String, List<Long>> getCustomParams() {
		return customParams;
	}

	public void setCustomParams(Map<String, List<Long>> customParams) {
		this.customParams = customParams;
	}

	public boolean isDynamicAddJob() {
		return dynamicAddJob;
	}

	public void setDynamicAddJob(boolean dynamicAddJob) {
		this.dynamicAddJob = dynamicAddJob;
	}

	@Autowired
	private ClientManager clientManager;

	@Autowired
	private ScheduleManager scheduleManager;

	@Autowired
	private DBManager dbManager;

	public ClientManager getClientManager() {
		return clientManager;
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

	public Map<String, Object> getSimpleTriggerMap() {
		return simpleTriggerMap;
	}

	public void setSimpleTriggerMap(Map<String, Object> simpleTriggerMap) {
		this.simpleTriggerMap = simpleTriggerMap;
	}

	// /**
	// * Set the name of the job.
	// * <p>
	// * Default is the bean name of this FactoryBean.
	// *
	// * @see org.quartz.JobDetail#setName
	// */
	// public void setName(String name) {
	// this.name = name;
	// }

	/**
	 * Set the group of the job.
	 * <p>
	 * Default is the default group of the Scheduler.
	 * 
	 * @see org.quartz.JobDetail#setGroup
	 * @see org.quartz.Scheduler#DEFAULT_GROUP
	 */
	public void setGroup(String group) {
		this.group = group;
	}

	/**
	 * Specify whether or not multiple jobs should be run in a concurrent
	 * fashion. The behavior when one does not want concurrent jobs to be
	 * executed is realized through adding the {@link StatefulJob} interface.
	 * More information on stateful versus stateless jobs can be found <a href=
	 * "http://www.quartz-scheduler.org/documentation/quartz-2.1.x/tutorials/tutorial-lesson-03"
	 * >here</a>.
	 * <p>
	 * The default setting is to run jobs concurrently.
	 */
	public void setConcurrent(boolean concurrent) {
		this.concurrent = concurrent;
	}

	/**
	 * Set the name of the target bean in the Spring BeanFactory.
	 * <p>
	 * This is an alternative to specifying {@link #setTargetObject
	 * "targetObject"}, allowing for non-singleton beans to be invoked. Note
	 * that specified "targetObject" and {@link #setTargetClass "targetClass"}
	 * values will override the corresponding effect of this "targetBeanName"
	 * setting (i.e. statically pre-define the bean type or even the bean
	 * object).
	 */
	public void setTargetBeanName(String targetBeanName) {
		this.targetBeanName = targetBeanName;
	}

	/**
	 * Set a list of JobListener names for this job, referring to non-global
	 * JobListeners registered with the Scheduler.
	 * <p>
	 * A JobListener name always refers to the name returned by the JobListener
	 * implementation.
	 * 
	 * @see SchedulerFactoryBean#setJobListeners
	 * @see org.quartz.JobListener#getName
	 */
	public void setJobListenerNames(String[] names) {
		this.jobListenerNames = names;
	}

	public void setBeanName(String beanName) {
		this.beanName = beanName;
	}

	public void setBeanClassLoader(ClassLoader classLoader) {
		this.beanClassLoader = classLoader;
	}

	public void setBeanFactory(BeanFactory beanFactory) {
		this.beanFactory = beanFactory;
	}

	@Override
	protected Class resolveClassName(String className) throws ClassNotFoundException {
		return ClassUtils.forName(className, this.beanClassLoader);
	}

	public void afterPropertiesSet() throws ClassNotFoundException, NoSuchMethodException, SchedulerException, ParseException {
		prepare();

		// Use specific name if given, else fall back to bean name.
		String name = (this.name != null ? this.name : this.beanName);

		Class jobClass = getMethodInvokingJobClass();

		// Build JobDetail instance.
		if (jobDetailImplClass != null) {
			// Using Quartz 2.0 JobDetailImpl class...
			this.jobDetail = (JobDetail) BeanUtils.instantiate(jobDetailImplClass);
			BeanWrapper bw = PropertyAccessorFactory.forBeanPropertyAccess(this.jobDetail);
			bw.setPropertyValue("name", name);
			bw.setPropertyValue("group", this.group);
			bw.setPropertyValue("jobClass", jobClass);
			bw.setPropertyValue("durability", true);
			((JobDataMap) bw.getPropertyValue("jobDataMap")).put("methodInvoker", this);
			((JobDataMap) bw.getPropertyValue("jobDataMap")).put("clientManager", clientManager);
			((JobDataMap) bw.getPropertyValue("jobDataMap")).put("customParams", customParams);
		} else {
			// Using Quartz 1.x JobDetail class...
			addJob(name, true);
		}

		// Register job listener names.
		if (this.jobListenerNames != null) {
			for (String jobListenerName : this.jobListenerNames) {
				if (jobDetailImplClass != null) {
					throw new IllegalStateException("Non-global JobListeners not supported on Quartz 2 - " + "manually register a Matcher against the Quartz ListenerManager instead");
				}
				this.jobDetail.addJobListener(jobListenerName);
			}
		}

		postProcessJobDetail(this.jobDetail);
	}

	public void addJob(String name, boolean is_app_start) throws ParseException, SchedulerException {
		String job_group_name = ScheduleManager.JOB_GROUP_NAME;
		Class jobClass = getMethodInvokingJobClass();
		this.jobDetail = new JobDetail(name, job_group_name, jobClass);
		this.jobDetail.setVolatility(true);
		this.jobDetail.setDurability(true);
		this.jobDetail.getJobDataMap().put("methodInvoker", this);
		this.jobDetail.getJobDataMap().put("clientManager", clientManager);
		this.jobDetail.getJobDataMap().put("customParams", customParams);

		if (scheduleManager.getScheduler().getJobDetail(name, job_group_name) != null) {
			scheduleManager.getScheduler().deleteJob(name, job_group_name);
		}

		boolean need_add_task = true;
		boolean existsJob = false;
		String jobRunStatus = TaskConstant.TASK_STATUS_NORMAL;
		AllTasksDO oneTasksDO = null;
		try {
			existsJob = clientManager.existsJob(job_group_name, name);
			oneTasksDO = clientManager.getJobRunStatus(job_group_name, name);
			if (null != oneTasksDO) {
				jobRunStatus = oneTasksDO.getRunStatus();// 考虑db中状态 db中非正常状态需考虑
			}
			// if (is_app_start && existsJob &&
			// jobRunStatus.equals(TaskConstant.TASK_STATUS_REMOVE)) {//
			// 启动时移除的任务不需要加入,不加入的话，没有设置监听
			// need_add_task = false;
			// }
		} catch (KeeperException e) {
			logger.error(e.getMessage(), e);
		} catch (InterruptedException e) {
			logger.error(e.getMessage(), e);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		if (!need_add_task) {
			return;
		}

		Map<String, String> task_map = dbManager.get_one_task(job_group_name, name);
		// if (null != task_map) {
		// jobRunStatus = task_map.get("run_status");
		// if (task_map.get("last_action_result").equals("opreator_start") &&
		// jobRunStatus.equals("remove")) {// TODO 需移除
		// return;
		// }
		// }
		if (null != task_map && null != task_map.get("only_run_once")) {
			if (Integer.valueOf(task_map.get("only_run_once")) == 1 && task_map.get("last_action_result").equals("opreator_success")) {
				return;
			}
		}
		AllTasksDO taskDO = new AllTasksDO();
		if (null != task_map) {
			taskDO.setJobEnvironmentType(task_map.get("job_environment_type"));
			this.jobDetail.getJobDataMap().put("job_environment_type", task_map.get("job_environment_type"));
		} else {
			this.jobDetail.getJobDataMap().put("job_environment_type", DynamicActionDO.JOB_ENVIRONMENT_TYPE_SINGLE);
			taskDO.setJobEnvironmentType(DynamicActionDO.JOB_ENVIRONMENT_TYPE_SINGLE);
		}

		taskDO.setRunStatus(TaskConstant.TASK_STATUS_NORMAL);
		taskDO.setCronExpression(cronExpression);

		taskDO.setJobName(name);
		taskDO.setJobGroupName(job_group_name);
		taskDO.setTriggerName(name);

		taskDO.setTriggerGroupName(ScheduleManager.TRIGGER_GROUP_NAME);

		if (null != jobDesc && !"".equals(jobDesc)) {
			taskDO.setJobMemo(jobDesc);
		} else {
			taskDO.setJobMemo(name);
		}

		if (existsJob) {
			if (null != task_map && !task_map.isEmpty()) {
				if (task_map.get("job_time_type").equals(DynamicActionDO.JOB_TIME_TYPE_CRON)) {
					String db_cron_expression = task_map.get("cron_expression");
					taskDO.setCronExpression(db_cron_expression);
				}
			}
		}

		if (null != simpleTriggerMap && !simpleTriggerMap.isEmpty()) {
			SimpleTrigger simpleTrigger = new SimpleTrigger(name, ScheduleManager.TRIGGER_GROUP_NAME);
			if (null != simpleTriggerMap.get(SIMPLE_TRIGGER_START_DELAY)) {
				Long start_delay = Long.valueOf(simpleTriggerMap.get(SIMPLE_TRIGGER_START_DELAY).toString());
				simpleTrigger.setStartTime(new Date(System.currentTimeMillis() + start_delay));
			}
			if (null != simpleTriggerMap.get(SIMPLE_TRIGGER_REPEATCOUNT)) {
				int repeatCount = Integer.valueOf(simpleTriggerMap.get(SIMPLE_TRIGGER_REPEATCOUNT).toString());
				simpleTrigger.setRepeatCount(repeatCount);

			}
			if (null != simpleTriggerMap.get(SIMPLE_TRIGGER_REPEATINTERVAL)) {
				Long repeatinterval = Long.valueOf(simpleTriggerMap.get(SIMPLE_TRIGGER_REPEATINTERVAL).toString());
				simpleTrigger.setRepeatInterval(repeatinterval);
			}
			if (null != simpleTriggerMap.get(SIMPLE_TRIGGER_ENDTIME)) {
				Date end_time = (Date) simpleTriggerMap.get(SIMPLE_TRIGGER_ENDTIME);
				simpleTrigger.setEndTime(end_time);
			}
			clientManager.create_jobGroupName_jobName_path(job_group_name, name);
			scheduleManager.getScheduler().scheduleJob(jobDetail, simpleTrigger);
			if (jobRunStatus.equals(TaskConstant.TASK_STATUS_PAUSE)) {
				scheduleManager.pauseJob(name);
			}
			if (is_app_start && jobRunStatus.equals(TaskConstant.TASK_STATUS_REMOVE)) {
				scheduleManager.removeJob(name);
			}
			taskDO.setJobTimeType(DynamicActionDO.JOB_TIME_TYPE_SIMPLE);
		} else {
			taskDO.setJobTimeType(DynamicActionDO.JOB_TIME_TYPE_CRON);
			CronTrigger crontrigger = new CronTrigger(name, ScheduleManager.TRIGGER_GROUP_NAME);
			crontrigger.setCronExpression(taskDO.getCronExpression());
			scheduleManager.getScheduler().scheduleJob(jobDetail, crontrigger);
			if (jobRunStatus.equals(TaskConstant.TASK_STATUS_PAUSE)) {
				scheduleManager.pauseJob(name);
			}
			if (is_app_start && jobRunStatus.equals(TaskConstant.TASK_STATUS_REMOVE)) {
				scheduleManager.removeJob(name);
			}
		}

		try {
			if (need_add_task && taskDO.getJobTimeType().equals(DynamicActionDO.JOB_TIME_TYPE_CRON)) {
				if (!this.isDynamicAddJob()) {// 动态job添加到task表中，应在服务端，此客户端不做操作
					dbManager.save_task(taskDO);
				}
				logger.info("############################################################################################################addTaskPath taskDO=>" + taskDO.toString());
				logger.info("############################################################################################################is_app_start=>" + is_app_start);
				clientManager.addTaskPath(taskDO);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	private Class getMethodInvokingJobClass() {
		return (this.concurrent ? MethodInvokingJob.class : StatefulMethodInvokingJob.class);
	}

	/**
	 * Callback for post-processing the JobDetail to be exposed by this
	 * FactoryBean.
	 * <p>
	 * The default implementation is empty. Can be overridden in subclasses.
	 * 
	 * @param jobDetail
	 *            the JobDetail prepared by this FactoryBean
	 */
	protected void postProcessJobDetail(JobDetail jobDetail) {
	}

	/**
	 * Overridden to support the {@link #setTargetBeanName "targetBeanName"}
	 * feature.
	 */
	@Override
	public Class getTargetClass() {
		Class targetClass = super.getTargetClass();
		if (targetClass == null && this.targetBeanName != null) {
			Assert.state(this.beanFactory != null, "BeanFactory must be set when using 'targetBeanName'");
			targetClass = this.beanFactory.getType(this.targetBeanName);
		}
		return targetClass;
	}

	/**
	 * Overridden to support the {@link #setTargetBeanName "targetBeanName"}
	 * feature.
	 */
	@Override
	public Object getTargetObject() {
		Object targetObject = super.getTargetObject();
		if (targetObject == null && this.targetBeanName != null) {
			Assert.state(this.beanFactory != null, "BeanFactory must be set when using 'targetBeanName'");
			targetObject = this.beanFactory.getBean(this.targetBeanName);
		}
		return targetObject;
	}

	// public JobDetail getObject() {
	// return this.jobDetail;
	// }
	//
	// public Class<? extends JobDetail> getObjectType() {
	// return (this.jobDetail != null ? this.jobDetail.getClass() :
	// JobDetail.class);
	// }
	// public boolean isSingleton() {
	// return true;
	// }

	/**
	 * Quartz Job implementation that invokes a specified method. Automatically
	 * applied by MethodInvokingJobDetailFactoryBean.
	 */
	public static class MethodInvokingJob extends QuartzJobBean {

		protected static final Log logger = LogFactory.getLog(MethodInvokingJob.class);

		private MethodInvoker methodInvoker;

		/**
		 * Set the MethodInvoker to use.
		 */
		public void setMethodInvoker(MethodInvoker methodInvoker) {
			this.methodInvoker = methodInvoker;
		}

		/**
		 * Invoke the method via the MethodInvoker.
		 */
		@Override
		protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
			try {
				List<Long> params = new ArrayList<Long>();
				if (this.methodInvoker.getTargetObject() instanceof IMultiSchedule) {
					Map<String, List<Long>> customParams = (Map<String, List<Long>>) context.getMergedJobDataMap().get("customParams");
					if (null != customParams && !customParams.isEmpty()) {
						params = customParams.get(ScheduleUtil.getLocalIP());
						Object[] args = new Object[1];
						args[0] = params;
						this.methodInvoker.setArguments(args);
					}
				}
				long startTime = System.currentTimeMillis();
				ReflectionUtils.invokeMethod(setResultMethod, context, this.methodInvoker.invoke());
				Object message = context.getResult();
				if (null != message) {
					message = ";method=>" + this.methodInvoker.getTargetMethod() + ";params=>" + params.toString() + ";result=>" + message;
				} else {
					message = ";method=>" + this.methodInvoker.getTargetMethod() + ";params=>" + params.toString();
				}
				long endTime = System.currentTimeMillis();

				TriggerLogDO log = new TriggerLogDO();
				String triggerName = context.getJobDetail().getName();
				Trigger trigger = context.getScheduler().getTrigger(triggerName, ScheduleManager.TRIGGER_GROUP_NAME);
				if (trigger instanceof CronTrigger) {
					CronTrigger cron_trigger = (CronTrigger) trigger;
					log.setCronExpression(cron_trigger.getCronExpression());
				} else {
					log.setCronExpression("");
				}
				log.setJobName(triggerName);
				log.setJobGroupName(ScheduleManager.JOB_GROUP_NAME);
				log.setStartTime(startTime);
				log.setEndTime(endTime);
				log.setRunStatus(1);
				log.setIp(ScheduleUtil.getLocalIP());
				log.setRunMemo("class=>" + this.methodInvoker.getTargetClass() + message + ";" + context.getJobDetail().getJobDataMap().get("lock_msg"));
				ClientManager clientManager = (ClientManager) context.getMergedJobDataMap().get("clientManager");
				clientManager.getDbManager().save_trigger_log(log);
			} catch (InvocationTargetException ex) {
				if (ex.getTargetException() instanceof JobExecutionException) {
					// -> JobExecutionException, to be logged at info level
					// by
					// Quartz
					throw (JobExecutionException) ex.getTargetException();
				} else {
					// -> "unhandled exception", to be logged at error level
					// by
					// Quartz
					throw new JobMethodInvocationFailedException(this.methodInvoker, ex.getTargetException());
				}
			} catch (Exception ex) {
				// -> "unhandled exception", to be logged at error level by
				// Quartz
				throw new JobMethodInvocationFailedException(this.methodInvoker, ex);
			}
		}
	}

	/**
	 * Extension of the MethodInvokingJob, implementing the StatefulJob
	 * interface. Quartz checks whether or not jobs are stateful and if so,
	 * won't let jobs interfere with each other.
	 */
	public static class StatefulMethodInvokingJob extends MethodInvokingJob implements StatefulJob {
		// No implementation, just an addition of the tag interface StatefulJob
		// in order to allow stateful method invoking jobs.
	}

}
