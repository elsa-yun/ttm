package com.elsa.ttm.test;
//package com.elsa.ttm.test;
//
//import java.lang.reflect.Field;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import org.apache.commons.dbcp.BasicDataSource;
//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;
//import org.springframework.beans.BeansException;
//import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
//import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.ApplicationContextAware;
//import org.springframework.core.PriorityOrdered;
//import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
//
//public class CopyOfWatchMysql implements BeanFactoryPostProcessor, PriorityOrdered, ApplicationContextAware {
//
//	private static final String SLAVE = "slave";
//
//	private Map<String, AbstractRoutingDataSource> dataSourceMap = new HashMap<String, AbstractRoutingDataSource>();
//
//	private Map<String, BasicDataSource> dbcpSourceMap = new HashMap<String, BasicDataSource>();
//
//	private Map<String, Object> slaveTargetDataSources = new HashMap<String, Object>();
//
//	private Map<String, Object> removeList = new HashMap<String, Object>();
//
//	private List<MysqlDO> mysqlSlaves = new ArrayList<MysqlDO>();
//
//	private ApplicationContext applicationContext;
//
//	private static final Log logger = LogFactory.getLog(CopyOfWatchMysql.class);
//
//	@Override
//	public int getOrder() {
//		return LOWEST_PRECEDENCE - 12;
//	}
//
//	@Override
//	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
//		dataSourceMap = beanFactory.getBeansOfType(org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource.class);
//		dbcpSourceMap = beanFactory.getBeansOfType(org.apache.commons.dbcp.BasicDataSource.class);
//		logger.debug("======================================postProcessBeanFactory=======================================" + dataSourceMap.size());
//		for (Map.Entry<String, AbstractRoutingDataSource> entry : dataSourceMap.entrySet()) {
//			AbstractRoutingDataSource ards = entry.getValue();
//			this.filterSlaveDataSource(ards);
//		}
//
//		for (Map.Entry<String, BasicDataSource> entry : dbcpSourceMap.entrySet()) {
//			if (entry.getKey().toLowerCase().contains(SLAVE)) {
//				BasicDataSource bds = entry.getValue();
//				String[] get_ip_port = get_ip_port(bds.getUrl());
//				MysqlDO mysql = new MysqlDO();
//				mysql.setDriverClassName(bds.getDriverClassName());
//				mysql.setUrl(bds.getUrl());
//				mysql.setPassWord(bds.getPassword());
//				mysql.setIp(get_ip_port[0]);
//				mysql.setPort(Integer.parseInt(get_ip_port[1]));
//				mysql.setDbName(get_db_name(bds.getUrl()));
//				mysql.setUserName(bds.getUsername());
//				mysql.setBeanId(entry.getKey());
//				mysqlSlaves.add(mysql);
//			}
//		}
//
//		for (MysqlDO m : mysqlSlaves) {
//			logger.debug("==================mysqlList====================" + m.toString());
//		}
//		removeSlaveDataSource("schedule");
//		addSlaveDataSource("schedule");
//	}
//
//	private void removeSlaveDataSource(String dbname) {
//		String dataSourceBeanId = "";
//		for (MysqlDO m : mysqlSlaves) {
//			if (m.getDbName().equals(dbname)) {
//				dataSourceBeanId = m.getBeanId();
//				break;
//			}
//		}
//
//		if (!"".equals(dataSourceBeanId)) {
//			for (Map.Entry<String, AbstractRoutingDataSource> ard : dataSourceMap.entrySet()) {
//				AbstractRoutingDataSource ds = (AbstractRoutingDataSource) ard.getValue();
//				removeSlaveDataSource(ds, dataSourceBeanId);
//			}
//		}
//
//	}
//
//	private void addSlaveDataSource(String dbname) {
//		String dataSourceBeanId = "";
//		for (MysqlDO m : mysqlSlaves) {
//			if (m.getDbName().equals(dbname)) {
//				dataSourceBeanId = m.getBeanId();
//				break;
//			}
//		}
//
//		Map<String, Object> returnAddMap = returnAddMapNew(dataSourceBeanId);
//		logger.info("returnAddMap size=>" + returnAddMap.size());
//
//		for (Map.Entry<String, AbstractRoutingDataSource> ard : dataSourceMap.entrySet()) {
//			AbstractRoutingDataSource ds = (AbstractRoutingDataSource) ard.getValue();
//			addSlaveDataSource(ds, returnAddMap);
//		}
//
//	}
//
//	private Map<String, Object> returnAddMapNew(String dataSourceBeanId) {
//		org.apache.commons.dbcp.BasicDataSource bean = (BasicDataSource) applicationContext.getBean(dataSourceBeanId);
//		Map<String, Object> map = new HashMap<String, Object>();
//		for (Map.Entry<String, Object> ent : removeList.entrySet()) {
//			BasicDataSource as = (BasicDataSource) ent.getValue();
//			if (bean.getUrl().equals(as.getUrl())) {
//				map.put(ent.getKey(), ent.getValue());
//				break;
//			}
//		}
//		return map;
//	}
//
////	private Map<String, Object> returnAddMap(String dataSourceBeanId) {
////		org.apache.commons.dbcp.BasicDataSource bean = (BasicDataSource) applicationContext.getBean(dataSourceBeanId);
////		Map<String, Object> map = new HashMap<String, Object>();
////		String addKey = "";
////		BasicDataSource addValue = null;
////		for (Map.Entry<String, AbstractRoutingDataSource> ent : defaultMap.entrySet()) {
////			Object as = ent.getValue();
////			Field[] fields = as.getClass().getSuperclass().getDeclaredFields();
////			int length = fields.length;
////			for (int i = 0; i < length; i++) {
////				Field f = fields[i];
////				f.setAccessible(true);
////				String name = f.getName();
////				if (name.equals("targetDataSources")) {
////					try {
////						Map<Object, Object> targetDataSources = (Map<Object, Object>) f.get(as);
////
////						for (Map.Entry<Object, Object> entry : targetDataSources.entrySet()) {
////							logger.debug("=====================kkkk=>" + entry.getKey());
////							if (entry.getKey().toString().contains(SLAVE)) {
////								org.apache.commons.dbcp.BasicDataSource value = (BasicDataSource) entry.getValue();
////								if (value.getUrl().equals(bean.getUrl())) {
////									if (value.getUsername().equals(bean.getUsername())) {
////										addKey = entry.getKey().toString();
////										addValue = (BasicDataSource) entry.getValue();
////										map.put(addKey, addValue);
////										break;
////									}
////								}
////							}
////						}
////
////					} catch (IllegalArgumentException e) {
////						logger.error(e.getMessage(), e);
////					} catch (IllegalAccessException e) {
////						logger.error(e.getMessage(), e);
////					}
////				}
////			}
////		}
////		return map;
////	}
//
//	private void addSlaveDataSource(Object ards, Map<String, Object> addMap) {
//		for (Map.Entry<String, AbstractRoutingDataSource> ent : dataSourceMap.entrySet()) {
//
//			Field[] fieldlist = ards.getClass().getSuperclass().getDeclaredFields();
//			for (int i = 0; i < fieldlist.length; i++) {
//				Field f = fieldlist[i];
//				f.setAccessible(true);
//				String name = f.getName();
//				if (name.equals("targetDataSources")) {
//					try {
//						Map<Object, Object> targetDataSources = (Map<Object, Object>) f.get(ards);
//						for (Map.Entry<String, Object> e : addMap.entrySet()) {
//							targetDataSources.put(e.getKey(), e.getValue());
//							logger.info("f addKey targetDataSources e.getKey()==>" + e.getKey());
//						}
//						f.set(ards, targetDataSources);
//						for (Map.Entry<Object, Object> e : targetDataSources.entrySet()) {
//						}
//					} catch (IllegalArgumentException e) {
//						logger.error(e.getMessage(), e);
//					} catch (IllegalAccessException e) {
//						logger.error(e.getMessage(), e);
//					}
//				}
//			}
//
//			// after remove view logs
//			for (Map.Entry<String, AbstractRoutingDataSource> drd : dataSourceMap.entrySet()) {
//				Object ds = drd.getValue();
//				fieldlist = ds.getClass().getSuperclass().getDeclaredFields();
//				for (int i = 0; i < fieldlist.length; i++) {
//					Field f = fieldlist[i];
//					f.setAccessible(true);
//					String name = f.getName();
//					if (name.equals("targetDataSources")) {
//						try {
//							Map<Object, Object> targetDataSources = (Map<Object, Object>) f.get(ards);
//							for (Map.Entry<Object, Object> slave : targetDataSources.entrySet()) {
//								logger.info("add after targetDataSources key is==>" + slave.getKey());
//							}
//
//						} catch (IllegalArgumentException e) {
//							logger.error(e.getMessage(), e);
//						} catch (IllegalAccessException e) {
//							logger.error(e.getMessage(), e);
//						}
//					}
//				}
//			}
//		}
//	}
//
//	private void removeSlaveDataSource(Object ards, String dataSourceBeanId) {
//		org.apache.commons.dbcp.BasicDataSource bean = (BasicDataSource) applicationContext.getBean(dataSourceBeanId);
//		Field[] fieldlist = ards.getClass().getSuperclass().getDeclaredFields();
//		for (int i = 0; i < fieldlist.length; i++) {
//			Field f = fieldlist[i];
//			f.setAccessible(true);
//			String name = f.getName();
//			if (name.equals("targetDataSources")) {
//				try {
//					Map<Object, Object> targetDataSources = (Map<Object, Object>) f.get(ards);
//					String removeKey = "";
//					for (Map.Entry<Object, Object> entry : targetDataSources.entrySet()) {
//						org.apache.commons.dbcp.BasicDataSource value = (BasicDataSource) entry.getValue();
//						if (entry.getKey().toString().contains(SLAVE) && value.getUrl().equals(bean.getUrl())) {
//							removeKey = entry.getKey().toString();
//							removeList.put(removeKey, entry.getValue());
//							break;
//						}
//					}
//					targetDataSources.remove(removeKey);
//					f.set(ards, targetDataSources);
//					logger.info("f removeKey size==>" + removeKey);
//					logger.info("f targetDataSources size==>" + targetDataSources.size());
//				} catch (IllegalArgumentException e) {
//					logger.error(e.getMessage(), e);
//				} catch (IllegalAccessException e) {
//					logger.error(e.getMessage(), e);
//				}
//			}
//		}
//
//		// after remove view logs
//		for (Map.Entry<String, AbstractRoutingDataSource> drd : dataSourceMap.entrySet()) {
//			Object ds = drd.getValue();
//			fieldlist = ds.getClass().getSuperclass().getDeclaredFields();
//			for (int i = 0; i < fieldlist.length; i++) {
//				Field f = fieldlist[i];
//				f.setAccessible(true);
//				String name = f.getName();
//				if (name.equals("targetDataSources")) {
//					try {
//						Map<Object, Object> targetDataSources = (Map<Object, Object>) f.get(ards);
//						for (Map.Entry<Object, Object> slave : targetDataSources.entrySet()) {
//							logger.info("move after targetDataSources key is==>" + slave.getKey());
//						}
//
//					} catch (IllegalArgumentException e) {
//						logger.error(e.getMessage(), e);
//					} catch (IllegalAccessException e) {
//						logger.error(e.getMessage(), e);
//					}
//				}
//			}
//		}
//	}
//
//	private void filterSlaveDataSource(Object ards) {
//		Field[] fieldlist = ards.getClass().getSuperclass().getDeclaredFields();
//		for (int i = 0; i < fieldlist.length; i++) {
//			Field f = fieldlist[i];
//			f.setAccessible(true);
//			String name = f.getName();
//			if (name.equals("targetDataSources")) {
//				try {
//					Map<Object, Object> targetDataSources = (Map<Object, Object>) f.get(ards);
//					logger.info("filterSlaveDataSource targetDataSources size==>" + targetDataSources.size());
//					for (Map.Entry<Object, Object> entry : targetDataSources.entrySet()) {
//						if (entry.getKey().toString().toLowerCase().contains(SLAVE)) {
//							slaveTargetDataSources.put(entry.getKey().toString(), entry.getValue());
//						}
//
//					}
//				} catch (IllegalArgumentException e) {
//					logger.error(e.getMessage(), e);
//				} catch (IllegalAccessException e) {
//					logger.error(e.getMessage(), e);
//				}
//			}
//		}
//		logger.info("slaveTargetDataSources targetDataSources size==>" + slaveTargetDataSources.size());
//	}
//
//	@Override
//	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
//		this.applicationContext = applicationContext;
//	}
//
//	private static String[] get_ip_port(String dburl) {
//		dburl = dburl.trim();
//		String[] split = dburl.split("jdbc:mysql://");
//		String[] split2 = split[1].split("/");
//		String[] split3 = split2[0].split(":");
//		return split3;
//	}
//
//	private static String get_db_name(String dburl) {
//		String[] split = dburl.split("3306/");
//		String dbname = "";
//		if (split[1].indexOf("?") != -1) {
//			String[] split2 = split[1].split("\\?");
//			dbname = split2[0];
//		} else {
//			dbname = split[1];
//		}
//		return dbname;
//	}
//
//	public List<MysqlDO> getMysqlSlaves() {
//		return mysqlSlaves;
//	}
//
//	public void setMysqlSlaves(List<MysqlDO> mysqlSlaves) {
//		this.mysqlSlaves = mysqlSlaves;
//	}
//
//}
