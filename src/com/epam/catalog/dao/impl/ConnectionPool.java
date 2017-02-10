package com.epam.catalog.dao.impl;



import java.sql.Connection; 
import java.sql.DriverManager; 
import java.sql.SQLException; 
import java.util.concurrent.ArrayBlockingQueue; 
import java.util.concurrent.BlockingQueue; 

public final class ConnectionPool { 
	
	
 private static ConnectionPool instance = null;
 private BlockingQueue<Connection> connectionQueue;  
 private BlockingQueue<Connection> givenAwayConQueue; 
 
 private final static String DRIVER_NAME = "com.mysql.jdbc.Driver";  
 private final static String URL = "jdbc:mysql://localhost/catalog";  
 private final static String USER = "root";  
 private final static String PASSWORD = "admin";  
 private final static int POLL_SIZE = 5; 

 private ConnectionPool() throws ConnectionPoolException {   
	 initPoolData(); 
  } 
 
 public static ConnectionPool getInstance() throws ConnectionPoolException {
	if (instance == null) {
		instance = new ConnectionPool();
	}
	return instance;
}
 
 public void initPoolData() throws ConnectionPoolException {   
	 
	 try {    
		 
		 Class.forName(DRIVER_NAME);   
		 
		 givenAwayConQueue = new ArrayBlockingQueue<Connection>(POLL_SIZE);    
		 connectionQueue = new ArrayBlockingQueue<Connection>(POLL_SIZE);    
		 
		 for (int i = 0; i < POLL_SIZE; i++) {     
			 Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
			 connectionQueue.add(connection);
			 }   
		 } catch (SQLException e) {    
			 throw new ConnectionPoolException("SQLException in ConnectionPool",e);   
		 } catch (ClassNotFoundException e) {    
			 throw new ConnectionPoolException("Can't find database driver class", e);   
		 } 
  } 
 
 public void dispose() {   
	 try {    
		 closeConnectionsQueue(givenAwayConQueue);    
		 closeConnectionsQueue(connectionQueue);   
	} catch (SQLException e) {    
		// logger.log(Level.ERROR, "Error closing the connection.", e);   
	} 
 } 


 
 public Connection takeConnection() throws ConnectionPoolException {   
	 Connection connection = null;   
	 try {    
		 connection = connectionQueue.take();    
		 givenAwayConQueue.add(connection);   
	 } catch (InterruptedException e) {    
		 throw new ConnectionPoolException("Error connecting to the data source.", e);   
	 }   
	 return connection;  
 }
 
 public void returnConnection(Connection c) throws ConnectionPoolException {
		try {
			if (!c.isClosed()) {
				
				givenAwayConQueue.remove(c);
				connectionQueue.add(c);
			 } else {
				 throw new ConnectionPoolException("Attempting to close closed connection.");
			 }
		} catch (SQLException e) {
			throw new ConnectionPoolException(e);
		}
 }
 

 private void closeConnectionsQueue(BlockingQueue<Connection> queue) throws SQLException  {   
	 Connection connection;   
	 while ((connection = queue.poll()) != null) {  
		connection.close(); 
	 }  
 }

}
	
