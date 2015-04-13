package me.j360.core.bean;

import java.io.Serializable;

public class ApiRequest implements Serializable {
	private static final long serialVersionUID = -2755329278196422648L;
	
	private String companyId;
	private String targetId;
	private String targetEntity;
	private String actionType;
	
	public String getTargetId() {
		return targetId;
	}
	public void setTargetId(String targetId) {
		this.targetId = targetId;
	}
	public String getTargetEntity() {
		return targetEntity;
	}
	public void setTargetEntity(String targetEntity) {
		this.targetEntity = targetEntity;
	}
	public String getActionType() {
		return actionType;
	}
	public void setActionType(String actionType) {
		this.actionType = actionType;
	}
	public String getCompanyId() {
		return companyId;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	
	
	
}
