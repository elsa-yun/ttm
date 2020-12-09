package com.elsa.ttm.manager;

import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.CronTrigger;
import org.quartz.JobExecutionContext;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.util.ReflectionUtils;

import com.elsa.ttm.domain.DynamicActionDO;
import com.elsa.ttm.schedule.IMultiSchedule;
import com.elsa.ttm.schedule.MethodInvokingJobDetailFactoryBean;
import com.elsa.ttm.util.ScheduleUtil;

/**
 * @author longhaisheng
 *
 */
public class ScheduleManager implements DisposableBean, ApplicationContextAware {

	private static final Log logger = LogFactory.getLog(ScheduleManager.class);

	public static String JOB_GROUP_NAME = "job_group_name";

	public static String TRIGGER_GROUP_NAME = "trigger_group_name";

	private SchedulerFactory schedulerFactory;

	private static Map<Integer, String> triggerStatusMap = new HashMap<Integer, String>();

	private static Map<String, String> triggerStatusDescMap = new HashMap<String, String>();

	private ApplicationContext applicationContext;

	private Scheduler scheduler;

	private boolean waitForJobsToCompleteOnShutdown = false;

	public ScheduleManager(String appName) {
		init(appName);
	}

	public ScheduleManager(String appName, boolean waitForJobsToComplete) {
		init(appName);
		waitForJobsToCompleteOnShutdown = waitForJobsToComplete;
	}

	private void init(String appName) {
		JOB_GROUP_NAME = appName + "_" + JOB_GROUP_NAME;
		TRIGGER_GROUP_NAME = appName + "_" + TRIGGER_GROUP_NAME;

		triggerStatusMap.put(Trigger.STATE_NORMAL, "正常");
		triggerStatusMap.put(Trigger.STATE_PAUSED, "暂停");
		triggerStatusMap.put(Trigger.STATE_COMPLETE, "结束");
		triggerStatusMap.put(Trigger.STATE_ERROR, "错误");
		triggerStatusMap.put(Trigger.STATE_BLOCKED, "阻塞");
		triggerStatusMap.put(Trigger.STATE_NONE, "不存在");

		triggerStatusDescMap.put("normal", "正常");
		triggerStatusDescMap.put("pause", "暂停");
		triggerStatusDescMap.put("complete", "结束");
		triggerStatusDescMap.put("error", "错误");
		triggerStatusDescMap.put("blocked", "阻塞");
		triggerStatusDescMap.put("none", "不存在");
		triggerStatusDescMap.put("remove", "已移除");

		schedulerFactory = new StdSchedulerFactory();
		try {
			scheduler = schedulerFactory.getScheduler();
		} catch (SchedulerException e) {
			logger.error(e.getMessage(), e);
		}

	}

	public String getJobGroupName() {
		return JOB_GROUP_NAME;
	}

	public final Scheduler getScheduler() {
		return scheduler;
	}

	public boolean updateJobCronExpression(String jobName, String cronExpression) {
		boolean suc = false;
		try {
			CronTrigger trigger = (CronTrigger) scheduler.getTrigger(jobName, TRIGGER_GROUP_NAME);
			if (trigger == null) {
				return suc;
			}
			String oldTime = trigger.getCronExpression();
			if (!oldTime.equalsIgnoreCase(cronExpression)) {
				trigger.setCronExpression(cronExpression);
				scheduler.rescheduleJob(jobName, TRIGGER_GROUP_NAME, trigger);
			}
			suc = true;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return suc;
	}

	public boolean removeJob(String jobName) {
		boolean success = false;
		try {
			int triggerState = scheduler.getTriggerState(jobName, TRIGGER_GROUP_NAME);
			if (triggerState == Trigger.STATE_NONE) {
				return true;
			}

			scheduler.pauseTrigger(jobName, TRIGGER_GROUP_NAME);
			scheduler.unscheduleJob(jobName, TRIGGER_GROUP_NAME);
			scheduler.deleteJob(jobName, JOB_GROUP_NAME);

			success = true;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return success;
	}

	public boolean triggerJob(String jobName) {
		boolean suc = false;
		try {
			String[] jobNames = scheduler.getJobNames(JOB_GROUP_NAME);
			if (jobNames.length > 0) {
				for (String str : jobNames) {
					if (str.equals(jobName)) {
						scheduler.triggerJob(jobName, JOB_GROUP_NAME);
					}
				}
			}
			suc = true;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return suc;
	}

	public boolean pauseJob(String jobName) {
		boolean success = false;
		try {
			int triggerState = scheduler.getTriggerState(jobName, TRIGGER_GROUP_NAME);
			if (triggerState == Trigger.STATE_PAUSED) {
				return true;
			}
			if (triggerState == Trigger.STATE_BLOCKED) {
				return false;
			}
			scheduler.pauseTrigger(jobName, TRIGGER_GROUP_NAME);
			scheduler.pauseJob(jobName, JOB_GROUP_NAME);
			success = true;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return success;
	}

	public boolean resumeJob(String jobName) {
		boolean success = false;
		try {
			int triggerState = scheduler.getTriggerState(jobName, TRIGGER_GROUP_NAME);
			if (triggerState == Trigger.STATE_NORMAL) {
				return true;
			}
			scheduler.resumeTrigger(jobName, TRIGGER_GROUP_NAME);
			scheduler.resumeJob(jobName, JOB_GROUP_NAME);
			success = true;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return success;
	}

	public void getJobStatus(String jobName) {
		try {
			scheduler.getTriggerState(jobName, TRIGGER_GROUP_NAME);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public List<JobExecutionContext> getCurrentlyExecutingJobs(String jobName) {
		List<JobExecutionContext> list = new ArrayList<JobExecutionContext>();
		try {
			list = scheduler.getCurrentlyExecutingJobs();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		if (!list.isEmpty()) {
			for (JobExecutionContext jobExecutionContext : list) {
				jobExecutionContext.getJobDetail().getName();
			}
		}
		return list;
	}

	public void start() {
		try {
			if (!scheduler.isShutdown()) {
				scheduler.start();
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	private void shutdown(boolean waitFor) {
		try {
			scheduler.shutdown(waitFor);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	public void destroy() throws Exception {
		this.shutdown(waitForJobsToCompleteOnShutdown);
	}

	public String get_job_status(int triggerState) {
		return triggerStatusMap.get(triggerState);
	}

	public String get_job_status_desc(String triggerState) {
		return triggerStatusDescMap.get(triggerState);
	}

	public boolean addRemoveJob(String jobBeanName) {
		boolean suc = false;
		com.elsa.ttm.schedule.MethodInvokingJobDetailFactoryBean bean = applicationContext.getBean(jobBeanName, com.elsa.ttm.schedule.MethodInvokingJobDetailFactoryBean.class);
		try {
			bean.addJob(jobBeanName, false);
			suc = true;
		} catch (ParseException e) {
			logger.error(e.getMessage(), e);
		} catch (SchedulerException e) {
			logger.error(e.getMessage(), e);
		}
		return suc;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

	public ApplicationContext getApplicationContext() {
		return this.applicationContext;
	}

	public boolean dynamicAddJob(DynamicActionDO job) {

		ConfigurableApplicationContext configurableApplicationContext = (ConfigurableApplicationContext) applicationContext;
		DefaultListableBeanFactory defaultListableBeanFactory = (DefaultListableBeanFactory) configurableApplicationContext.getBeanFactory();
		BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(com.elsa.ttm.schedule.MethodInvokingJobDetailFactoryBean.class);

		Object bean = null;
		try {

			bean = configurableApplicationContext.getBean(job.getTargetObject());
			boolean findMethod = false;
			int argCount = 0;
			if (null != job.getParamList() && job.getParamList().size() > 0) {
				argCount = 1;
			}
			Method[] candidates = ReflectionUtils.getAllDeclaredMethods(bean.getClass());

			for (Method candidate : candidates) {
				if (candidate.getName().equals(job.getTargetMethod())) {
					Class[] paramTypes = candidate.getParameterTypes();
					if (paramTypes.length == argCount) {
						findMethod = true;
						logger.info("=============bean method find==============" + job.getTargetMethod());
						break;
					}
				}
			}

			if (findMethod == false) {
				logger.info("=============bean find not method==============" + job.getTargetMethod());
				return false;
			}

			if (job.getJobEnvironmentType().equals(DynamicActionDO.JOB_ENVIRONMENT_TYPE_DISTRIBUTE)) {
				if (!(bean instanceof IMultiSchedule)) {
					return false;
				} else if (null == job.getParamList() || job.getParamList().isEmpty()) {
					return false;
				} else {
					logger.info("=============bean instanceof IMultiSchedule==============");
				}
			}
		} catch (Exception e) {
			logger.error("dynamicAddJob bean =>" + e.getMessage(), e);
			bean = null;
		}
		if (null == bean) {
			return false;
		}
		beanDefinitionBuilder.addPropertyReference("targetObject", job.getTargetObject());
		beanDefinitionBuilder.addPropertyValue("targetMethod", job.getTargetMethod());
		beanDefinitionBuilder.addPropertyValue("jobDesc", job.getJobDesc());
		beanDefinitionBuilder.addPropertyValue("dynamicAddJob", true);
		if (job.getJobEnvironmentType().equals(DynamicActionDO.JOB_ENVIRONMENT_TYPE_DISTRIBUTE)) {
			beanDefinitionBuilder.addPropertyValue("customParams", asignIpParams(job));
		}

		/********************************** SimpleTrigger 以下配置任务只执行一次(当前时间一秒后) **************************************/
		if (job.getTriggerType().equals(DynamicActionDO.SCHEDULE_TYPE_SIMPLE_TRIGGER)) {
			Map<String, Object> simpleTriggerMap = new HashMap<String, Object>();
			simpleTriggerMap.put(MethodInvokingJobDetailFactoryBean.SIMPLE_TRIGGER_START_DELAY, job.getStartDelay());
			simpleTriggerMap.put(MethodInvokingJobDetailFactoryBean.SIMPLE_TRIGGER_REPEATINTERVAL, job.getRepeatInterval());
			simpleTriggerMap.put(MethodInvokingJobDetailFactoryBean.SIMPLE_TRIGGER_REPEATCOUNT, job.getRepeatCount());
			beanDefinitionBuilder.addPropertyValue("simpleTriggerMap", simpleTriggerMap);
		}
		/********************************** SimpleTrigger **************************************/

		/********************************** CronTrigger **************************************/
		if (job.getTriggerType().equals(DynamicActionDO.SCHEDULE_TYPE_CRON_TRIGGER)) {
			beanDefinitionBuilder.addPropertyValue("cronExpression", job.getCronExpression());
		}
		/********************************** CronTrigger **************************************/

		defaultListableBeanFactory.registerBeanDefinition(job.getBeanName(), beanDefinitionBuilder.getRawBeanDefinition());
		applicationContext.getBean(job.getBeanName(), com.elsa.ttm.schedule.MethodInvokingJobDetailFactoryBean.class);
		return true;
	}

	private static Map<String, List<Long>> asignIpParams(DynamicActionDO job) {
		List<String> ipList = job.getIpList();
		List<String> paramList = job.getParamList();
		int[] assignTaskNumber = ScheduleUtil.assignTaskNumber(ipList.size(), paramList.size(), 0);

		List<Long> longParams = new ArrayList<Long>();
		for (Iterator<String> iterator = paramList.iterator(); iterator.hasNext();) {
			String str = (String) iterator.next();
			longParams.add(Long.valueOf(str));
		}

		Map<String, List<Long>> returnMap = new HashMap<String, List<Long>>();
		int ip_size = ipList.size();
		int fetch = 0;
		for (int i = 0; i < ip_size; i++) {
			String ip = ipList.get(i);
			int num = assignTaskNumber[i];
			if (i == 0) {
				returnMap.put(ip, longParams.subList(fetch, num));
			} else {
				returnMap.put(ip, longParams.subList(fetch, num + fetch));
			}
			fetch = fetch + num;
		}

		return returnMap;
	}

	// public static void main(String args[]) {
	/*
	 * Map<String, Object> simpleTriggerMap = new HashMap<String, Object>();
	 * simpleTriggerMap
	 * .put(MethodInvokingJobDetailFactoryBean.SIMPLE_TRIGGER_START_DELAY,
	 * 1000L); simpleTriggerMap.put(MethodInvokingJobDetailFactoryBean.
	 * SIMPLE_TRIGGER_REPEATINTERVAL, 0L);
	 * simpleTriggerMap.put(MethodInvokingJobDetailFactoryBean
	 * .SIMPLE_TRIGGER_REPEATCOUNT, 0L);
	 * 
	 * ScheduleManager s=new ScheduleManager("saleOrder"); ClientManager c=new
	 * ClientManager(); String json = c.gson.toJson(simpleTriggerMap);
	 * System.out.println(json);
	 */
	// DynamicActionDO job = new DynamicActionDO();
	// List<String> ipList = new ArrayList<String>();
	// String[] ss = "10.10.10.165,10.10.10.216,10.10.10.217".split(",");
	// for (String str : ss) {
	// ipList.add(str);
	// }
	//
	// List<String> paramList = new ArrayList<String>();
	// String[] arguments = "10,20,30,40,23,40,50".split(",");
	// for (String str : arguments) {
	// paramList.add(str);
	// }
	// job.setIpList(ipList);
	// job.setParamList(paramList);
	// System.out.println(asignIpParams(job));

	// }

}
