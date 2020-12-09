package com.elsa.ttm.manager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.elsa.ttm.constant.TaskConstant;
import com.elsa.ttm.domain.ActionDO;
import com.elsa.ttm.domain.AllTasksDO;
import com.elsa.ttm.domain.DynamicActionDO;
import com.elsa.ttm.domain.OperatorLogDO;
import com.elsa.ttm.domain.TriggerLogDO;
import com.elsa.ttm.domain.UsersDO;
import com.elsa.ttm.schedule.MethodInvokingJobDetailFactoryBean;
import com.elsa.ttm.util.DBUtil;

/**
 * @author longhaisheng
 *
 */
public class DBManager {

	private DBUtil dbUtil;

	public DBUtil getDbUtil() {
		return dbUtil;
	}

	public void setDbUtil(DBUtil jdbc) {
		this.dbUtil = jdbc;
	}

	public boolean save_task(AllTasksDO task) {
		boolean is_exists = this.exists_one_task(task);
		if (!is_exists) {
			String sql = "insert into sd_job_all_tasks(job_name,job_group_name,trigger_name,trigger_group_name,job_memo,job_time_type,job_environment_type,cron_expression,run_status) values "
					+ "(#job_name#,#job_group_name#,#trigger_name#,#trigger_group_name#,#job_memo#,#job_time_type#,#job_environment_type#,#cron_expression#,#run_status#)";
			Map<String, Object> map = new HashMap<String, Object>();

			map.put("job_name", task.getJobName());
			map.put("job_group_name", task.getJobGroupName());
			map.put("trigger_name", task.getTriggerName());

			map.put("trigger_group_name", task.getTriggerGroupName());
			map.put("job_memo", task.getJobMemo());
			map.put("job_time_type", task.getJobTimeType());

			map.put("job_environment_type", task.getJobEnvironmentType());
			map.put("cron_expression", task.getCronExpression());
			map.put("run_status", task.getRunStatus());

			return dbUtil.executeQuery(sql, map) > 0;

		}
		return false;
	}

	public boolean save_task_new(DynamicActionDO task) {
		boolean is_exists = this.exists_one_task(task);
		if (!is_exists) {
			String columns = "job_name,job_group_name,trigger_name,trigger_group_name,job_memo,job_time_type,job_environment_type";
			String values = "#job_name#,#job_group_name#,#trigger_name#,#trigger_group_name#,#job_memo#,#job_time_type#,#job_environment_type#";

			Map<String, Object> map = new HashMap<String, Object>();

			columns = columns + ",only_run_once";
			map.put("only_run_once", task.isOnlyRunOnce() ? 1 : 0);
			values = values + ",#only_run_once#";

			map.put("job_name", task.getJobName());
			map.put("job_group_name", task.getJobGroupName());
			map.put("trigger_name", task.getTriggerName());
			map.put("trigger_group_name", task.getTriggerGroupName());
			map.put("job_memo", task.getJobDesc());
			if (task.getTriggerType().equals(DynamicActionDO.SCHEDULE_TYPE_SIMPLE_TRIGGER)) {
				columns = columns + ",start_delay";
				values = values + ",#start_delay#";
				map.put("start_delay", task.getStartDelay());

				columns = columns + ",repeat_count";
				values = values + ",#repeat_count#";
				map.put("repeat_count", task.getRepeatCount());

				columns = columns + ",repeat_interval";
				values = values + ",#repeat_interval#";
				map.put("repeat_interval", task.getRepeatInterval());

			}
			map.put("job_time_type", task.getJobTimeType());
			map.put("job_environment_type", task.getJobEnvironmentType());

			if (null != task.getTargetObject() && !"".equals(task.getTargetObject())) {
				columns = columns + ",target_object";
				values = values + ",#target_object#";
				map.put("target_object", task.getTargetObject());
			}
			if (null != task.getTargetMethod() && !"".equals(task.getTargetMethod())) {
				columns = columns + ",target_method";
				values = values + ",#target_method#";
				map.put("target_method", task.getTargetMethod());
			}
			if (null != task.getParamList() && !task.getParamList().isEmpty()) {
				StringBuffer sb = new StringBuffer();
				int i = 0;
				for (String str : task.getParamList()) {
					sb.append(str.trim());
					if (i < task.getParamList().size() - 1) {
						sb.append(",");
					}
					i++;
				}
				columns = columns + ",arguments";
				values = values + ",#arguments#";
				map.put("arguments", sb.toString());
			}
			if (null != task.getIpList() && !task.getIpList().isEmpty()) {
				StringBuffer sb = new StringBuffer();
				int i = 0;
				for (String str : task.getIpList()) {
					sb.append(str.trim());
					if (i < task.getIpList().size() - 1) {
						sb.append(",");
					}
					i++;
				}
				columns = columns + ",dist_ips";
				values = values + ",#dist_ips#";
				map.put("dist_ips", sb.toString());
			}

			columns = columns + ",run_status";
			values = values + ",#run_status#";
			map.put("run_status", TaskConstant.TASK_STATUS_REMOVE);

			columns = columns + ",is_dynamic_add";
			values = values + ",#is_dynamic_add#";
			map.put("is_dynamic_add", task.isDynamicAdd() ? 1 : 0);

			columns = columns + ",last_action_no";
			values = values + ",#last_action_no#";
			map.put("last_action_no", task.getActionNo());

			if (task.getTriggerType().equals(DynamicActionDO.SCHEDULE_TYPE_CRON_TRIGGER)) {
				if (null != task.getCronExpression() && !"".equals(task.getCronExpression())) {
					columns = columns + ",cron_expression";
					values = values + ",#cron_expression#";
					map.put("cron_expression", task.getCronExpression());
				}
			}

			String newSql = "insert into sd_job_all_tasks(" + columns + ") values (" + values + ")";
			return dbUtil.executeQuery(newSql, map) > 0;

		}
		return false;
	}

	private boolean exists_one_task(AllTasksDO task) {
		String sql = "select id from sd_job_all_tasks where job_name=#job_name# and job_group_name=#job_group_name#";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("job_group_name", task.getJobGroupName());
		map.put("job_name", task.getJobName());
		List<Map<String, String>> queryAll = dbUtil.queryAll(sql, map);
		if (!queryAll.isEmpty()) {
			return true;
		}
		return false;
	}

	public boolean update_task_ips(String jobName, String jobGroupName, String sourceIp, String replaceIp) {
		String sql = "select id,dist_ips from sd_job_all_tasks where job_name=#job_name# and job_group_name=#job_group_name#";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("job_group_name", jobGroupName);
		map.put("job_name", jobName);
		List<Map<String, String>> queryAll = dbUtil.queryAll(sql, map);
		for (Map<String, String> m : queryAll) {
			Integer id = Integer.valueOf(m.get("id"));
			String dist_ips = m.get("dist_ips");
			if (dist_ips.contains(sourceIp)) {
				dist_ips = dist_ips.replace(sourceIp, replaceIp);
				String update_sql = "update sd_job_all_tasks set dist_ips=#dist_ips# where id=#id# ";
				Map<String, Object> update_map = new HashMap<String, Object>();
				update_map.put("id", id);
				update_map.put("dist_ips", dist_ips);
				return dbUtil.executeQuery(update_sql, update_map) > 0;
			}
		}
		return false;
	}

	public boolean exists_one_task(DynamicActionDO task) {
		String sql = "select id from sd_job_all_tasks where job_name=#job_name# and job_group_name=#job_group_name#";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("job_group_name", task.getJobGroupName());
		map.put("job_name", task.getJobName());
		List<Map<String, String>> queryAll = dbUtil.queryAll(sql, map);
		if (!queryAll.isEmpty()) {
			return true;
		}
		return false;
	}

	public boolean update_task(AllTasksDO task, String new_cron_expression, String run_status) {
		String sql = "update sd_job_all_tasks set cron_expression=#cron_expression#,run_status=#run_status#,modify_time=now() where job_name=#job_name# and job_group_name=#job_group_name#";
		Map<String, Object> map = new HashMap<String, Object>();

		map.put("job_name", task.getJobName());
		map.put("job_group_name", task.getJobGroupName());
		map.put("cron_expression", new_cron_expression);
		map.put("run_status", run_status);

		return dbUtil.executeQuery(sql, map) > 0;
	}

	public boolean update_task_last_action_no(String job_group_name, String job_name, String last_action_no, String last_action_result) {
		String sql = "update sd_job_all_tasks set last_action_no=#last_action_no#,last_action_result=#last_action_result#,modify_time=now() where job_name=#job_name# and job_group_name=#job_group_name#";
		Map<String, Object> map = new HashMap<String, Object>();

		map.put("job_name", job_name);
		map.put("job_group_name", job_group_name);
		map.put("last_action_no", last_action_no);
		map.put("last_action_result", last_action_result);

		return dbUtil.executeQuery(sql, map) > 0;
	}

	public boolean update_task_last_action_no(String job_group_name, String job_name, String last_action_no, String last_action_result, String dist_ips) {
		String sql = "update sd_job_all_tasks set last_action_no=#last_action_no#,last_action_result=#last_action_result#,modify_time=now(),dist_ips=#dist_ips# where job_name=#job_name# and job_group_name=#job_group_name#";
		Map<String, Object> map = new HashMap<String, Object>();

		map.put("job_name", job_name);
		map.put("job_group_name", job_group_name);
		map.put("last_action_no", last_action_no);
		map.put("last_action_result", last_action_result);
		map.put("dist_ips", dist_ips);

		return dbUtil.executeQuery(sql, map) > 0;
	}

	public boolean update_task_last_action_result(String job_group_name, String job_name, String last_action_result) {
		String sql = "update sd_job_all_tasks set last_action_result=#last_action_result#,modify_time=now() where job_name=#job_name# and job_group_name=#job_group_name#";
		Map<String, Object> map = new HashMap<String, Object>();

		map.put("job_name", job_name);
		map.put("job_group_name", job_group_name);
		map.put("last_action_result", last_action_result);

		return dbUtil.executeQuery(sql, map) > 0;
	}

	public boolean update_task_cron_expression(String job_group_name, String job_name, String cron_expression, String last_action_result) {
		String sql = "update sd_job_all_tasks set cron_expression=#cron_expression#,last_action_result=#last_action_result#,modify_time=now() where job_name=#job_name# and job_group_name=#job_group_name#";
		Map<String, Object> map = new HashMap<String, Object>();

		map.put("job_name", job_name);
		map.put("job_group_name", job_group_name);
		map.put("cron_expression", cron_expression);
		map.put("last_action_result", last_action_result);

		return dbUtil.executeQuery(sql, map) > 0;
	}

	public boolean update_task_run_status(String job_group_name, String job_name, String new_cron_expression, String run_status, String last_action_result) {
		String sql = "update sd_job_all_tasks set modify_time=now()";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("job_name", job_name);
		map.put("job_group_name", job_group_name);

		if (null != new_cron_expression && !"".equals(new_cron_expression.trim())) {
			sql = sql + ",cron_expression=#cron_expression#";
			map.put("cron_expression", new_cron_expression);
		}
		if (null != run_status && !"".equals(run_status)) {
			sql = sql + ",run_status=#run_status#";
			map.put("run_status", run_status);
		}
		if (null != last_action_result && !"".equals(last_action_result)) {
			sql = sql + ",last_action_result=#last_action_result#";
			map.put("last_action_result", last_action_result);
		}

		sql = sql + " where job_name=#job_name# and job_group_name=#job_group_name#";
		return dbUtil.executeQuery(sql, map) > 0;
	}

	public List<Map<String, String>> query_job_all_tasks(String job_group_name) {
		String sql = "select id,job_name,job_group_name,trigger_name,trigger_group_name,job_memo,job_time_type,job_environment_type,cron_expression,last_run_time,start_time,end_time,create_time,modify_time,run_status,last_action_result,only_run_once "
				+ " from sd_job_all_tasks ";
		Map<String, Object> map = new HashMap<String, Object>();
		if (null != job_group_name && !"".equals(job_group_name)) {
			sql = sql + " where job_group_name=#job_group_name#";
			map.put("job_group_name", job_group_name);
		}
		sql = sql + " order by modify_time desc ";

		return dbUtil.queryAll(sql, map);
	}

	public List<Map<String, String>> query_all_dynamic_job_tasks(boolean is_dynamic_add, String job_time_type) {
		String sql = "select id,job_name,job_group_name,trigger_name,trigger_group_name,job_memo,job_time_type,job_environment_type,simple_trigger_json,cron_expression,"
				+ "only_run_once,target_object,target_method,arguments,dist_ips,run_status,last_action_no,last_action_result,last_run_time,start_time,end_time,"
				+ "create_time,modify_time,start_delay,repeat_count,repeat_interval,is_dynamic_add  " + " from sd_job_all_tasks ";
		Map<String, Object> params = new HashMap<String, Object>();
		sql = sql + " where is_dynamic_add=#is_dynamic_add# and job_time_type=#job_time_type#";
		params.put("is_dynamic_add", is_dynamic_add ? 1 : 0);
		params.put("job_time_type", job_time_type);
		sql = sql + " order by modify_time desc ";
		List<Map<String, String>> list = dbUtil.queryAll(sql, params);
		return list;
	}

	public List<DynamicActionDO> query_all_dynamic_job_tasks() {
		List<Map<String, String>> list = query_all_dynamic_job_tasks(true, DynamicActionDO.JOB_TIME_TYPE_CRON);
		List<DynamicActionDO> returnList = new ArrayList<DynamicActionDO>();
		if (!list.isEmpty()) {
			for (Map<String, String> map : list) {
				DynamicActionDO dynamicActionDO = new DynamicActionDO();
				dynamicActionDO.setJobName(map.get("job_name"));
				dynamicActionDO.setJobGroupName(map.get("job_group_name"));
				dynamicActionDO.setBeanName(map.get("job_name"));
				dynamicActionDO.setTriggerGroupName(map.get("trigger_group_name"));
				dynamicActionDO.setTriggerName(map.get("trigger_name"));

				dynamicActionDO.setJobEnvironmentType(map.get("job_environment_type"));
				dynamicActionDO.setTargetObject(map.get("target_object"));
				dynamicActionDO.setTargetMethod(map.get("target_method"));

				List<String> ipList = new ArrayList<String>();
				String[] strArray = map.get("dist_ips").split(",");
				for (String str : strArray) {
					ipList.add(str);
				}

				List<String> paramList = new ArrayList<String>();
				String args = map.get("arguments");
				if (null != args) {
					String[] arguments = args.split(",");
					for (String str : arguments) {
						paramList.add(str);
					}
				}

				dynamicActionDO.setIpList(ipList);
				dynamicActionDO.setParamList(paramList);
				dynamicActionDO.setJobDesc(map.get("job_memo"));
				dynamicActionDO.setActionNo(map.get("last_action_no"));
				dynamicActionDO.setJobTimeType(map.get("job_time_type"));
				dynamicActionDO.setDynamicAdd(true);

				if (map.get("job_time_type").equals(DynamicActionDO.JOB_TIME_TYPE_SIMPLE)) {
					dynamicActionDO.setTriggerType(DynamicActionDO.SCHEDULE_TYPE_SIMPLE_TRIGGER);
				}
				if (map.get("job_time_type").equals(DynamicActionDO.JOB_TIME_TYPE_CRON)) {
					dynamicActionDO.setTriggerType(DynamicActionDO.SCHEDULE_TYPE_CRON_TRIGGER);
				}

				if (null != map.get("only_run_once") && map.get("only_run_once").equals("1")) {
					dynamicActionDO.setOnlyRunOnce(true);
					dynamicActionDO.setRepeatCount(0L);
					dynamicActionDO.setRepeatInterval(0L);
					dynamicActionDO.setStartDelay(Long.valueOf(map.get("start_delay")));

					Map<String, Object> simpleTriggerMap = new HashMap<String, Object>();
					simpleTriggerMap.put(MethodInvokingJobDetailFactoryBean.SIMPLE_TRIGGER_START_DELAY, dynamicActionDO.getStartDelay());
					simpleTriggerMap.put(MethodInvokingJobDetailFactoryBean.SIMPLE_TRIGGER_REPEATINTERVAL, dynamicActionDO.getRepeatInterval());
					simpleTriggerMap.put(MethodInvokingJobDetailFactoryBean.SIMPLE_TRIGGER_REPEATCOUNT, dynamicActionDO.getRepeatCount());
					dynamicActionDO.setSimpleTriggerMap(simpleTriggerMap);
				}
				if (null != map.get("only_run_once") && map.get("only_run_once").equals("0")) {
					dynamicActionDO.setCronExpression(map.get("cron_expression"));
				}
				returnList.add(dynamicActionDO);
			}
		}
		return returnList;
	}

	public Map<String, String> get_one_task(String job_group_name, String job_name) {
		String sql = "select id,job_name,job_group_name,trigger_name,trigger_group_name,job_memo,job_time_type,job_environment_type,cron_expression,last_run_time,start_time,end_time,create_time,modify_time,run_status,dist_ips,only_run_once,last_action_result "
				+ " from sd_job_all_tasks ";
		Map<String, Object> map = new HashMap<String, Object>();
		sql = sql + " where job_name=#job_name# and job_group_name=#job_group_name# ";
		map.put("job_group_name", job_group_name);
		map.put("job_name", job_name);

		List<Map<String, String>> queryAll = dbUtil.queryAll(sql, map);
		if (null != queryAll && !queryAll.isEmpty()) {
			return queryAll.get(0);
		}
		return null;
	}

	public boolean save_trigger_log(TriggerLogDO log) {
		String tableName = get_sd_job_trigger_log_table_name();
		String sql = "insert into  "
				+ tableName
				+ "(job_name,job_group_name,cron_expression,start_time,end_time,run_status,run_memo,ip) values (#job_name#,#job_group_name#,#cron_expression#,#start_time#,#end_time#,#run_status#,#run_memo#,#ip#)";

		Map<String, Object> map = new HashMap<String, Object>();

		map.put("job_name", log.getJobName());
		map.put("job_group_name", log.getJobGroupName());
		map.put("cron_expression", log.getCronExpression());

		map.put("start_time", log.getStartTime());
		map.put("end_time", log.getEndTime());
		map.put("run_status", log.getRunStatus());
		map.put("run_memo", log.getRunMemo());
		map.put("ip", log.getIp());

		return dbUtil.executeQuery(sql, map) > 0;
	}

	public List<Map<String, String>> query_trigger_logs(TriggerLogDO log) {
		String tableName = get_sd_job_trigger_log_table_name();
		String sql = "select id,job_name,job_group_name,cron_expression,start_time,end_time,run_status,create_time,ip,run_memo from  " + tableName
				+ " where job_name=#job_name# and job_group_name=#job_group_name# order by id desc limit 100";
		Map<String, Object> map = new HashMap<String, Object>();

		map.put("job_name", log.getJobName());
		map.put("job_group_name", log.getJobGroupName());

		return dbUtil.queryAll(sql, map);
	}

	public boolean save_operator_log(OperatorLogDO log) {
		String sql = "insert into sd_job_operator_log(operator,content,type,job_name,job_group_name,action_no) values (#operator#,#content#,#type#,#job_name#,#job_group_name#,#action_no#)";

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("operator", log.getOperator());
		map.put("content", log.getContent());
		map.put("type", log.getType());

		map.put("job_name", log.getJobName());
		map.put("job_group_name", log.getJobGroupName());
		map.put("action_no", log.getActionNo());
		return dbUtil.executeQuery(sql, map) > 0;
	}

	public boolean update_operator_log(String actionNo) {
		String sql = "update sd_job_operator_log set success=1 where action_no=#action_no#";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("action_no", actionNo);
		return dbUtil.executeQuery(sql, map) > 0;
	}

	public List<Map<String, String>> query_all_operator_log(String job_name, String job_group_name) {
		String sql = "select id, operator,content,type,create_time,job_name,job_group_name,success "
				+ " from sd_job_operator_log where job_name=#job_name# and job_group_name=#job_group_name# order by id desc limit 100";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("job_group_name", job_group_name);
		map.put("job_name", job_name);
		return dbUtil.queryAll(sql, map);
	}

	public Map<String, String> get_one_user(String user_name, String pass_word) {
		String sql = "select id,user_name,pass_word,role,is_delete,create_time,modify_time,group_names from sd_job_users where user_name=#user_name# and pass_word=#pass_word#";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("user_name", user_name);
		map.put("pass_word", pass_word);
		List<Map<String, String>> queryAll = dbUtil.queryAll(sql, map);
		if (!queryAll.isEmpty()) {
			return queryAll.get(0);
		}
		return null;
	}

	public List<Map<String, String>> query_all_users() {
		String sql = "select id,user_name,pass_word,role,is_delete,create_time,modify_time,group_names from sd_job_users order by id desc limit 200";
		Map<String, Object> map = new HashMap<String, Object>();
		return dbUtil.queryAll(sql, map);
	}

	public boolean save_user(UsersDO user) {
		String sql = "insert into sd_job_users(user_name,pass_word,role,group_names) values (#user_name#,#pass_word#,#role#,#group_names#)";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("user_name", user.getUserName());
		map.put("pass_word", user.getPassWord());
		map.put("group_names", user.getGroupNames());
		map.put("role", user.getRole());
		return dbUtil.executeQuery(sql, map) > 0;
	}

	public boolean update_user(UsersDO user) {
		String sql = "update sd_job_users set pass_word=#pass_word#,modify_time=now() where id=#id# ";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", user.getId());
		map.put("pass_word", user.getPassWord());
		return dbUtil.executeQuery(sql, map) > 0;
	}

	public boolean save_action_result(ActionDO action) {
		String sql = "insert into sd_action_result(job_group_name,job_name,action,action_no,ip,cron_expression) values (#job_group_name#,#job_name#,#action#,#action_no#,#ip#,#cron_expression#)";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("job_group_name", action.getJobGroupName());
		map.put("job_name", action.getJobName());
		map.put("action", action.getAction());
		map.put("action_no", action.getActionNo());
		map.put("ip", action.getIp());
		String cronExpression = action.getCronExpression();
		if (null == action.getCronExpression()) {
			cronExpression = "";
		}
		map.put("cron_expression", cronExpression);
		return dbUtil.executeQuery(sql, map) > 0;
	}

	public boolean save_action_result(DynamicActionDO action) {
		String sql = "insert into sd_action_result(job_group_name,job_name,action,action_no,ip,cron_expression,simple_trigger_json,only_run_once,dist_ips) values (#job_group_name#,#job_name#,#action#,#action_no#,#ip#,#cron_expression#,#simple_trigger_json#,#only_run_once#,#dist_ips#)";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("job_group_name", action.getJobGroupName());
		map.put("job_name", action.getJobName());
		map.put("action", action.getAction());
		map.put("action_no", action.getActionNo());
		map.put("ip", action.getIpList().toString());
		map.put("dist_ips", action.getIpList().toString());
		if (action.isOnlyRunOnce()) {
			map.put("simple_trigger_json", action.getSimpleTriggerMap().toString());
		} else {
			map.put("simple_trigger_json", "");
		}
		map.put("only_run_once", action.isOnlyRunOnce() ? 1 : 0);

		String cronExpression = action.getCronExpression();
		if (null == action.getCronExpression()) {
			cronExpression = "";
		}
		map.put("cron_expression", cronExpression);
		return dbUtil.executeQuery(sql, map) > 0;
	}

	public List<Map<String, String>> query_action_result(ActionDO action) {
		String sql = "select id,job_group_name,job_name,action,action_no,ip,create_time from sd_action_result where job_name=#job_name# and job_group_name=#job_group_name# and action_no=#action_no# ";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("job_group_name", action.getJobGroupName());
		map.put("action_no", action.getActionNo());
		map.put("job_name", action.getJobName());
		List<Map<String, String>> queryAll = dbUtil.queryAll(sql, map);
		return queryAll;
	}

	public Map<String, String> query_job_trigger_log_count() {
		String tableName = get_sd_job_trigger_log_table_name();
		String sql = "select count(id) as record from " + tableName + " ";
		Map<String, Object> map = new HashMap<String, Object>();
		List<Map<String, String>> queryAll = dbUtil.queryAll(sql, map);
		if (!queryAll.isEmpty()) {
			return queryAll.get(0);
		}
		return new HashMap<String, String>();
	}

	public static String getYearMonth(String filePath) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");// yyyyMMddHHmmss
		String format = sdf.format(new Date());
		return filePath + "_" + format;
	}

	public static String get_sd_job_trigger_log_table_name() {
		return getYearMonth("sd_job_trigger_log");
	}

/*	public static void main(String args[]) {
		for (int i = 1; i < 13; i++) {
			String str = "" + i;
			if (i < 10) {
				str = "0" + i;
			}
			String table = "CREATE TABLE sd_job_trigger_log_2018"
					+ str
					+ "(id int(11) NOT NULL AUTO_INCREMENT,job_name varchar(100) DEFAULT NULL COMMENT 'job名', job_group_name varchar(100) DEFAULT NULL COMMENT 'job所属组',cron_expression varchar(100) DEFAULT NULL COMMENT '时间表达式',  run_memo longtext COMMENT '运行信息备注',  start_time bigint(20) DEFAULT NULL COMMENT '方法运行开始时间',  end_time bigint(20) DEFAULT NULL COMMENT '方法运行结束时间',  run_status int(11) DEFAULT NULL COMMENT 'job运行是否成功',  create_time datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',  ip varchar(50) DEFAULT NULL COMMENT 'IP',  PRIMARY KEY (id),  KEY index_jobName_jobGroupName (job_name,job_group_name)) ENGINE=InnoDB AUTO_INCREMENT=10598 DEFAULT CHARSET=utf8 COMMENT='任务运行日志表';";
			System.out.println(table);
		}
	}*/

}
