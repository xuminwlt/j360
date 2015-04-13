package me.j360.zookeeper;

/**
 * Created with j360 -> me.j360.zookeeper.
 * User: min_xu
 * Date: 2015/3/30
 * Time: 19:22
 * 说明：
 */
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

public class ZooKeeperFactory {

    public static final String CONNECT_STRING = "192.168.145.128:2181,192.168.145.129:2181";

    public static final int MAX_RETRIES = 3;

    public static final int BASE_SLEEP_TIMEMS = 3000;

    public static final String NAME_SPACE = "cfg";

    public static CuratorFramework get() {
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(BASE_SLEEP_TIMEMS, MAX_RETRIES);
        CuratorFramework client = CuratorFrameworkFactory.builder()
                .connectString(CONNECT_STRING)
                .retryPolicy(retryPolicy)
                .namespace(NAME_SPACE)
                .build();
        client.start();
        return client;
    }
}
