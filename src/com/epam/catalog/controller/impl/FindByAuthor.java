package com.epam.catalog.controller.impl;

import com.epam.catalog.controller.Command;
import com.epam.catalog.service.Service;
import com.epam.catalog.service.ServiceExeption;
import com.epam.catalog.service.ServiceFactory;

public class FindByAuthor implements Command{

	@Override
	public String execute(String request) {
		
		ServiceFactory sfactory = ServiceFactory.getInstance();
		Service service;
		String parts[] = request.split("@");
		String response = "";
		if (parts.length >= 3) {
			if(parts[1].equalsIgnoreCase("film")) {
				
				service = sfactory.getFilmService();
				try {
					response = Util.responseCreator(service.findByAuthor(parts[2]), "Authоr", parts[2]);
					
				} catch (ServiceExeption e) {
					//log
					response = "Sorry, we have problems in finding films by author";
				}
			} else {
				if(parts[1].equalsIgnoreCase("book")) {
					
					service = sfactory.getBookSerice();
					try {
						response = Util.responseCreator(service.findByAuthor(parts[2]), "Authоr", parts[2]);
						
					} catch (ServiceExeption e) {
						//log
						response = "Sorry, we have problems in finding books by author";
					}
				} else {
					if(parts[1].equalsIgnoreCase("disk")) {
					
					service = sfactory.getDiskService();
					try {
						response = Util.responseCreator(service.findByAuthor(parts[2]), "Authоr", parts[2]);
					} catch (ServiceExeption e) {
						//log
						response = "Sorry, we have problems in finding disks by author";
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
