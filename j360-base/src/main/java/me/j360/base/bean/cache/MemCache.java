package me.j360.base.bean.cache;

import me.j360.base.service.memcached.SpyMemcachedClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Set;

/**
 * Created with us-parent -> com.fz.us.base.cache.
 * User: min_xu
 * Date: 2014/9/15
 * Time: 16:54
 * 说明：
 */
public class MemCache {
    private static Logger log = LoggerFactory.getLogger(MemCache.class);

    private Set<String> keySet = new HashSet<String>();
    private final String name;
    private final int expire;
    private final SpyMemcachedClient memcachedClient;

    public MemCache(String name, int expire,SpyMemcachedClient memcachedClient) {
        this.name = name;
        this.expire = expire;
        this.memcachedClient = memcachedClient;
    }

    public Object get(String key) {
        Object value = null;
            key = this.getKey(key);
            value = memcachedClient.get(key);

        return value;
    }

    public void put(String key, Object value) {
        if (value == null)
            return;

            key = this.getKey(key);
            memcachedClient.set(key, expire, value);
            keySet.add(key);

    }

    public void clear() {
        for (String key : keySet) {
                memcachedClient.delete(this.getKey(key));
        }
    }

    public void delete(String key) {
        key = this.getKey(key);
        memcachedClient.delete(key);

    }

    private String getKey(String key) {
        return name + "_" + key;
    }
}
