package com.elsa.ttm.quartz.ao;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.elsa.ttm.quartz.manager.CancleItemManager;
import com.elsa.ttm.schedule.IMultiSchedule;

public class CancleOrderAO implements IMultiSchedule {

	private static final Log logger = LogFactory.getLog(CancleOrderAO.class);

	private CancleItemManager cancleItemManager;

	public CancleItemManager getCancleItemManager() {
		return cancleItemManager;
	}

	public void setCancleItemManager(CancleItemManager cancleItemManager) {
		this.cancleItemManager = cancleItemManager;
	}

	@Override
	public boolean execute(List<Long> params) throws Exception {
		logger.info("=============CancleOrderAO execute start==============" + params.toString());
		// 业务逻辑处理
		logger.info("=============CancleOrderAO execute end==============" + params.toString());
		return true;
	}

}
