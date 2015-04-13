package me.j360.core.entity.common;

import me.j360.core.entity.parent.NameEntity;

import javax.persistence.*;
import java.util.Set;

/**
 * Created with us-parent -> com.fz.us.core.entity.common.
 * User: min_xu
 * Date: 2014/9/28
 * Time: 16:00
 * 说明：
 */
@Entity
@Table(name="com_department")
public class Department extends NameEntity {
	private static final long serialVersionUID = -6614052029623997372L;

    public Department(){
        super();
    }

    public Department(String name,Company company,Department parent){
        super();

        this.setName(name);
        this.setParent(parent);
    }
	/**
	 * 是否为系统内置部门 1
	 */
	private int beSystem;


	/**
	 * 描述
	 */
	private String description;
	/**
	 * 岗位
	 */
	private Set<Post> postSet;
	/**
	 * 排序
	 */
	private Integer orderList;
	/**
	 * 父节点
	 */
	private Department parent;
	/**
	 * 子节点
	 */
	private Set<Department> children;
	/**
	 *令牌
	 */
	private String value;
	/**
	 * 职责
	 */
	private Set<Duty> dutySet;

	/**
	 * 小组内部是否共享1 0（不共享）
	 * **/
	private int departmentShare;


	@Column(length = 5000)
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false)
	@OrderBy("name asc")
	public Set<Post> getPostSet() {
		return postSet;
	}

	public void setPostSet(Set<Post> postSet) {
		this.postSet = postSet;
	}
	
	public Integer getOrderList() {
		return orderList;
	}

	public void setOrderList(Integer orderList) {
		this.orderList = orderList;
	}
	@ManyToOne(fetch = FetchType.LAZY)
	public Department getParent() {
		return parent;
	}

	public void setParent(Department parent) {
		this.parent = parent;
	}
	
	@OneToMany(mappedBy = "parent", fetch = FetchType.LAZY)
	@OrderBy("orderList asc")
	public Set<Department> getChildren() {
		return children;
	}

	public void setChildren(Set<Department> children) {
		this.children = children;
	}


	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	@OneToMany(mappedBy = "department", fetch = FetchType.LAZY)
	@OrderBy("id asc")
	public Set<Duty> getDutySet() {
		return dutySet;
	}

	public void setDutySet(Set<Duty> dutySet) {
		this.dutySet = dutySet;
	}

	public int getBeSystem() {
		return beSystem;
	}

	public void setBeSystem(int beSystem) {
		this.beSystem = beSystem;
	}

	public int getDepartmentShare() {
		return departmentShare;
	}

	public void setDepartmentShare(int departmentShare) {
		this.departmentShare = departmentShare;
	}
}