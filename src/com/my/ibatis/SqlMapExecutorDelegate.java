package com.my.ibatis;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.my.ibatis.SqlMapConfig.Sql;

public class SqlMapExecutorDelegate {
	
	private Connection connection;
	
	public SqlMapExecutorDelegate() {
		init();
	}

	private void init() {
		try {
			if(null == connection || connection.isClosed()){
				connection = ConnectioProvider.getConnection();				
			}
			
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void executeQueryForObject(SessionScope scope,Sql sql,Object parameterObject) throws Exception {
		String sqlString = sql.getSqlString();
		PreparedStatement ps = null;
		ResultSet resultSet = null;
		try {
			scope.setStatement(ps);
			ps = connection.prepareStatement(sqlString);
			if(parameterObject.getClass() == sql.getParameterClass()){
				TypeHandlerFactory.getTypeHandler(sql.getParameterClass()).setParamters(ps, sql.getParameterIndexMap(), parameterObject);
				resultSet = ps.executeQuery();
				scope.setResultSet(resultSet);
			}else{
				throw new RuntimeException("wrong paramters");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}finally{
			
		}
		
	}

}
