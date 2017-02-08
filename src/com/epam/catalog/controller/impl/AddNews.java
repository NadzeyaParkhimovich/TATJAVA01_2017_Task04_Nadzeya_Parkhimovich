package com.epam.catalog.controller.impl;

import com.epam.catalog.controller.Command;
import com.epam.catalog.service.Service;
import com.epam.catalog.service.ServiceExeption;
import com.epam.catalog.service.ServiceFactory;

public class AddNews implements Command{

	@Override
	public String execute(String request) {
		ServiceFactory sfactory = ServiceFactory.getInstance();
		Service service;
		String parts[] = request.split("@");
		String news = request.substring(request.indexOf('@', 9)+1);
		String response = "";
		if (parts.length >= 3) {
			if(parts[1].equalsIgnoreCase("film")) {
				
				service = sfactory.getFilmService();
				try {
					service.addNews(news);
					response = "Adding is OK";
				} catch (ServiceExeption e) {
					response = "Sorry, we have problems in adding news";
				}
			} else {
				if(parts[1].equalsIgnoreCase("book")) {
					
					service = sfactory.getBookSerice();
					try {
						service.addNews(news);
						response = "Adding is OK";
					} catch (ServiceExeption e) {
						response = "Sorry, we have problems in adding news";
					}
				} else {
					if(parts[1].equalsIgnoreCase("disk")) {
					
					service = sfactory.getDiskService();
					try {
						service.addNews(news);
						response = "Adding is OK";
					} catch (ServiceExeption e) {
						response = "Sorry, we have problems in adding news";
					}
				
				} else {
					response = "Sorry, incorrect request";
				}
			}
		}
		}else {
		response = "Sorry, incorrect request";
	}
		return response;
	}
	

}