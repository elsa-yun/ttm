package com.elsa.ttm.watcher;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.KeeperState;

import com.elsa.ttm.constant.TaskConstant;
import com.elsa.ttm.domain.DynamicActionDO;
import com.elsa.ttm.manager.ClientManager;

/**
 * @author longhaisheng
 *
 */
public class DynamicActionWatcher implements Watcher {

	private static final Log logger = LogFactory.getLog(ActionWatcher.class);

	private ClientManager clientManager;

	public DynamicActionWatcher(ClientManager clientManager) {
		this.clientManager = clientManager;
	}

	@Override
	public void process(WatchedEvent event) {
		String action_path = event.getPath();
		logger.info("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@DynamicActionWatcher process action_path@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@=>" + action_path);
		if (event.getType() == Event.EventType.NodeDataChanged) {
			DynamicActionDO action = null;
			try {
				action = clientManager.get_dynamic_action_value(action_path);
			} catch (KeeperException e) {
				logger.error(e.getMessage(), e);
			} catch (InterruptedException e) {
				logger.error(e.getMessage(), e);
			}
			if (null != action && null != action.getAction()) {
				String str = action.getAction();
				if (str.equals(TaskConstant.TASK_STATUS_DYNAMIC_ADD_JOB)) {
					try {
						boolean dynamicAddJob = clientManager.getScheduleManager().dynamicAddJob(action);
						logger.info("DynamicActionWatcher===>" + dynamicAddJob + ":::" + action.toString());
						if (dynamicAddJob) {
							String path = clientManager.set_dynamic_action_result_ip();
							logger.info("DynamicActionWatcher===>" + dynamicAddJob + ":::path=>" + path);
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
