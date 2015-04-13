package me.j360.core.service.listener;

import me.j360.base.service.listener.BaseListener;
import me.j360.core.bean.event.ResumeEvent;
import me.j360.core.service.common.ResumeBeanService;
import me.j360.ext.entity.Resume;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Created with us-parent -> com.fz.us.core.service.listener.
 * User: min_xu
 * Date: 2014/9/19
 * Time: 14:39
 * 说明：applicationContext.publishEvent -> ResumeListener
 */

@Component
public class ResumeListener extends BaseListener<ResumeEvent> {
    @Resource
    private ResumeBeanService resumeBeanService;

    @Async
    @Override
    public void onApplicationEvent(ResumeEvent resumeEvent) {
        Resume resume = (Resume) resumeEvent.getSource();
        resumeBeanService.translate(resume);
    }
}
