package me.j360.base.test.observer;

import java.util.Observable;
import java.util.Observer;

/**
 * Created with j360 -> me.j360.base.test.observer.
 * User: min_xu
 * Date: 2015-03-16
 * Time: 16:21
 * 说明：被观察者
 */
public class Police extends Observable {
    private String state;
    private String name;

    public void ringOn(){
        setChanged();
        notifyObservers();
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
