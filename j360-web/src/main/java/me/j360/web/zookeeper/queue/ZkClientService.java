package me.j360.web.zookeeper.queue;

import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.CreateMode;
import org.springframework.util.Assert;

/**
 * Created with j360 -> me.j360.web.zookeeper.queue.
 * User: min_xu
 * Date: 2015/3/30
 * Time: 23:24
 * 说明：
 */
public class ZkClientService {

    private CuratorFramework zkClient;

    public ZkClientService(final CuratorFramework zkClient) {
        Assert.notNull(zkClient, "zkClient cannot be null");
        this.zkClient = zkClient;
    }

    public String getPath(byte[] bytes){
        //创建znode并添加数据
        String path = null;
        try {
            path = zkClient.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT_SEQUENTIAL).forPath("/zk_queue_test/queue-");
            zkClient.setData().forPath(path, bytes);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return path;
    }
}
