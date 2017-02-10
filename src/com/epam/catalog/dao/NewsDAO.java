package com.epam.catalog.dao;

import java.util.ArrayList;

import com.epam.catalog.bean.News;

public interface NewsDAO {
	
	ArrayList<? extends News> findAll() throws DAOException;
	ArrayList<? extends News> findBy(String type, String value) throws DAOException;
	void addNews(News news) throws DAOException;
	void closeConnection();
	
}
