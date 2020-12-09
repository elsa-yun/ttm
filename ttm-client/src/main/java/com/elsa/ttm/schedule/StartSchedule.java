package com.elsa.ttm.schedule;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

import com.elsa.ttm.manager.ScheduleManager;

/**
 * @author longhaisheng
 * 
 */
public class StartSchedule implements ApplicationListener<ApplicationEvent> {

	private static final Log logger = LogFactory.getLog(StartSchedule.class);

	private static volatile boolean isStart = false;

	private ScheduleManager scheduleManager;

	public ScheduleManager getScheduleManager() {
		return scheduleManager;
	}

	public void setScheduleManager(ScheduleManager scheduleManager) {
		this.scheduleManager = scheduleManager;
	}

	public void onApplicationEvent(ApplicationEvent event) {
		if (!isStart) {
			isStart = true;
			scheduleManager.start();
			logger.info("============================================StartSchedule=============================================");
		}
	}
}
