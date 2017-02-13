package com.epam.catalog.controller.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.epam.catalog.controller.Command;
import com.epam.catalog.service.Service;
import com.epam.catalog.service.ServiceExeption;
import com.epam.catalog.service.ServiceFactory;

public class FindAll implements Command{

	private final static Logger LOG = LogManager.getRootLogger();
	
	@Override
	public String execute(String request) {
		
		ServiceFactory sfactory = ServiceFactory.getInstance();
		Service service;
		String parts[] = request.split("@");
		String response = "";
		if (parts.length >= 2) {
			
			if(parts[1].equalsIgnoreCase("film")) {
				
				service = sfactory.getFilmService();
				try {
					response = Util.responseCreator(service.findAll(), "All", "films");
					
				} catch (ServiceExeption e) {
					LOG.error(e);
					response = "Sorry, we have problems in finding films";
				}
			} else {
				if(parts[1].equalsIgnoreCase("book")) {
					
					service = sfactory.getBookSerice();
					try {
						response = Util.responseCreator(service.findAll(), "All", "books");
					} catch (ServiceExeption e) {
						LOG.error(e);
						response = "Sorry, we have problems in finding books";
					}
				} else {
					if(parts[1].equalsIgnoreCase("disk")) {
						
						service = sfactory.getDiskService();
						try {
							response = Util.responseCreator(service.findAll(), "All", "disks");
						} catch (ServiceExeption e) {
							LOG.error(e);
							response = "Sorry, we have problems in finding disks";
						}
					} else {
						LOG.info("incorrect request");
						response = "Sorry, incorrect request";
					}
				}
			}
		} else {
			LOG.info("incorrect request");
			response = "Sorry, incorrect request";
		}
		 
		return response;
	}
	

}
