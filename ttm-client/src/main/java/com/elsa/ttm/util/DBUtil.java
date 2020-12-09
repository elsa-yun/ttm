package com.elsa.ttm.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author longhaisheng
 * 
 */
public class DBUtil {

	private static final Log logger = LogFactory.getLog(DBUtil.class);

	private DataSource dataSource;

	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource masterDataSource) {
		this.dataSource = masterDataSource;
	}

	public Connection getConnection() {
		Connection conn = null;
		try {
			conn = dataSource.getConnection();
		} catch (SQLException e) {
			logger.error("SQLException for:" + e.getMessage(), e);
		}
		return conn;
	}

	public int executeQuery(String sql, Map<String, Object> map) {
		Pattern p = Pattern.compile("(#[^#]*#)");
		Matcher m = p.matcher(sql);
		List<String> result = new ArrayList<String>();
		while (m.find()) {
			String group = m.group();
			result.add(group.substring(1, group.length() - 1));
			sql = sql.replaceAll(group, "?");
		}
		Connection conn = getConnection();
		int count = 0;
		PreparedStatement stmt = null;
		try {
			if (null != conn) {
				stmt = conn.prepareStatement(sql);
				int size = result.size();
				for (int j = 0; j < size; j++) {
					Object object = map.get(result.get(j));
					if (null != object) {
						stmt.setString(j + 1, object.toString());
					} else {
						stmt.setString(j + 1, null);
					}
				}
				count = stmt.executeUpdate();
			}
		} catch (SQLException e) {
			logger.info("JDBC executeQuery exception for=>" + e.getMessage(), e);
		} finally {
			close(conn, stmt, null);
		}
		return count;
	}

	public List<Map<String, String>> queryAll(String sql, Map<String, Object> map) {
		Pattern p = Pattern.compile("(#[^#]*#)");
		Matcher m = p.matcher(sql);
		List<String> result = new ArrayList<String>();
		while (m.find()) {
			String group = m.group();
			result.add(group.substring(1, group.length() - 1));
			sql = sql.replaceAll(group, "?");
		}

		List<String> colums = getColumNames(sql);

		Connection conn = getConnection();
		List<Map<String, String>> resultList = new ArrayList<Map<String, String>>();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			if (null != conn) {
				stmt = conn.prepareStatement(sql);
				int size = result.size();
				for (int j = 0; j < size; j++) {
					stmt.setString(j + 1, map.get(result.get(j)).toString());
				}
				rs = stmt.executeQuery();
				while (rs.next()) {
					Map<String, String> row = new HashMap<String, String>();
					for (String string : colums) {
						String value = rs.getString(string);
						row.put(string, value);
					}
					// name = new String(name.getBytes("ISO-8859-1"), "GB2312");
					resultList.add(row);
				}
			}
		} catch (SQLException e) {
			logger.error("JDBC queryAll SQLException for:" + e.getMessage(), e);
		} finally {
			close(conn, stmt, rs);
		}
		return resultList;
	}

	private void close(Connection conn, PreparedStatement stmt, ResultSet rs) {
		try {
			if (null != rs) {
				rs.close();
			}
		} catch (SQLException e) {
			logger.error("SQLException for:" + e.getMessage(), e);
		}
		try {
			if (null != stmt) {
				stmt.close();
			}
		} catch (SQLException e) {
			logger.error("SQLException for:" + e.getMessage(), e);
		}
		try {
			if (null != conn) {
				conn.close();
			}
		} catch (SQLException e) {
			logger.error("SQLException for:" + e.getMessage(), e);
		}
	}

	public static String parseDate(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(new Date());
	}

	private static List<String> getColumNames(String sql) {
		List<String> list = new ArrayList<String>();
		String columns = sql.substring(sql.indexOf("select ") + 7, sql.indexOf("from "));
		if (null != columns) {
			for (String str : columns.split(",")) {
				str = str.trim();
				if (str.contains(" as ")) {
					String[] split = str.split(" as ");
					list.add(split[1].trim());
				} else if (str.contains(" ")) {
					str = str.replaceAll(" +", " ");
					String[] split = str.split(" ");
					list.add(split[1].trim());
				} else {
					list.add(str.trim());
				}
			}
		}
		return list;
	}

	// public static void main(String args[]) {
	// String sql =
	// "insert into test (name,age,add_time) values(#name#,#age#,#add_time#) ";
	// Map<String, Object> map = new HashMap<String, Object>();
	// map.put("name", "name");
	// map.put("sex", "sex");
	// map.put("age", 1);
	// map.put("add_time", parseDate(new Date()));
	//
	// JDBC jdbc = new JDBC();
	// jdbc.setHost("127.0.0.1");
	// jdbc.setPort(3306);
	// jdbc.setDbName("elsa");
	// jdbc.setUserName("root");
	// jdbc.setPassWord("123456");
	//
	// System.out.println(jdbc.executeQuery(sql, map));
	// sql = "select name,age,add_time,id from test where id>#id#";
	// // getColumNames(sql);
	//
	// map = new HashMap<String, Object>();
	// map.put("id", 0);
	// List<Map<String, String>> queryAll = jdbc.queryAll(sql, map);
	// for (Map<String, String> m : queryAll) {
	// for (Map.Entry<String, String> entry : m.entrySet()) {
	// System.out.println(entry.getKey() + "=>" + entry.getValue());
	// }
	// }
	//
	// }
}
