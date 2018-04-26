package com.qiu221b.framework.util;

import org.junit.Test;

import java.util.Iterator;
import java.util.Set;
import static com.qiu221b.framework.util.ClassUtil.*;

public class ClassUtilTest {
    @Test
    public void getClassSetTest(){
        Set<Class<?>> cls = getClassSet("com.qiu221b.framework");
        Iterator<Class<?>> iter = cls.iterator();
        while(iter.hasNext()){
            Class cl = iter.next();
            System.out.println(cl.getName());
        }
    }
}
