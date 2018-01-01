package com.my.ibatis;

/**
 * Created by tufei on 2017/12/29.
 */
public class Main {

    public static void main(String[] args) throws Exception {
        SqlMapClient sqlMapClient = new SqlMapClientImpl("sqlmapConfig.xml");
        int userId = 1;
        UserDTO user = (UserDTO) sqlMapClient.selectForObject("user.findById",userId);
    }
}
