package com.elsa.ttm.test;
//package com.elsa.ttm.test;
//
//import java.sql.Timestamp;
//import java.util.UUID;
//
//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;
//import org.apache.zookeeper.ZooKeeper;
//
//import com.google.gson.Gson;
//import com.google.gson.GsonBuilder;
//
///**
// * @author longhaisheng
// * 
// */
//public class WatcherClientManager {
//
//	private DataSourceZKManager zkManager;
//
//	private MonitorMysqlSlave watchMysql;
//
//	public Gson gson;
//
//	private static final Log logger = LogFactory.getLog(WatcherClientManager.class);
//
//	public WatcherClientManager() {
//		gson = new GsonBuilder().registerTypeAdapter(Timestamp.class, new DataSourceTimestampTypeAdapter()).setDateFormat("yyyy-MM-dd HH:mm:ss").create();
//
//	}
//
//	public ZooKeeper getZookeeper() {
//		return zkManager.getZookeeper();
//	}
//
//	public void init() {
//		
//		//注册watch 节点信息
//		// root/appName/slave_db_name/
//		
////		List<MysqlDO> mysqlSlaves = watchMysql.getMysqlSlaves();
////		for (MysqlDO m : mysqlSlaves) {
////			
////		}
//	}
//
//	public static String get_uuid() {
//		return UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
//	}
//
//	public String getRootPath() {
//		return zkManager.getZkConfig().getRootPath();
//	}
//
//	public DataSourceZKManager getZkManager() {
//		return zkManager;
//	}
//
//	public void setZkManager(DataSourceZKManager zkManager) {
//		this.zkManager = zkManager;
//	}
//
//	public MonitorMysqlSlave getWatchMysql() {
//		return watchMysql;
//	}
//
//	public void setWatchMysql(MonitorMysqlSlave watchMysql) {
//		this.watchMysql = watchMysql;
//	}
//
//}
