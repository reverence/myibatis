package com.my.ibatis;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;

/**
 * Created by tufei on 2018/1/1.
 */
public class IntegerTypeHandler implements TypeHandler {
    @Override
    public void setParamters(PreparedStatement preparedStatement, Map<String,Integer>parameterMap, Object value) throws SQLException {
        //只有一个
        Integer index = (Integer) (parameterMap.values().toArray()[0]);
        preparedStatement.setInt(index+1,(Integer)value);
    }

    @Override
    public Class getParameterClass(String name) {
        return Integer.class;
    }
}
