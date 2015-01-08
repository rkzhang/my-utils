package com.apep.util.zookeeper;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.data.Stat;

/**
 * Zookeeper共享锁实现类，此对象为线程不安全，应实例化后，以线程封闭使用
 * @author rkzhang
 */
public class ZookeeperLock {
	
	private ZooKeeper zk;
	
	private String root;
	
	private String ownNode;
	
	private String lockPath;
	
	public ZookeeperLock(String root) throws KeeperException, InterruptedException {
		super();
		this.zk = ZookeeperFactory.achieveZkClient();
		this.root = root;
		Stat stat = zk.exists(root, false); 
		if(stat == null) {
			zk.create(root, "1".getBytes(), Ids.OPEN_ACL_UNSAFE,
					   CreateMode.PERSISTENT);
		}
	}

	/**
	 * 获取锁
	 * @param code
	 * @throws KeeperException
	 * @throws InterruptedException
	 */
	public void achieveLock(String code) throws KeeperException, InterruptedException{ 
		lockPath = getLockPath(code);
		Stat stat = zk.exists(getLockPath(code), false); 
		if(stat == null){
			zk.create(lockPath, "1".getBytes(), Ids.OPEN_ACL_UNSAFE,
					   CreateMode.PERSISTENT);
		}
		
		String znode = zk.create(getDataPath(code), "1".getBytes(), Ids.OPEN_ACL_UNSAFE,
				   CreateMode.EPHEMERAL_SEQUENTIAL);
		this.ownNode = znode;
		System.out.println("own znode : " + znode);
		getLock();
    }
	
	/**
	 * 释放锁
	 * @throws InterruptedException
	 * @throws KeeperException
	 */
	public void releaseLock() throws InterruptedException, KeeperException { 
		zk.delete(ownNode, -1);
		System.out.println("delete znode : " + ownNode);
		List<String> list = zk.getChildren(lockPath, false); 
		if(list == null || list.isEmpty()) {
			zk.delete(lockPath, -1);
			System.out.println("delete znode : " + lockPath);
		}
	}

	private void getLock() throws KeeperException, InterruptedException {
		List<String> list = zk.getChildren(lockPath, false); 
		String lastNode = lockPath + "/" + getLastNode(list);
		System.out.println("last znode : " + lastNode);
		if(isLastNode(ownNode, lastNode)) { 
			return;
		} else {
			waitForLock(lastNode);
		}
	}
	
    private void waitForLock(String lowerPath) throws InterruptedException, KeeperException {
        Stat stat = zk.exists(lowerPath, false); 
        while(stat != null){ 
            Thread.sleep(200);
            stat = zk.exists(lowerPath, false); 
        } 
        getLock();
    }

	private boolean isLastNode(String ownNode, String lastNode) {
        return ownNode.equals(lastNode) ? true : false;
	}

	private String getLastNode(List<String> list) {
		String[] nodes = list.toArray(new String[list.size()]); 
		for(String node : nodes) {
			 System.out.println("child node - " + node);
		}
		
        Arrays.sort(nodes); 
        String lastNode = nodes[0];
		return lastNode;
	}

	private  String getDataPath(String code) {
		return root + "/" + code + "/" + code;
	} 
	
	public String getLockPath(String code) {
		return root + "/" + code;
	}
	
	public static void main(String[] args) throws KeeperException, InterruptedException {
		ZookeeperLock lock  = new ZookeeperLock("/shop");
		lock.achieveLock("abcde");
		System.out.println("do action");
		CountDownLatch cd = new CountDownLatch(1);
		cd.await();
		lock.releaseLock();

	}
}
