package com.my.ibatis;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by tufei on 2017/12/29.
 */
public interface SqlMapClient {//暴露给客户端
    /**
     * insert
     * @param sql
     * @param object
     * @return
     */
    public Object insert(String sql,Object object);

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
    public List<Object> selectForList(String sql, Object parameterObject);

    /**
     * update
     * @param sql
     * @param parameterObject
     * @return
     */
    public int update(String sql,Object parameterObject);
}
