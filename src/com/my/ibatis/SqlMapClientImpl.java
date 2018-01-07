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
    public Object insert(String sql, Object object) throws SQLException {
        return getLocalSqlMapSession().insert(sql,object);
    }

    @Override
    public Object selectForObject(String sql, Object parameterObject) throws SQLException {
    	
    	return getLocalSqlMapSession().selectForObject(sql,parameterObject);
    }

    private SqlMapSession getLocalSqlMapSession() {
        SqlMapSessionImpl sqlMapSession = (SqlMapSessionImpl) localSqlMapSession.get();
		if(null == sqlMapSession || sqlMapSession.isClosed() ){
			sqlMapSession = (SqlMapSessionImpl) open();
			localSqlMapSession.set(sqlMapSession);
		}
		return localSqlMapSession.get();
	}

	@Override
    public List<Object> selectForList(String sql, Object parameterObject) throws SQLException {
        return getLocalSqlMapSession().selectForList(sql,parameterObject);
    }

    @Override
    public int update(String sql, Object parameterObject) throws SQLException {
        return getLocalSqlMapSession().update(sql,parameterObject);
    }

	@Override
	public SqlMapSession open() {
		
		return new SqlMapSessionImpl(this);
	}

    public SqlMapConfig getSqlMapConfig() {
        return sqlMapConfig;
    }

    @Override
    public void startTransaction() throws SQLException {
        getLocalSqlMapSession().startTransaction();
    }

    @Override
    public void commitTransaction() throws SQLException {
        getLocalSqlMapSession().commitTransaction();
    }

    @Override
    public void endTransaction() throws SQLException {
        getLocalSqlMapSession().endTransaction();
    }
}
