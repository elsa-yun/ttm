package com.elsa.ttm.manager;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.elsa.ttm.domain.ActionDO;
import com.elsa.ttm.domain.AllTasksDO;
import com.elsa.ttm.domain.DynamicActionDO;
import com.elsa.ttm.domain.JobDO;
import com.elsa.ttm.util.ScheduleUtil;
import com.elsa.ttm.util.ZKUtil;
import com.elsa.ttm.watcher.ActionWatcher;
import com.elsa.ttm.watcher.DynamicActionWatcher;

/**
 * @author longhaisheng
 * 
 */
public class ClientManager {

	public static final String REMOVE_JOBS = "remove_jobs";

	public static final String NORMARL_JOBS = "normal_jobs";

	public static final String PAUSE_JOBS = "pause_jobs";

	private ZKManager zkManager;

	private ScheduleManager scheduleManager;

	private DBManager dbManager;

	public Gson gson;

	private ActionWatcher actionWatcher;

	private DynamicActionWatcher dynamicWatcher;

	private static volatile boolean isLoad = false;

	private static final Log logger = LogFactory.getLog(ClientManager.class);

	public ClientManager() {
		gson = new GsonBuilder().registerTypeAdapter(Timestamp.class, new TimestampTypeAdapter()).setDateFormat("yyyy-MM-dd HH:mm:ss").create();

	}

	public ZooKeeper getZookeeper() {
		return zkManager.getZookeeper();
	}

	public void init() {
		List<DynamicActionDO> list = dbManager.query_all_dynamic_job_tasks();
		if (!list.isEmpty()) {
			for (DynamicActionDO dynamicActionDO : list) {
				scheduleManager.dynamicAddJob(dynamicActionDO);
			}
		}
	}

	public void addTaskPath(final AllTasksDO taskDO) throws Exception {
		String job_path = getJobPath(taskDO);
		final String action_path = job_path + ZKManager.SEPARATOR + "action";
		String result_path = job_path + ZKManager.SEPARATOR + "result";
		String last_operation_path = job_path + ZKManager.SEPARATOR + "last_operation";
		String lock_path = job_path + ZKManager.SEPARATOR + "lock";
		ZKUtil.createPath(getZookeeper(), job_path, CreateMode.PERSISTENT, zkManager.getAcl());
		ZKUtil.createPath(getZookeeper(), action_path, CreateMode.PERSISTENT, zkManager.getAcl());
		ZKUtil.createPath(getZookeeper(), result_path, CreateMode.PERSISTENT, zkManager.getAcl());
		ZKUtil.createPath(getZookeeper(), last_operation_path, CreateMode.PERSISTENT, zkManager.getAcl());
		ZKUtil.createPath(getZookeeper(), lock_path, CreateMode.PERSISTENT, zkManager.getAcl());

		if (getZookeeper().getData(job_path, false, null) == null) {
			set_task_value(taskDO, job_path);
		}
		get_task_action_value(action_path);
		register_local_ip(taskDO.getJobGroupName());

		if (!isLoad) {
			String dynamic_action_path = get_dynamic_add_action_path();
			String dynamic_result_path = get_dynamic_add_result_path();
			try {
				ZKUtil.createPath(getZookeeper(), dynamic_action_path, CreateMode.PERSISTENT, zkManager.getAcl());
				ZKUtil.createPath(getZookeeper(), dynamic_result_path, CreateMode.PERSISTENT, zkManager.getAcl());
				get_dynamic_action_value(dynamic_action_path);
				isLoad = true;
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}
	}

	public List<String> get_dynamic_add_result_ips() {
		List<String> children = null;
		try {
			children = getZookeeper().getChildren(get_dynamic_add_result_path(), false, null);
		} catch (KeeperException e) {
			logger.error(e.getMessage(), e);
		} catch (InterruptedException e) {
			logger.error(e.getMessage(), e);
		}
		return children;
	}

	public List<String> delete_dynamic_add_result_ips() {
		String dynamic_result_path = get_dynamic_add_result_path();
		List<String> children = null;
		try {
			children = getZookeeper().getChildren(dynamic_result_path, false, null);
			if (null != children && !children.isEmpty()) {
				for (String str : children) {
					getZookeeper().delete(dynamic_result_path + ZKManager.SEPARATOR + str, -1);
				}
			}
		} catch (KeeperException e) {
			logger.error(e.getMessage(), e);
		} catch (InterruptedException e) {
			logger.error(e.getMessage(), e);
		}
		return children;
	}

	public boolean set_null_for_dynamic_add_action_path_value() {
		String dynamic_action_path = get_dynamic_add_action_path();
		boolean suc = false;
		try {
			if (getZookeeper().exists(dynamic_action_path, false) != null) {
				getZookeeper().setData(dynamic_action_path, null, -1);
			}
			suc = true;
		} catch (KeeperException e) {
			logger.error(e.getMessage(), e);
		} catch (InterruptedException e) {
			logger.error(e.getMessage(), e);
		}

		return suc;
	}

	private String get_dynamic_add_action_path() {
		return getRootPath() + ZKManager.SEPARATOR + "dynamic" + ZKManager.SEPARATOR + "action";
	}

	private String get_dynamic_add_result_path() {
		return getRootPath() + ZKManager.SEPARATOR + "dynamic" + ZKManager.SEPARATOR + "result";
	}

	public String get_action_path(String job_group_name, String job_name) {
		return getJobPath(job_group_name, job_name) + ZKManager.SEPARATOR + "action";
	}

	public String get_result_path(String job_group_name, String job_name) {
		return getJobPath(job_group_name, job_name) + ZKManager.SEPARATOR + "result";
	}

	public List<String> get_result_ips(String job_group_name, String job_name) {
		String result_path = get_result_path(job_group_name, job_name);
		List<String> children = null;
		try {
			children = getZookeeper().getChildren(result_path, false, null);
		} catch (KeeperException e) {
			logger.error(e.getMessage(), e);
		} catch (InterruptedException e) {
			logger.error(e.getMessage(), e);
		}
		return children;
	}

	public List<String> delete_result_ips(String job_group_name, String job_name) {
		String result_path = get_result_path(job_group_name, job_name);
		List<String> children = null;
		try {
			children = getZookeeper().getChildren(result_path, false, null);
			if (null != children && !children.isEmpty()) {
				for (String str : children) {
					getZookeeper().delete(result_path + ZKManager.SEPARATOR + str, -1);
				}

			}
		} catch (KeeperException e) {
			logger.error(e.getMessage(), e);
		} catch (InterruptedException e) {
			logger.error(e.getMessage(), e);
		}
		return children;
	}

	public boolean set_action_path_value(ActionDO action) {
		String action_path = get_action_path(action.getJobGroupName(), action.getJobName());
		boolean suc = false;
		try {
			String json = gson.toJson(action);
			getZookeeper().setData(action_path, json.getBytes(), -1);
			suc = true;
		} catch (KeeperException e) {
			logger.error(e.getMessage(), e);
		} catch (InterruptedException e) {
			logger.error(e.getMessage(), e);
		}

		return suc;
	}

	public boolean set_null_for_action_path_value(ActionDO action) {
		String action_path = get_action_path(action.getJobGroupName(), action.getJobName());
		boolean suc = false;
		try {
			getZookeeper().setData(action_path, null, -1);
			suc = true;
		} catch (KeeperException e) {
			logger.error(e.getMessage(), e);
		} catch (InterruptedException e) {
			logger.error(e.getMessage(), e);
		}

		return suc;
	}

	public ActionDO get_task_action_value(ActionDO action) {
		String action_path = get_action_path(action.getJobGroupName(), action.getJobName());
		ActionDO act = null;
		try {
			act = get_task_action_value(action_path);
		} catch (KeeperException e) {
			logger.error(e.getMessage(), e);
		} catch (InterruptedException e) {
			logger.error(e.getMessage(), e);
		}

		return act;
	}

	public void set_task_value(AllTasksDO taskDO, String job_group_name, String job_name) throws Exception {
		String job_path = getJobPath(job_group_name, job_name);
		this.set_task_value(taskDO, job_path);
	}

	public void set_task_value(AllTasksDO taskDO, String job_path) throws Exception {
		String valueString = this.gson.toJson(taskDO);
		getZookeeper().setData(job_path, valueString.getBytes(), -1);
	}

	public void set_task_action_result_ip(String job_group_name, String job_name) throws Exception {
		String result_path = get_result_path(job_group_name, job_name) + ZKManager.SEPARATOR + ScheduleUtil.getLocalIP();
		if (getZookeeper().exists(result_path, false) == null) {
			String path = getZookeeper().create(result_path, null, zkManager.getAcl(), CreateMode.PERSISTENT);
			logger.info("set_task_action_result_ip job_group_name=>" + job_group_name + " job_name=>" + job_name + ";result create path=>" + path);
		} else {
			logger.info("set_task_action_result_ip job_group_name=>" + job_group_name + " job_name=>" + job_name + ";result_path exists =>" + result_path);
		}
	}

	public ActionDO get_task_action_value(final String action_path) throws KeeperException, InterruptedException {
		if (null == this.actionWatcher) {
			this.actionWatcher = new ActionWatcher(this);
		}
		logger.info("############################################################################################################action_path=>" + action_path);
		byte[] data = getZookeeper().getData(action_path, this.actionWatcher, new Stat());
		if (data != null) {
			return this.gson.fromJson(new String(data), ActionDO.class);
		}
		return null;
	}

	public DynamicActionDO get_dynamic_action_value(final String dynamic_action_path) throws KeeperException, InterruptedException {
		if (null == this.dynamicWatcher) {
			this.dynamicWatcher = new DynamicActionWatcher(this);
		}
		byte[] data = getZookeeper().getData(dynamic_action_path, this.dynamicWatcher, new Stat());
		if (data != null) {
			return this.gson.fromJson(new String(data), DynamicActionDO.class);
		}
		return null;
	}

	public DynamicActionDO get_dynamic_action_value() {
		String dynamic_action_path = get_dynamic_add_action_path();
		try {
			return get_dynamic_action_value(dynamic_action_path);
		} catch (KeeperException e) {
			logger.error(e.getMessage(), e);
		} catch (InterruptedException e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	public boolean set_dynamic_action_path_value(DynamicActionDO action) {
		String dynamic_action_path = get_dynamic_add_action_path();
		boolean suc = false;
		try {
			if (getZookeeper().exists(dynamic_action_path, false) != null) {
				String json = gson.toJson(action);
				getZookeeper().setData(dynamic_action_path, json.getBytes(), -1);
				suc = true;
			}
		} catch (KeeperException e) {
			logger.error(e.getMessage(), e);
		} catch (InterruptedException e) {
			logger.error(e.getMessage(), e);
		}

		return suc;
	}

	public String set_dynamic_action_result_ip() throws Exception {
		String dynamic_result_path = get_dynamic_add_result_path();

		String result_path = dynamic_result_path + ZKManager.SEPARATOR + ScheduleUtil.getLocalIP();
		String path = "";
		if (getZookeeper().exists(result_path, false) == null) {
			path = getZookeeper().create(result_path, null, zkManager.getAcl(), CreateMode.PERSISTENT);
		} else {
			path = result_path;
		}
		return path;
	}

	public boolean existsJob(String job_group_name, String job_name) throws KeeperException, InterruptedException {
		String job_path = getJobPath(job_group_name, job_name);
		if (getZookeeper().exists(job_path, false) != null) {
			return true;
		}
		return false;
	}

	public boolean register_local_ip(String job_group_name) throws KeeperException, InterruptedException {
		String path = getRootPath() + ZKManager.SEPARATOR + job_group_name + "_alive_ips";
		if (getZookeeper().exists(path, false) == null) {
			getZookeeper().create(path, null, zkManager.getAcl(), CreateMode.PERSISTENT);
		}

		String ip_path = getRootPath() + ZKManager.SEPARATOR + job_group_name + "_alive_ips" + ZKManager.SEPARATOR + ScheduleUtil.getLocalIP();
		if (getZookeeper().exists(ip_path, false) == null) {
			getZookeeper().create(ip_path, null, zkManager.getAcl(), CreateMode.PERSISTENT);
		}
		return false;
	}

	private boolean deleteLocalIP(String job_group_name, String ip) throws KeeperException, InterruptedException {
		String path = getRootPath() + ZKManager.SEPARATOR + job_group_name + "_alive_ips";
		if (getZookeeper().exists(path, false) == null) {
			getZookeeper().create(path, null, zkManager.getAcl(), CreateMode.PERSISTENT);
		}

		String ip_path = getRootPath() + ZKManager.SEPARATOR + job_group_name + "_alive_ips" + ZKManager.SEPARATOR + ip;
		if (getZookeeper().exists(ip_path, false) != null) {
			getZookeeper().delete(ip_path, -1);
			return true;
		}
		return false;
	}

	public List<String> get_job_group_alive_ips(String job_group_name) {
		String path = getRootPath() + ZKManager.SEPARATOR + job_group_name + "_alive_ips";
		List<String> list = null;
		try {
			list = getZookeeper().getChildren(path, false);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return list;
	}

	private List<String> get_all_job_group_names() {
		String path = getRootPath();
		List<String> list = null;
		List<String> returnList = new ArrayList<String>();
		try {
			list = getZookeeper().getChildren(path, false);
			if (!list.isEmpty()) {
				for (String str : list) {
					if (str.endsWith("_job_group_name")) {
						returnList.add(str);
					}
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return returnList;
	}

	public void repeat_regist_local_job_group_name_all_watchers() {
		String root_path = getRootPath();
		List<String> groupNameList = get_all_job_group_names();
		if (null != groupNameList && !groupNameList.isEmpty()) {
			for (String groupName : groupNameList) {
				if (groupName.equals(scheduleManager.getJobGroupName())) {
					String group_path = root_path + ZKManager.SEPARATOR + groupName;
					try {
						List<String> jobList = getZookeeper().getChildren(group_path, false);
						if (null != jobList && !jobList.isEmpty()) {
							for (String jobName : jobList) {
								String job_action_path = group_path + ZKManager.SEPARATOR + jobName + ZKManager.SEPARATOR + "action";
								get_task_action_value(job_action_path);
							}
						}
					} catch (Exception e) {
						logger.error(e.getMessage(), e);
					}
				}
			}
		}
		String dynamic_action_path = get_dynamic_add_action_path();
		try {
			get_dynamic_action_value(dynamic_action_path);
		} catch (KeeperException e) {
			logger.error(e.getMessage(), e);
		} catch (InterruptedException e) {
			logger.error(e.getMessage(), e);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	public AllTasksDO getJobRunStatus(String job_group_name, String job_name) throws KeeperException, InterruptedException {
		String job_path = getJobPath(job_group_name, job_name);
		if (existsJob(job_group_name, job_name)) {
			byte[] data = getZookeeper().getData(job_path, false, null);
			if (data != null) {
				AllTasksDO task = this.gson.fromJson(new String(data), AllTasksDO.class);
				return task;
			}
		}
		return null;
	}

	public boolean create_jobGroupName_jobName_path(String job_group_name, String job_name) {
		String job_path = getJobPath(job_group_name, job_name);
		String lock_path = job_path + ZKManager.SEPARATOR + "lock";
		boolean is_success = false;
		try {
			ZKUtil.createPath(getZookeeper(), lock_path, CreateMode.PERSISTENT, zkManager.getAcl());
			is_success = true;
		} catch (Exception e) {
			is_success = false;
		}
		return is_success;
	}

	public boolean lock(String job_group_name, String job_name) {
		String job_path = getJobPath(job_group_name, job_name);
		String lock_path = job_path + ZKManager.SEPARATOR + "lock" + ZKManager.SEPARATOR + job_group_name + "_" + job_name;
		boolean is_success = false;
		try {
			getZookeeper().create(lock_path, null, zkManager.getAcl(), CreateMode.EPHEMERAL);
			is_success = true;
		} catch (Exception e) {
			is_success = false;
		}
		return is_success;
	}

	public boolean unLock(String job_group_name, String job_name) {
		String job_path = getJobPath(job_group_name, job_name);
		String lock_path = job_path + ZKManager.SEPARATOR + "lock" + ZKManager.SEPARATOR + job_group_name + "_" + job_name;
		boolean is_success = false;
		try {
			if (null != getZookeeper().exists(lock_path, false)) {
				getZookeeper().delete(lock_path, -1);
			}
			is_success = true;
		} catch (Exception e) {
			is_success = false;
		}
		return is_success;
	}

	private String getJobPath(AllTasksDO task) {
		return getRootPath() + ZKManager.SEPARATOR + task.getJobGroupName() + ZKManager.SEPARATOR + task.getJobName();
	}

	private String getJobPath(String job_group_name, String job_name) {
		return getRootPath() + ZKManager.SEPARATOR + job_group_name + ZKManager.SEPARATOR + job_name;
	}

	public void registJobAliveIP(JobDO job) throws Exception {
		String path = getRootPath() + ZKManager.SEPARATOR + job.getJobGroupName() + ZKManager.SEPARATOR + ScheduleUtil.getLocalIP();
		ZKUtil.createPath(getZookeeper(), path, CreateMode.EPHEMERAL, zkManager.getAcl());
	}

	public void createGroupNodes(String job_group_name) throws Exception {
		String pause_list_path = getRootPath() + ZKManager.SEPARATOR + job_group_name + ZKManager.SEPARATOR + PAUSE_JOBS;
		String remove_list_path = getRootPath() + ZKManager.SEPARATOR + job_group_name + ZKManager.SEPARATOR + REMOVE_JOBS;
		String normal_list_path = getRootPath() + ZKManager.SEPARATOR + job_group_name + ZKManager.SEPARATOR + NORMARL_JOBS;
		ZKUtil.createPath(getZookeeper(), pause_list_path, CreateMode.PERSISTENT, zkManager.getAcl());
		ZKUtil.createPath(getZookeeper(), remove_list_path, CreateMode.PERSISTENT, zkManager.getAcl());
		ZKUtil.createPath(getZookeeper(), normal_list_path, CreateMode.PERSISTENT, zkManager.getAcl());
	}

	public void addPauseNode(String job_group_name, String job_name) throws Exception {
		String pause_path = getRootPath() + ZKManager.SEPARATOR + job_group_name + ZKManager.SEPARATOR + PAUSE_JOBS + ZKManager.SEPARATOR + job_name;
		if (getZookeeper().exists(pause_path, false) == null) {
			getZookeeper().create(pause_path, null, zkManager.getAcl(), CreateMode.PERSISTENT);
		}
	}

	public void addRemoveNode(String job_group_name, String job_name) throws Exception {
		String remove_path = getRootPath() + ZKManager.SEPARATOR + job_group_name + ZKManager.SEPARATOR + REMOVE_JOBS + ZKManager.SEPARATOR + job_name;
		if (getZookeeper().exists(remove_path, false) == null) {
			getZookeeper().create(remove_path, null, zkManager.getAcl(), CreateMode.PERSISTENT);
		}
	}

	public void deleteFromPauseNode(String job_group_name, String job_name) throws Exception {
		String path = getRootPath() + ZKManager.SEPARATOR + job_group_name + ZKManager.SEPARATOR + PAUSE_JOBS + ZKManager.SEPARATOR + job_name;
		if (getZookeeper().exists(path, false) != null) {
			getZookeeper().delete(path, -1);
		}
	}

	public void deleteFromRemoveNode(String job_group_name, String job_name) throws Exception {
		String path = getRootPath() + ZKManager.SEPARATOR + job_group_name + ZKManager.SEPARATOR + REMOVE_JOBS + ZKManager.SEPARATOR + job_name;
		if (getZookeeper().exists(path, false) != null) {
			getZookeeper().delete(path, -1);
		}
	}

	public AllTasksDO getOneTask(String jobGroupName, String jobName) throws Exception {
		String path = getRootPath() + ZKManager.SEPARATOR + jobGroupName + ZKManager.SEPARATOR + jobName;
		if (getZookeeper().exists(path, false) == null) {
			return null;
		}
		String valueString = new String(getZookeeper().getData(path, false, null));
		AllTasksDO job = (AllTasksDO) this.gson.fromJson(valueString, AllTasksDO.class);
		return job;
	}

	public boolean update(JobDO job) throws Exception {
		String path = getRootPath() + ZKManager.SEPARATOR + job.getJobGroupName() + ZKManager.SEPARATOR + job.getJobName();
		if (getZookeeper().exists(path, false) == null) {
			String valueString = this.gson.toJson(job);
			getZookeeper().setData(path, valueString.getBytes(), -1);
			return true;
		}
		return false;
	}

	public static String get_uuid() {
		return UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
	}

	public String getRootPath() {
		return zkManager.getZkConfig().getRootPath();
	}

	public ZKManager getZkManager() {
		return zkManager;
	}

	public void setZkManager(ZKManager zkManager) {
		this.zkManager = zkManager;
	}

	public ScheduleManager getScheduleManager() {
		return scheduleManager;
	}

	public void setScheduleManager(ScheduleManager scheduleManager) {
		this.scheduleManager = scheduleManager;
	}

	public DBManager getDbManager() {
		return dbManager;
	}

	public void setDbManager(DBManager dbManager) {
		this.dbManager = dbManager;
	}

}
