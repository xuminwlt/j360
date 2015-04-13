package me.j360.core.entity.common;

import me.j360.base.bean.BaseEnum;
import me.j360.core.entity.parent.BaseEntity;
import me.j360.core.entity.parent.NameEntity;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created with us-parent -> com.fz.us.core.entity.biz.
 * User: min_xu
 * Date: 2014/8/12
 * Time: 17:29
 * 说明：
 */
@Entity
@Table(name="com_users")
public class Users extends BaseEntity {
    private static final long serialVersionUID = 4235713397850259820L;

    public Users(){
        super();
    }
    public Users(String name,Company company,String adminId){
        super();

        this.setName(name);
        this.setAdminId(adminId);
    }


    public String getAdminId() {
        return adminId;
    }
    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }
    /**
     * 账号信息
     */
    private String adminId;


    /**
     * 性别（0女 1男）
     */
    private BaseEnum.SexEnum sex;
    /**
     * 生日
     */
    private Date birthday;
    /**
     * 身份证号
     */
    private String idCard;
    /**
     * 家庭住址
     */
    private String address;
    /**
     * 省
     */
    private String province;
    /**
     * 市
     */
    private String city;
    /**
     * 区
     */
    private String county;
    /**
     * 移动电话
     */
    private String mobile;
    /**
     * 固定电话
     */
    private String phone;
    /**
     * email
     */
    private String email;
    /**
     * 照片头像
     */
    private FileManage headImage;



    /**
     * 名称
     */
    private String name;
    /**
     * 拼音首字母
     */
    private String pinYinHead;
    /**
     * 拼音
     */
    private String pinYin;

    public Date getBirthday() {
        return birthday;
    }
    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }
    public String getIdCard() {
        return idCard;
    }
    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getProvince() {
        return province;
    }
    public void setProvince(String province) {
        this.province = province;
    }
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public String getCounty() {
        return county;
    }
    public void setCounty(String county) {
        this.county = county;
    }
    public String getMobile() {
        return mobile;
    }
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    @OneToOne(fetch = FetchType.LAZY)
    public FileManage getHeadImage() {
        return headImage;
    }
    public void setHeadImage(FileManage headImage) {
        this.headImage = headImage;
    }


    @Column
    @Enumerated(EnumType.STRING)
    public BaseEnum.SexEnum getSex() {
        return sex;
    }

    public void setSex(BaseEnum.SexEnum sex) {
        this.sex = sex;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPinYinHead() {
        return pinYinHead;
    }

    public void setPinYinHead(String pinYinHead) {
        this.pinYinHead = pinYinHead;
    }

    public String getPinYin() {
        return pinYin;
    }

    public void setPinYin(String pinYin) {
        this.pinYin = pinYin;
    }
}

