package com.my.ibatis;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * Created by tufei on 2018/1/1.
 */
public class ConnectioProvider {
    public synchronized static Connection getConnection() throws Exception{
        Class.forName("org.postgresql.Driver").newInstance();
        String url ="jdbc:postgresql://localhost:5432/dev";
        //myDB为数据库名
        String user="postgres";
        String password="xxxxxx";
        Connection conn= DriverManager.getConnection(url,user,password);
        return conn;
    }
}
