package com.epam.catalog.dao.impl;



import java.sql.Connection; 
import java.sql.DriverManager; 
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.concurrent.ArrayBlockingQueue; 
import java.util.concurrent.BlockingQueue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger; 

public final class ConnectionPool { 
	
 private final static Logger LOG = LogManager.getRootLogger();

 private static ConnectionPool instance = null;
 private BlockingQueue<Connection> connectionQueue;  
 private BlockingQueue<Connection> givenAwayConQueue; 
 
 private static String driver_name;  
 private static String url;
 private static String user;  
 private static String password;
 private static int poll_size;

 private ConnectionPool() throws ConnectionPoolException { 
	 
	 Locale ourLocale = new Locale("en", "GB", "WIN");  
	 ResourceBundle rb = ResourceBundle.getBundle("_MyPropertiesFile", ourLocale);
	 
	 driver_name = rb.getString("db.driver").trim();
	 url = rb.getString("db.url").trim();
	 user = rb.getString("db.user").trim();
	 password = rb.getString("db.password").trim();
	 try {
		 poll_size = Integer.parseInt(rb.getString("db.poolsize").trim());
	 } catch (NumberFormatException e) {
		 poll_size = 5;
		 LOG.error(e);
	 }
	 	 
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
		 Class.forName(driver_name);   
		 
		 givenAwayConQueue = new ArrayBlockingQueue<Connection>(poll_size);    
		 connectionQueue = new ArrayBlockingQueue<Connection>(poll_size);    
		 for (int i = 0; i < poll_size; i++) {     
			 Connection connection = DriverManager.getConnection(url, user, password);
			 connectionQueue.add(connection);
			 }  
		 } catch (SQLException e) {    
			 LOG.error("SQLException in ConnectionPool", e);
			 throw new ConnectionPoolException("SQLException in ConnectionPool",e);   
		 } catch (ClassNotFoundException e) {   
			 LOG.error("Can't find database driver class", e);
			 throw new ConnectionPoolException("Can't find database driver class", e);   
		 } 
  } 
 
 public void dispose() {   
	 try {    
		 closeConnectionsQueue(givenAwayConQueue);    
		 closeConnectionsQueue(connectionQueue);   
	} catch (SQLException e) {    
		LOG.error("Error closing the connection.", e);   
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
			if (!c.isClosed() && c != null) {
				
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
	
