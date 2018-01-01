package com.my.ibatis;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;

/**
 * Created by tufei on 2018/1/1.
 */
public interface TypeHandler {

    public void setParamters(PreparedStatement preparedStatement, Map<String,Integer>parameterMap, Object value) throws SQLException;

    public Class getParameterClass(String name);
}
