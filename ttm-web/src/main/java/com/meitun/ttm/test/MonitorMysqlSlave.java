package com.elsa.ttm.test;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.core.PriorityOrdered;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;


/**
 * 监控mysql slave 从库，做到自动切换,如果从库不可用，自动去除此从库，从库可以用自动添加上从库
 * 
 * @author longhaisheng
 *
 */
public class MonitorMysqlSlave implements BeanFactoryPostProcessor, PriorityOrdered, DisposableBean {

	private Map<String, AbstractRoutingDataSource> dataSourceMap = new HashMap<String, AbstractRoutingDataSource>();

	private Map<String, ?> dbcpSourceMap;

	private static final Log logger = LogFactory.getLog(MonitorMysqlSlave.class);

	private ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);

	private MysqlSlaveRunable watchMysqlSlaveRunable;

	/** 是否使用DBCP连接池 */
	private String poolStr ="dbcp";
	
	/** 线程每隔多少毫秒运行一次 */
	private int scheduleDelay = 500;

	@Override
	public int getOrder() {
		return LOWEST_PRECEDENCE - 11;
	}

	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
		dataSourceMap = beanFactory.getBeansOfType(org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource.class);
		logger.debug("======================================watch slave mysql start ======================================= use pool==>" + poolStr);
		if (poolStr.equals("dbcp")) {
			dbcpSourceMap = beanFactory.getBeansOfType(org.apache.commons.dbcp.BasicDataSource.class);
		} 
		if (poolStr.equals("c3p0")) {
			dbcpSourceMap = beanFactory.getBeansOfType(com.mchange.v2.c3p0.ComboPooledDataSource.class);
		}
		if (poolStr.equals("mtc3p0")) {
			dbcpSourceMap = beanFactory.getBeansOfType(com.mchange.v2.c3p0.MtComboPooledDataSource.class);
		}
		this.watcher();
	}

	private void watcher() {
		watchMysqlSlaveRunable = new MysqlSlaveRunable();
		watchMysqlSlaveRunable.setDataSourceMap(dataSourceMap);
		watchMysqlSlaveRunable.setDbcpSourceMap(dbcpSourceMap);
		scheduledExecutorService.scheduleWithFixedDelay(watchMysqlSlaveRunable, 1000, scheduleDelay, TimeUnit.MILLISECONDS);
	}

	@Override
	public void destroy() throws Exception {
		if (null != watchMysqlSlaveRunable) {
			watchMysqlSlaveRunable.setFlag(false);
		}
		scheduledExecutorService.shutdown();
		if (logger.isInfoEnabled()) {
			logger.info("=========================================watch slave mysql scheduler shutdown=========================================");
		}
	}

	public int getScheduleDelay() {
		return scheduleDelay;
	}

	public void setScheduleDelay(int scheduleDelay) {
		this.scheduleDelay = scheduleDelay;
	}

	public String getPoolStr() {
		return poolStr;
	}

	public void setPoolStr(String poolStr) {
		this.poolStr = poolStr;
	}

}
