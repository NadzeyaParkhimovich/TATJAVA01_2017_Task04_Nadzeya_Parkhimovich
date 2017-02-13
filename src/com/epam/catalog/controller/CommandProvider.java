package com.epam.catalog.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.epam.catalog.controller.impl.AddNews;
import com.epam.catalog.controller.impl.Exit;
import com.epam.catalog.controller.impl.FindAll;
import com.epam.catalog.controller.impl.FindByAuthor;
import com.epam.catalog.controller.impl.FindByGenre;
import com.epam.catalog.controller.impl.FindByText;
import com.epam.catalog.controller.impl.FindByTitle;
import com.epam.catalog.controller.impl.FindByYear;
import com.epam.catalog.controller.impl.WrongRequest;

final class CommandProvider {   
	
	private final static Logger LOG = LogManager.getRootLogger();
	
	private final Map<CommandName, Command> repository = new HashMap<>();    
	
	CommandProvider(){   
		repository.put(CommandName.FIND_ALL, new FindAll());   
		repository.put(CommandName.FIND_BY_TITLE, new FindByTitle());   
		repository.put(CommandName.FIND_BY_AUTHOR, new FindByAuthor());
		repository.put(CommandName.FIND_BY_YEAR, new FindByYear());
		repository.put(CommandName.FIND_BY_TEXT, new FindByText());
		repository.put(CommandName.FIND_BY_GENRE, new FindByGenre());
		repository.put(CommandName.ADD_NEWS, new AddNews());
		repository.put(CommandName.EXIT, new Exit());
		repository.put(CommandName.WRONG_REQUEST, new WrongRequest());   
	}
	
	Command getCommand(String name) {   
		CommandName commandName = null;   
		Command command = null;      
		try{    
				commandName = CommandName.valueOf(name.toUpperCase());    
				command = repository.get(commandName);
				
		 }catch(IllegalArgumentException | NullPointerException e){    
			LOG.info(e);
		 	command = repository.get(CommandName.WRONG_REQUEST);
		 }
		return command;
	}
 }