package com.epam.catalog.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.epam.catalog.bean.Disk;
import com.epam.catalog.bean.MusicGenre;
import com.epam.catalog.bean.News;
import com.epam.catalog.dao.DAOException;
import com.epam.catalog.dao.NewsDAO;

public class DiskDAO implements NewsDAO{
		
	DBWorker db = DBWorker.getInstance();
	ArrayList<Disk> disks = new ArrayList<Disk>();
	ResultSet result;
	
	public ArrayList<Disk> findAll() throws DAOException {
		disks.clear();
		try {
			result = db.getDBData("SELECT * FROM `disk`");
			while (result.next()) {
				Disk disk = new Disk(result.getString("title"),result.getString("author"),result.getInt("year"),
						result.getString("text"),MusicGenre.valueOf(result.getString("genre")));
				disks.add(disk);
			}
		} catch (SQLException | InstantiationException | IllegalAccessException | ClassNotFoundException | ConnectionPoolException  e) {
			throw new DAOException(e);
		}
		
		return disks;
	}
	
	public ArrayList<Disk> findByTitle(String title) throws DAOException {
		disks.clear();
		try {
			result = db.getDBData("SELECT * FROM `disk` WHERE `title`=\"" + title +"\"");
			while (result.next()) {
				Disk disk = new Disk(result.getString("title"),result.getString("author"),result.getInt("year"),
						result.getString("text"),MusicGenre.valueOf(result.getString("genre")));
				disks.add(disk);
			}
		} catch (SQLException | InstantiationException | IllegalAccessException | ClassNotFoundException | ConnectionPoolException e) {
			throw new DAOException(e);
		}
		
		return disks;
	}

	public ArrayList<Disk> findByAuthor(String author) throws DAOException {
		disks.clear();
		try {
			result = db.getDBData("SELECT * FROM `disk` WHERE `author`=\"" + author + "\"");
			while (result.next()) {
				Disk disk = new Disk(result.getString("title"),result.getString("author"),result.getInt("year"),
						result.getString("text"),MusicGenre.valueOf(result.getString("genre")));
				disks.add(disk);
			}
		} catch (SQLException | InstantiationException | IllegalAccessException | ClassNotFoundException | ConnectionPoolException e) {
			throw new DAOException(e);
		}
		
		return disks;
	}
	
	public ArrayList<Disk> findByYear(int year) throws DAOException {
		disks.clear();
		try {
			result = db.getDBData("SELECT * FROM `disk` WHERE `year`=" + year);
			while (result.next()) {
				Disk disk = new Disk(result.getString("title"),result.getString("author"),result.getInt("year"),
						result.getString("text"),MusicGenre.valueOf(result.getString("genre")));
				disks.add(disk);
			}
		} catch (SQLException | InstantiationException | IllegalAccessException | ClassNotFoundException | ConnectionPoolException e) {
			throw new DAOException(e);
		}
		
		return disks;
	}
	
	public ArrayList<Disk> findByText(String text) throws DAOException {
		disks.clear();
		try {
			result = db.getDBData("SELECT * FROM `disk` WHERE `text`=\"" + text + "\"");
			while (result.next()) {
				Disk disk = new Disk(result.getString("title"),result.getString("author"),result.getInt("year"),
						result.getString("text"),MusicGenre.valueOf(result.getString("genre")));
				disks.add(disk);
			}
		} catch (SQLException | InstantiationException | IllegalAccessException | ClassNotFoundException | ConnectionPoolException e) {
			throw new DAOException(e);
		}
		
		return disks;
	}
	
	public ArrayList<Disk> findByGenre(String genre) throws DAOException {
		disks.clear();
		try {
			result = db.getDBData("SELECT * FROM `disk` WHERE `genre`=\"" + genre + "\"");
			while (result.next()) {
				Disk disk = new Disk(result.getString("title"),result.getString("author"),result.getInt("year"),
						result.getString("text"),MusicGenre.valueOf(result.getString("genre")));
				disks.add(disk);
			}
		} catch (SQLException | InstantiationException | IllegalAccessException | ClassNotFoundException | ConnectionPoolException e) {
			throw new DAOException(e);
		}
		
		return disks;
	}
	
	public void addNews(News news) throws DAOException {
		if (news instanceof Disk) {
			Disk disk = (Disk)news;
			String query = "INSERT INTO `disk` (`title`,`author`,`year`,`text`,`genre`) "
					+ "VALUES (\"" + disk.getTitle() + "\",\"" + disk.getAuthor() + "\","
					+ disk.getYear() + ",\"" + disk.getText() + "\",\"" + disk.getGenre() + "\")";
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
