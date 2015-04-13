package me.j360.base.test;

import me.j360.base.service.memcached.SpyMemcachedClient;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.transaction.TransactionConfiguration;

import javax.annotation.Resource;

/**
 * Created with j360 -> me.j360.web.test.base.
 * User: min_xu
 * Date: 2015-03-12
 * Time: 11:30
 * 说明：分布式缓存服务器memcache通过spyMemcachedClient调用，正式环境需要安装memcache
 * 见：http://my.oschina.net/smartsales/blog
 */
@TransactionConfiguration(defaultRollback=true)
@ContextConfiguration(locations = {"/applicationContext-base.xml" })
public class CacheTest extends SpringTransactionalTestCase {
    @Resource
    private SpyMemcachedClient spyMemcachedClient;

    @Test
    public void setGetAndDelete() {
        String key = "j360_minxu:1";
        String value = "admin";

        //Set
        spyMemcachedClient.set(key, 60 * 60 * 1, value);
        //Get
        String result = spyMemcachedClient.get(key);
        Assert.assertEquals(result, value);
        //logger
        logger.info("set result: " + result);
        //Delete
        spyMemcachedClient.delete(key);
        result = spyMemcachedClient.get(key);
        Assert.assertNull("null", result);
        //logger
        logger.info("delete result: " + result);
    }
}
