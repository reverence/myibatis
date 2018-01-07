package com.my.ibatis;

/**
 * Created by tufei on 2017/12/29.
 */
public class Main {

    public static void main(String[] args) throws Exception {
        //事务
        SqlMapClient sqlMapClient = new SqlMapClientImpl("sqlmapConfig.xml");
        try{
            int userId = 1;
            UserDTO user = (UserDTO) sqlMapClient.selectForObject("user.findById",userId);
            System.out.println(user);
            sqlMapClient.startTransaction();
            sqlMapClient.insert("user.insert",user);
            sqlMapClient.update("user.update",user);
            sqlMapClient.commitTransaction();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            sqlMapClient.endTransaction();
        }
    }
}
