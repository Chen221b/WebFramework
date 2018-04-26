package com.qiu221b.framework.bean;

import java.util.Map;

public class Param {
    private Map<String, Object> paramMap;

    public Param(Map<String, Object> paramMap){
        this.paramMap = paramMap;
    }

    public long getLong(String name){
        Object obj = paramMap.get(name);
        String str = String.valueOf(obj);
        return Long.parseLong(str);
    }

    public Map<String, Object> getMap(){
        return paramMap;
    }
}
