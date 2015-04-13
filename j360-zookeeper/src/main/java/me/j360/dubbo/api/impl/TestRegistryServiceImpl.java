package me.j360.dubbo.api.impl;

import me.j360.dubbo.api.TestRegistryService;
import org.springframework.stereotype.Service;

/**
 * Created with j360 -> me.j360.dubbo.api.impl.
 * User: min_xu
 * Date: 2015/4/2
 * Time: 10:19
 * 说明：
 */
@Service("testRegistryService")
public class TestRegistryServiceImpl implements TestRegistryService {
    public String hello(String name) {
        return "hello"+name;
    }
}
