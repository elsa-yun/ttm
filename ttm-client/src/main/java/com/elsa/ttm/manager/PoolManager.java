package com.elsa.ttm.manager;
//package com.elsa.ttm.manager;
//
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.sql.Statement;
//import java.util.Locale;
//import java.util.ResourceBundle;
//
//import org.apache.commons.dbcp.ConnectionFactory;
//import org.apache.commons.dbcp.DriverManagerConnectionFactory;
//import org.apache.commons.dbcp.PoolableConnectionFactory;
//import org.apache.commons.dbcp.PoolingDriver;
//import org.apache.commons.pool.ObjectPool;
//import org.apache.commons.pool.impl.GenericObjectPool;
//
//public class PoolManager {
//	private static String driver = "net.sourceforge.jtds.jdbc.Driver"; // 驱动
//	private static String url = ""; // URL
//	private static String name = "sa"; // 用户名
//	private static String password = ""; // 密码
//	private static Class driverClass = null;
//	private static ObjectPool connectionPool = null;
//	private static String poolname = "";
//	private static ResourceBundle rb;
//
//	/**
//	 * 初始化数据源
//	 */
//	private static synchronized void initDataSource() {
//		if (driverClass == null) {
//			try {
//				driverClass = Class.forName(driver);
//			} catch (ClassNotFoundException e) {
//				e.printStackTrace();
//			}
//		}
//	}
//
//	/**
//	 * 装配配置文件 initProperties
//	 */
//	private static void loadProperties() {
//		rb = ResourceBundle.getBundle("config", Locale.getDefault());
//		driver = rb.getString("jdbc.sql.driverClassName");
//		url = rb.getString("jdbc.sql.url");
//		name = rb.getString("jdbc.sql.username");
//		password = rb.getString("jdbc.sql.password");
//		poolname = rb.getString("jdbc.sql.poolname");
//	}
//
//	/**
//	 * 连接池启动
//	 * 
//	 * @throws Exception
//	 */
//	public static void StartPool() {
//		loadProperties();
//		initDataSource();
//		if (connectionPool != null) {
//			ShutdownPool();
//		}
//		try {
//			connectionPool = new GenericObjectPool(null);
//			ConnectionFactory connectionFactory = new DriverManagerConnectionFactory(url, name, password);
//			new PoolableConnectionFactory(connectionFactory, connectionPool, null, null, false, true);
//			Class.forName("org.apache.commons.dbcp.PoolingDriver");
//			PoolingDriver driver = (PoolingDriver) DriverManager.getDriver("jdbc:apache:commons:dbcp:");
//			driver.registerPool(poolname, connectionPool);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//
//	/**
//	 * 释放连接池
//	 */
//	public static void ShutdownPool() {
//		try {
//			PoolingDriver driver = (PoolingDriver) DriverManager.getDriver("jdbc:apache:commons:dbcp:");
//			driver.closePool(poolname);
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//	}
//
//	/**
//	 * 取得连接池中的连接
//	 * 
//	 * @return
//	 */
//	public static Connection getConnection() {
//		Connection conn = null;
//		if (connectionPool == null)
//			StartPool();
//		try {
//			conn = DriverManager.getConnection("jdbc:apache:commons:dbcp:" + poolname);
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		return conn;
//	}
//
//	/**
//	 * 获取连接 getConnection
//	 * 
//	 * @param name
//	 * @return
//	 */
//	public static Connection getConnection(String name) {
//		return getConnection();
//	}
//
//	/**
//	 * 释放连接 freeConnection
//	 * 
//	 * @param conn
//	 */
//	public static void freeConnection(Connection conn) {
//		if (conn != null) {
//			try {
//				conn.close();
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//		}
//	}
//
//	/**
//	 * 释放连接 freeConnection
//	 * 
//	 * @param name
//	 * @param con
//	 */
//	public static void freeConnection(String name, Connection con) {
//		freeConnection(con);
//	}
//
//	/**
//	 * 例子 main
//	 * 
//	 * @param args
//	 */
//	public static void main(String[] args) {
//		try {
//			Connection conn = PoolManager.getConnection();
//			System.out.println(conn.isClosed());
//			if (conn != null) {
//				Statement statement = conn.createStatement();
//				ResultSet rs = statement.executeQuery("select * from test_log");
//				int c = rs.getMetaData().getColumnCount();
//				while (rs.next()) {
//					System.out.println();
//					for (int i = 1; i <= c; i++) {
//						System.out.print(rs.getObject(i));
//					}
//				}
//				rs.close();
//			}
//			PoolManager.freeConnection(conn);
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//
//	}
//
// }