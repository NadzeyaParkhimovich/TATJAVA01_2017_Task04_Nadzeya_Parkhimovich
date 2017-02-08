package com.epam.catalog.dao;

import com.epam.catalog.dao.impl.BookDAO;
import com.epam.catalog.dao.impl.DiskDAO;
import com.epam.catalog.dao.impl.FilmDAO;

public final class DAOFactory {
	
	private static final DAOFactory instance = new DAOFactory();
	private final NewsDAO bookdao = new BookDAO();
	private final NewsDAO filmdao = new FilmDAO();
	private final NewsDAO diskdao = new DiskDAO();
	
	private DAOFactory(){}
	
	public static DAOFactory getInstance(){
		return instance;
	}
	
	public NewsDAO getBookDAO() {
		return bookdao;
	}
	
	public NewsDAO getFilmDAO() {
		return filmdao;
	}
	
	public NewsDAO getDiskDAO() {
		return diskdao;
	}
	

}