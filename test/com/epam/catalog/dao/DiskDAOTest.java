package com.epam.catalog.dao;

import org.testng.annotations.Test;
import com.epam.catalog.beans.Disk;
import com.epam.catalog.beans.MusicGenre;
import com.epam.catalog.dao.impl.DiskDAO;
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


public class DiskDAOTest {
	
	Statement statement;
	Connection connect;
	ResultSet resultSet;
	DiskDAO diskdao;
	ArrayList<Disk> disks = new ArrayList<Disk>();
	int id;
	
	String selectQuery = "SELECT * FROM `disk`";
	String updateQuery = "INSERT INTO `disk` (`title`,`author`,`year`,`text`,`genre`) VALUES (\"Orchid\",\"Opeth\",1995,\"test\",\"METAL\")";
	String deleteQuery = "DELETE FROM `disk` WHERE `id`=";
	
	
	@Test
	public void findByTitleTest() throws DAOException  {
		disks = diskdao.findByTitle("Orchid");
		for (Disk disk : disks) {
			Assert.assertEquals(disk.getTitle(), "Orchid");
		}
	}
	
	@Test
	public void findByTitleNegativeTest() throws DAOException  {
		disks = diskdao.findByTitle("zdrh7894%^&%");
		Assert.assertTrue(disks.isEmpty());
	}
	
	@Test
	public void findByAuthorTest() throws DAOException {
		disks = diskdao.findByAuthor("Opeth");
		for (Disk disk : disks) {
			Assert.assertEquals(disk.getAuthor(), "Opeth");
		}
	}
	
	@Test
	public void findByAuthorNegativeTest() throws DAOException  {
		disks = diskdao.findByAuthor("zdrh7894%^&%");
		Assert.assertTrue(disks.isEmpty());
	}
	
	@Test
	public void findByYearTest() throws DAOException {
		disks = diskdao.findByYear(1995);
		for (Disk disk : disks) {
			Assert.assertEquals(disk.getYear(), 1995);
		}
	}
	
	@Test
	public void findByYearNegativeTest() throws DAOException  {
		disks = diskdao.findByYear(0);
		Assert.assertTrue(disks.isEmpty());
	}
	
	@Test
	public void findByTextTest() throws DAOException {
		disks = diskdao.findByText("test");
		for (Disk disk : disks) {
			Assert.assertEquals(disk.getText(), "test");
		}
	}
	
	@Test
	public void findByTextNegativeTest() throws DAOException  {
		disks = diskdao.findByText("zdrh7894%^&%");
		Assert.assertTrue(disks.isEmpty());
	}
	
	@Test
	public void findByGenreTest() throws DAOException {
		disks = diskdao.findByGenre("METAL");
		for (Disk disk : disks) {
			Assert.assertEquals(disk.getGenre().toString(), "METAL");
		}
	}
	
	@Test
	public void findByGenreNegativeTest() throws DAOException  {
		disks = diskdao.findByGenre("zdrh7894%^&%");
		Assert.assertTrue(disks.isEmpty());
	}
	
	@Test
	public void findAllTest() throws DAOException, SQLException  {
		disks = diskdao.findAll();
		int i = 0;
		while (resultSet.next()) {
			i++;
		}
		Assert.assertEquals(disks.size(), i);
	}
	
	@Test
	public void addNewsTest() throws DAOException, SQLException {
		Disk disk = new Disk("Play with Fire", "Aria", 1989, "test", MusicGenre.valueOf("METAL"));
		diskdao.addNews(disk);
		Statement newStatement = connect.createStatement();
		ResultSet resultForInsert = newStatement.executeQuery("SELECT * FROM `disk` WHERE `title` = \"Play with Fire\"");
		int diskId = 0;
		while(resultForInsert.next()) {
			diskId = resultForInsert.getInt(1);
			Assert.assertEquals(resultForInsert.getString("author"), "Aria");
			Assert.assertEquals(resultForInsert.getInt("year"), 1989);
			Assert.assertEquals(resultForInsert.getString("text"), "test");
			Assert.assertEquals(resultForInsert.getString("genre"), "METAL");
		}
		newStatement.executeUpdate("DELETE FROM `disk` WHERE `id`=" + diskId);
		resultForInsert.close();
		newStatement.close();
	}
	
		
	@BeforeMethod
	public void beforeMethod() {
		disks.clear();
	}

	@AfterMethod
	public void afterMethod() {
	}

	@BeforeTest
	public void beforeClass() throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		
		diskdao = new DiskDAO();
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
