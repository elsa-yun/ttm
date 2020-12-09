package com.elsa.ttm.quartz.ao;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.JobExecutionException;

import com.elsa.ttm.quartz.manager.CancleItemManager;

public class CancleItemAO {

	private static final Log logger = LogFactory.getLog(CancleItemAO.class);

	private CancleItemManager cancleItemManager;

	public CancleItemManager getCancleItemManager() {
		return cancleItemManager;
	}

	public void setCancleItemManager(CancleItemManager cancleItemManager) {
		this.cancleItemManager = cancleItemManager;
	}

	public boolean executeTestArgs(List<Integer> list) throws JobExecutionException {
		logger.info("=============execute CancleItemAO==============" + System.currentTimeMillis());
		String cancleOrder = cancleItemManager.cancleOrder("1234567890 id=>" + list.toString());
		logger.info("=============cancleItemManager==============" + cancleOrder);
		return true;
	}

	public boolean executeTest() throws JobExecutionException {
		logger.info("=============execute CancleItemAO==============" + System.currentTimeMillis());
		String cancleOrder = cancleItemManager.cancleOrder("1234567890 id=>");
		logger.info("=============cancleItemManager==============" + cancleOrder);
		return true;
	}

}
