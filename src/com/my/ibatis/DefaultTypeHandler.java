package com.my.ibatis;

import java.lang.reflect.Method;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by tufei on 2018/1/7.
 */
public class DefaultTypeHandler implements TypeHandler {
    @Override
    public void setParamters(PreparedStatement preparedStatement, Map<String, Integer> parameterMap, Object value) throws SQLException {
        for(String key : parameterMap.keySet()){
            int index = parameterMap.get(key);
            try{
                Class cls = value.getClass().getDeclaredField(key).getType();
                String methodName = "get"+key.substring(0,1).toUpperCase()+key.substring(1);
                Method method = value.getClass().getMethod(methodName,null);
                Object v = method.invoke(value);
                Map<String,Integer> m = new HashMap<>();
                m.put(key,index);
                TypeHandlerFactory.getTypeHandler(cls).setParamters(preparedStatement,m,v);
            }catch (Exception e){
                throw new SQLException(e);
            }

        }
    }

    @Override
    public Class getParameterClass(String name) throws SQLException {
        try {
            return Class.forName(name);
        }catch (Exception e){
            throw new SQLException(e);
        }
    }

    @Override
    public void setValueForObject(ResultSet resultSet, Object object, String columnName, String propertyName) throws SQLException {

    }
}
