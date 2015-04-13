package me.j360.base.service.observer;

import org.springframework.stereotype.Service;

import java.util.Observable;
import java.util.Observer;

/**
 * Created with me.j360.base.service.observer.
 * User: min_xu
 * Date: 2014/9/19
 * Time: 12:08
 * 说明：
 */

@Service
public class BaseObserver implements Observer {

    public void update(Observable o, Object arg) {
        System.out.println(o.getClass() + " " + arg.toString());
    }
}
