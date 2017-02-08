package com.epam.catalog.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import com.epam.catalog.beans.Book;
import com.epam.catalog.beans.BookGenre;
import com.epam.catalog.beans.News;
import com.epam.catalog.dao.DAOException;
import com.epam.catalog.dao.NewsDAO;

public class BookDAO implements NewsDAO{
		
	DBWorker db = DBWorker.getInstance();
	ArrayList<Book> books = new ArrayList<Book>();
	ResultSet result;
	
	public ArrayList<Book> findAll() throws DAOException {
		books.clear();
		try {
			result = db.getDBData("SELECT * FROM `book`");
			while (result.next()) {
				Book book = new Book(result.getString("title"),result.getString("author"),result.getInt("year"),
						result.getString("text"),BookGenre.valueOf(result.getString("genre")),result.getInt("numberOfPages"));
				books.add(book);
			}
		} catch (SQLException | InstantiationException | IllegalAccessException | ClassNotFoundException | ConnectionPoolException e) {
			throw new DAOException(e);
		}
		
		return books;
	}
	
	public ArrayList<Book> findByTitle(String title) throws DAOException {
		books.clear();
		try {
			result = db.getDBData("SELECT * FROM `book` WHERE `title`=\"" + title +"\"");
			while (result.next()) {
				Book book = new Book(result.getString("title"),result.getString("author"),result.getInt("year"),
						result.getString("text"),BookGenre.valueOf(result.getString("genre")),result.getInt("numberOfPages"));
				books.add(book);
			}
		} catch (SQLException | InstantiationException | IllegalAccessException | ClassNotFoundException | ConnectionPoolException e) {
			throw new DAOException(e);
		}
		
		return books;
	}

	public ArrayList<Book> findByAuthor(String author) throws DAOException {
		books.clear();
		try {
			result = db.getDBData("SELECT * FROM `book` WHERE `author`=\"" + author + "\"");
			while (result.next()) {
				Book book = new Book(result.getString("title"),result.getString("author"),result.getInt("year"),
						result.getString("text"),BookGenre.valueOf(result.getString("genre")),result.getInt("numberOfPages"));
				books.add(book);
			}
		} catch (SQLException | InstantiationException | IllegalAccessException | ClassNotFoundException | ConnectionPoolException e) {
			throw new DAOException(e);
		}
		
		return books;
	}
	
	public ArrayList<Book> findByYear(int year) throws DAOException {
		books.clear();
		try {
			result = db.getDBData("SELECT * FROM `book` WHERE `year`=" + year);
			while (result.next()) {
				Book book = new Book(result.getString("title"),result.getString("author"),result.getInt("year"),
						result.getString("text"),BookGenre.valueOf(result.getString("genre")),result.getInt("numberOfPages"));
				books.add(book);
			}
		} catch (SQLException | InstantiationException | IllegalAccessException | ClassNotFoundException | ConnectionPoolException e) {
			throw new DAOException(e);
		}
		
		return books;
	}
	
	public ArrayList<Book> findByText(String text) throws DAOException {
		books.clear();
		try {
			result = db.getDBData("SELECT * FROM `Book` WHERE `text`=\"" + text + "\"");
			while (result.next()) {
				Book book = new Book(result.getString("title"),result.getString("author"),result.getInt("year"),
						result.getString("text"),BookGenre.valueOf(result.getString("genre")),result.getInt("numberOfPages"));
				books.add(book);
			}
		} catch (SQLException | InstantiationException | IllegalAccessException | ClassNotFoundException | ConnectionPoolException e) {
			throw new DAOException(e);
		}
		
		return books;
	}
	
	public ArrayList<Book> findByGenre(String genre) throws DAOException {
		books.clear();
		try {
			while (result.next()) {
				result = db.getDBData("SELECT * FROM `Book` WHERE `genre`=\"" + genre + "\"");
				Book book = new Book(result.getString("title"),result.getString("author"),result.getInt("year"),
						result.getString("text"),BookGenre.valueOf(result.getString("genre")),result.getInt("numberOfPages"));
				books.add(book);
			}
		} catch (SQLException | InstantiationException | IllegalAccessException | ClassNotFoundException | ConnectionPoolException e) {
			throw new DAOException(e);
		}
		
		return books;
	}
	
	public ArrayList<Book> findByPages(int numberOfPages) throws DAOException {
		books.clear();
		try {
			result = db.getDBData("SELECT * FROM `Book` WHERE `numberOfPages`=" + numberOfPages);
			while (result.next()) {
				Book book = new Book(result.getString("title"),result.getString("author"),result.getInt("year"),
						result.getString("text"),BookGenre.valueOf(result.getString("genre")),result.getInt("numberOfPages"));
				books.add(book);
			}
		} catch (SQLException | InstantiationException | IllegalAccessException | ClassNotFoundException | ConnectionPoolException e) {
			throw new DAOException(e);
		}
		
		return books;
	}
	
	public void addNews(News news) throws DAOException {
		if (news instanceof Book) {
			Book book = (Book)news;
			String query = "INSERT INTO `book` (`title`,`author`,`year`,`text`,`genre`,`numberOfPages`) "
					+ "VALUES (\"" + book.getTitle() + "\",\"" + book.getAuthor() + "\","
					+ book.getYear() + ",\"" + book.getText() + "\",\"" + book.getGenre() + "\"," + book.getNumberOfPages() + ")";
			try {
				if(db.changeDBData(query) != 1) {
					throw new DAOException("Problem in insert file");
				}
			} catch (SQLException | InstantiationException | IllegalAccessException | ClassNotFoundException | ConnectionPoolException e) {
				throw new DAOException(e);
			}
		} else {
			throw new DAOException("Incorrect type of news");
		}
		
	}
	
	public void close() throws DAOException {
		try {
			db.closeConnection();
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}
		
}
