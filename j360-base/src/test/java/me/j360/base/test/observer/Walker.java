package me.j360.base.test.observer;

import java.util.Observable;
import java.util.Observer;

/**
 * Created with j360 -> me.j360.base.test.observer.
 * User: min_xu
 * Date: 2015-03-16
 * Time: 16:21
 * 说明：观察者
 */
public class Walker implements Observer {


    @Override
    public void update(Observable o, Object arg) {
        Police myObserable=(Police) o;
        System.out.println("Data has changed to " +myObserable.getState());
    }
}
