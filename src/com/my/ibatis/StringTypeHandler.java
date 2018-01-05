package com.my.ibatis;

import java.lang.reflect.Method;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class StringTypeHandler implements TypeHandler {

	@Override
	public void setParamters(PreparedStatement preparedStatement,Map<String, Integer> parameterMap, Object value)
			throws SQLException {
		int index = (Integer)(parameterMap.values().toArray()[0]);
		preparedStatement.setString(index+1, (String)value);
	}

	@Override
	public Class getParameterClass(String name) {
		
		return String.class;
	}

	@Override
	public void setValueForObject(ResultSet resultSet, Object object,
			String columnName, String propertyName) {
		try {
			String s = resultSet.getString(columnName);
			String methodName="set"+propertyName.substring(0,1).toUpperCase()+propertyName.substring(1);
			Method method = object.getClass().getDeclaredMethod(methodName, String.class);
			method.invoke(object, s);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		

	}

}
