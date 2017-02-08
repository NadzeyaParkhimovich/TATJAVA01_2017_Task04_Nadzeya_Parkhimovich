package com.epam.catalog.dao;

import java.util.ArrayList;

import com.epam.catalog.beans.News;

public interface NewsDAO {
	
	ArrayList<? extends News> findAll() throws DAOException;
	ArrayList<? extends News> findByTitle(String title) throws DAOException;
	ArrayList<? extends News> findByAuthor(String author) throws DAOException;
	ArrayList<? extends News> findByYear(int year) throws DAOException;
	ArrayList<? extends News> findByText(String text) throws DAOException;
	ArrayList<? extends News> findByGenre(String genre) throws DAOException;
	void addNews(News news) throws DAOException;
	void close() throws DAOException;
	
}
