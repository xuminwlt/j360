package me.j360.base.test;

import me.j360.base.bean.Email;
import me.j360.base.service.common.MimeMailService;
import me.j360.base.service.common.SimpleMailService;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.transaction.TransactionConfiguration;

import javax.annotation.Resource;

/**
 * Created with j360 -> me.j360.web.test.base.
 * User: min_xu
 * Date: 2015-03-12
 * Time: 11:30
 * 说明：
 */
@TransactionConfiguration(defaultRollback=true)
@ContextConfiguration(locations = {"/applicationContext-base.xml" })
public class MailTest extends SpringTransactionalTestCase {

    @Resource
    private SimpleMailService simpleMailService;
    @Resource
    private MimeMailService mimeMailService;

    @Test
    public void simpleMailTest(){
        Email email = new Email();

        simpleMailService.send(email);
    }

    @Test
    public void mimeMailTest(){

        //mimeMailService.setFreemarkerConfiguration(null);
    }

}
