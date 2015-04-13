
package me.j360.core.bean.event;


import me.j360.base.bean.BaseEvent;
import me.j360.ext.entity.RequestLog;

/**
 * Log的生成事件
 * */
public class RequestLogEvent extends BaseEvent {
    public RequestLogEvent(RequestLog log) {
        super(log);
    }
}
