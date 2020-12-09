package com.elsa.ttm.quartz.ao;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.JobExecutionException;

import com.elsa.ttm.manager.DBManager;

public class ClearTriggerLogAO {

	private static final Log logger = LogFactory.getLog(ClearTriggerLogAO.class);

	private DBManager dbManager;

	public DBManager getDbManager() {
		return dbManager;
	}

	public void setDbManager(DBManager dbManager) {
		this.dbManager = dbManager;
	}

	public boolean deleteTriggerLog() throws JobExecutionException {// 每天运行一次
		logger.info("=============ClearTriggerLogAO execute deleteTriggerLog==============" + System.currentTimeMillis());
		Map<String, String> recordMap = dbManager.query_job_trigger_log_count();
		int num = 0;
		if (!recordMap.isEmpty()) {
			num = Integer.valueOf(recordMap.get("record"));
		}
		logger.info("=============ClearTriggerLogAO query_job_trigger_log has count==============>" + num);
		return true;
	}

}
