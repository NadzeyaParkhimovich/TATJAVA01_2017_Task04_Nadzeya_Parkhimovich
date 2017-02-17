package com.epam.catalog.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.epam.catalog.bean.Disk;
import com.epam.catalog.bean.News;
import com.epam.catalog.bean.genre.MusicGenre;
import com.epam.catalog.dao.DAOException;
import com.epam.catalog.dao.NewsDAO;

public class DiskDAO implements NewsDAO{
	
	private final static Logger LOG = LogManager.getRootLogger();
	
	private ConnectionPool cp;
		
	private final static String select_all = "SELECT * FROM `disk`";
	private final static String select_by = "SELECT * FROM `disk` WHERE `";
	private final static String insert = "INSERT INTO `disk` (`title`,`author`,`year`,`text`,`genre`) VALUES (?,?,?,?,?)";
	
	public ArrayList<Disk> findAll() throws DAOException {
		Connection con = null;
		Statement st = null;
		try {
			cp = ConnectionPool.getInstance();
			con = cp.takeConnection();
			st = con.createStatement();
			return disksCreator(st.executeQuery(select_all));
			
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
	
	public ArrayList<Disk> findBy(String type, String value) throws DAOException {
		Connection con = null;
		PreparedStatement ps = null;
		try {
			cp = ConnectionPool.getInstance();
			con = cp.takeConnection();
			//Поиск по части слова, предложения
			ps = con.prepareStatement(select_by + type + "` LIKE ?");
			ps.setString(1, "%" + value + "%");
			return disksCreator(ps.executeQuery());
			
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
		if (news instanceof Disk) {
			Disk disk = (Disk)news;
			try {
				cp = ConnectionPool.getInstance();
				con = cp.takeConnection();
				ps = con.prepareStatement(insert);
				ps.setString(1, disk.getTitle());
				ps.setString(2, disk.getAuthor());
				ps.setInt(3, disk.getYear());
				ps.setString(4, disk.getText());
				ps.setString(5, disk.getGenre().toString());
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
	
	private ArrayList<Disk> disksCreator(ResultSet result) throws SQLException {
		ArrayList<Disk> disks = new ArrayList<Disk>();
		while (result.next()) {
			Disk disk = new Disk(result.getString("title"),result.getString("author"),result.getInt("year"),
					result.getString("text"),MusicGenre.valueOf(result.getString("genre")));
			disks.add(disk);
		}
		return disks;
	}

}
