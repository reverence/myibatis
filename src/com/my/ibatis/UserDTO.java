package com.my.ibatis;

/**
 * Created by tufei on 2017/12/29.
 */

public class UserDTO {

    private int id;
    private String sex;
    private int age;
    private String userName;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getSex() {
        return sex;
    }
    public void setSex(String sex) {
        this.sex = sex;
    }
    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    
    @Override
    public String toString() {
    	// TODO Auto-generated method stub
    	return "id:"+id+",userName:"+userName+",age:"+age+",sex:"+sex;
    }
}
