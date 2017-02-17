package com.epam.catalog.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.epam.catalog.bean.Book;
import com.epam.catalog.bean.News;
import com.epam.catalog.bean.genre.BookGenre;
import com.epam.catalog.dao.DAOException;
import com.epam.catalog.dao.NewsDAO;

public class BookDAO implements NewsDAO{
		
	private final static Logger LOG = LogManager.getRootLogger();
	
	private ConnectionPool cp;

	private final static String select_all = "SELECT * FROM `book`";
	private final static String select_by = "SELECT * FROM `book` WHERE `";
	private final static String insert = "INSERT INTO `book` (`title`,`author`,`year`,`text`,`genre`,`numberOfPages`) VALUES (?,?,?,?,?,?)";
	
	public ArrayList<Book> findAll() throws DAOException {
		Connection con = null;
		Statement st = null;
		try {
			cp = ConnectionPool.getInstance();
			con = cp.takeConnection();
			st = con.createStatement();
			return booksCreator(st.executeQuery(select_all));
		} catch (ConnectionPoolException | SQLException e) {
			LOG.error(e);
			throw new DAOException(e);
		} finally {
			try {
				st.close();
				cp.returnConnection(con);
			} catch (ConnectionPoolException | SQLException e) {
				LOG.error(e);
			}
		}
	}
	
	public ArrayList<Book> findBy(String type, String value) throws DAOException {
		
		Connection con = null;
		PreparedStatement ps = null;
		try {
			cp = ConnectionPool.getInstance();
			con = cp.takeConnection();
			ps = con.prepareStatement(select_by + type + "` LIKE ?");
			ps.setString(1, "%" + value + "%");
			return booksCreator(ps.executeQuery());
			
		}catch (ConnectionPoolException | SQLException e) {
			LOG.error(e);
			throw new DAOException(e);
		}finally {
			try {
				ps.close();
				cp.returnConnection(con);
			} catch (ConnectionPoolException | SQLException e) {
				LOG.error(e);
			}
		}
	}
	
	public void addNews(News news) throws DAOException {
		Connection con = null;
		PreparedStatement ps = null;
		if (news instanceof Book) {
			Book book = (Book)news;
			try {
				cp = ConnectionPool.getInstance();
				con = cp.takeConnection();
				ps = con.prepareStatement(insert);
				ps.setString(1, book.getTitle());
				ps.setString(2, book.getAuthor());
				ps.setInt(3, book.getYear());
				ps.setString(4, book.getText());
				ps.setString(5, book.getGenre().toString());
				ps.setInt(6, book.getNumberOfPages());
				ps.executeUpdate();
			}catch (ConnectionPoolException | SQLException e) {
				LOG.error(e);
				throw new DAOException(e);
			}finally {
				try {
					ps.close();
					cp.returnConnection(con);
				} catch (ConnectionPoolException | SQLException e) {
					LOG.error(e);
				}
			}
		} else {
			LOG.error("Incorrect type of news");
			throw new DAOException("Incorrect type of news");
		}
	}
	
	public void closeConnection() {	
		if (cp != null) {
			cp.dispose();
		}
	}
	
	private ArrayList<Book> booksCreator(ResultSet result) throws SQLException {
		ArrayList<Book> books = new ArrayList<Book>();
		while (result.next()) {
			Book book = new Book(result.getString("title"),result.getString("author"),result.getInt("year"),
					result.getString("text"),BookGenre.valueOf(result.getString("genre")),result.getInt("numberOfPages"));
			books.add(book);
		}
		return books;
	}
		
}
