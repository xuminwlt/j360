package me.j360.web.test.core.entity;

import me.j360.base.test.SpringTransactionalTestCase;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.transaction.TransactionConfiguration;

/**
 * Created with j360 -> me.j360.web.test.core.entity.
 * User: min_xu
 * Date: 2015-03-11
 * Time: 21:16
 * 说明：
 */

@TransactionConfiguration(defaultRollback=true)
@ContextConfiguration(locations = {"/applicationContext.xml" })
public class BaseEntityTest extends SpringTransactionalTestCase {

}
