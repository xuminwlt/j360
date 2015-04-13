
package me.j360.core.bean.event;


import me.j360.base.bean.BaseEvent;
import me.j360.ext.entity.Resume;

/**
 * 履历的生成事件
 * */
public class ResumeEvent extends BaseEvent {
    public ResumeEvent(Resume resume) {
        super(resume);
    }
}
