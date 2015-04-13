package me.j360.base.service.memcached;

import me.j360.base.bean.cache.MemcachedCache;
import me.j360.base.service.memcached.SpyMemcachedClient;
import org.springframework.cache.Cache;
import org.springframework.cache.transaction.AbstractTransactionSupportingCacheManager;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Created with me.j360.base.cache.
 * User: min_xu
 * Date: 2014/9/15
 * Time: 16:52
 * 说明：
 */
@Service
public class MemcachedCacheManager extends AbstractTransactionSupportingCacheManager {

    private ConcurrentMap<String, Cache> cacheMap = new ConcurrentHashMap<String, Cache>();
    private Map<String, Integer> expireMap = new HashMap<String, Integer>();   //缓存的时间

    /*public MemcachedClient getMemcachedClient() {
        return memcachedClient;
    }

    public void setMemcachedClient(MemcachedClient memcachedClient) {
        this.memcachedClient = memcachedClient;
    }
*/
    /*public void setSpyMemcachedClient(SpyMemcachedClient spyMemcachedClient) {
            this.spyMemcachedClient = spyMemcachedClient;
        }

        public SpyMemcachedClient getSpyMemcachedClient() {
            return spyMemcachedClient;
        }

        private SpyMemcachedClient spyMemcachedClient;*/
    @Resource
    private SpyMemcachedClient spyMemcachedClient;

    public MemcachedCacheManager() {

    }

    @Override
    protected Collection<? extends Cache> loadCaches() {
        Collection<Cache> values = cacheMap.values();
        return values;
    }

    @Override
    public Cache getCache(String name) {
        Cache cache = cacheMap.get(name);
        if (cache == null) {
            Integer expire = expireMap.get(name);
            if (expire == null) {
                expire = 0;
                expireMap.put(name, expire);
            }
            cache = new MemcachedCache(name, expire.intValue(), spyMemcachedClient);
            cacheMap.put(name, cache);
        }
        return cache;
    }

    public void setConfigMap(Map<String, Integer> configMap) {
        this.expireMap = configMap;
    }

}
