package me.j360.dubbo.consumer.service;

import org.springframework.stereotype.Service;

/**
 * Created with j360 -> me.j360.dubbo.consumer.service.
 * User: min_xu
 * Date: 2015/4/2
 * Time: 11:24
 * 说明：
 */
@Service
public class DubboLocalService {

    public String helloLocal(String name){
        return "too";
    }
}
