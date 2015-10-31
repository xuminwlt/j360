package me.j360.data.multdatasource;

import org.springframework.util.Assert;

/**
 * Created with j360 -> me.j360.data.multdatasource.
 * User: min_xu
 * Date: 2015/10/31
 * Time: 9:56
 * 说明：
 */
public class CustomerContextHolder {

    private static final ThreadLocal<CustomerType> contextHolder =
            new ThreadLocal<CustomerType>();

    public static void setCustomerType(CustomerType customerType) {
        Assert.notNull(customerType, "customerType cannot be null");
        contextHolder.set(customerType);
    }

    public static CustomerType getCustomerType() {
        return (CustomerType) contextHolder.get();
    }

    public static void clearCustomerType() {
        contextHolder.remove();
    }
}