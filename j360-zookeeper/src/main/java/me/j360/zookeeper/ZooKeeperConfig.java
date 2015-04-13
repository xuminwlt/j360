package me.j360.zookeeper;

/**
 * Created with j360 -> me.j360.zookeeper.
 * User: min_xu
 * Date: 2015/3/30
 * Time: 19:21
 * 说明：
 */
import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.data.Stat;


public class ZooKeeperConfig implements Config {

    @Override
    public byte[] getConfig(String path) throws Exception {
        CuratorFramework client = ZooKeeperFactory.get();
        if (!exists(client, path)) {
            throw new RuntimeException("Path " + path + " does not exists.");
        }
        return client.getData().forPath(path);
    }

    private boolean exists(CuratorFramework client, String path) throws Exception {
        Stat stat = client.checkExists().forPath(path);
        return !(stat == null);
    }

}
