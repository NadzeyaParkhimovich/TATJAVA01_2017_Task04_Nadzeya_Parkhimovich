package com.epam.catalog.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.epam.catalog.bean.Film;
import com.epam.catalog.bean.FilmGenre;
import com.epam.catalog.bean.News;
import com.epam.catalog.dao.DAOException;
import com.epam.catalog.dao.NewsDAO;

public class FilmDAO implements NewsDAO {
		
	DBWorker db = DBWorker.getInstance();
	ArrayList<Film> films = new ArrayList<Film>();
	ResultSet result;
	
	public ArrayList<Film> findAll() throws DAOException {
		films.clear();
		try {
			result = db.getDBData("SELECT * FROM `film`");
			while (result.next()) {
				Film film = new Film(result.getString("title"),result.getString("author"),result.getInt("year"),
						result.getString("text"),FilmGenre.valueOf(result.getString("genre")));
				films.add(film);
			}
		} catch (SQLException | InstantiationException | IllegalAccessException | ClassNotFoundException | ConnectionPoolException e) {
			throw new DAOException(e);
		}
		
		return films;
	}
	
	public ArrayList<Film> findByTitle(String title) throws DAOException {
		films.clear();
		try {
			result = db.getDBData("SELECT * FROM `film` WHERE `title`=\"" + title +"\"");
			while (result.next()) {
				Film film = new Film(result.getString("title"),result.getString("author"),result.getInt("year"),
						result.getString("text"),FilmGenre.valueOf(result.getString("genre")));
				films.add(film);
			}
		} catch (SQLException | InstantiationException | IllegalAccessException | ClassNotFoundException | ConnectionPoolException e) {
			throw new DAOException(e);
		}
		
		return films;
	}

	public ArrayList<Film> findByAuthor(String author) throws DAOException {
		films.clear();
		try {
			result = db.getDBData("SELECT * FROM `film` WHERE `author`=\"" + author + "\"");
			while (result.next()) {
				Film film = new Film(result.getString("title"),result.getString("author"),result.getInt("year"),
						result.getString("text"),FilmGenre.valueOf(result.getString("genre")));
				films.add(film);
			}
		} catch (SQLException | InstantiationException | IllegalAccessException | ClassNotFoundException | ConnectionPoolException e) {
			throw new DAOException(e);
		}
		
		return films;
	}
	
	public ArrayList<Film> findByYear(int year) throws DAOException {
		films.clear();
		try {
			result = db.getDBData("SELECT * FROM `film` WHERE `year`=" + year);
			while (result.next()) {
				Film film = new Film(result.getString("title"),result.getString("author"),result.getInt("year"),
						result.getString("text"),FilmGenre.valueOf(result.getString("genre")));
				films.add(film);
			}
		} catch (SQLException | InstantiationException | IllegalAccessException | ClassNotFoundException | ConnectionPoolException e) {
			throw new DAOException(e);
		}
		
		return films;
	}
	
	public ArrayList<Film> findByText(String text) throws DAOException {
		films.clear();
		try {
			result = db.getDBData("SELECT * FROM `film` WHERE `text`=\"" + text + "\"");
			while (result.next()) {
				Film film = new Film(result.getString("title"),result.getString("author"),result.getInt("year"),
						result.getString("text"),FilmGenre.valueOf(result.getString("genre")));
				films.add(film);
			}
		} catch (SQLException | InstantiationException | IllegalAccessException | ClassNotFoundException | ConnectionPoolException e) {
			throw new DAOException(e);
		}
		
		return films;
	}
	
	public ArrayList<Film> findByGenre(String genre) throws DAOException {
		films.clear();
		try {
			result = db.getDBData("SELECT * FROM `film` WHERE `genre`=\"" + genre + "\"");
			while (result.next()) {
				Film film = new Film(result.getString("title"),result.getString("author"),result.getInt("year"),
						result.getString("text"),FilmGenre.valueOf(result.getString("genre")));
				films.add(film);
			}
		} catch (SQLException | InstantiationException | IllegalAccessException | ClassNotFoundException | ConnectionPoolException e) {
			throw new DAOException(e);
		}
		
		return films;
	}
	
	public void addNews(News news) throws DAOException {
		if (news instanceof Film) {
			Film film = (Film)news;
			String query = "INSERT INTO `film` (`title`,`author`,`year`,`text`,`genre`) "
					+ "VALUES (\"" + film.getTitle() + "\",\"" + film.getAuthor() + "\","
					+ film.getYear() + ",\"" + film.getText() + "\",\"" + film.getGenre() + "\")";
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
