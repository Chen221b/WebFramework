package com.qiu221b.framework;

import com.qiu221b.framework.annotation.Controller;
import com.qiu221b.framework.helper.AopHelper;
import com.qiu221b.framework.helper.BeanHelper;
import com.qiu221b.framework.helper.ClassHelper;
import com.qiu221b.framework.helper.IocHelper;
import com.qiu221b.framework.util.ClassUtil;

public class HelperLoader {
    public static void init(){
        Class<?>[] classList = {ClassHelper.class, BeanHelper.class, IocHelper.class, Controller.class, AopHelper.class};
        for(Class<?> cls : classList){
            ClassUtil.loadClass(cls.getName(), true);
        }
    }
}
