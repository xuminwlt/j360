package me.j360.base.test;

import me.j360.base.test.entity.Users;
import me.j360.base.util.mapper.JsonMapper;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.transaction.TransactionConfiguration;

/**
 * Created with j360 -> me.j360.web.test.base.
 * User: min_xu
 * Date: 2015-03-12
 * Time: 11:30
 * 说明：fastxml gson生成json
 */
@TransactionConfiguration(defaultRollback=true)
@ContextConfiguration(locations = {"/applicationContext-base.xml" })
public class JsonTest extends SpringTransactionalTestCase {

    private JsonMapper jsonMapper = JsonMapper.nonEmptyMapper();

    public void fastJsonTest(){
        Users users = new Users();
        users.setId("1");
        users.setName("徐敏");
        String json = jsonMapper.toJson(users);
    }

    public void gsonTest(){

    }



}
