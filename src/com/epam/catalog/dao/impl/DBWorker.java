package com.epam.catalog.dao.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBWorker {
	
	
	private Integer affected_rows = 0;
	private static DBWorker instance = null;
	
	Statement statement;
	Connection connect;
	
	ConnectionPool cp; 
	
	public static DBWorker getInstance()
	{
		if (instance == null)
		{
			instance = new DBWorker();
			
		}
		return instance;
	}
	
	
	private DBWorker()
	{	
	}
	
	
	public ResultSet getDBData(String query) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException, ConnectionPoolException
	{
		cp = ConnectionPool.getInstance();
		connect = cp.takeConnection();
		statement = connect.createStatement();
		ResultSet resultSet = statement.executeQuery(query);
		cp.returnConnection(connect);
		connect = null;
		return resultSet;
	}
	
	
	public Integer changeDBData(String query) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException, ConnectionPoolException
	{
		
		cp = ConnectionPool.getInstance();
		connect = cp.takeConnection();
		statement = connect.createStatement();
		this.affected_rows = statement.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
		cp.returnConnection(connect);
		connect = null;
		return this.affected_rows;
		
		
	}

	
	public Integer getAffectedRowsCount()
	{
		return this.affected_rows;
	}
	
	public void closeConnection() throws SQLException {	
			
		if (statement != null) {
			statement.close();
		}
		if (cp != null) {
			cp.dispose();
		}
	}


}
