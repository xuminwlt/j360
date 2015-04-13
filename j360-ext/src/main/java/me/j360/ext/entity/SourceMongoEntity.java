package me.j360.ext.entity;


import me.j360.base.entity.MongoEntity;

/**
 * Created with us-parent -> com.fz.us.modules.entity.
 * User: min_xu
 * Date: 2014/9/15
 * Time: 16:04
 * 说明：
 */
public class SourceMongoEntity extends MongoEntity {

    private String targetId;
    private String targetClass;
    protected String bizerId;
    protected String companyId;


    public String getTargetId() {
        return targetId;
    }

    public void setTargetId(String targetId) {
        this.targetId = targetId;
    }

    public String getTargetClass() {
        return targetClass;
    }

    public void setTargetClass(String targetClass) {
        this.targetClass = targetClass;
    }

    public String getBizerId() {
        return bizerId;
    }

    public void setBizerId(String bizerId) {
        this.bizerId = bizerId;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

}
