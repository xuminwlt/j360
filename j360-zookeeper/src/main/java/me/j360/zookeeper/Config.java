package me.j360.zookeeper;

/**
 * Created with j360 -> me.j360.zookeeper.
 * User: min_xu
 * Date: 2015/3/30
 * Time: 19:21
 * 说明：
 */
public interface Config {

    byte[] getConfig(String path) throws Exception;
}
