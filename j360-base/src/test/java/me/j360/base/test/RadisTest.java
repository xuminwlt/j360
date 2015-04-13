package me.j360.base.test;

import me.j360.base.test.entity.Users;
import me.j360.base.util.LogUtil;
import org.junit.Test;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.transaction.TransactionConfiguration;

import javax.annotation.Resource;

/**
 * Created with j360 -> me.j360.web.test.base.
 * User: min_xu
 * Date: 2015-03-12
 * Time: 11:30
 * 说明：Jadis调用Radis，需要安装Radis服务器
 * 见：http://my.oschina.net/smartsales/blog
 */
@TransactionConfiguration(defaultRollback=true)
@ContextConfiguration(locations = {"/applicationContext-base.xml" })
public class RadisTest extends SpringTransactionalTestCase {
    @Resource
    private RedisTemplate redisTemplate;

    @Test
    public void save() {
        Users users = new Users("xumin","13601827146");
        saveUsers2Redis(users);
    }

    public void saveUsers2Redis(final Users users){
        redisTemplate.execute(new RedisCallback<Object>() {
            @Override
            public Object doInRedis(RedisConnection connection)
                    throws DataAccessException {
                connection.set(
                        redisTemplate.getStringSerializer().serialize(
                                "users.uid." + users.getId()),
                        redisTemplate.getStringSerializer().serialize(
                                users.getName()));
                return null;
            }
        });
    }
    @Test
    public void read(){
        final String id = "8a8a8bdd49ad00be0149ad00c2300000";
        Users users = (Users) redisTemplate.execute(new RedisCallback<Users>() {
            @Override
            public Users doInRedis(RedisConnection connection)
                    throws DataAccessException {
                byte[] key = redisTemplate.getStringSerializer().serialize(
                        "users.uid." + id);
                if (connection.exists(key)) {
                    byte[] value = connection.get(key);
                    String username = (String) redisTemplate.getStringSerializer()
                            .deserialize(value);
                    Users user = new Users();
                    user.setName(username);
                    user.setId(id);
                    return user;
                }
                return null;
            }
        });
        LogUtil.error(users.getName());
    }
}
