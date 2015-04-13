package me.j360.core.entity.system;

import me.j360.base.bean.BaseEnum;

import javax.persistence.*;
import java.util.Set;

/**
 * 实体类 -角色
 * ============================================================================
 * 版权所有 2014 。
 * 
 * @author min_xu
 *
 * @version 0.1 2014-01-20
 * @version 0.2 2014-07-23 update by fallenpanda
 * ============================================================================
 */
@Entity
@Table(name="sys_role")
public class Role extends me.j360.base.entity.Entity {

	private static final long serialVersionUID = 4024678941753035423L;

    public Role(){
        super();
    }

    public Role(BaseEnum.RoleEnum roleEnum){
        super();
        this.name = roleEnum.value();
        this.value = roleEnum.name();

        this.ifSystem = 1;
        this.isRoleEnabled = true;
    }
	/**
	 * 角色名称
	 */
	private String name;
	/**
	 * 角色标识
	 */
	private String value;
	/**
	 * 是否为系统内置角色(0|1)
	 */
	private int ifSystem;
	/**
	 * 描述
	 */
	private String remark;
	/**
	 * 管理员
	 */
	private Set<Account> accountSet;



    private Set<App> appSet;


    /**
     *  角色是否启用
     */
    private Boolean isRoleEnabled;

	@Column(nullable = false, unique = true)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Column(nullable = false, unique = true)
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	@Column(nullable = false, updatable = false)
	public int getIfSystem() {
		return ifSystem;
	}
	public void setIfSystem(int ifSystem) {
		this.ifSystem = ifSystem;
	}
	@Column(length = 5000)
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false)
	@OrderBy("id asc")
	public Set<Account> getAdminSet() {
		return accountSet;
	}
	public void setAdminSet(Set<Account> adminSet) {
		this.accountSet = adminSet;
	}

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinColumn
    public Set<App> getAppSet() {
        return appSet;
    }

    public void setAppSet(Set<App> appSet) {
        this.appSet = appSet;
    }

    public Boolean getIsRoleEnabled() {
        return isRoleEnabled;
    }

    public void setIsRoleEnabled(Boolean isRoleEnabled) {
        this.isRoleEnabled = isRoleEnabled;
    }

	@Transient
	public boolean isSystem() {
		return ifSystem==1;
	}

}