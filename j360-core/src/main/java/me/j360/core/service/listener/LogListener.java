package me.j360.core.service.listener;

import me.j360.base.service.listener.BaseListener;
import me.j360.core.bean.event.RequestLogEvent;
import me.j360.core.service.common.LogBeanService;
import me.j360.ext.entity.RequestLog;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Created with us-parent -> com.fz.us.core.service.listener.
 * User: min_xu
 * Date: 2014/9/19
 * Time: 14:39
 * 说明：applicationContext.publishEvent -> logListener
 */

@Component
public class LogListener extends BaseListener<RequestLogEvent> {
    @Resource
    private LogBeanService logBeanService;

    @Async
    @Override
    public void onApplicationEvent(RequestLogEvent logEvent) {
        RequestLog log = (RequestLog) logEvent.getSource();
        //logBeanService.translate(log);
    }
}
