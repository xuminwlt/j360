package me.j360.base.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * Created with me.j360.base.entity.
 * User: min_xu
 * Date: 2014/9/15
 * Time: 15:08
 * 说明：
 */
public class MongoEntity implements Serializable {
    private static final long serialVersionUID = -2755329278196422648L;

    /**
     * ID
     */
    protected String id;
    /**
     * 创建日期
     */
    protected Date createDate;
    /**
     * 修改日期
     */
    protected Date modifyDate;
    /**
     * 创建时间戳
     */
    protected String timestamp;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }


}
