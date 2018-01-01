package com.my.ibatis;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Created by tufei on 2018/1/1.
 */
public class TypeHandlerFactory {

    private static final Map<Object ,TypeHandler> handlerMap = new HashMap<Object,TypeHandler>();

    static {
        handlerMap.put(Integer.class,new IntegerTypeHandler());//todo 处理
        handlerMap.put("int",new IntegerTypeHandler());
    }

    public static TypeHandler getTypeHandler(Object obj){
        return handlerMap.get(obj);
    }
}
