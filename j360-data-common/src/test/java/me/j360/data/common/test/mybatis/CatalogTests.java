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
 * 说明：
 */

@DirtiesContext
@ContextConfiguration(locations = { "/applicationContext-multdatasource.xml" })
public class CatalogTests extends SpringContextTestCase {

    @Resource
    private Catalog catalog;


    @Test
    public void testDataSourceRouting() {
        CustomerContextHolder.setCustomerType(CustomerType.GOLD);
        List<Item> goldItems = catalog.getItems();
        assertThat(3).isEqualTo(goldItems.size());
        System.out.println("gold items: " + goldItems);

        CustomerContextHolder.setCustomerType(CustomerType.SILVER);
        List<Item> silverItems = catalog.getItems();
        assertThat(2).isEqualTo(silverItems.size());
        System.out.println("silver items: " + silverItems);

        CustomerContextHolder.clearCustomerType();
        List<Item> bronzeItems = catalog.getItems();
        assertThat(1).isEqualTo(bronzeItems.size());
        System.out.println("bronze items: " + bronzeItems);
    }

}