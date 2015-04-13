package me.j360.core.entity.common;

import me.j360.core.bean.EnumManage;
import me.j360.core.entity.parent.BaseEntity;

import javax.persistence.*;

/**
 * Created with us-parent -> com.fz.us.core.entity.common.
 * User: min_xu
 * Date: 2014/9/28
 * Time: 16:00
 * 说明：
 */
@Entity
@Table(name="com_duty")
public class Duty extends BaseEntity {
    private static final long serialVersionUID = 13320517262715056L;

    public Duty() {
        super();
    }

    public Duty(Users users, Department department,Post post) {
        super();
        this.users = users;
        this.department = department;
        this.post = post;
    }

    public Duty(Users users, Department department,Post post,EnumManage.DutyEnum dutyEnum) {
        super();
        this.users = users;
        this.department = department;
        this.post = post;
        this.dutyState = dutyEnum;
    }

    public Duty(Users users, Department department,Post post,EnumManage.DutyEnum dutyEnum,int dutyDefault) {
        super();
        this.users = users;
        this.department = department;
        this.post = post;
        this.dutyState = dutyEnum;
        this.dutyDefault = dutyDefault;
    }

    /**
     *用户
     */
    private Users users;
    /**
     *部门
     */
    private Department department;
    /**
     *岗位
     */
    private Post post;

    /**
     *职责情况
     */
    private EnumManage.DutyEnum dutyState;

    /**
     * 是否默认岗位，同一个人的职责类，只存在一个默认1,否则0
     * */
    private int dutyDefault;


    @ManyToOne(fetch = FetchType.LAZY)
    //@JoinColumn(name="users_id")
    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }
    @ManyToOne(fetch = FetchType.LAZY)
    //@JoinColumn(name="department_id")
    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    //@JoinColumn(name="post_id")
    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    @Enumerated(EnumType.STRING)
    public EnumManage.DutyEnum getDutyState() {
        return dutyState;
    }

    public void setDutyState(EnumManage.DutyEnum dutyState) {
        this.dutyState = dutyState;
    }

    public int getDutyDefault() {
        return dutyDefault;
    }

    public void setDutyDefault(int dutyDefault) {
        this.dutyDefault = dutyDefault;
    }


}
