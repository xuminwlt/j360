package me.j360.base.test;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.transaction.TransactionConfiguration;

/**
 * Created with j360 -> me.j360.web.test.base.
 * User: min_xu
 * Date: 2015-03-12
 * Time: 11:30
 * 说明：基于Spring的容器的事件监听模式
 * 见：http://my.oschina.net/smartsales/blog
 */
@TransactionConfiguration(defaultRollback=true)
@ContextConfiguration(locations = {"/applicationContext-base.xml" })
public class SpringEventListenerTest extends SpringTransactionalTestCase {


}
