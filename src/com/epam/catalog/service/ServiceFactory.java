package com.epam.catalog.service;

import com.epam.catalog.dao.DAOFactory;
import com.epam.catalog.service.impl.ServiceImpl;

public final class ServiceFactory {
	
	private static final ServiceFactory instance = new ServiceFactory();
	
	private DAOFactory daof = DAOFactory.getInstance();
	
	private final Service bookService = new ServiceImpl(daof.getBookDAO(), "book");
	private final Service filmService = new ServiceImpl(daof.getFilmDAO(), "film");
	private final Service diskService = new ServiceImpl(daof.getDiskDAO(), "disk");
	
	private ServiceFactory(){}
	
	public static ServiceFactory getInstance(){
		return instance;
	}
	
	public Service getBookSerice() {
		return bookService;
	}
	
	public Service getFilmService() {
		return filmService;
	}
	
	public Service getDiskService() {
		return diskService;
	}
	

}
