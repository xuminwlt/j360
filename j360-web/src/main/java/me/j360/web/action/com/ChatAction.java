/*
package me.j360.web.action.com;

import me.j360.web.action.BaseAction;
import me.j360.web.zookeeper.queue.ItemSerializer;
import me.j360.web.zookeeper.queue.QueueItemSerializer;
import me.j360.web.zookeeper.queue.ZkClientService;
import org.apache.commons.lang3.StringUtils;
import org.apache.curator.framework.CuratorFramework;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.zookeeper.CreateMode;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.List;

*/
/**
 * 后台Action类 - 工作日历
 * ============================================================================
 * 版权所有  。
 * ----------------------------------------------------------------------------
 *
 *//*


@ParentPackage("com")
public class ChatAction extends BaseAction {
	private static final long serialVersionUID = -5383463207248344967L;

    // 一览
    public String list() {

        return ajaxText("hello");
    }

    public String getVal() {
        return val;
    }

    public void setVal(String val) {
        this.val = val;
    }

    private String val;

    @Resource
    private ZkClientService zkClientService;


    //简单的使用传递值来做数据处理的实体
    public String put() throws Exception {
        //需要使用特定的格式来添加数据到队列，使用ItemSerializer来做格式化生成byte。
        byte[] bytes = ItemSerializer.serialize(val, new QueueItemSerializer());
        String path = "" ;

        path = zkClientService.getPath(bytes);
        return ajaxText( path);
    }

}
*/
