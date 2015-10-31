package me.j360.data.multdatasource;

/**
 * Created with j360 -> me.j360.data.multdatasource.
 * User: min_xu
 * Date: 2015/10/31
 * Time: 9:54
 * 说明：
 */
public class Item {

    private String name;
    private double price;

    public Item(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public String toString() {
        return name + " (" + price + ")";
    }

}