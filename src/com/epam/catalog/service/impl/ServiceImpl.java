package com.epam.catalog.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.epam.catalog.beans.Book;
import com.epam.catalog.beans.BookGenre;
import com.epam.catalog.beans.Disk;
import com.epam.catalog.beans.Film;
import com.epam.catalog.beans.FilmGenre;
import com.epam.catalog.beans.MusicGenre;
import com.epam.catalog.beans.News;
import com.epam.catalog.dao.DAOException;
import com.epam.catalog.dao.NewsDAO;
import com.epam.catalog.service.Service;
import com.epam.catalog.service.ServiceExeption;

public class ServiceImpl implements Service{

	private NewsDAO dao;
	String type;
	
	public ServiceImpl(NewsDAO dao, String type) {
		this.dao = dao;
		this.type = type;
	}
	
	@Override
	public ArrayList<? extends News> findAll() throws ServiceExeption {
		try {
			return dao.findAll();
		} catch (DAOException e) {
			throw new ServiceExeption(e);
		}
	}

	@Override
	public ArrayList<? extends News> findByTitle(String title) throws ServiceExeption {
		if(stringChecker(title)) {
			try {
				return dao.findByTitle(title);
			} catch (DAOException e) {
				throw new ServiceExeption(e);
			}
		} else {
			throw new ServiceExeption("Incorrect title");
		}
		
	}

	@Override
	public ArrayList<? extends News> findByAuthor(String author) throws ServiceExeption {
			if(stringChecker(author)) {
			try {
				return dao.findByAuthor(author);
			} catch (DAOException e) {
				throw new ServiceExeption(e);
			}
		} else {
			throw new ServiceExeption("Incorrect author");
		}
		
	}

	@Override
	public ArrayList<? extends News> findByYear(String year) throws ServiceExeption {
		try {
			int intYear = Integer.parseInt(year);
			if (yearChecker(intYear)) {
				return dao.findByYear(intYear);
			} else {
				throw new ServiceExeption("Incorrect year!");
			}
		} catch (DAOException | NumberFormatException e) {
			throw new ServiceExeption(e);
		}
	}

	@Override
	public ArrayList<? extends News> findByText(String text) throws ServiceExeption {
		try {
			return dao.findByText(text);
		} catch (DAOException e) {
			throw new ServiceExeption(e);
		}
	}

	@Override
	public ArrayList<? extends News> findByGenre(String genre) throws ServiceExeption {
		if (type.equals("film")) {
			try {
				FilmGenre.valueOf(genre);
				return dao.findByGenre(genre);
			} catch (IllegalArgumentException | DAOException e) {
				throw new ServiceExeption(e);
			}
		} else {
			if (type.equals("book")) {
				try {
					BookGenre.valueOf(genre);
					return dao.findByGenre(genre);
				} catch (IllegalArgumentException | DAOException e) {
					throw new ServiceExeption(e);
				}
			} else {
				if (type.equals("disk")) {
					try {
						MusicGenre.valueOf(genre);
						return dao.findByGenre(genre);
					} catch (IllegalArgumentException | DAOException e) {
						throw new ServiceExeption(e);
					}
				} else {
					throw new ServiceExeption("Illegal type of product");
				}
			}
		}
	}

	public void addNews(String news) throws ServiceExeption {
		
		if (type.equals("film")) {
			String[] parts = news.split("@");
			if (parts.length != 5) {
				throw new ServiceExeption("Illegel string command");
			}
			try {
				int year = Integer.parseInt(parts[2]);
				if (yearChecker(year)) {
					if(stringChecker(parts[0])) {
						if (stringChecker(parts[1])) {
							Film film = new Film(parts[0], parts[1], year, parts[3], FilmGenre.valueOf(parts[4]));
							dao.addNews(film);
						} else {
							throw new ServiceExeption("Incorrect author!");
						}
						
					} else {
						throw new ServiceExeption("Incorrect title!");
					}
					
				} else {
					throw new ServiceExeption("Incorrect year!");
				}
				
			} catch (IllegalArgumentException | DAOException e) {
				throw new ServiceExeption(e);
			}
			
		}
		
		if (type.equals("book")) {
			String[] parts = news.split("@");
			if (parts.length != 6) {
				throw new ServiceExeption("Illegel string command");
			}
			try {
				int year = Integer.parseInt(parts[2]);
				if (yearChecker(year)) {
					int numberOfPages = Integer.parseInt(parts[5]);
					if (numberOfPages > 0) {
						if (stringChecker(parts[0])) {
							if (stringChecker(parts[1])) {
								Book book = new Book(parts[0], parts[1], year, parts[3], BookGenre.valueOf(parts[4]), numberOfPages);
								dao.addNews(book);
							} else {
								throw new ServiceExeption("Incorrect author!");
							}
						} else {
							throw new ServiceExeption("Incorrect title!");
						}
					} else {
						throw new ServiceExeption("Incorrect numberOfPages!");
					}
				} else {
					throw new ServiceExeption("Incorrect year!");
				}
				
			} catch (IllegalArgumentException | DAOException e) {
				throw new ServiceExeption(e);
			}
			
		}
		
		if (type.equals("disk")) {
			String[] parts = news.split("@");
			if (parts.length != 5) {
				throw new ServiceExeption("Illegel string command");
			}
			try {
				int year = Integer.parseInt(parts[2]);
				if (yearChecker(year)) {
					if (stringChecker(parts[0])) {
						if (stringChecker(parts[1])) {
							Disk disk = new Disk(parts[0], parts[1], year, parts[3], MusicGenre.valueOf(parts[4]));
							dao.addNews(disk);
						} else {
							throw new ServiceExeption("Incorrect author!");
						}
					} else {
						throw new ServiceExeption("Incorrect title!");
					}
				}else {
					throw new ServiceExeption("Incorrect year!");
				}
			} catch (IllegalArgumentException | DAOException e) {
				throw new ServiceExeption(e);
			}
			
		}
		
	}
	
	public void close() throws ServiceExeption {
		try {
			dao.close();
		} catch (DAOException e) {
			throw new ServiceExeption(e);
		}
	}
	
	private boolean stringChecker(String str) {
		Matcher matcher = Pattern.compile("[\\wà-ÿ¸À-ß¨-]{0,45}").matcher(str);
		return matcher.matches();
	}
	
	private boolean yearChecker(int year) {
		Calendar c = new GregorianCalendar();
		if (year > 0 && year <= c.get(Calendar.YEAR)) {
			return true;
		} else {
			return false;
		}
	}
}
