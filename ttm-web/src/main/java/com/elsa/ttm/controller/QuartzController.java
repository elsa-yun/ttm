package com.elsa.ttm.controller;

import java.lang.reflect.Type;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.elsa.ttm.constant.OperatorLogConstant;
import com.elsa.ttm.constant.TaskConstant;
import com.elsa.ttm.constant.UsersConstant;
import com.elsa.ttm.core.manager.ServerManager;
import com.elsa.ttm.domain.ActionDO;
import com.elsa.ttm.domain.AllTasksDO;
import com.elsa.ttm.domain.DynamicActionDO;
import com.elsa.ttm.domain.OperatorLogDO;
import com.elsa.ttm.domain.ResultDO;
import com.elsa.ttm.domain.TriggerLogDO;
import com.elsa.ttm.manager.ClientManager;
import com.elsa.ttm.manager.DBManager;
import com.elsa.ttm.manager.ScheduleManager;
import com.elsa.ttm.schedule.MethodInvokingJobDetailFactoryBean;

@Controller
public class QuartzController extends BaseController {

	private Logger logger = LoggerFactory.getLogger(QuartzController.class);

	public static final int THREAD_SLEPP_TIME = 3000;

	public static final int DYNAMIC_ADD_THREAD_SLEPP_TIME = 4000;

	@Autowired
	ScheduleManager scheduleManager;

	@Autowired
	DBManager dbManager;

	@Autowired
	ServerManager serverManager;

	public Gson gson = new GsonBuilder().registerTypeAdapter(Timestamp.class, new TimestampTypeAdapters()).setDateFormat("yyyy-MM-dd HH:mm:ss").create();

	@RequestMapping(value = "/quartz", method = RequestMethod.GET)
	public String quartz(final ModelMap model, HttpServletRequest request) {
		HttpSession session = request.getSession();
		String user_id = (String) session.getAttribute(UsersConstant.SESSION_USER_ID);
		if (StringUtils.isBlank(user_id)) {
			return "redirect:/login";
		}

		Map<String, String> get_all_job_group_names = serverManager.get_all_job_group_names();
		String session_group_names = (String) session.getAttribute(UsersConstant.SESSION_GROUP_NAMES);
		String role = (String) session.getAttribute(UsersConstant.SESSION_ROLE);
		if (role.equals(UsersConstant.ROLE_TL) && StringUtils.isNotBlank(session_group_names)) {
			filter_group_names(get_all_job_group_names, session_group_names);
		}

		List<Map<String, String>> task_list = dbManager.query_job_all_tasks(null);
		if (null != task_list && !task_list.isEmpty()) {
			for (Map<String, String> map : task_list) {
				String has_operator = "no";
				if (null != get_all_job_group_names && !get_all_job_group_names.isEmpty()) {
					for (Map.Entry<String, String> m : get_all_job_group_names.entrySet()) {
						if (m.getKey().equals(map.get("job_group_name"))) {
							has_operator = "yes";
							break;
						}
					}
				}
				map.put("has_operator", has_operator);
			}
		}

		set_task_list(task_list);
		model.addAttribute("task_list", task_list);
		model.addAttribute("job_group_name_list", get_all_job_group_names);
		return "task_list";
	}

	private static void filter_group_names(Map<String, String> get_all_job_group_names, String session_group_names) {
		List<String> move_group_names = new ArrayList<String>();
		for (Map.Entry<String, String> entry : get_all_job_group_names.entrySet()) {
			if (!containsGroupName(session_group_names, entry.getKey())) {
				move_group_names.add(entry.getKey());
			}
		}
		for (String key : move_group_names) {
			get_all_job_group_names.remove(key);
		}
	}

	@RequestMapping(value = "/quartz/zk/{jobName}/{jobGroupName}", method = RequestMethod.GET)
	public String getOneTaskString(final ModelMap model, @PathVariable String jobName, @PathVariable String jobGroupName, HttpServletRequest request) {
		HttpSession session = request.getSession();
		String user_id = (String) session.getAttribute(UsersConstant.SESSION_USER_ID);
		if (StringUtils.isBlank(user_id)) {
			return "redirect:/login";
		}

		model.addAttribute("oneTaskString", serverManager.getOneTaskString(jobGroupName, jobName));
		return "task_str";
	}

	@RequestMapping(value = "/trigger/logs/{jobName}/{jobGroupName}", method = RequestMethod.GET)
	public String triggerLogs(final ModelMap model, @PathVariable String jobName, @PathVariable String jobGroupName, HttpServletRequest request) {
		HttpSession session = request.getSession();
		String user_id = (String) session.getAttribute(UsersConstant.SESSION_USER_ID);
		if (StringUtils.isBlank(user_id)) {
			return "redirect:/login";
		}

		TriggerLogDO log = new TriggerLogDO();
		log.setJobGroupName(jobGroupName);
		log.setJobName(jobName);
		List<Map<String, String>> trigger_logs = dbManager.query_trigger_logs(log);
		if (!trigger_logs.isEmpty()) {
			for (Iterator<Map<String, String>> iterator = trigger_logs.iterator(); iterator.hasNext();) {
				Map<String, String> map = (Map<String, String>) iterator.next();
				Long time = Long.valueOf(map.get("end_time")) - Long.valueOf(map.get("start_time"));
				map.put("consum_time", time.toString());

			}
		}
		model.addAttribute("trigger_logs", trigger_logs);
		return "trigger_logs";
	}

	@RequestMapping(value = "/opereator/logs/{jobName}/{jobGroupName}", method = RequestMethod.GET)
	public String opereatorLogs(final ModelMap model, @PathVariable String jobName, @PathVariable String jobGroupName, HttpServletRequest request) {
		HttpSession session = request.getSession();
		String user_id = (String) session.getAttribute(UsersConstant.SESSION_USER_ID);
		if (StringUtils.isBlank(user_id)) {
			return "redirect:/login";
		}
		List<Map<String, String>> opereator_logs = dbManager.query_all_operator_log(jobName, jobGroupName);
		model.addAttribute("opereator_logs", opereator_logs);
		return "opereator_logs";
	}

	private void set_task_list(List<Map<String, String>> task_list) {
		if (null != task_list && !task_list.isEmpty()) {
			for (Map<String, String> map : task_list) {

				map.put("status_desc", scheduleManager.get_job_status_desc(map.get("run_status")));
				String job_environment_type = map.get("job_environment_type");

				String environment_desc = "";
				if (job_environment_type.equals("single")) {
					environment_desc = "单节点任务";
				}
				if (job_environment_type.equals("distribute")) {
					environment_desc = "多节点任务";
				}
				map.put("environment_desc", environment_desc);

				String only_run_once = map.get("only_run_once");
				if (null != only_run_once && "1".equals(only_run_once)) {
					map.put("only_run_once_desc", "YES");
				} else {
					map.put("only_run_once_desc", "NO");
				}

			}
		}
	}

	@RequestMapping(value = "/dynamic/delete/ip", method = RequestMethod.POST)
	public String deleteIP(final ModelMap model, HttpServletRequest request) {
		HttpSession session = request.getSession();
		String user_id = (String) session.getAttribute(UsersConstant.SESSION_USER_ID);
		String role = (String) session.getAttribute(UsersConstant.SESSION_ROLE);
		if (StringUtils.isBlank(user_id)) {
			return "redirect:/login";
		}
		if (!role.equals(UsersConstant.ROLE_ADMIN)) {
			return "redirect:/quartz";
		}
		String job_group_name = request.getParameter("job_group_name").trim();
		String delete_ip = request.getParameter("delete_ip").trim();
		serverManager.deleteLocalGroupIP(job_group_name, delete_ip);
		return "redirect:/quartz";
	}

	@RequestMapping(value = "/dynamic/replace/ip", method = RequestMethod.POST)
	public String replaceIP(final ModelMap model, HttpServletRequest request) {
		HttpSession session = request.getSession();
		String user_id = (String) session.getAttribute(UsersConstant.SESSION_USER_ID);
		String role = (String) session.getAttribute(UsersConstant.SESSION_ROLE);
		if (StringUtils.isBlank(user_id)) {
			return "redirect:/login";
		}
		if (!role.equals(UsersConstant.ROLE_ADMIN)) {
			return "redirect:/quartz";
		}
		String job_name = request.getParameter("job_name").trim();
		String job_group_name = request.getParameter("job_group_name").trim();
		String source_ip = request.getParameter("source_ip").trim();
		String replace_ip = request.getParameter("replace_ip").trim();
		dbManager.update_task_ips(job_name, job_group_name, source_ip, replace_ip);
		return "redirect:/quartz";
	}

	@RequestMapping(value = "/quartz/update/cron", method = RequestMethod.GET)
	public @ResponseBody String updateCronExpression(final ModelMap model, HttpServletRequest request) {
		HttpSession session = request.getSession();
		String user_id = (String) session.getAttribute(UsersConstant.SESSION_USER_ID);
		if (StringUtils.isBlank(user_id)) {
			return "redirect:/login";
		}
		String cronExpression = request.getParameter("cron_expression").trim();
		String jobGroupName = request.getParameter("job_group_name").trim();
		String jobName = request.getParameter("job_name").trim();

		ResultDO result = new ResultDO();

		String session_group_names = (String) session.getAttribute(UsersConstant.SESSION_GROUP_NAMES);
		String role = (String) session.getAttribute(UsersConstant.SESSION_ROLE);
		if (role.equals(UsersConstant.ROLE_TL) && StringUtils.isNotBlank(session_group_names)) {
			if (!containsGroupName(session_group_names, jobGroupName)) {// session_group_names.contains(jobGroupName)
				result.setSuccess(false);
				result.setErrorCode("has_no_auth");
				return gson.toJson(result);
			}
		}

		ActionDO action = new ActionDO();
		action.setJobGroupName(jobGroupName);
		action.setJobName(jobName);
		action.setAction(TaskConstant.TASK_STATUS_UPDATE_CRON_EXPRESSION);
		String actionNo = ClientManager.get_uuid();
		action.setActionNo(actionNo);
		action.setCronExpression(cronExpression);
		ActionDO act = serverManager.get_task_action_value(action);

		if (null == act || act.getAction().equals(TaskConstant.TASK_STATUS_UPDATE_CRON_EXPRESSION)) {
			serverManager.set_action_path_value(action);
			dbManager.update_task_last_action_no(jobGroupName, jobName, actionNo, TaskConstant.ACTION_OPREATOR_START);
			OperatorLogDO log = new OperatorLogDO();
			log.setJobGroupName(jobGroupName);
			log.setJobName(jobName);
			log.setType(OperatorLogConstant.OPERATOR_UPDATE_CRON_EXPRESSION);
			String session_user_name = (String) session.getAttribute(UsersConstant.SESSION_USER_NAME);
			String admin = session_user_name;
			log.setOperator(admin);
			String content = admin + " set job_name=>" + jobName + " and job_group_name=>" + jobGroupName + " task status=>" + TaskConstant.TASK_STATUS_UPDATE_CRON_EXPRESSION
					+ " new cron_expression=>" + cronExpression;
			log.setContent(content);
			log.setActionNo(actionNo);
			dbManager.save_operator_log(log);
			try {
				Thread.sleep(THREAD_SLEPP_TIME);
			} catch (InterruptedException e) {
				logger.error(e.getMessage(), e);
			}

			List<String> result_ips = serverManager.get_result_ips(jobGroupName, jobName);
			List<String> alive_ips = serverManager.get_job_group_alive_ips(jobGroupName);
			List<String> opreator_fail_list = new ArrayList<String>();
			if (null == result_ips || result_ips.size() == 0) {
				result.setSuccess(false);
				result.setErrorCode("has_no_result");
				result.setFailIps(alive_ips);
			}

			if (null != result_ips && result_ips.size() > 0) {
				if (null != alive_ips && alive_ips.size() > 0) {
					if (result_ips.containsAll(alive_ips) && result_ips.size() == alive_ips.size()) {
						try {
							serverManager.delete_result_ips(jobGroupName, jobName);
							serverManager.set_null_for_action_path_value(action);
							dbManager.update_task_cron_expression(jobGroupName, jobName, cronExpression, TaskConstant.ACTION_OPREATOR_SUCCESS);
							dbManager.update_operator_log(actionNo);
							result.setSuccess(true);
							result.setSuccessIps(result_ips);
						} catch (Exception e) {
							logger.error(e.getMessage(), e);
						}
					} else {
						for (Iterator<String> iterator = alive_ips.iterator(); iterator.hasNext();) {
							String str = (String) iterator.next();
							for (Iterator<String> it = result_ips.iterator(); it.hasNext();) {
								String ret = (String) it.next();
								if (ret.equals(str)) {
									iterator.remove();
									break;
								}
							}
						}
						opreator_fail_list = alive_ips;
						result.setSuccess(false);
						result.setErrorCode("opreator_fail");
						result.setFailIps(opreator_fail_list);
					}
				}
			} else {
				opreator_fail_list = alive_ips;
				result.setSuccess(false);
				result.setErrorCode("opreator_fail");
				result.setFailIps(opreator_fail_list);
			}
		} else {
			result.setSuccess(false);
			result.setErrorCode("exists_other_opreator");
		}

		return gson.toJson(result);

	}

	@RequestMapping(value = "/quartz/remove/{jobName}/{jobGroupName}", method = RequestMethod.GET)
	public @ResponseBody String remove(final ModelMap model, @PathVariable String jobName, @PathVariable String jobGroupName, HttpServletRequest request) {
		HttpSession session = request.getSession();
		String user_id = (String) session.getAttribute(UsersConstant.SESSION_USER_ID);
		if (StringUtils.isBlank(user_id)) {
			return "redirect:/login";
		}
		ResultDO result = new ResultDO();
		String session_group_names = (String) session.getAttribute(UsersConstant.SESSION_GROUP_NAMES);
		String session_user_name = (String) session.getAttribute(UsersConstant.SESSION_USER_NAME);
		String role = (String) session.getAttribute(UsersConstant.SESSION_ROLE);
		if (role.equals(UsersConstant.ROLE_TL) && StringUtils.isNotBlank(session_group_names)) {
			if (!containsGroupName(session_group_names, jobGroupName)) {
				result.setSuccess(false);
				result.setErrorCode("has_no_auth");
				return gson.toJson(result);
			}
		}
		ActionDO action = new ActionDO();
		action.setJobGroupName(jobGroupName);
		action.setJobName(jobName);
		action.setAction(TaskConstant.TASK_STATUS_REMOVE);
		String actionNo = ClientManager.get_uuid();
		action.setActionNo(actionNo);
		ActionDO act = serverManager.get_task_action_value(action);
		if (null == act || act.getAction().equals(TaskConstant.TASK_STATUS_REMOVE)) {
			serverManager.set_action_path_value(action);
			dbManager.update_task_last_action_no(jobGroupName, jobName, actionNo, TaskConstant.ACTION_OPREATOR_START);
			OperatorLogDO log = new OperatorLogDO();
			log.setJobGroupName(jobGroupName);
			log.setJobName(jobName);
			log.setType(OperatorLogConstant.OPERATOR_REMOVE);
			String admin = session_user_name;
			log.setOperator(admin);
			String content = admin + " set job_name=>" + jobName + " and job_group_name=>" + jobGroupName + " task status=>" + TaskConstant.TASK_STATUS_REMOVE;
			log.setContent(content);
			log.setActionNo(actionNo);
			dbManager.save_operator_log(log);
			try {
				Thread.sleep(THREAD_SLEPP_TIME);
			} catch (InterruptedException e) {
				logger.error(e.getMessage(), e);
			}

			List<String> opreator_fail_list = new ArrayList<String>();
			List<String> result_ips = serverManager.get_result_ips(jobGroupName, jobName);
			List<String> alive_ips = serverManager.get_job_group_alive_ips(jobGroupName);
			if (null == result_ips || result_ips.size() == 0) {
				result.setSuccess(false);
				result.setErrorCode("has_no_result");
				result.setFailIps(alive_ips);
			}
			if (null != result_ips && result_ips.size() > 0) {
				if (null != alive_ips && alive_ips.size() > 0) {
					if (result_ips.containsAll(alive_ips) && result_ips.size() == alive_ips.size()) {
						AllTasksDO task;
						try {
							task = serverManager.getOneTask(jobGroupName, jobName);
							task.setRunStatus(TaskConstant.TASK_STATUS_REMOVE);
							serverManager.set_task_value(task, jobGroupName, jobName);
							serverManager.delete_result_ips(jobGroupName, jobName);
							serverManager.set_null_for_action_path_value(action);
							dbManager.update_task_run_status(jobGroupName, jobName, null, TaskConstant.TASK_STATUS_REMOVE, TaskConstant.ACTION_OPREATOR_SUCCESS);
							dbManager.update_operator_log(actionNo);
							result.setSuccess(true);
							result.setSuccessIps(result_ips);
						} catch (Exception e) {
							logger.error(e.getMessage(), e);
						}
					} else {
						for (Iterator<String> iterator = alive_ips.iterator(); iterator.hasNext();) {
							String str = (String) iterator.next();
							for (Iterator<String> it = result_ips.iterator(); it.hasNext();) {
								String ret = (String) it.next();
								if (ret.equals(str)) {
									iterator.remove();
									break;
								}
							}
						}
						opreator_fail_list = alive_ips;
						result.setSuccess(false);
						result.setErrorCode("opreator_fail");
						result.setFailIps(opreator_fail_list);
					}
				}

			}
		} else {
			result.setSuccess(false);
			result.setErrorCode("exists_other_opreator");
		}

		return gson.toJson(result);
	}

	@RequestMapping(value = "/quartz/addRemoveJob/{jobName}/{jobGroupName}", method = RequestMethod.GET)
	public @ResponseBody String addRemoveJob(final ModelMap model, @PathVariable String jobName, @PathVariable String jobGroupName, HttpServletRequest request) {
		HttpSession session = request.getSession();
		String user_id = (String) session.getAttribute(UsersConstant.SESSION_USER_ID);
		if (StringUtils.isBlank(user_id)) {
			return "redirect:/login";
		}
		ResultDO result = new ResultDO();
		String session_group_names = (String) session.getAttribute(UsersConstant.SESSION_GROUP_NAMES);
		String role = (String) session.getAttribute(UsersConstant.SESSION_ROLE);
		if (role.equals(UsersConstant.ROLE_TL) && StringUtils.isNotBlank(session_group_names)) {
			if (!containsGroupName(session_group_names, jobGroupName)) {
				result.setSuccess(false);
				result.setErrorCode("has_no_auth");
				return gson.toJson(result);
			}
		}
		ActionDO action = new ActionDO();
		action.setJobGroupName(jobGroupName);
		action.setJobName(jobName);
		action.setAction(TaskConstant.TASK_STATUS_ADD_FROM_REMOVE);
		String actionNo = ClientManager.get_uuid();
		action.setActionNo(actionNo);
		ActionDO act = serverManager.get_task_action_value(action);
		if (null == act || act.getAction().equals(TaskConstant.TASK_STATUS_ADD_FROM_REMOVE)) {
			serverManager.set_action_path_value(action);
			dbManager.update_task_last_action_no(jobGroupName, jobName, actionNo, TaskConstant.ACTION_OPREATOR_START);
			OperatorLogDO log = new OperatorLogDO();
			log.setJobGroupName(jobGroupName);
			log.setJobName(jobName);
			log.setType(OperatorLogConstant.OPERATOR_ADD_FROM_REMOVE);
			String session_user_name = (String) session.getAttribute(UsersConstant.SESSION_USER_NAME);
			String admin = session_user_name;
			log.setOperator(admin);
			String content = admin + " set job_name=>" + jobName + " and job_group_name=>" + jobGroupName + " task status=>" + TaskConstant.TASK_STATUS_ADD_FROM_REMOVE;
			log.setContent(content);
			log.setActionNo(actionNo);
			dbManager.save_operator_log(log);
			try {
				Thread.sleep(THREAD_SLEPP_TIME);
			} catch (InterruptedException e) {
				logger.error(e.getMessage(), e);
			}

			List<String> opreator_fail_list = new ArrayList<String>();
			List<String> alive_ips = serverManager.get_job_group_alive_ips(jobGroupName);
			List<String> result_ips = serverManager.get_result_ips(jobGroupName, jobName);
			if (null == result_ips || result_ips.size() == 0) {
				result.setSuccess(false);
				result.setErrorCode("has_no_result");
				result.setFailIps(alive_ips);
			}
			if (null != result_ips && result_ips.size() > 0) {
				if (null != alive_ips && alive_ips.size() > 0) {
					if (result_ips.containsAll(alive_ips) && result_ips.size() == alive_ips.size()) {
						AllTasksDO task;
						try {
							task = serverManager.getOneTask(jobGroupName, jobName);
							task.setRunStatus(TaskConstant.TASK_STATUS_NORMAL);
							serverManager.set_task_value(task, jobGroupName, jobName);
							serverManager.delete_result_ips(jobGroupName, jobName);
							serverManager.set_null_for_action_path_value(action);
							dbManager.update_task_run_status(jobGroupName, jobName, null, TaskConstant.TASK_STATUS_NORMAL, TaskConstant.ACTION_OPREATOR_SUCCESS);
							dbManager.update_operator_log(actionNo);
							result.setSuccess(true);
							result.setSuccessIps(result_ips);
						} catch (Exception e) {
							logger.error(e.getMessage(), e);
						}
					} else {
						for (Iterator<String> iterator = alive_ips.iterator(); iterator.hasNext();) {
							String str = (String) iterator.next();
							for (Iterator<String> it = result_ips.iterator(); it.hasNext();) {
								String ret = (String) it.next();
								if (ret.equals(str)) {
									iterator.remove();
									break;
								}
							}
						}
						opreator_fail_list = alive_ips;
						result.setSuccess(false);
						result.setErrorCode("opreator_fail");
						result.setFailIps(opreator_fail_list);
					}
				}

			}
		} else {
			result.setSuccess(false);
			result.setErrorCode("exists_other_opreator");
		}
		return gson.toJson(result);
	}

	@RequestMapping(value = "/quartz/pause/{jobName}/{jobGroupName}", method = RequestMethod.GET)
	public @ResponseBody String pause(final ModelMap model, @PathVariable String jobName, @PathVariable String jobGroupName, HttpServletRequest request) {
		HttpSession session = request.getSession();
		String user_id = (String) session.getAttribute(UsersConstant.SESSION_USER_ID);
		if (StringUtils.isBlank(user_id)) {
			return "redirect:/login";
		}
		ResultDO result = new ResultDO();
		String session_group_names = (String) session.getAttribute(UsersConstant.SESSION_GROUP_NAMES);
		String role = (String) session.getAttribute(UsersConstant.SESSION_ROLE);
		if (role.equals(UsersConstant.ROLE_TL) && StringUtils.isNotBlank(session_group_names)) {
			if (!containsGroupName(session_group_names, jobGroupName)) {
				result.setSuccess(false);
				result.setErrorCode("has_no_auth");
				return gson.toJson(result);
			}
		}
		ActionDO action = new ActionDO();
		action.setJobGroupName(jobGroupName);
		action.setJobName(jobName);
		action.setAction(TaskConstant.TASK_STATUS_PAUSE);
		String actionNo = ClientManager.get_uuid();
		action.setActionNo(actionNo);
		ActionDO act = serverManager.get_task_action_value(action);
		if (null == act || act.getAction().equals(TaskConstant.TASK_STATUS_PAUSE)) {
			serverManager.set_action_path_value(action);
			dbManager.update_task_last_action_no(jobGroupName, jobName, actionNo, TaskConstant.ACTION_OPREATOR_START);
			OperatorLogDO log = new OperatorLogDO();
			log.setJobGroupName(jobGroupName);
			log.setJobName(jobName);
			log.setType(OperatorLogConstant.OPERATOR_PAUSE);
			String session_user_name = (String) session.getAttribute(UsersConstant.SESSION_USER_NAME);
			String admin = session_user_name;
			log.setOperator(admin);
			String content = admin + " set job_name=>" + jobName + " and job_group_name=>" + jobGroupName + " task status=>" + TaskConstant.TASK_STATUS_PAUSE;
			log.setContent(content);
			log.setActionNo(actionNo);
			dbManager.save_operator_log(log);
			try {
				Thread.sleep(THREAD_SLEPP_TIME);
			} catch (InterruptedException e) {
				logger.error(e.getMessage(), e);
			}

			List<String> opreator_fail_list = new ArrayList<String>();
			List<String> result_ips = serverManager.get_result_ips(jobGroupName, jobName);
			List<String> alive_ips = serverManager.get_job_group_alive_ips(jobGroupName);
			if (null == result_ips || result_ips.size() == 0) {
				result.setSuccess(false);
				result.setErrorCode("has_no_result");
				result.setFailIps(alive_ips);
			}
			if (null != result_ips && result_ips.size() > 0) {
				if (null != alive_ips && alive_ips.size() > 0) {
					if (result_ips.containsAll(alive_ips) && result_ips.size() == alive_ips.size()) {
						AllTasksDO task;
						try {
							task = serverManager.getOneTask(jobGroupName, jobName);
							task.setRunStatus(TaskConstant.TASK_STATUS_PAUSE);
							serverManager.set_task_value(task, jobGroupName, jobName);
							serverManager.delete_result_ips(jobGroupName, jobName);
							serverManager.set_null_for_action_path_value(action);
							dbManager.update_task_run_status(jobGroupName, jobName, null, TaskConstant.TASK_STATUS_PAUSE, TaskConstant.ACTION_OPREATOR_SUCCESS);
							dbManager.update_operator_log(actionNo);
							result.setSuccess(true);
							result.setSuccessIps(result_ips);
						} catch (Exception e) {
							logger.error(e.getMessage(), e);
						}
					} else {
						for (Iterator<String> iterator = alive_ips.iterator(); iterator.hasNext();) {
							String str = (String) iterator.next();
							for (Iterator<String> it = result_ips.iterator(); it.hasNext();) {
								String ret = (String) it.next();
								if (ret.equals(str)) {
									iterator.remove();
									break;
								}
							}
						}
						opreator_fail_list = alive_ips;
						result.setSuccess(false);
						result.setErrorCode("opreator_fail");
						result.setFailIps(opreator_fail_list);
					}

				}
			} else {
				opreator_fail_list = alive_ips;
				result.setSuccess(false);
				result.setErrorCode("opreator_fail");
				result.setFailIps(opreator_fail_list);
			}
		} else {
			result.setSuccess(false);
			result.setErrorCode("exists_other_opreator");
		}
		return gson.toJson(result);

	}

	@RequestMapping(value = "/quartz/resume/{jobName}/{jobGroupName}", method = RequestMethod.GET)
	public @ResponseBody String resume(final ModelMap model, @PathVariable String jobName, @PathVariable String jobGroupName, HttpServletRequest request) {
		HttpSession session = request.getSession();
		String user_id = (String) session.getAttribute(UsersConstant.SESSION_USER_ID);
		if (StringUtils.isBlank(user_id)) {
			return "redirect:/login";
		}
		ResultDO result = new ResultDO();
		String session_group_names = (String) session.getAttribute(UsersConstant.SESSION_GROUP_NAMES);
		String role = (String) session.getAttribute(UsersConstant.SESSION_ROLE);
		if (role.equals(UsersConstant.ROLE_TL) && StringUtils.isNotBlank(session_group_names)) {
			if (!containsGroupName(session_group_names, jobGroupName)) {
				result.setSuccess(false);
				result.setErrorCode("has_no_auth");
				return gson.toJson(result);
			}
		}
		ActionDO action = new ActionDO();
		action.setJobGroupName(jobGroupName);
		action.setJobName(jobName);
		action.setAction(TaskConstant.TASK_STATUS_NORMAL);
		String actionNo = ClientManager.get_uuid();
		action.setActionNo(actionNo);
		ActionDO act = serverManager.get_task_action_value(action);
		if (null == act || act.getAction().equals(TaskConstant.TASK_STATUS_NORMAL)) {
			serverManager.set_action_path_value(action);
			dbManager.update_task_last_action_no(jobGroupName, jobName, actionNo, TaskConstant.ACTION_OPREATOR_START);
			OperatorLogDO log = new OperatorLogDO();
			log.setJobGroupName(jobGroupName);
			log.setJobName(jobName);
			log.setType(OperatorLogConstant.OPERATOR_RESUME);
			String session_user_name = (String) session.getAttribute(UsersConstant.SESSION_USER_NAME);
			String admin = session_user_name;
			log.setOperator(admin);
			String content = admin + " set job_name=>" + jobName + " and job_group_name=>" + jobGroupName + " task status=>" + TaskConstant.TASK_STATUS_NORMAL;
			log.setContent(content);
			log.setActionNo(actionNo);
			dbManager.save_operator_log(log);
			try {
				Thread.sleep(THREAD_SLEPP_TIME);
			} catch (InterruptedException e) {
				logger.error(e.getMessage(), e);
			}

			List<String> opreator_fail_list = new ArrayList<String>();
			List<String> result_ips = serverManager.get_result_ips(jobGroupName, jobName);
			List<String> alive_ips = serverManager.get_job_group_alive_ips(jobGroupName);
			if (null == result_ips || result_ips.size() == 0) {
				result.setSuccess(false);
				result.setErrorCode("has_no_result");
				result.setFailIps(alive_ips);
			}
			if (null != result_ips && result_ips.size() > 0) {
				if (null != alive_ips && alive_ips.size() > 0) {
					if (result_ips.containsAll(alive_ips) && result_ips.size() == alive_ips.size()) {
						AllTasksDO task;
						try {
							task = serverManager.getOneTask(jobGroupName, jobName);
							task.setRunStatus(TaskConstant.TASK_STATUS_NORMAL);
							serverManager.set_task_value(task, jobGroupName, jobName);
							serverManager.delete_result_ips(jobGroupName, jobName);
							serverManager.set_null_for_action_path_value(action);
							dbManager.update_task_run_status(jobGroupName, jobName, null, TaskConstant.TASK_STATUS_NORMAL, TaskConstant.ACTION_OPREATOR_SUCCESS);
							dbManager.update_operator_log(actionNo);
							result.setSuccess(true);
							result.setSuccessIps(result_ips);
						} catch (Exception e) {
							logger.error(e.getMessage(), e);
						}
					} else {
						for (Iterator<String> iterator = alive_ips.iterator(); iterator.hasNext();) {
							String str = (String) iterator.next();
							for (Iterator<String> it = result_ips.iterator(); it.hasNext();) {
								String ret = (String) it.next();
								if (ret.equals(str)) {
									iterator.remove();
									break;
								}
							}
						}
						opreator_fail_list = alive_ips;
						result.setSuccess(false);
						result.setErrorCode("opreator_fail");
						result.setFailIps(opreator_fail_list);
					}
				}

			}

		} else {
			result.setSuccess(false);
			result.setErrorCode("exists_other_opreator");
		}

		return gson.toJson(result);

	}

	@RequestMapping(value = "/quartz/triggerOnce/{jobName}/{jobGroupName}", method = RequestMethod.GET)
	public @ResponseBody String triggerOnce(final ModelMap model, @PathVariable String jobName, @PathVariable String jobGroupName, HttpServletRequest request) {
		HttpSession session = request.getSession();
		String user_id = (String) session.getAttribute(UsersConstant.SESSION_USER_ID);
		if (StringUtils.isBlank(user_id)) {
			return "redirect:/login";
		}
		ResultDO result = new ResultDO();
		String session_group_names = (String) session.getAttribute(UsersConstant.SESSION_GROUP_NAMES);
		String role = (String) session.getAttribute(UsersConstant.SESSION_ROLE);
		if (role.equals(UsersConstant.ROLE_TL) && StringUtils.isNotBlank(session_group_names)) {
			if (!containsGroupName(session_group_names, jobGroupName)) {
				result.setSuccess(false);
				result.setErrorCode("has_no_auth");
				return gson.toJson(result);
			}
		}
		ActionDO action = new ActionDO();
		action.setJobGroupName(jobGroupName);
		action.setJobName(jobName);
		action.setAction(TaskConstant.TASK_STATUS_TRIGGER_AT_ONCE);
		String actionNo = ClientManager.get_uuid();
		action.setActionNo(actionNo);
		ActionDO act = serverManager.get_task_action_value(action);
		if (null == act || act.getAction().equals(TaskConstant.TASK_STATUS_TRIGGER_AT_ONCE)) {
			serverManager.set_action_path_value(action);
			dbManager.update_task_last_action_no(jobGroupName, jobName, actionNo, TaskConstant.ACTION_OPREATOR_START);
			OperatorLogDO log = new OperatorLogDO();
			log.setJobGroupName(jobGroupName);
			log.setJobName(jobName);
			log.setType(OperatorLogConstant.OPERATOR_RUN_AT_ONCE);
			String session_user_name = (String) session.getAttribute(UsersConstant.SESSION_USER_NAME);
			String admin = session_user_name;
			log.setOperator(admin);
			String content = admin + " set job_name=>" + jobName + " and job_group_name=>" + jobGroupName + " action is =>" + TaskConstant.TASK_STATUS_TRIGGER_AT_ONCE;
			log.setContent(content);
			log.setActionNo(actionNo);
			dbManager.save_operator_log(log);
			try {
				Thread.sleep(THREAD_SLEPP_TIME);
			} catch (InterruptedException e) {
				logger.error(e.getMessage(), e);
			}

			List<String> opreator_fail_list = new ArrayList<String>();
			List<String> result_ips = serverManager.get_result_ips(jobGroupName, jobName);
			List<String> alive_ips = serverManager.get_job_group_alive_ips(jobGroupName);
			if (null == result_ips || result_ips.size() == 0) {
				result.setSuccess(false);
				result.setErrorCode("has_no_result");
				result.setFailIps(alive_ips);
			}
			if (null != result_ips && result_ips.size() > 0) {
				if (null != alive_ips && alive_ips.size() > 0) {
					if (result_ips.containsAll(alive_ips) && result_ips.size() == alive_ips.size()) {
						try {
							serverManager.delete_result_ips(jobGroupName, jobName);
							serverManager.set_null_for_action_path_value(action);
							dbManager.update_task_last_action_result(jobGroupName, jobName, TaskConstant.ACTION_OPREATOR_SUCCESS);
							dbManager.update_operator_log(actionNo);
							result.setSuccess(true);
							result.setSuccessIps(result_ips);
						} catch (Exception e) {

						}
					} else {
						for (Iterator<String> iterator = alive_ips.iterator(); iterator.hasNext();) {
							String str = (String) iterator.next();
							for (Iterator<String> it = result_ips.iterator(); it.hasNext();) {
								String ret = (String) it.next();
								if (ret.equals(str)) {
									iterator.remove();
									break;
								}
							}
						}
						opreator_fail_list = alive_ips;
						result.setSuccess(false);
						result.setErrorCode("opreator_fail");
						result.setFailIps(opreator_fail_list);
					}
				}

			}

		} else {
			result.setSuccess(false);
			result.setErrorCode("exists_other_opreator");
		}

		return gson.toJson(result);
	}

	@RequestMapping(value = "/dynamic", method = RequestMethod.GET)
	public String dynamic(final ModelMap model, HttpServletRequest request) {
		HttpSession session = request.getSession();
		String user_id = (String) session.getAttribute(UsersConstant.SESSION_USER_ID);
		if (StringUtils.isBlank(user_id)) {
			return "redirect:/login";
		}
		String session_role = (String) session.getAttribute(UsersConstant.SESSION_ROLE);
		model.addAttribute("session_role", session_role);
		if (StringUtils.isBlank(session_role) || !session_role.equals(UsersConstant.ROLE_ADMIN)) {
			return "redirect:/quartz";
		}

		Map<String, String> get_all_job_group_names = serverManager.get_all_job_group_names();
		String session_group_names = (String) session.getAttribute(UsersConstant.SESSION_GROUP_NAMES);
		String role = (String) session.getAttribute(UsersConstant.SESSION_ROLE);
		if (role.equals(UsersConstant.ROLE_TL) && StringUtils.isNotBlank(session_group_names)) {
			filter_group_names(get_all_job_group_names, session_group_names);
		}
		model.addAttribute("job_group_name_list", get_all_job_group_names);
		return "dynamic";
	}

	@RequestMapping(value = "/job_trigger_log_count", method = RequestMethod.GET)
	public String job_trigger_log_count(final ModelMap model, HttpServletRequest request) {
		HttpSession session = request.getSession();
		String session_role = (String) session.getAttribute(UsersConstant.SESSION_ROLE);
		model.addAttribute("session_role", session_role);
		if (StringUtils.isBlank(session_role) || !session_role.equals(UsersConstant.ROLE_ADMIN)) {
			return "redirect:/quartz";
		}

		Map<String, String> recordMap = dbManager.query_job_trigger_log_count();
		int num = 0;
		if (!recordMap.isEmpty()) {
			num = Integer.valueOf(recordMap.get("record"));
		}
		model.addAttribute("job_trigger_log_count", num);
		return "job_trigger_log_count";
	}

	@RequestMapping(value = "/dynamic/store", method = RequestMethod.POST)
	public String dynamicStore(final ModelMap model, HttpServletRequest request) {
		HttpSession session = request.getSession();
		String session_role = (String) session.getAttribute(UsersConstant.SESSION_ROLE);
		model.addAttribute("session_role", session_role);
		if (StringUtils.isBlank(session_role) || !session_role.equals(UsersConstant.ROLE_ADMIN)) {
			return "redirect:/quartz";
		}

		String jobEnvironmentType = request.getParameter("job_environment_type");
		String targetObject = request.getParameter("targetObject");
		String targetMethod = request.getParameter("targetMethod");

		String ips = request.getParameter("ips");
		String params = request.getParameter("params");
		String jobDesc = request.getParameter("jobDesc");

		String triggerType = request.getParameter("triggerType");
		String startDelay = request.getParameter("startDelay");
		String cronExpression = request.getParameter("cronExpression");

		String jobGroupName = request.getParameter("job_group_name").trim();
		String jobName = request.getParameter("job_name").trim();
		String runTimes = request.getParameter("runTimes").trim();

		String triggerGroupName = jobGroupName.substring(0, jobGroupName.indexOf("_job_group_name")) + "_trigger_group_name";

		List<String> ipList = strToList(ips);
		List<String> paramList = strToList(params);

		DynamicActionDO dynamicActionDO = new DynamicActionDO();
		dynamicActionDO.setJobName(jobName);
		dynamicActionDO.setJobGroupName(jobGroupName);
		dynamicActionDO.setBeanName(jobName);
		dynamicActionDO.setTriggerGroupName(triggerGroupName);
		dynamicActionDO.setTriggerName(jobName);

		dynamicActionDO.setJobEnvironmentType(jobEnvironmentType);
		dynamicActionDO.setTargetObject(targetObject);
		dynamicActionDO.setTargetMethod(targetMethod);

		dynamicActionDO.setIpList(ipList);
		dynamicActionDO.setParamList(paramList);
		dynamicActionDO.setJobDesc(jobDesc);

		dynamicActionDO.setTriggerType(triggerType);

		dynamicActionDO.setAction(TaskConstant.TASK_STATUS_DYNAMIC_ADD_JOB);
		String actionNo = ClientManager.get_uuid();
		dynamicActionDO.setActionNo(actionNo);

		model.addAttribute("has_job_name", "0");
		model.addAttribute("success", "0");
		model.addAttribute("jobName", jobName);
		if (ipList.isEmpty()) {
			model.addAttribute("errorCode", "运行机器IP列表为空!");
			return "dynamic_success";
		}

		String jobTimeType = "";
		if (dynamicActionDO.getTriggerType().equals(DynamicActionDO.SCHEDULE_TYPE_SIMPLE_TRIGGER)) {
			jobTimeType = DynamicActionDO.JOB_TIME_TYPE_SIMPLE;
		}
		if (dynamicActionDO.getTriggerType().equals(DynamicActionDO.SCHEDULE_TYPE_CRON_TRIGGER)) {
			jobTimeType = DynamicActionDO.JOB_TIME_TYPE_CRON;
		}
		dynamicActionDO.setJobTimeType(jobTimeType);
		dynamicActionDO.setDynamicAdd(true);
		if (triggerType.equals(DynamicActionDO.SCHEDULE_TYPE_SIMPLE_TRIGGER)) {
			if (StringUtils.isBlank(startDelay)) {
				model.addAttribute("errorCode", "延时startDelay为空!");
				return "dynamic_success";
			}

			if ("once".equals(runTimes)) {
				dynamicActionDO.setOnlyRunOnce(true);
				dynamicActionDO.setRepeatCount(0L);
				dynamicActionDO.setRepeatInterval(0L);
				dynamicActionDO.setStartDelay(Long.valueOf(startDelay));

				Map<String, Object> simpleTriggerMap = new HashMap<String, Object>();
				simpleTriggerMap.put(MethodInvokingJobDetailFactoryBean.SIMPLE_TRIGGER_START_DELAY, dynamicActionDO.getStartDelay());
				simpleTriggerMap.put(MethodInvokingJobDetailFactoryBean.SIMPLE_TRIGGER_REPEATINTERVAL, dynamicActionDO.getRepeatInterval());
				simpleTriggerMap.put(MethodInvokingJobDetailFactoryBean.SIMPLE_TRIGGER_REPEATCOUNT, dynamicActionDO.getRepeatCount());
				dynamicActionDO.setSimpleTriggerMap(simpleTriggerMap);
			} else {
				model.addAttribute("errorCode", DynamicActionDO.SCHEDULE_TYPE_SIMPLE_TRIGGER + "只能运行一次!");
				return "dynamic_success";
			}

		}
		if (triggerType.equals(DynamicActionDO.SCHEDULE_TYPE_CRON_TRIGGER)) {
			if (StringUtils.isBlank(cronExpression)) {
				model.addAttribute("errorCode", "请填写trigger时间表达式!");
				return "dynamic_success";
			}
			dynamicActionDO.setCronExpression(cronExpression);
		}
		if (jobEnvironmentType.equals(DynamicActionDO.JOB_ENVIRONMENT_TYPE_DISTRIBUTE)) {
			if (paramList.isEmpty()) {
				model.addAttribute("errorCode", "请填写方法参数agrments!");
				return "dynamic_success";
			}
		}
		if (jobEnvironmentType.equals(DynamicActionDO.JOB_ENVIRONMENT_TYPE_SINGLE)) {

		}

		if (dbManager.exists_one_task(dynamicActionDO)) {
			model.addAttribute("errorCode", "任务已存在,请重新填写任务名称及相关信息!");
			model.addAttribute("has_job_name", "1");
			return "dynamic_success";
		}

		ResultDO result = new ResultDO();
		DynamicActionDO dynamicAction = serverManager.get_dynamic_action_value();
		if (null == dynamicAction || (dynamicAction.getAction().equals(TaskConstant.TASK_STATUS_DYNAMIC_ADD_JOB) && dynamicAction.getJobName().equals(jobName))) {
			dbManager.save_task_new(dynamicActionDO);
			serverManager.set_dynamic_action_path_value(dynamicActionDO);
			dbManager.update_task_last_action_no(jobGroupName, jobName, actionNo, TaskConstant.ACTION_OPREATOR_START);
			OperatorLogDO log = new OperatorLogDO();
			log.setJobGroupName(jobGroupName);
			log.setJobName(jobName);
			log.setType(OperatorLogConstant.OPERATOR_DYNAMIC_ADD_JOB);
			String session_user_name = (String) request.getSession().getAttribute(UsersConstant.SESSION_USER_NAME);
			String admin = session_user_name;
			log.setOperator(admin);
			String content = admin + " set job_name=>" + jobName + " and job_group_name=>" + jobGroupName + " action is =>" + TaskConstant.TASK_STATUS_DYNAMIC_ADD_JOB + " dynamic add content is=>"
					+ dynamicActionDO.toString();
			log.setContent(content);
			log.setActionNo(actionNo);
			dbManager.save_operator_log(log);
			try {
				Thread.sleep(DYNAMIC_ADD_THREAD_SLEPP_TIME);
			} catch (InterruptedException e) {
				logger.error(e.getMessage(), e);
			}

			List<String> opreator_fail_list = new ArrayList<String>();
			List<String> result_ips = serverManager.get_dynamic_add_result_ips();
			Map<String, String> get_one_task = dbManager.get_one_task(jobGroupName, jobName);
			List<String> alive_ips = strToList(get_one_task.get("dist_ips"));
			if (null == result_ips || result_ips.size() == 0) {
				result.setSuccess(false);
				result.setErrorCode("has_no_result");
				result.setFailIps(alive_ips);
				model.addAttribute("errorCode", "失败IP为：" + alive_ips.toString());
				return "dynamic_success";
			}
			if (null != result_ips && result_ips.size() > 0) {
				if (null != alive_ips && alive_ips.size() > 0) {
					if (result_ips.containsAll(alive_ips) && result_ips.size() == alive_ips.size()) {
						try {
							serverManager.delete_dynamic_add_result_ips();
							serverManager.set_null_for_dynamic_add_action_path_value();
							dbManager.update_task_run_status(jobGroupName, jobName, null, TaskConstant.TASK_STATUS_NORMAL, TaskConstant.ACTION_OPREATOR_SUCCESS);
							dbManager.save_action_result(dynamicActionDO);
							dbManager.update_operator_log(actionNo);
							model.addAttribute("success", "1");
							result.setSuccess(true);
							result.setSuccessIps(result_ips);
						} catch (Exception e) {
							logger.error(e.getMessage(), e);
						}
					} else {
						for (Iterator<String> iterator = alive_ips.iterator(); iterator.hasNext();) {
							String str = (String) iterator.next();
							for (Iterator<String> it = result_ips.iterator(); it.hasNext();) {
								String ret = (String) it.next();
								if (ret.equals(str)) {
									iterator.remove();
									break;
								}
							}
						}
						opreator_fail_list = alive_ips;
						result.setSuccess(false);
						result.setErrorCode("opreator_fail");
						result.setFailIps(opreator_fail_list);

						model.addAttribute("errorCode", "失败IP为：" + opreator_fail_list.toString());
						return "dynamic_success";
					}
				}
			}

		} else {
			result.setSuccess(false);
			result.setErrorCode("exists_other_opreator");

			model.addAttribute("errorCode", "存在其他操作，请check操作，或删除操作");
			return "dynamic_success";
		}

		model.put("result", result);
		return "dynamic_success";
	}

	@RequestMapping(value = "/dynamic/clear/result", method = RequestMethod.GET)
	public String dynamicClearResult(final ModelMap model, HttpServletRequest request) {
		HttpSession session = request.getSession();
		String session_role = (String) session.getAttribute(UsersConstant.SESSION_ROLE);
		model.addAttribute("session_role", session_role);
		if (StringUtils.isBlank(session_role) || !session_role.equals(UsersConstant.ROLE_ADMIN)) {
			return "redirect:/quartz";
		}
		serverManager.delete_dynamic_add_result_ips();
		serverManager.set_null_for_dynamic_add_action_path_value();
		ResultDO result = new ResultDO();
		result.setSuccess(true);
		model.addAttribute("result", "手动清除成功");
		return "clear_dynamic_result";
	}

	// @RequestMapping(value = "/clear/all/node", method = RequestMethod.GET)
	// public String clearRoot(final ModelMap model, HttpServletRequest request)
	// {
	// HttpSession session = request.getSession();
	// String session_role = (String)
	// session.getAttribute(UsersConstant.SESSION_ROLE);
	// model.addAttribute("session_role", session_role);
	// if (StringUtils.isBlank(session_role) ||
	// !session_role.equals(UsersConstant.ROLE_ADMIN)) {
	// return "redirect:/quartz";
	// }
	// serverManager.clearAllPath();
	// ResultDO result = new ResultDO();
	// result.setSuccess(true);
	// model.addAttribute("result", "手动清除所有节点成功");
	// return "clear_dynamic_result";
	// }

	@RequestMapping(value = "/dynamic/check", method = RequestMethod.GET)
	public String dynamicCheck(final ModelMap model, HttpServletRequest request) {

		ResultDO result = new ResultDO();
		DynamicActionDO dynamicAction = serverManager.get_dynamic_action_value();
		if (null == dynamicAction) {
			model.put("result", result);
			return "dynamic_check";
		}
		String jobGroupName = dynamicAction.getJobGroupName();
		String jobName = dynamicAction.getJobName();

		List<String> opreator_fail_list = new ArrayList<String>();
		List<String> result_ips = serverManager.get_dynamic_add_result_ips();
		Map<String, String> get_one_task = dbManager.get_one_task(jobGroupName, jobName);
		List<String> alive_ips = strToList(get_one_task.get("dist_ips"));
		if (null == result_ips || result_ips.size() == 0) {
			result.setSuccess(false);
			result.setErrorCode("has_no_result");
			result.setFailIps(alive_ips);
			model.addAttribute("errorCode", "失败IP为：" + alive_ips.toString());
		}
		if (null != result_ips && result_ips.size() > 0) {
			if (null != alive_ips && alive_ips.size() > 0) {
				if (result_ips.containsAll(alive_ips) && result_ips.size() == alive_ips.size()) {
					try {
						serverManager.delete_dynamic_add_result_ips();
						serverManager.set_null_for_dynamic_add_action_path_value();
						dbManager.update_task_run_status(jobGroupName, jobName, null, TaskConstant.TASK_STATUS_NORMAL, TaskConstant.ACTION_OPREATOR_SUCCESS);
						dbManager.save_action_result(dynamicAction);
						dbManager.update_operator_log(dynamicAction.getActionNo());
						result.setSuccess(true);
						result.setSuccessIps(result_ips);
					} catch (Exception e) {
						logger.error(e.getMessage(), e);
					}
				} else {
					for (Iterator<String> iterator = alive_ips.iterator(); iterator.hasNext();) {
						String str = (String) iterator.next();
						for (Iterator<String> it = result_ips.iterator(); it.hasNext();) {
							String ret = (String) it.next();
							if (ret.equals(str)) {
								iterator.remove();
								break;
							}
						}
					}
					opreator_fail_list = alive_ips;
					result.setSuccess(false);
					result.setErrorCode("opreator_fail");
					result.setFailIps(opreator_fail_list);

					model.addAttribute("errorCode", "失败IP为：" + opreator_fail_list.toString());
				}
			}
		}

		model.put("jobGroupName", jobGroupName);
		model.put("jobName", jobName);
		model.put("result", result);
		return "dynamic_check";
	}

	private static List<String> strToList(String strings) {
		List<String> ipList = new ArrayList<String>();
		if (StringUtils.isNotBlank(strings)) {
			String[] split = strings.split(",");
			for (String ip : split) {
				if (!ipList.contains(ip.trim())) {
					ipList.add(ip.trim());
				}
			}
		}
		return ipList;
	}

	private static boolean containsGroupName(String session_group_names, String jobGroupName) {
		List<String> list = strToList(session_group_names);
		for (String str : list) {
			if (null != str && !"".equals(str)) {
				if (str.trim().contains(jobGroupName.trim())) {
					return true;
				}
			}
		}
		return false;
	}
}

class TimestampTypeAdapters implements JsonSerializer<Timestamp>, JsonDeserializer<Timestamp> {
	public JsonElement serialize(Timestamp src, Type arg1, JsonSerializationContext arg2) {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateFormatAsString = format.format(new Date(src.getTime()));
		return new JsonPrimitive(dateFormatAsString);
	}

	public Timestamp deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
		if (!(json instanceof JsonPrimitive)) {
			throw new JsonParseException("The date should be a string value");
		}

		try {
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date = (Date) format.parse(json.getAsString());
			return new Timestamp(date.getTime());
		} catch (Exception e) {
			throw new JsonParseException(e);
		}
	}

}
