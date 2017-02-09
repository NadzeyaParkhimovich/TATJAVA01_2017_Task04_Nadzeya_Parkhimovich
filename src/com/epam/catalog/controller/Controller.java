package com.epam.catalog.controller;

public final class Controller {  
	
	private final CommandProvider provider = new CommandProvider();    
	private final char paramDelimeter = '@';    
	
	public String executeTask(String request){   
		String commandName;   
		Command executionCommand; 
		String response; 
		
		if(request.indexOf(paramDelimeter) != -1){
			
			commandName = request.substring(0, request.indexOf(paramDelimeter));
			executionCommand = provider.getCommand(commandName);  
			response = executionCommand.execute(request);
			
		} else {
			
			executionCommand = provider.getCommand(request);
			response = executionCommand.execute(request);
		}
		return response;
	} 
}
