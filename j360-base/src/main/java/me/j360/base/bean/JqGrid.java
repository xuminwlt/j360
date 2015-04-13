package me.j360.base.bean;

import java.util.Map;

/**
 * ============================================================================
 */
public class JqGrid {

	public JqGrid() {
		super();
	}

	public JqGrid(int state, String message) {
		this(state, message, null);
	}

	public JqGrid(int state, String message, Map<String, Object> data) {
		super();
		this.state = state;
		this.message = message;
		this.data = data;
	}
	
	/**
	 * 0失败|1成功
	 */
	private int state;
	/**
	 * 返回信息
	 */
	private String message;
	/**
	 * 返回结果集
	 */
	private Map<String, Object> data;
	
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Map<String, Object> getData() {
		return data;
	}
	public void setData(Map<String, Object> data) {
		this.data = data;
	}
	
}
