package me.j360.core.entity.parent;

import me.j360.core.entity.common.Users;

import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

/**
 * Created with us-parent -> com.fz.us.core.entity.
 * User: min_xu
 * Date: 2014/8/12
 * Time: 15:38
 * 说明：业务数据父类，将集成自定义字段，扩展10个字段
 */
@MappedSuperclass
public class FormEntity extends NameEntity {


    //权限按照该字段进行控制，不能为空
    @ManyToOne(fetch = FetchType.LAZY)
    public Users getCreater() {
        return creater;
    }

    public void setCreater(Users creater) {
        this.creater = creater;
    }

    /**
     * 所属销售员
     */
    private Users creater;


}
