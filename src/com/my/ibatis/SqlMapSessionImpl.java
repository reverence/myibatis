package com.my.ibatis;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.List;

import com.my.ibatis.SqlMapConfig.ResultMap;
import com.my.ibatis.SqlMapConfig.Sql;
import com.my.ibatis.SqlMapConfig.SqlMapInfo;

public class SqlMapSessionImpl implements SqlMapSession {
	
	private SqlMapConfig sqlMapConfig;
	
	private SqlMapExecutorDelegate sqlMapExecutorDelegate;
	
	private SessionScope sessionScope;
	
	public SqlMapSessionImpl(SqlMapConfig config){
		this.sqlMapConfig = config;
		this.sqlMapExecutorDelegate = new SqlMapExecutorDelegate();
		this.sessionScope = new SessionScope();
	}

	@Override
	public Object insert(String sql, Object object) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object selectForObject(String sqlId, Object parameterObject) throws SQLException {
		
		/*SqlMapInfo sqlMapInfo = sqlMapConfig.getMappedSql().get(sqlId);
		Sql sql2 = sqlMapInfo.getSql();
		Object resultObject = null;
		try {
			sqlMapExecutorDelegate.executeQueryForObject(sessionScope,sql2,parameterObject);
			resultObject = buildResultObject(sessionScope,sql2);
		} catch (Exception e) {
			e.printStackTrace();
			throw new SQLException(e);
		}finally{
			cleanUpSessionScope(sessionScope);
		}		
		return resultObject;*/
	}

	private void cleanUpSessionScope(SessionScope sessionScope2) {
		try {
			if(null != sessionScope2.getResultSet()){
				sessionScope2.getResultSet().close();
			}
			if(null != sessionScope2.getStatement()){
				sessionScope2.getStatement().close();
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
	}

	private Object buildResultObject(SessionScope sessionScope2, Sql sql2) throws Exception {
		ResultSet resultSet = sessionScope2.getResultSet();
		if(null != resultSet){
			ResultSetMetaData rsmd = resultSet.getMetaData();
			ResultMap resultMap = sql2.getResultMap();
            int columnNum = rsmd.getColumnCount();
            rsmd = resultSet.getMetaData();
            String clsString = resultMap.getClassz();
            Class cls = null;
            Object object = null;
            try {
            	cls = Class.forName(clsString);
            	object = cls.newInstance();
			} catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
            for(int i=1;i<=columnNum;i++){
                String name = rsmd.getColumnName(i);
                String fieldName = resultMap.getPropertyMap().get(name.toUpperCase());
                try {
                	Class class1 = cls.getDeclaredField(fieldName).getType();
                	TypeHandlerFactory.getTypeHandler(class1).setValueForObject(resultSet,object,name,fieldName);
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
                
            }
            return object;
        }
		
		return null;
	}

	@Override
	public List<Object> selectForList(String sql, Object parameterObject) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int update(String sql, Object parameterObject) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub

	}

	public SqlMapConfig getSqlMapConfig() {
		return sqlMapConfig;
	}

	public void setSqlMapConfig(SqlMapConfig sqlMapConfig) {
		this.sqlMapConfig = sqlMapConfig;
	}

}
