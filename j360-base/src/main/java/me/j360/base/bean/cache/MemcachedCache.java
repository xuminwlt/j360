package me.j360.base.bean.cache;

import me.j360.base.service.memcached.SpyMemcachedClient;
import org.springframework.cache.Cache;
import org.springframework.cache.support.SimpleValueWrapper;

/**
 * Created with us-parent -> me.j360.base.cache.
 * User: min_xu
 * Date: 2014/9/15
 * Time: 16:53
 * 说明：
 */
public class MemcachedCache implements Cache {

    private final String name;
    private final MemCache memCache;

    public MemcachedCache(String name, int expire, SpyMemcachedClient memcachedClient) {
        this.name = name;
        this.memCache = new MemCache(name, expire, memcachedClient);
    }

    @Override
    public void clear() {
        memCache.clear();
    }

    @Override
    public void evict(Object key) {
        memCache.delete(key.toString());
    }

    @Override
    public ValueWrapper get(Object key) {
        ValueWrapper wrapper = null;
        Object value = memCache.get(key.toString());
        if (value != null) {
            wrapper = new SimpleValueWrapper(value);
        }
        return wrapper;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public MemCache getNativeCache() {
        return this.memCache;
    }

    @Override
    public void put(Object key, Object value) {
        memCache.put(key.toString(), value);
    }

    @Override
    public ValueWrapper putIfAbsent(Object o, Object o2) {
        return null;
    }

    @Override
    public <T> T get(Object key, Class<T> type) {
        Object cacheValue = this.memCache.get(key.toString());
        Object value = (cacheValue != null ? cacheValue : null);
        if (type != null && !type.isInstance(value)) {
            throw new IllegalStateException("Cached value is not of required type [" + type.getName() + "]: " + value);
        }
        return (T) value;
    }

}
