package com.epam.catalog.controller.impl;

import com.epam.catalog.controller.Command;
import com.epam.catalog.service.Service;
import com.epam.catalog.service.ServiceExeption;
import com.epam.catalog.service.ServiceFactory;

public class Exit implements Command{

	@Override
	public String execute(String request) {
		
		String response = "Goodbye!";
		ServiceFactory sfactory = ServiceFactory.getInstance();
		Service service = sfactory.getBookSerice();
		try {
			service.close();
		} catch (ServiceExeption e) {
			//log
		}
		return response;
	}

}
