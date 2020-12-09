package com.elsa.ttm.controller;
//package com.elsa.ttm.controller;
//
//import java.util.List;
//import java.util.Map;
//
//import javax.servlet.http.HttpServletRequest;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.ModelMap;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//
//import com.elsa.ttm.manager.DBManager;
//import com.elsa.ttm.manager.ScheduleManager;
//import com.elsa.ttm.quartz.domain.DynamicJob;
//import com.elsa.ttm.quartz.domain.JobDO;
//
//@Controller
//public class CopyOfQuartzController {
//
//	private Logger logger = LoggerFactory.getLogger(this.getClass());
//
//	@Autowired
//	ScheduleManager scheduleManager;
//	
//	@Autowired
//	DBManager dbManager;
//
//	@RequestMapping(value = "/quartz", method = RequestMethod.GET)
//	public String list(final ModelMap model, HttpServletRequest request) {
//		Long startTime = System.currentTimeMillis();
//		Long endTime = System.currentTimeMillis();
//		List<JobDO> list = ScheduleManager.getJobList();
//		List<JobDO> delList = ScheduleManager.getDelList();
//		
//		List<Map<String, String>> task_list=dbManager.query_job_all_tasks(null);
//		
//		model.addAttribute("task_list", task_list);
//		
//		model.addAttribute("delList", delList);
//		model.addAttribute("cacheList", list);
//		model.addAttribute("consumeTime", "start=>" + startTime + "end=>" + endTime);
//		return "task_list";
//	}
//
//	@RequestMapping(value = "/quartz/update/{jobName}", method = RequestMethod.POST)
//	public String update(final ModelMap model, @PathVariable String jobName, HttpServletRequest request) {
//		Long startTime = System.currentTimeMillis();
//		Long endTime = System.currentTimeMillis();
//		String cronExpression = request.getParameter("cronExpression").trim();
//		scheduleManager.updateJobCronExpression(jobName, cronExpression);
//		List<JobDO> list = scheduleManager.getJobList();
//		model.addAttribute("cacheList", list);
//		model.addAttribute("consumeTime", "start=>" + startTime + "end=>" + endTime);
//		return "quartz";
//	}
//
//	@RequestMapping(value = "/quartz/delete/{jobName}", method = RequestMethod.GET)
//	public String remove(final ModelMap model, @PathVariable String jobName, HttpServletRequest request) {
//		scheduleManager.removeJob(jobName);
//		List<JobDO> list = ScheduleManager.getJobList();
//		List<JobDO> delList = ScheduleManager.getDelList();
//		model.addAttribute("delList", delList);
//		model.addAttribute("cacheList", list);
//		return "quartz";
//	}
//
//	@RequestMapping(value = "/quartz/addRemoveJob/{jobName}", method = RequestMethod.POST)
//	public String addRemoveJob(final ModelMap model, @PathVariable String jobName, HttpServletRequest request) {
//		scheduleManager.addRemoveJob(jobName);
//		List<JobDO> list = ScheduleManager.getJobList();
//		List<JobDO> delList = ScheduleManager.getDelList();
//		model.addAttribute("delList", delList);
//		model.addAttribute("cacheList", list);
//		return "quartz";
//	}
//
//	@RequestMapping(value = "/quartz/pause/{jobName}", method = RequestMethod.GET)
//	public String pause(final ModelMap model, @PathVariable String jobName, HttpServletRequest request) {
//		Long startTime = System.currentTimeMillis();
//		Long endTime = System.currentTimeMillis();
//		scheduleManager.pauseJob(jobName);
//		List<JobDO> list = scheduleManager.getJobList();
//		model.addAttribute("cacheList", list);
//		model.addAttribute("consumeTime", "start=>" + startTime + "end=>" + endTime);
//		return "quartz";
//	}
//
//	@RequestMapping(value = "/quartz/resume/{jobName}", method = RequestMethod.GET)
//	public String resume(final ModelMap model, @PathVariable String jobName, HttpServletRequest request) {
//		Long startTime = System.currentTimeMillis();
//
//		Long endTime = System.currentTimeMillis();
//		scheduleManager.resumeJob(jobName);
//		List<JobDO> list = scheduleManager.getJobList();
//		model.addAttribute("cacheList", list);
//		model.addAttribute("consumeTime", "start=>" + startTime + "end=>" + endTime);
//		return "quartz";
//	}
//
//	@RequestMapping(value = "/quartz/trigger/{jobName}", method = RequestMethod.GET)
//	public String trigger(final ModelMap model, @PathVariable String jobName, HttpServletRequest request) {
//		Long startTime = System.currentTimeMillis();
//		scheduleManager.triggerJob(jobName);
//		List<JobDO> list = scheduleManager.getJobList();
//		model.addAttribute("cacheList", list);
//		Long endTime = System.currentTimeMillis();
//		model.addAttribute("consumeTime", "start=>" + startTime + "end=>" + endTime);
//		return "redirect:/quartz";
//	}
//
//	@RequestMapping(value = "/quartz/dynamicStore", method = RequestMethod.POST)
//	public String dynamicStore(final ModelMap model, HttpServletRequest request) {
//
//		String cronExpression = request.getParameter("cronExpression");
//		String targetObject = request.getParameter("targetObject");
//		String targetMethod = request.getParameter("targetMethod");
//		String jobDesc = request.getParameter("jobDesc");
//		String triggerType = request.getParameter("triggerType");
//		String startDelay = request.getParameter("startDelay");
//
//		DynamicJob job = new DynamicJob();
//		
//		job.setTriggerType(triggerType);
//		job.setJobDesc(jobDesc);
//		job.setTargetObject(targetObject);
//		job.setTargetMethod(targetMethod);
//		
//		if (triggerType.equals(DynamicJob.SCHEDULE_TYPE_SIMPLE_TRIGGER)) {
//			job.setStartDelay(Long.valueOf(startDelay));
//			job.setBeanName(DynamicJob.SIMPLE_BEAN_NAME);
//		}
//		if (triggerType.equals(DynamicJob.SCHEDULE_TYPE_CRON_TRIGGER)) {
//			job.setCronExpression(cronExpression);
//			job.setBeanName(DynamicJob.CRON_BEAN_NAME);
//		}
//		scheduleManager.dynamicAddJob(job);
//		
//		List<JobDO> list = ScheduleManager.getJobList();
//		List<JobDO> delList = ScheduleManager.getDelList();
//		model.addAttribute("delList", delList);
//		model.addAttribute("cacheList", list);
//		return "quartz";
//	}
//
// }
