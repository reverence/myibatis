package com.my.ibatis;

import java.lang.reflect.Method;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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

	@Override
	public void setValueForObject(ResultSet resultSet, Object object,
			String columnName, String propertyName) {
		try {
			int v = resultSet.getInt(columnName);
			String methodName="set"+propertyName.substring(0,1).toUpperCase()+propertyName.substring(1);
			Method method = object.getClass().getDeclaredMethod(methodName, int.class);
			method.invoke(object, v);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
		
		
	}
}
