package com.elsa.ttm.test;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class T {

	public final static String P = "D:/data/";
	public final static String S = "http://jumper.elsamama.com:3000/websql_front/desensitization/req?d=user&s=";
	public final static String SS = "http://jumper.elsamama.com:3000/websql_front/desensitization/req";
	public final static String ss = "http://jumper.elsamama.com:3000/websql_front/desensitization/req?d=user&s=select count(id) from uc_user where id>0 and id<2&c="
			+ System.currentTimeMillis();
	public static final String SELECT = "select id,nick_name,mobile,password,login_type,email from uc_user where id>0 limit 1";

	private static final Log logger = LogFactory.getLog(T.class);

	private Map<String, String> map = new HashMap<String, String>();

	public String str = "aaaa";

	public void init() {
		logger.info("");
	}

	// public static void main(String args[]) {
	// T t = new T();
	// Field fields[] = t.getClass().getDeclaredFields();// 获得对象所有属性
	// Field field = null;
	// String[] attr = { "map", "str" };
	// for (int i = 0; i < fields.length; i++) {
	// field = fields[i];
	// field.setAccessible(true);// 修改访问权限
	// for (int j = 0; j < attr.length; j++) {
	// if (attr[j].equals(field.getName())) {
	// try {
	// System.out.println(field.getName() + ":" + field.get(t));
	// } catch (IllegalArgumentException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// } catch (IllegalAccessException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }// 读取属性值
	// }
	// }
	// }
	// }
}
