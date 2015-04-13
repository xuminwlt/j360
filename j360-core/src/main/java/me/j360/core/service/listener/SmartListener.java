package me.j360.core.service.listener;

import me.j360.core.entity.system.Account;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.SmartApplicationListener;
import org.springframework.stereotype.Component;

/**
 * Created with us-parent -> com.fz.us.core.service.listener.
 * User: min_xu
 * Date: 2014/9/19
 * Time: 15:39
 * 说明：
 */

@Component
public class SmartListener implements SmartApplicationListener {

    @Override
    public boolean supportsEventType(final Class<? extends ApplicationEvent> eventType) {
        //return eventType == RegisterEvent.class;
        return false;
    }
    @Override
    public boolean supportsSourceType(final Class<?> sourceType) {
        return sourceType == Account.class;
    }

    @Override
    public void onApplicationEvent(final ApplicationEvent event) {
        System.out.println("王五在孙六之前收到新的内容：" + ((Account)event.getSource()).getUsername());
    }

    @Override
    public int getOrder() {
        return 1;
    }
}
