package me.j360.data.multdatasource;

/**
 * Created with j360 -> me.j360.data.multdatasource.
 * User: min_xu
 * Date: 2015/10/31
 * Time: 9:55
 * 说明：
 */
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class CustomerRoutingDataSource extends AbstractRoutingDataSource {

    @Override
    protected Object determineCurrentLookupKey() {
        return CustomerContextHolder.getCustomerType();
    }
}