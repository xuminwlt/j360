package me.j360.web.zookeeper.log;

import org.apache.curator.framework.CuratorFramework;

/**
 * Created with j360 -> me.j360.web.zookeeper.
 * User: min_xu
 * Date: 2015/3/30
 * Time: 20:11
 * 说明：
 */
public interface IZKListener {
    void executor(CuratorFramework client);
}
