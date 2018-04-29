package com.qiu221b.framework.helper;

import com.qiu221b.framework.util.ClassUtil;
import com.qiu221b.framework.util.ReflectionUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class BeanHelper {
    private static final Map<Class<?>, Object> BEAN_MAP = new HashMap<>();

    static{
        Set<Class<?>> beanClassSet = ClassHelper.getBeanClassSet();
        for(Class<?> beanClass : beanClassSet){
            Object obj = ReflectionUtil.newInstance(beanClass);
            BEAN_MAP.put(beanClass, obj);
        }
    }

    public static Map<Class<?>, Object> getBeanMap(){
        return BEAN_MAP;
    }

    public static <T> T getBean(Class<T> cls){
        if(!BEAN_MAP.containsKey(cls)){
        }
        return (T) BEAN_MAP.get(cls);
    }

    public static void setBean(Class<?> cls, Object obj){
        BEAN_MAP.put(cls, obj);
    }
}
