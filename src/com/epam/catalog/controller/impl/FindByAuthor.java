package com.epam.catalog.controller.impl;

import java.util.ArrayList;

import com.epam.catalog.beans.Book;
import com.epam.catalog.beans.Disk;
import com.epam.catalog.beans.Film;
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
					response = "Author: " + parts[2] + "\nTitle Author Year Text Genre\n";
					for(Film film : (ArrayList<Film>)service.findByAuthor(parts[2])) {
						response += film.getTitle() + " " + film.getAuthor() + " " + film.getYear() + " " + film.getText() + " " + film.getGenre() + "\n";
					}
				} catch (ServiceExeption e) {
					response = "Sorry, we have problems in finding films by author";
				}
			} else {
				if(parts[1].equalsIgnoreCase("book")) {
					
					service = sfactory.getBookSerice();
					try {
						response = "Author: " + parts[2] + "\nTitle Author Year Text Genre NumberOfPages\n";
						for(Book book : (ArrayList<Book>)service.findByAuthor(parts[2])) {
							response += book.getTitle() + " " + book.getAuthor() + " " + book.getYear() + " " + book.getText() + " " + book.getGenre() + " " + book.getNumberOfPages() + "\n";
						}
					} catch (ServiceExeption e) {
						response = "Sorry, we have problems in finding books by author";
					}
				} else {
					if(parts[1].equalsIgnoreCase("disk")) {
					
					service = sfactory.getDiskService();
					try {
						response = "Author: " + parts[2] + "\nTitle Author Year Text Genre\n";
						for(Disk disk : (ArrayList<Disk>)service.findByAuthor(parts[2])) {
							response += disk.getTitle() + " " + disk.getAuthor() + " " + disk.getYear() + " " + disk.getText() + " " + disk.getGenre() + "\n";
						}
					} catch (ServiceExeption e) {
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
