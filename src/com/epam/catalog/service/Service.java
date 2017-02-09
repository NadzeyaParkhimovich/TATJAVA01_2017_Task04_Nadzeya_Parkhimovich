package com.epam.catalog.service;

import java.util.ArrayList;

import com.epam.catalog.bean.News;
import com.epam.catalog.dao.DAOException;

public interface Service {
	
	ArrayList<? extends News> findAll() throws ServiceExeption;
	ArrayList<? extends News> findByTitle(String title) throws ServiceExeption;
	ArrayList<? extends News> findByAuthor(String author) throws ServiceExeption;
	ArrayList<? extends News> findByYear(String year) throws ServiceExeption;
	ArrayList<? extends News> findByText(String text) throws ServiceExeption;
	ArrayList<? extends News> findByGenre(String genre) throws ServiceExeption;
	void addNews(String news) throws ServiceExeption;
	void close() throws ServiceExeption;
}
