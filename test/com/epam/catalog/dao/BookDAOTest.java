package com.epam.catalog.dao;

import org.testng.annotations.Test;

import com.epam.catalog.bean.Book;
import com.epam.catalog.bean.genre.BookGenre;
import com.epam.catalog.dao.impl.BookDAO;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import org.testng.Assert;


public class BookDAOTest {
	
	private Statement statement;
	private Connection connect;
	private ResultSet resultSet;
	private BookDAO bookdao;
	private ArrayList<Book> books = new ArrayList<Book>();
	private int id;
	
	private final static String selectQuery = "SELECT * FROM `book`";
	private final static String updateQuery = "INSERT INTO `book` (`title`,`author`,`year`,`text`,`genre`,`numberOfPages`) VALUES (\"The Adventures of Tom Sawyer\",\"Mark Twain\",1876,\"test\",\"HISTORY\",335)";
	private final static String deleteQuery = "DELETE FROM `book` WHERE `id`=";
	
	
	@Test
	public void findByTitleTest() throws DAOException  {
		books = bookdao.findBy("title", "The Adventures of Tom Sawyer");
		for (Book book : books) {
			Assert.assertEquals(book.getTitle(), "The Adventures of Tom Sawyer");
		}
	}
	
	@Test
	public void findByTitleNegativeTest() throws DAOException  {
		books = bookdao.findBy("title", "zdrh7894%^&%");
		Assert.assertTrue(books.isEmpty());
	}
	
	@Test
	public void findByAuthorTest() throws DAOException {
		books = bookdao.findBy("author", "Mark Twain");
		for (Book book : books) {
			Assert.assertEquals(book.getAuthor(), "Mark Twain");
		}
	}
	
	@Test
	public void findByAuthorNegativeTest() throws DAOException  {
		books = bookdao.findBy("author", "zdrh7894%^&%");
		Assert.assertTrue(books.isEmpty());
	}
	
	@Test
	public void findByYearTest() throws DAOException {
		books = bookdao.findBy("year", "1876");
		for (Book book : books) {
			Assert.assertEquals(book.getYear(), 1876);
		}
	}
	
	@Test
	public void findByYearNegativeTest() throws DAOException  {
		books = bookdao.findBy("year", "0");
		Assert.assertTrue(books.isEmpty());
	}
	
	@Test
	public void findByTextTest() throws DAOException {
		books = bookdao.findBy("text", "test");
		for (Book book : books) {
			Assert.assertEquals(book.getText(), "test");
		}
	}
	
	@Test
	public void findByTextNegativeTest() throws DAOException  {
		books = bookdao.findBy("text", "zdrh7894%^&%");
		Assert.assertTrue(books.isEmpty());
	}
	
	@Test
	public void findByGenreTest() throws DAOException {
		books = bookdao.findBy("genre", "HISTORY");
		for (Book book : books) {
			Assert.assertEquals(book.getGenre().toString(), "HISTORY");
		}
	}
	
	@Test
	public void findByGenreNegativeTest() throws DAOException  {
		books = bookdao.findBy("genre", "zdrh7894%^&%");
		Assert.assertTrue(books.isEmpty());
	}
	
	@Test
	public void findByPagesTest() throws DAOException {
		books = bookdao.findBy("numberOfPages", "335");
		for (Book book : books) {
			Assert.assertEquals(book.getNumberOfPages(), 335);
		}
	}
	
	@Test
	public void findByPagesNegativeTest() throws DAOException  {
		books = bookdao.findBy("numberOfPages", "-10");
		Assert.assertTrue(books.isEmpty());
	}
	
	@Test
	public void findAllTest() throws DAOException, SQLException  {
		books = bookdao.findAll();
		int i = 0;
		while (resultSet.next()) {
			i++;
		}
		Assert.assertEquals(books.size(), i);
	}
	
	@Test
	public void addNewsTest() throws DAOException, SQLException {
		Book book = new Book("War and Peace", "Tolstoy", 1869, "test", BookGenre.valueOf("HISTORY"), 5202);
		bookdao.addNews(book);
		Statement newStatement = connect.createStatement();
		ResultSet resultForInsert = newStatement.executeQuery("SELECT * FROM `book` WHERE `title` = \"War and Peace\"");
		int bookid = 0;
		while(resultForInsert.next()) {
			bookid = resultForInsert.getInt(1);
			Assert.assertEquals(resultForInsert.getString("author"), "Tolstoy");
			Assert.assertEquals(resultForInsert.getInt("year"), 1869);
			Assert.assertEquals(resultForInsert.getString("text"), "test");
			Assert.assertEquals(resultForInsert.getString("genre"), "HISTORY");
			Assert.assertEquals(resultForInsert.getInt("numberOfPages"), 5202);
		}
		newStatement.executeUpdate("DELETE FROM `book` WHERE `id`=" + bookid);
		resultForInsert.close();
		newStatement.close();
	}
	
	@BeforeMethod
	public void beforeMethod() {
		books.clear();
	}

	@AfterMethod
	public void afterMethod() {
	}

	@BeforeTest
	public void beforeClass() throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		
		bookdao = new BookDAO();
		Class.forName("com.mysql.jdbc.Driver").newInstance();
		connect = DriverManager.getConnection("jdbc:mysql://localhost/catalog?user=root&password=admin&useUnicode=true&characterEncoding=UTF-8&characterSetResults=utf8&connectionCollation=utf8_general_ci");
		statement = connect.createStatement();
		statement.executeUpdate(updateQuery, Statement.RETURN_GENERATED_KEYS);
		resultSet = statement.getGeneratedKeys();
		if (resultSet.next()) {
			id = resultSet.getInt(1);
		}
		resultSet = statement.executeQuery(selectQuery);
	  
	}

	@AfterTest
	public void afterClass() throws SQLException {
		statement.executeUpdate(deleteQuery + id);
		resultSet.close();
		statement.close();
		connect.close();
	}

}
