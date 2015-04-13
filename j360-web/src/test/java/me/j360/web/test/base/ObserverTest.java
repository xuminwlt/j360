package me.j360.web.test.base;

import me.j360.base.test.SpringTransactionalTestCase;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.transaction.TransactionConfiguration;

/**
 * Created with j360 -> me.j360.web.test.base.
 * User: min_xu
 * Date: 2015-03-12
 * Time: 11:30
 * 说明：
 */
@TransactionConfiguration(defaultRollback=true)
@ContextConfiguration(locations = {"/applicationContext-base.xml" })
public class ObserverTest extends SpringTransactionalTestCase {

}
