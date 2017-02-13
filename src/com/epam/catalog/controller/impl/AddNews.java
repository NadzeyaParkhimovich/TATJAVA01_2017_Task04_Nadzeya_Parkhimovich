package com.epam.catalog.controller.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.epam.catalog.controller.Command;
import com.epam.catalog.service.Service;
import com.epam.catalog.service.ServiceExeption;
import com.epam.catalog.service.ServiceFactory;

public class AddNews implements Command{

	private final static Logger LOG = LogManager.getRootLogger();
	
	@Override
	public String execute(String request) {
		ServiceFactory sfactory = ServiceFactory.getInstance();
		Service service;
		String parts[] = request.split("@");
		String news = request.substring(request.indexOf('@', 9)+1);
		String response = "Adding is OK";
		if (parts.length >= 3) {
			if(parts[1].equalsIgnoreCase("film")) {
				
				service = sfactory.getFilmService();
				try {
					service.addNews(news);
				} catch (ServiceExeption e) {
					LOG.error(e);
					response = "Sorry, we have problems in adding news";
				}
			} else {
				if(parts[1].equalsIgnoreCase("book")) {
					
					service = sfactory.getBookSerice();
					try {
						service.addNews(news);
					} catch (ServiceExeption e) {
						LOG.error(e);
						response = "Sorry, we have problems in adding news";
					}
				} else {
					if(parts[1].equalsIgnoreCase("disk")) {
					
					service = sfactory.getDiskService();
					try {
						service.addNews(news);
					} catch (ServiceExeption e) {
						LOG.error(e);
						response = "Sorry, we have problems in adding news";
					}
				
				} else {
					LOG.info("incorrect request");
					response = "Sorry, incorrect request";
				}
			}
		}
		}else {
			LOG.info("incorrect request");
			response = "Sorry, incorrect request";
	}
		return response;
	}
	

}