package com.my.ibatis;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by tufei on 2018/1/6.
 */
public class TransactionImpl implements Transaction {

    private Connection connection;

    public TransactionImpl(){
        init();
    }

    private void init(){
        try {
            this.connection = ConnectioProvider.getConnection();
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public void commit() throws SQLException {
        connection.commit();
    }

    @Override
    public void rollback() throws SQLException {
        connection.rollback();
    }

    @Override
    public Connection getConnection() {
        return connection;
    }

}
