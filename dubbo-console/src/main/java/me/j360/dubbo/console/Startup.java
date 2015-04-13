package me.j360.dubbo.console;

/**
 * Created with j360 -> me.j360.zookeeper.
 * User: min_xu
 * Date: 2015/3/30
 * Time: 19:21
 * 说明：
 */
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Startup {

    public static void main(String[] args) {
        new ClassPathXmlApplicationContext("classpath:/api.xml");
    }

}
