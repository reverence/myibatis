package com.my.ibatis;

import java.sql.*;
import java.util.List;

/**
 * Created by tufei on 2017/12/29.
 */
public class SqlMapClientImpl implements SqlMapClient {

    private SqlMapConfig sqlMapConfig;
    
    private static ThreadLocal<SqlMapSession>localSqlMapSession = new ThreadLocal<>();
    
    public SqlMapClientImpl(String sqlMapConfigFile){
        /**
         * parse xml file
         */
        try {
            sqlMapConfig = SqlMapConfigParser.parseSqlMap(sqlMapConfigFile);
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public Object insert(String sql, Object object) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Object selectForObject(String sql, Object parameterObject) throws SQLException {
    	
    	return getLocalSqlMapSession().selectForObject(sql,parameterObject);
    }

    private SqlMapSession getLocalSqlMapSession() {
		if(null == localSqlMapSession.get()){
			SqlMapSession session = open();
			localSqlMapSession.set(session);
		}
		return localSqlMapSession.get();
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
	public SqlMapSession open() {
		
		return new SqlMapSessionImpl(sqlMapConfig);
	}
}
