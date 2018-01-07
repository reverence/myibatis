package com.my.ibatis;

public interface SqlMapSession extends SqlMapExecutor,SqlMapTransactionManager{

	public void close();
}
