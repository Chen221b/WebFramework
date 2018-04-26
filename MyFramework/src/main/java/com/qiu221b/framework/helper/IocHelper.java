package com.qiu221b.framework.helper;

import com.qiu221b.framework.annotation.Inject;
import com.qiu221b.framework.util.ReflectionUtil;
import org.apache.commons.lang3.ArrayUtils;

import java.lang.reflect.Field;
import java.util.Map;

public class IocHelper {
    static{
        Map<Class<?>, Object> beanMap = BeanHelper.getBeanMap();
        for(Map.Entry<Class<?>, Object> beanEntry : beanMap.entrySet()){
            Class<?> beanClass = beanEntry.getKey();
            Object beanInstance = beanEntry.getValue();
            Field[] beanField = beanClass.getFields();
            if(ArrayUtils.isNotEmpty(beanField)){
                for(Field field : beanField){
                    if(field.isAnnotationPresent(Inject.class)){
                        Class<?> beanFieldClass = field.getType();
                        Object beanFieldInstance = beanMap.get(beanFieldClass);
                        if(beanFieldInstance != null){
                            ReflectionUtil.setAttribute(beanInstance, field, beanFieldInstance);
                        }
                    }
                }
            }
        }
    }
}
