package me.j360.base.test.entity;

import me.j360.base.bean.BaseEnum;

/**
 * Created with j360 -> me.j360.base.test.entity.
 * User: min_xu
 * Date: 2015-03-16
 * Time: 16:04
 * 说明：
 */
public class Users {

    public Users(){

    }
    public Users(String name,String phone){

    }

    private String id;
    private String name;
    private BaseEnum.SexEnum sex;
    private int age;
    private String phone;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BaseEnum.SexEnum getSex() {
        return sex;
    }

    public void setSex(BaseEnum.SexEnum sex) {
        this.sex = sex;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

}
