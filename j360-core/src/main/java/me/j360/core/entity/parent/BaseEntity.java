package me.j360.core.entity.parent;

import me.j360.base.bean.BaseEnum;

import javax.persistence.*;

/**
 * Created with us-parent -> com.fz.us.core.entity.
 * User: min_xu
 * Date: 2014/8/12
 * Time: 15:38
 * 说明：US基类，所有US对象继承此类
 */
@MappedSuperclass
public class BaseEntity extends me.j360.base.entity.Entity {
    private static final long serialVersionUID = 5929110077551124922L;
    /**
     * 文档状态
     */
    private BaseEnum.StateEnum state;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    public BaseEnum.StateEnum getState() {
        return state;
    }
    public void setState(BaseEnum.StateEnum state) {
        this.state = state;
    }
}
