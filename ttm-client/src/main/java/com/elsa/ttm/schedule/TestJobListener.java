package com.elsa.ttm.schedule;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobListener;

public class TestJobListener implements JobListener {

	private static final Log logger = LogFactory.getLog(TestJobListener.class);

	private String name;

	public TestJobListener(String name) {
		this.name = name;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public void jobToBeExecuted(JobExecutionContext context) {
		logger.info("================>" + context.getJobDetail().getName() + "::::jobToBeExecuted");
	}

	@Override
	public void jobExecutionVetoed(JobExecutionContext context) {
		logger.info("================>" + context.getJobDetail().getName() + "::::jobExecutionVetoed");
	}

	@Override
	public void jobWasExecuted(JobExecutionContext context, JobExecutionException jobException) {
		logger.info("================>" + context.getJobDetail().getName() + "::::jobWasExecuted");
	}

}
