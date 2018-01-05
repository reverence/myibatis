package com.my.ibatis;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class SessionScope {
	
	private ResultSet resultSet;
	
	private PreparedStatement statement;

	public ResultSet getResultSet() {
		return resultSet;
	}

	public void setResultSet(ResultSet resultSet) {
		this.resultSet = resultSet;
	}

	public PreparedStatement getStatement() {
		return statement;
	}

	public void setStatement(PreparedStatement statement) {
		this.statement = statement;
	}

}
