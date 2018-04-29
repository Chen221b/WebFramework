package com.qiu221b.framework.proxy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

public abstract class AspectProxy implements Proxy {
    private static final Logger logger = LoggerFactory.getLogger(AspectProxy.class);

    public final Object doProxy(ProxyChain proxyChain){
        Object result = null;
        Class<?> cls = proxyChain.getTargetClass();
        Method method = proxyChain.getTargetMethod();
        Object[] params = proxyChain.getMethodParams();

        begin();
        try {
            if (intercept(cls, method, params)) {
                before(cls, method, params);
                result = proxyChain.doProxyChain();
                after(cls, method, params, result);
            } else {
                result = proxyChain.doProxyChain();
            }
        }catch (Throwable throwable){
            logger.error("proxy failure", throwable);
        }finally {
            end();
        }

        return result;
    }

    public void begin(){}

    public boolean intercept(Class<?> cls, Method method, Object[] params){
        return true;
    }

    public void before(Class<?> cls, Method method, Object[] params){}

    public void after(Class<?> cls, Method method, Object[] params, Object result){}

    public void error(Class<?> cls, Method method, Object[] params){}

    public void end(){}
}
