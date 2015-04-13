/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package me.j360.base.service.common;

import me.j360.base.bean.Result;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * 
 * @author calvin
 */
@Service
public class ResultService {
	private static Logger logger = LoggerFactory.getLogger(ResultService.class);

	public Result build(int state,long errorCode,String message,Map<String, Object> data) {
        return new Result(state,message,errorCode,data);
    }

    public Result success(){
        return new Result(1,"操作成功",0,null);
    }

    public Result error(){
        return new Result(0,"操作失败",1,null);
    }

    public Result successWithId(String id){
        Map map = new HashMap<String, String>();
        map.put("id",id);
        return build(1, 0, "操作成功", map);
    }

    public Result success(String message){
        return new Result(1,message,0,null);
    }

    public Result error(String message){
        return new Result(0,message,1,null);
    }

    public String toAjax(Result result){
        int errorCode = result.getState();
        String errorMessage = StringUtils.isNotEmpty(result.getMessage())?result.getMessage():"";
        JSONObject object = result.getData()!=null? JSONObject.fromObject(result.getData()):null;

        Map<String, Object> mpMap = new HashMap<String, Object>();
        Map<String, Object> rsMap = new HashMap<String, Object>();
        rsMap.put("errorCode", errorCode);
        rsMap.put("errorMessage", errorMessage);
        mpMap.put("result", JSONObject.fromObject(rsMap));
        mpMap.put("data", object==null?"":object);
        return JSONObject.fromObject(mpMap).toString();
    }
}
