package com.my.ibatis;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by tufei on 2018/1/6.
 */
public interface Transaction {

    public void commit() throws SQLException;

    public void rollback() throws SQLException;

    public Connection getConnection();

}
