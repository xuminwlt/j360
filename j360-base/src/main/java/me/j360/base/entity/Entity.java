package me.j360.base.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.util.Date;

/**
 * Created with me.j360.base.entity.
 * User: min_xu
 * Date: 2014/8/12
 * Time: 15:36
 * 说明：系统基类
 */
@MappedSuperclass
public class Entity implements Serializable {

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

    @Id
    @Column(length = 32, nullable = false)
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Column(updatable = false)
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

    @Override
    public int hashCode() {
        return id == null ? System.identityHashCode(this) : id.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass().getPackage() != obj.getClass().getPackage()) {
            return false;
        }
        final Entity other = (Entity) obj;
        if (id == null) {
            if (other.getId() != null) {
                return false;
            }
        } else if (!id.equals(other.getId())) {
            return false;
        }
        return true;
    }

    @Column(nullable = false, unique = true)
    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }


}
