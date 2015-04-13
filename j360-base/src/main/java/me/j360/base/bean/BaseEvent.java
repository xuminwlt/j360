/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package me.j360.base.bean;

import me.j360.base.entity.Entity;
import me.j360.base.entity.MongoEntity;
import org.springframework.context.ApplicationEvent;

/**
 * Created with j360 -> me.j360.base.bean.
 * User: min_xu
 * Date: 2015-03-12
 * Time: 11:30
 * 说明：
 */
public abstract class BaseEvent extends ApplicationEvent {

    public BaseEvent(Entity entity) {
        super(entity);
    }

    public BaseEvent(MongoEntity entity) {
        super(entity);
    }
}
