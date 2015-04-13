package me.j360.base.bean;

import java.util.Map;

/**
 * Bean类 - 结果集
 * ============================================================================
 * ============================================================================
 */
public class Result {
	
	public Result() {
		super();
	}
	
	public Result(int state, String message) {
		this(state, message, null);
	}

	public Result(int state, String message, Map<String, Object> data) {
		super();
		this.state = state;
		this.message = message;
		this.data = data;
	}

    public Result(int state, String message,long errorCode, Map<String, Object> data) {
        super();
        this.state = state;
        this.message = message;
        this.errorCode = errorCode;
        this.data = data;
    }

	public String getId(){
		if(this.getData()!=null){
			if(this.getData().containsKey("id")){
				return (String) this.getData().get("id");
			}
		}
		return null;
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
    /**
     * Error code (0:ok)
     * */
	private long errorCode;

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

    public long getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(long errorCode) {
        this.errorCode = errorCode;
    }


}
