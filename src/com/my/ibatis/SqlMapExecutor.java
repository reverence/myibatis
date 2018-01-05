package com.my.ibatis;

import java.sql.SQLException;
import java.util.List;

public interface SqlMapExecutor {
	/**
     * insert
     * @param sql
     * @param object
     * @return
     */
    public Object insert(String sql,Object object)throws SQLException;

    /**
     * select one
     * @param sql
     * @param parameterObject
     * @return
     */
    public Object selectForObject(String sql,Object parameterObject) throws SQLException;

    /**
     * select list
     * @param sql
     * @param parameterObject
     * @return
     */
    public List<Object> selectForList(String sql, Object parameterObject)throws SQLException;

    /**
     * update
     * @param sql
     * @param parameterObject
     * @return
     */
    public int update(String sql,Object parameterObject)throws SQLException;

}
