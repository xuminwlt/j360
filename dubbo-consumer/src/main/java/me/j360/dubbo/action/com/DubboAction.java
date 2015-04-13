package me.j360.dubbo.action.com;

import me.j360.base.util.LogUtil;
import me.j360.dubbo.action.BaseAction;
import me.j360.dubbo.api.TestRegistryService;
import org.apache.struts2.convention.annotation.ParentPackage;

import javax.annotation.Resource;

/**
 * Created with j360 -> me.j360.dubbo.com.
 * User: min_xu
 * Date: 2015/4/2
 * Time: 11:29
 * 说明：
 */
@ParentPackage("com")
public class DubboAction extends BaseAction {
    private static final long serialVersionUID = -5383463207248344967L;

    @Resource
    private TestRegistryService testRegistryService;

    public String hello(){
        String name = testRegistryService.hello("hello dubbo");
        LogUtil.info(name);

        return ajaxText(name);
    }
}
