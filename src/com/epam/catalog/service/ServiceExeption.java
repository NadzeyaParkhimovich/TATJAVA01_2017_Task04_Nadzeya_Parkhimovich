package com.epam.catalog.service;

public class ServiceExeption extends Exception {

	private static final long serialVersionUID = 1L;
	
	public ServiceExeption(){ 
		super();
	}
	
	public ServiceExeption(String message){
		super(message);
	}

	public ServiceExeption(Exception e){
		super(e);
	}
	public ServiceExeption(String message, Exception e){
		super(message, e);
	}
}

