package me.j360.base.service.listener;

import me.j360.base.bean.BaseEvent;
import org.springframework.context.ApplicationListener;

/**
 * Created with me.j360.base.service.lisener.
 * User: min_xu
 * Date: 2014/9/19
 * Time: 14:35
 * 说明：
 */
public abstract class BaseListener<T extends BaseEvent> implements ApplicationListener<T> {

}
