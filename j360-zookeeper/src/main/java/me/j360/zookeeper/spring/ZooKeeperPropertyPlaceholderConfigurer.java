package me.j360.zookeeper.spring;

/**
 * Created with j360 -> me.j360.zookeeper.spring.
 * User: min_xu
 * Date: 2015/3/30
 * Time: 19:21
 * 说明：
 */
import java.io.UnsupportedEncodingException;
import java.util.Properties;

import me.j360.zookeeper.Config;
import me.j360.zookeeper.ZooKeeperConfig;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;


public class ZooKeeperPropertyPlaceholderConfigurer extends PropertyPlaceholderConfigurer {

    public static final String PATH = "zoo.paths";

    @Override
    protected void processProperties(ConfigurableListableBeanFactory beanFactoryToProcess, Properties props)
            throws BeansException {
        super.processProperties(beanFactoryToProcess, props);

        try {
            fillCustomProperties(props);

            System.out.println(props);
        }
        catch (Exception e) {
            // Ignore
            e.printStackTrace();
        }
    }

    private void fillCustomProperties(Properties props) throws Exception {
        byte[] data = getData(props);
        fillProperties(props, data);
    }

    private void fillProperties(Properties props, byte[] data) throws UnsupportedEncodingException {
        String cfg = new String(data, "UTF-8");
        if (StringUtils.isNotBlank(cfg)) {
            // 完整的应该还需要处理：多条配置、value中包含=、忽略#号开头
            String[] cfgItem = StringUtils.split(cfg, "=");
            props.put(cfgItem[0], cfgItem[1]);
        }
    }

    private byte[] getData(Properties props) throws Exception {
        String path = props.getProperty(PATH);
        Config config = new ZooKeeperConfig();
        return config.getConfig(path);
    }

}
