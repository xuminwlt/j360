package me.j360.web.zookeeper.log;

import me.j360.base.util.LogUtil;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.framework.recipes.cache.NodeCacheListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created with j360 -> me.j360.web.zookeeper.
 * User: min_xu
 * Date: 2015/3/30
 * Time: 20:14
 * 说明：
 */
public class LogbackLevelListener implements IZKListener {

    //获取logback实例
    Logger log = (Logger) LoggerFactory.getLogger(this.getClass());

    private String path;

    //Logback日志级别ZNode
    public LogbackLevelListener(String path) {
        this.path = path;
    }

    @Override
    public void executor(CuratorFramework client) {
        LogUtil.info(path);

        //使用Curator的NodeCache来做ZNode的监听，不用我们自己实现重复监听
        final NodeCache cache = new NodeCache(client, path);
        cache.getListenable().addListener(new NodeCacheListener() {
            @Override
            public void nodeChanged() throws Exception {

                byte[] data = cache.getCurrentData().getData();

                //设置日志级别
                if (data != null) {
                    String level = new String(data);
                    //Logger logger = (Logger) LoggerFactory.getLogger("root");
                    /*Level newLevel = Level.fromLocationAwareLoggerInteger(Integer.parseInt(level));
                    logger.setLevel(newLevel);
                    System.out.println("Setting logback new level to :" + newLevel.levelStr);*/
                    LogUtil.info("data->"+level);
                }
            }
        });
        try {
            cache.start(true);
        } catch (Exception e) {
            log.error("Start NodeCache error for path: {}, error info: {}", path, e.getMessage());
        }
    }
}