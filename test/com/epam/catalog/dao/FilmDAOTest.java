package com.epam.catalog.dao;

import org.testng.annotations.Test;

import com.epam.catalog.bean.Film;
import com.epam.catalog.bean.genre.FilmGenre;
import com.epam.catalog.dao.impl.FilmDAO;
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


public class FilmDAOTest {
	
	private Statement statement;
	private Connection connect;
	private ResultSet resultSet;
	private FilmDAO filmdao;
	private ArrayList<Film> films = new ArrayList<Film>();
	private int id;
	
	private final static  String selectQuery = "SELECT * FROM `film`";
	private final static  String updateQuery = "INSERT INTO `film` (`title`,`author`,`year`,`text`,`genre`) VALUES (\"Intouchables\",\"Nakache\",2011,\"test\",\"DRAMA\")";
	private final static  String deleteQuery = "DELETE FROM `film` WHERE `id`=";
	
	
	@Test
	public void findByTitleTest() throws DAOException  {
		films = filmdao.findBy("title", "Intouchables");
		for (Film film : films) {
			Assert.assertEquals(film.getTitle(), "Intouchables");
		}
	}
	
	@Test
	public void findByTitleNegativeTest() throws DAOException  {
		films = filmdao.findBy("title", "zdrh7894%^&%");
		Assert.assertTrue(films.isEmpty());
	}
	
	@Test
	public void findByAuthorTest() throws DAOException {
		films = filmdao.findBy("author", "Nakache");
		for (Film film : films) {
			Assert.assertEquals(film.getAuthor(), "Nakache");
		}
	}
	
	@Test
	public void findByAuthorNegativeTest() throws DAOException  {
		films = filmdao.findBy("author", "zdrh7894%^&%");
		Assert.assertTrue(films.isEmpty());
	}
	
	@Test
	public void findByYearTest() throws DAOException {
		films = filmdao.findBy("year", "2011");
		for (Film film : films) {
			Assert.assertEquals(film.getYear(), 2011);
		}
	}
	
	@Test
	public void findByYearNegativeTest() throws DAOException  {
		films = filmdao.findBy("year", "0");
		Assert.assertTrue(films.isEmpty());
	}
	
	@Test
	public void findByTextTest() throws DAOException {
		films = filmdao.findBy("text", "test");
		for (Film film : films) {
			Assert.assertEquals(film.getText(), "test");
		}
	}
	
	@Test
	public void findByTextNegativeTest() throws DAOException  {
		films = filmdao.findBy("text", "zdrh7894%^&%");
		Assert.assertTrue(films.isEmpty());
	}
	
	@Test
	public void findByGenreTest() throws DAOException {
		films = filmdao.findBy("genre", "DRAMA");
		for (Film film : films) {
			Assert.assertEquals(film.getGenre().toString(), "DRAMA");
		}
	}
	
	@Test
	public void findByGenreNegativeTest() throws DAOException  {
		films = filmdao.findBy("genre", "zdrh7894%^&%");
		Assert.assertTrue(films.isEmpty());
	}
	
	@Test
	public void findAllTest() throws DAOException, SQLException  {
		films = filmdao.findAll();
		int i = 0;
		while (resultSet.next()) {
			i++;
		}
		Assert.assertEquals(films.size(), i);
	}
	
	@Test
	public void addNewsTest() throws DAOException, SQLException {
		Film film = new Film("The Green Mile", "Darabont", 1999, "test", FilmGenre.valueOf("DRAMA"));
		filmdao.addNews(film);
		Statement newStatement = connect.createStatement();
		ResultSet resultForInsert = newStatement.executeQuery("SELECT * FROM `film` WHERE `title` = \"The Green Mile\"");
		int filmId = 0;
		while(resultForInsert.next()) {
			filmId = resultForInsert.getInt(1);
			Assert.assertEquals(resultForInsert.getString("author"), "Darabont");
			Assert.assertEquals(resultForInsert.getInt("year"), 1999);
			Assert.assertEquals(resultForInsert.getString("text"), "test");
			Assert.assertEquals(resultForInsert.getString("genre"), "DRAMA");
		}
		newStatement.executeUpdate("DELETE FROM `film` WHERE `id`=" + filmId);
		resultForInsert.close();
		newStatement.close();
	}
	
		
	@BeforeMethod
	public void beforeMethod() {
		films.clear();
	}

	@AfterMethod
	public void afterMethod() {
	}

	@BeforeTest
	public void beforeClass() throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		
		filmdao = new FilmDAO();
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
		statement.close();
		connect.close();
	}

}
