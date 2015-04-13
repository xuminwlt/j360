package me.j360.core.entity.biz;


import me.j360.core.entity.parent.FormEntity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created with us-parent -> com.fz.us.core.entity.biz.
 * User: min_xu
 * Date: 2014/9/28
 * Time: 15:58
 * 说明：
 */
@Entity
@Table(name="biz_Linkman")
public class Linkman extends FormEntity {

    private static final long serialVersionUID = -7894524049778924812L;

    public Linkman(){
        super();
    }
    public Linkman(Client client,String name){
        super();
        this.setClient(client);
        this.setName(name);
    }
    /**
     * 客户
     */
    private Client client;
    /**
     * 客户备注名
     */
    private String  nickName;
    /**
     * 职位
     */
    private String duty;
    /**
     * 移动电话
     */
    private String mobile;
    /**
     * 移动电话
     */
    private String mobile_back;
    /**
     * 固定电话
     */
    private String tel;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 个人喜好
     */
    private String preferences;
    /**
     * 是否有决策权 0/1
     */
    private String ifHasDecision;
    /**
     * 生日
     */
    private Date birthday;
    private int birthdayMonth;
    private int birthdayDay;
    /**
     * 备注
     */
    private String remark;

    public String getMobile() {
        return mobile;
    }
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
    public String getRemark() {
        return remark;
    }
    public void setRemark(String remark) {
        this.remark = remark;
    }
    public String getDuty() {
        return duty;
    }
    public void setDuty(String duty) {
        this.duty = duty;
    }
    public String getTel() {
        return tel;
    }
    public void setTel(String tel) {
        this.tel = tel;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPreferences() {
        return preferences;
    }
    public void setPreferences(String preferences) {
        this.preferences = preferences;
    }
    public String getIfHasDecision() {
        return ifHasDecision;
    }
    public void setIfHasDecision(String ifHasDecision) {
        this.ifHasDecision = ifHasDecision;
    }
    @ManyToOne(fetch = FetchType.LAZY)
    public Client getClient() {
        return client;
    }
    public void setClient(Client client) {
        this.client = client;
    }
    public String getMobile_back() {
        return mobile_back;
    }
    public void setMobile_back(String mobile_back) {
        this.mobile_back = mobile_back;
    }
    public String getNickName() {
        return nickName;
    }
    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
    public Date getBirthday() {
        return birthday;
    }
    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }
    public int getBirthdayMonth() {
        return birthdayMonth;
    }
    public void setBirthdayMonth(int birthdayMonth) {
        this.birthdayMonth = birthdayMonth;
    }
    public int getBirthdayDay() {
        return birthdayDay;
    }
    public void setBirthdayDay(int birthdayDay) {
        this.birthdayDay = birthdayDay;
    }
}


