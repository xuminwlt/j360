package me.j360.web.interceptor;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.opensymphony.xwork2.interceptor.ExceptionHolder;
import org.apache.commons.lang.ArrayUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.struts2.ServletActionContext;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 拦截器 - 管理日志
 * ============================================================================

 * ============================================================================
 */

public class LogInterceptor extends AbstractInterceptor {
	private static final long serialVersionUID = 276741467699160227L;
	
	public static final String[] excludeActionClassNames = new String[] {"com.fz.us.web.action.InstallAction"};// 需要排除的Action类名称
	public static final String[] excludeActionMethodNames = new String[] {};// 需要排除的Action方法名称


	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		String result = null;
		result = invocation.invoke();

		return result;
	}

}