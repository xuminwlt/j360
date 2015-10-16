package me.j360.dubbo.action;

import com.mongodb.gridfs.GridFSDBFile;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import me.j360.base.bean.Pager;
import me.j360.base.bean.Result;
import me.j360.base.service.common.ResultService;
import me.j360.base.util.LogUtil;
import me.j360.base.util.mapper.JsonMapper;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.bson.types.ObjectId;
import org.springframework.context.annotation.Scope;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 后台Action类 - Action基类
 * ============================================================================
 * 版权所有 2014 。
 *
 * @author min_xu
 *
 * @version 0.1 2014-01-20
 * @version 0.2 2014-07-25 update by fallenpanda
 * ============================================================================
 */

@Scope("prototype")
public class BaseAction extends ActionSupport {
	protected static JsonMapper binder = JsonMapper.nonEmptyMapper();
	private static final long serialVersionUID = 6718838822334455667L;
	protected static final int mpManager = 0; //0->cookie 1->session

	protected static final String APP_KEYWORDS = "";


}
