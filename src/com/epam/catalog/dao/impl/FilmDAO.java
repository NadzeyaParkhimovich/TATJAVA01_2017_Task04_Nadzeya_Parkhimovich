package com.epam.catalog.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.epam.catalog.bean.Film;
import com.epam.catalog.bean.News;
import com.epam.catalog.bean.genre.FilmGenre;
import com.epam.catalog.dao.DAOException;
import com.epam.catalog.dao.NewsDAO;

public class FilmDAO implements NewsDAO {
	
	private final static Logger LOG = LogManager.getRootLogger();
	
	private ConnectionPool cp;
	private Connection con;
	private PreparedStatement ps;
	private Statement st;
	
	private final static String select_all = "SELECT * FROM `film`";
	private final static String select_by = "SELECT * FROM `film` WHERE `";
	private final static String insert = "INSERT INTO `film` (`title`,`author`,`year`,`text`,`genre`) VALUES (?,?,?,?,?)";
	
	public ArrayList<Film> findAll() throws DAOException {
		try {
			cp = ConnectionPool.getInstance();
			con = cp.takeConnection();
			st = con.createStatement();
			return filmsCreator(st.executeQuery(select_all));
			
		} catch (ConnectionPoolException | SQLException e) {
			LOG.error(e);
			throw new DAOException(e);
			
		} finally {
			try {
				cp.returnConnection(con);
			} catch (ConnectionPoolException e) {
				LOG.error(e);
			}
		}
	}
	
	public ArrayList<Film> findBy(String type, String value) throws DAOException {
		try {
			cp = ConnectionPool.getInstance();
			con = cp.takeConnection();
			//Поиск по части слова, предложения
			ps = con.prepareStatement(select_by + type + "` LIKE ?");
			ps.setString(1, "%" + value + "%");
			return filmsCreator(ps.executeQuery());
			
		}catch (ConnectionPoolException | SQLException e) {
			LOG.error(e);
			throw new DAOException(e);
		}finally {
			try {
				cp.returnConnection(con);
			} catch (ConnectionPoolException e) {
				LOG.error(e);
			}
		}
	}
	
	public void addNews(News news) throws DAOException {
		if (news instanceof Film) {
			Film film = (Film)news;
			try {
				cp = ConnectionPool.getInstance();
				con = cp.takeConnection();
				ps = con.prepareStatement(insert);
				ps.setString(1, film.getTitle());
				ps.setString(2, film.getAuthor());
				ps.setInt(3, film.getYear());
				ps.setString(4, film.getText());
				ps.setString(5, film.getGenre().toString());
				ps.executeUpdate();
			}catch (ConnectionPoolException | SQLException e) {
				LOG.error(e);
				throw new DAOException(e);
			}finally {
				try {
					cp.returnConnection(con);
				} catch (ConnectionPoolException e) {
					LOG.error(e);
				}
			}
		} else {
			LOG.error("Incorrect type of news");
			throw new DAOException("Incorrect type of news");
		}
	}
	
	public void closeConnection() {	
		try {
			if (st != null) {
				st.close();
			}
			if (ps != null) {
				ps.close();
			}
			if (cp != null) {
				cp.dispose();
			}
		} catch (SQLException e) {
			LOG.error(e);
		}
	}
	
	private ArrayList<Film> filmsCreator(ResultSet result) throws SQLException {
		ArrayList<Film> films = new ArrayList<Film>();
		while (result.next()) {
			Film film = new Film(result.getString("title"),result.getString("author"),result.getInt("year"),
					result.getString("text"),FilmGenre.valueOf(result.getString("genre")));
			films.add(film);
		}
		return films;
	}
	
		
}
