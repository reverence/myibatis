package com.my.ibatis;

import java.sql.*;
import java.util.List;

/**
 * Created by tufei on 2017/12/29.
 */
public class SqlMapClientImpl implements SqlMapClient {

    private SqlMapConfig sqlMapConfig;

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
        SqlMapConfig.SqlMapInfo sqlMapInfo = sqlMapConfig.getMappedSql().get(sql);
        SqlMapConfig.Sql sql1 = sqlMapInfo.getSql();
        SqlMapConfig.ResultMap resultMap = sql1.getResultMap();
        String sqlString = sql1.getSqlString();

        //获取conncetion
        Connection connection = null;
        try{
           connection = ConnectioProvider.getConnection();
        }catch (Exception e){
            throw new RuntimeException(e);
        }
        PreparedStatement ps = connection.prepareStatement(sqlString);
        if(parameterObject.getClass() == sql1.getParameterClass()){
            TypeHandlerFactory.getTypeHandler(sql1.getParameterClass()).setParamters(ps,sql1.getParameterIndexMap(),parameterObject);
            ResultSet resultSet = ps.executeQuery();
            ResultSetMetaData rsmd = resultSet.getMetaData();
            int columnNum = rsmd.getColumnCount();
            while(resultSet.next()){
                rsmd = resultSet.getMetaData();
                for(int i=1;i<=columnNum;i++){
                    String name = rsmd.getColumnName(i);

                }
            }
        }else{
            throw new RuntimeException("wrong paramters");
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

}
