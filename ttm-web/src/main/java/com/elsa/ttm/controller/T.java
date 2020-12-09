package com.elsa.ttm.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Id;
import org.apache.zookeeper.server.auth.DigestAuthenticationProvider;

import com.elsa.ttm.util.ZKUtil;

public class T {

//	public static void main(String args[]) throws Exception {
//		String uname = "longhaisheng";
//		String pwd = "password";
//		String authString = uname.trim() + ":" + pwd.trim();
//		String zk_connect_string = "127.0.0.1:2181";// 172.16.1.144 127.0.0.1
//		int timeout = 6000;
//		ZooKeeper zookeeper = new ZooKeeper(zk_connect_string.trim(), timeout, new Watcher() {
//			public void process(WatchedEvent event) {
//				// sessionEvent(connectionLatch, event);
//			}
//		});
//		String charset = "utf-8";
//		if (charset == null) {
//			zookeeper.addAuthInfo("digest", authString.getBytes());
//		} else {
//			zookeeper.addAuthInfo("digest", authString.getBytes(charset));
//		}
//		String generateDigest = DigestAuthenticationProvider.generateDigest(authString);
//		List<ACL> acl = new ArrayList<ACL>();
//		acl.clear();
//		acl.add(new ACL(ZooDefs.Perms.ALL, new Id("digest", generateDigest)));
//		acl.add(new ACL(ZooDefs.Perms.READ, Ids.ANYONE_ID_UNSAFE));
//		Thread.sleep(3000);
//		String rootPath = "/mt_schedule_root";
//		ZKUtil.deleteTree(zookeeper, rootPath);
//		zookeeper.close();
//	}

}
