package com.elsa.ttm.watcher;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.KeeperState;

import com.elsa.ttm.constant.TaskConstant;
import com.elsa.ttm.domain.ActionDO;
import com.elsa.ttm.manager.ClientManager;
import com.elsa.ttm.util.ScheduleUtil;

/**
 * @author longhaisheng
 * 
 */
public class ActionWatcher implements Watcher {

	private static final Log logger = LogFactory.getLog(ActionWatcher.class);

	private ClientManager clientManager;

	public ActionWatcher(ClientManager clientManager) {
		this.clientManager = clientManager;
	}

	@Override
	public void process(WatchedEvent event) {
		String action_path = event.getPath();
		logger.info("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ActionWatcher process action_path@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@=>" + action_path);
		if (event.getType() == Event.EventType.NodeDataChanged) {
			ActionDO action = null;
			try {
				action = clientManager.get_task_action_value(action_path);
			} catch (KeeperException e) {
				logger.error(e.getMessage(), e);
			} catch (InterruptedException e) {
				logger.error(e.getMessage(), e);
			}
			if (null != action && null != action.getAction()) {
				String str = action.getAction();
				String job_group_name = action.getJobGroupName();
				String job_name = action.getJobName();
				if (str.equals(TaskConstant.TASK_STATUS_PAUSE)) {
					try {
						boolean pauseJob = clientManager.getScheduleManager().pauseJob(job_name);
						if (pauseJob) {
							clientManager.set_task_action_result_ip(job_group_name, job_name);// 并在result下注册节点IP
							action.setIp(ScheduleUtil.getLocalIP());
							clientManager.getDbManager().save_action_result(action);// 在action_result表中插入结果集
						}
					} catch (Exception e) {
						logger.error(e.getMessage(), e);
					}
				}
				if (str.equals(TaskConstant.TASK_STATUS_NORMAL)) {
					try {
						boolean resumeJob = clientManager.getScheduleManager().resumeJob(job_name);
						if (resumeJob) {
							clientManager.set_task_action_result_ip(job_group_name, job_name);
							action.setIp(ScheduleUtil.getLocalIP());
							clientManager.getDbManager().save_action_result(action);
						}
					} catch (Exception e) {
						logger.error(e.getMessage(), e);
					}
				}
				if (str.equals(TaskConstant.TASK_STATUS_TRIGGER_AT_ONCE)) {
					try {
						boolean triggerOnce = clientManager.getScheduleManager().triggerJob(job_name);
						if (triggerOnce) {
							clientManager.set_task_action_result_ip(job_group_name, job_name);
							action.setIp(ScheduleUtil.getLocalIP());
							clientManager.getDbManager().save_action_result(action);
						}
					} catch (Exception e) {
						logger.error(e.getMessage(), e);
					}
				}
				if (str.equals(TaskConstant.TASK_STATUS_REMOVE)) {
					try {
						boolean removeJob = clientManager.getScheduleManager().removeJob(job_name);
						if (removeJob) {
							clientManager.set_task_action_result_ip(job_group_name, job_name);
							action.setIp(ScheduleUtil.getLocalIP());
							clientManager.getDbManager().save_action_result(action);
						}
					} catch (Exception e) {
						logger.error(e.getMessage(), e);
					}
				}
				if (str.equals(TaskConstant.TASK_STATUS_UPDATE_CRON_EXPRESSION)) {
					try {
						if (null != action.getCronExpression() && !"".equals(action.getCronExpression())) {
							boolean updateCron = clientManager.getScheduleManager().updateJobCronExpression(job_name, action.getCronExpression());
							if (updateCron) {
								clientManager.set_task_action_result_ip(job_group_name, job_name);
								action.setIp(ScheduleUtil.getLocalIP());
								clientManager.getDbManager().save_action_result(action);
							}
						}
					} catch (Exception e) {
						logger.error(e.getMessage(), e);
					}
				}
				if (str.equals(TaskConstant.TASK_STATUS_ADD_FROM_REMOVE)) {
					try {
						boolean addFromRemove = clientManager.getScheduleManager().addRemoveJob(job_name);
						if (addFromRemove) {
							clientManager.set_task_action_result_ip(job_group_name, job_name);
							action.setIp(ScheduleUtil.getLocalIP());
							clientManager.getDbManager().save_action_result(action);
						}
					} catch (Exception e) {
						logger.error(e.getMessage(), e);
					}
				}
			}
		} else if (event.getState() == KeeperState.SyncConnected) {
			logger.info("收到ZK连接成功事件！");
		} else if (event.getState() == KeeperState.Expired) {
			logger.error("会话超时，等待重新建立ZK连接");
			try {
				clientManager.getZkManager().reConnection();
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}
	}
}
