package com.qiu221b.framework.aspect;

import com.qiu221b.framework.annotation.Aspect;
import com.qiu221b.framework.annotation.Controller;
import com.qiu221b.framework.proxy.AspectProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

@Aspect(Controller.class)
public class ControllerAspect extends AspectProxy {
    private static final Logger LOGGER = LoggerFactory.getLogger(ControllerAspect.class);
    private long begin;

    public void before(Class<?> cls, Method method, Object[] params){
        LOGGER.debug("-------begin------");
        LOGGER.debug(String.format("class: %s", cls.getName()));
        LOGGER.debug(String.format("method: %s", method.getName()));
        begin = System.currentTimeMillis();
    }

    public void after(Class<?> cls, Method method, Object[] params, Object result){
        LOGGER.debug(String.format("time: %dms", System.currentTimeMillis()));
        LOGGER.debug("------end------");
    }
}
