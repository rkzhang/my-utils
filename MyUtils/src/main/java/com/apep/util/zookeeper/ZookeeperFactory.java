package com.apep.util.zookeeper;

import java.io.IOException;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

public class ZookeeperFactory {
	
	private static final String CLIENT_PORT = "2181";
	
	private static final int CONNECTION_TIMEOUT = 1000;

	private static ZooKeeper zk;
	
	private static void createZk() {
		try {
			zk = new ZooKeeper("localhost:" + CLIENT_PORT, 
			        CONNECTION_TIMEOUT, new Watcher() { 
			            // 监控所有被触发的事件
			            public void process(WatchedEvent event) { 
			                System.out.println("已经触发了" + event.getType() + "事件！"); 
			                System.out.println("modify path --- " + event.getPath()); 
			            } 
			        });
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
	
	public static ZooKeeper achieveZkClient() {
		 if(zk == null) {
			 createZk();
		 }
		 return zk;
	}
}
