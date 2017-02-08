package com.epam.catalog.controller.impl;

import com.epam.catalog.controller.Command;

public class WrongRequest implements Command{

	@Override
	public String execute(String request) {
		String response = "Sorry, rong request";
		return response;
	}
	

}
