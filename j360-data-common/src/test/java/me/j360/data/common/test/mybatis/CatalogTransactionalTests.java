package me.j360.data.common.test.mybatis;

import me.j360.data.multdatasource.Catalog;
import me.j360.data.multdatasource.CustomerContextHolder;
import me.j360.data.multdatasource.CustomerType;
import me.j360.data.multdatasource.Item;
import org.junit.Test;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import spring.SpringContextTestCase;
import spring.SpringTransactionalTestCase;

import javax.annotation.Resource;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
/**
 * Created with j360 -> me.j360.data.multdatasource.
 * User: min_xu
 * Date: 2015/10/31
 * Time: 9:56
 * 说明：使用事务方式，每个方法中不允许同时访问多个数据库，否则会抛出异常，增加事务拦截器实现
 */

@DirtiesContext
@ContextConfiguration(locations = { "/applicationContext-multdatasource.xml" })
public class CatalogTransactionalTests extends SpringTransactionalTestCase {

    @Resource
    private Catalog catalog;


    @Test
    public void testDataSourcegoldItems() {
        CustomerContextHolder.setCustomerType(CustomerType.GOLD);
        List<Item> goldItems = catalog.getItems();
        assertThat(goldItems.size()).isEqualTo(3);
        System.out.println("gold items: " + goldItems);

    }

    @Test
    public void testDataSourcesilverItems() {
        CustomerContextHolder.setCustomerType(CustomerType.SILVER);
        List<Item> silverItems = catalog.getItems();
        assertThat(silverItems.size()).isEqualTo(2);
        System.out.println("silver items: " + silverItems);

    }

    @Test
    public void testDataSourcebronzeItems() {
        CustomerContextHolder.clearCustomerType();
        List<Item> bronzeItems = catalog.getItems();
        assertThat(bronzeItems.size()).isEqualTo(1);
        System.out.println("bronze items: " + bronzeItems);
    }

}