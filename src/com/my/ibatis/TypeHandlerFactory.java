package com.my.ibatis;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by tufei on 2018/1/1.
 */
public class TypeHandlerFactory {

    private static final Map<Object ,TypeHandler> handlerMap = new HashMap<Object,TypeHandler>();

    static {
        handlerMap.put(Integer.class,new IntegerTypeHandler());//todo 可以考虑放入配置文件
        handlerMap.put("int",new IntegerTypeHandler());
        handlerMap.put(String.class, new StringTypeHandler());
        handlerMap.put(int.class, new IntegerTypeHandler());
    }

    public static TypeHandler getTypeHandler(Object obj){
        return handlerMap.get(obj);
    }
}
