package me.j360.core.entity.common;


import me.j360.core.entity.parent.NameEntity;

import javax.persistence.*;
import java.util.Set;

/**
 * 实体类 -岗位
 * ============================================================================
 * 版权所有 2014 。
 * 
 * @author min_xu
 * 
 * @version 0.1 2014-01-20
 * ============================================================================
 */
@Entity
@Table(name="com_post")
public class Post extends NameEntity {
	private static final long serialVersionUID = -6614052029623997372L;

	/**
	 * 令牌
	 */
	private String value; 
	/**
	 * 是否为系统内置部门 1
	 */
	private int beSystem;
	/**
	 * 描述
	 */
	private String description;
	/**
	 * 部门
	 */
	private Set<Department> departmentSet;
	/**
	 * 职责
	 */
	private Set<Duty> dutySet;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Column(length = 5000)
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@ManyToMany(cascade = {CascadeType.ALL},fetch = FetchType.EAGER, mappedBy = "postSet")
	@OrderBy("name asc")
	public Set<Department> getDepartmentSet() {
		return departmentSet;
	}

	public void setDepartmentSet(Set<Department> departmentSet) {
		this.departmentSet = departmentSet;
	}

	
	@OneToMany(mappedBy = "post", fetch = FetchType.EAGER)
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
}