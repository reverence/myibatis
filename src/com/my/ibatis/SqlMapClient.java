package com.my.ibatis;


/**
 * Created by tufei on 2017/12/29.
 */
public interface SqlMapClient extends SqlMapExecutor{//暴露给客户端
	
	/**
	 * 开启一个会话
	 * @return
	 */
	public SqlMapSession open();
    
}
