package com.my.ibatis;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class SqlMapSessionImpl implements SqlMapSession {
	
	private SqlMapClientImpl sqlMapClient;

	private boolean closed;

	private Transaction transaction;

	private SqlExecutor sqlExecutor;

	private boolean needRollback = false;

	public SqlMapSessionImpl(SqlMapClientImpl client){
		this.sqlMapClient = client;
		this.sqlExecutor = new SqlExecutor(client.getSqlMapConfig());
	}

	@Override
	public Object insert(String sqlId, Object object) throws SQLException {
		Transaction transaction = getTransaction();
		boolean autoCommit = (null == transaction ||transaction.getConnection().getAutoCommit());
		try{
			transaction = getCurrentTransaction(transaction,autoCommit);
			Object object1 = sqlExecutor.executeInsert(sqlId,transaction,object);
			autoCommitTransaction(transaction,autoCommit);
			return object1;
		}catch (Exception e){
			if(!autoCommit){
				needRollback = true;
			}
			throw new SQLException(e);
		}finally {
			autoEndTransaction(transaction,autoCommit);
		}
	}

	private void autoEndTransaction(Transaction transaction, boolean autoCommit) {
		if(autoCommit){
			try{
				if(!transaction.getConnection().isClosed()){
					transaction.getConnection().close();
				}
			}catch (Exception e){
				throw new RuntimeException(e);
			}
			this.close();
		}

	}

	private void autoCommitTransaction(Transaction transaction, boolean autoCommit) throws Exception {

	}

	private Transaction getCurrentTransaction(Transaction transaction, boolean autoCommit) {
		Transaction t = transaction;
		if(autoCommit){
			t = new TransactionImpl();
		}
		return t;
	}

	@Override
	public Object selectForObject(String sqlId, Object parameterObject) throws SQLException {
		List list = selectForList(sqlId,parameterObject);
		return list.get(0);
	}

	@Override
	public List<Object> selectForList(String sqlId, Object parameterObject)throws SQLException {
		Transaction transaction = getTransaction();
		boolean autoCommit = (null == transaction ||transaction.getConnection().getAutoCommit());
		try{
			transaction = getCurrentTransaction(transaction,autoCommit);
			List<Object> list = sqlExecutor.executeQuery(sqlId,transaction,parameterObject);
			autoCommitTransaction(transaction,autoCommit);
			return list;
		}catch (Exception e){
			if(!autoCommit){
				needRollback = true;
			}
			throw new SQLException(e);
		}finally {
			autoEndTransaction(transaction,autoCommit);
		}
	}

	@Override
	public int update(String sqlId, Object parameterObject) throws SQLException {
		Transaction transaction = getTransaction();
		boolean autoCommit = (null == transaction ||transaction.getConnection().getAutoCommit());
		try{
			transaction = getCurrentTransaction(transaction,autoCommit);
			int c = sqlExecutor.executeUpdate(sqlId,transaction,parameterObject);
			autoCommitTransaction(transaction,autoCommit);
			return c;
		}catch (Exception e){
			if(!autoCommit){
				needRollback = true;
			}
			throw new SQLException(e);
		}finally {
			autoEndTransaction(transaction,autoCommit);
		}
	}

	@Override
	public void close() {
		if(null != sqlMapClient){
			sqlMapClient = null;
		}
		if(null != sqlExecutor){
			sqlExecutor = null;
		}
		if(null != transaction){
			transaction = null;
		}
		this.closed = true;
	}

	public boolean isClosed() {
		return closed;
	}

	@Override
	public void startTransaction() throws SQLException {
		transaction = new TransactionImpl();
		transaction.getConnection().setAutoCommit(false);
	}

	@Override
	public void commitTransaction() throws SQLException {
		transaction.getConnection().commit();
	}

	@Override
	public void endTransaction() throws SQLException {
		Connection connection = transaction.getConnection();
		if(needRollback){
			connection.rollback();
		}
		if(null != connection && !connection.isClosed()){
			connection.close();
		}
		this.close();
	}

	public Transaction getTransaction() {
		return transaction;
	}
}
