package me.j360.core.entity.system;

import me.j360.base.bean.BaseEnum;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * 实体类 - 资源或菜单
 * ============================================================================
 * 版权所有 2014 。
 *
 * 定义目前系统中的资源
 * main
 * com
 * app
 * mp
 * cp
 *-------------------------------------
 * role对应到对应的资源中
 * ROLE_ADMIN
 * ROLE_USER
 * -->ROLE_COMPANY--delete
 * ROLE_MANAGER
 * ROLE_CMG
 * ROLE_MAIN
 *
 * admin对应到role
 * 
 * @author min_xu
 * 
 * @version 0.1 2014-01-20
 * ============================================================================
 */
@javax.persistence.Entity
@Table(name="ss_app")
public class App extends me.j360.base.entity.Entity {

	private static final long serialVersionUID = 8931644891304446093L;

	public App() {
		super();
	}

    public App(BaseEnum.AppEnum appEnum) {
        super();
        this.name = appEnum.value();
        this.value = appEnum.name();
        this.link = "/"+appEnum.name().toLowerCase()+"/";
        this.isAppEnabled = true;
    }

	/**
	 * 添加简单顶级资源
	 * @param name 名称
	 * @param isSystem 是否为系统内置资源
	 * @param description 描述
	 * @param orderList 排序
	 * @param enabled 是否启用
	 * @param value 模糊匹配路径
	 */
	public App(String name, Boolean isSystem, String description,
               Integer orderList, boolean enabled, String value) {
		super();
		this.name = name;
		this.description = description;
		this.value = "/**";
        this.isAppEnabled = true;
	}

	/**
	 * 添加顶级资源
	 * @param name 名称
	 * @param icon 图标
	 * @param isSystem 是否为系统内置资源
	 * @param description 描述
	 * @param orderList 排序
	 * @param enabled 是否启用
	 * @param value 模糊匹配路径
	 */
	public App(String name, String icon, Boolean isSystem, String description,
               Integer orderList, boolean enabled, String value) {
		super();
		this.name = name;
		this.icon = icon;
		this.description = description;
		this.value = "/**";
        this.isAppEnabled = true;
	}

	/**
	 * 添加下级资源
	 * @param name 名称
	 * @param target 目标
	 * @param link 链接
	 * @param icon 图标
	 * @param isSystem 是否为系统内置资源
	 * @param description 描述
	 * @param groupType 组别
	 * @param orderList 排序
	 * @param parent 父节点
	 * @param enabled 是否启用
	 * @param value 模糊匹配路径
	 */
	public App(String name, String target, String link,
               String icon, Boolean isSystem, String description, Integer groupType,
               Integer orderList, App parent, boolean enabled, String value) {
		super();
		this.name = name;
		this.target = target;
		this.link = link;
		this.icon = icon;
		this.description = description;
		this.parent = parent;
		this.value = "/**";
        this.isAppEnabled = true;
	}

	public static final String SEPARATOR = ",";
	/**
	 * 树路径分隔符
	 */
	public static final String PATH_SEPARATOR = "-";
	/**
	 * 树路径
	 */
	private String path;
	/**
	 * 资源名称
	 */
	private String name;
	/**
	 * 目标
	 */
	private String target;
	/**
	 * 链接
	 */
	private String link;
	/**
	 * 资源Icon
	 */
	private String icon;
	/**
	 * 描述
	 */
	private String description;
	/**
	 * 父节点
	 */
	private App parent;
	/**
	 * 子节点
	 */
	private Set<App> children;
	/**
	 * 模糊匹配路径
	 */
	private String value;

    public Boolean getIsAppEnabled() {
        return isAppEnabled;
    }

    public void setIsAppEnabled(Boolean isAppEnabled) {
        this.isAppEnabled = isAppEnabled;
    }
    /**
     *  系统是否启用
     */
    private Boolean isAppEnabled;

    /**
     *  管理角色
     */
    private Set<Role> roleSet;

	@Column(nullable = false, unique = true)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	@Column(length = 5000)
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}


	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}


	@ManyToOne(fetch = FetchType.LAZY)
	public App getParent() {
		return parent;
	}

	public void setParent(App parent) {
		this.parent = parent;
	}
	
	@OneToMany(mappedBy = "parent", fetch = FetchType.LAZY)
	@OrderBy("orderList asc")
	public Set<App> getChildren() {
		return children;
	}

	public void setChildren(Set<App> children) {
		this.children = children;
	}

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "appSet")
    public Set<Role> getRoleSet() {
        return roleSet;
    }
    public void setRoleSet(Set<Role> roleSet) {
        this.roleSet = roleSet;
    }

	@Column(nullable = true, length = 10000)
	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

    @Transient
    public Set<String> getRoleValues(){
        Set<String> values = new HashSet<String>();
        if(this.roleSet == null || this.roleSet.size() == 0) {
            return values;
        }
        for(Role role:this.roleSet){
            values.add(role.getValue());
        }
        return values;
    }
	/**
	 * 获取权限字符串（以分隔符间隔），从其他地方读取，非mysql
	 */
	@Transient
	public String getRoleSetString() {
		StringBuffer stringBuffer = new StringBuffer();
		/*if(this.roleSet == null || this.roleSet.size() == 0) {
			return "";
		}
		for (Role role : this.roleSet) {
			stringBuffer.append(SEPARATOR + role.getValue());
		}
		if (stringBuffer.length() > 0) {
			stringBuffer.deleteCharAt(0);
		}*/
		return stringBuffer.toString();
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}