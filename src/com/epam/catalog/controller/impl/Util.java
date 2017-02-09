package com.epam.catalog.controller.impl;

import java.util.ArrayList;

import com.epam.catalog.bean.Book;
import com.epam.catalog.bean.Disk;
import com.epam.catalog.bean.Film;
import com.epam.catalog.bean.News;

public class Util {
	
	public static String responseCreator(ArrayList<? extends News> news, String type, String founder) {
		StringBuilder response = new StringBuilder();
		if(news.size() != 0) {
			if(news.get(0) instanceof Film) {
				response.append(type + ": " + founder + "\nTitle | Author | Year | Text Genre\n");
				for(Film film : (ArrayList<Film>)news) {
					response.append(film.getTitle() + "|" + film.getAuthor() + "|" + film.getYear() + "|" + film.getText() + "|" + film.getGenre() + "\n");
				}
			} else {
				if (news.get(0) instanceof Book) {
					response.append(type + ": " + founder + "\nTitle | Author | Year | Text | Genre | NumberOfPages\n");
					for(Book book : (ArrayList<Book>)news) {
						response.append(book.getTitle() + "|" + book.getAuthor() + "|" + book.getYear() + "|" + book.getText() + "|" + book.getGenre() + "|" + book.getNumberOfPages() + "\n");
					}
				} else {
					if (news.get(0) instanceof Disk) {
						response.append(type + ": " + founder + "\nTitle | Author | Year | Text |Genre\n");
						for(Disk disk : (ArrayList<Disk>)news) {
							response.append(disk.getTitle() + "|" + disk.getAuthor() + "|" + disk.getYear() + "|" + disk.getText() + "|" + disk.getGenre() + "\n");
						}
					}
				}
			}
		} else {
			response.append(type + ": " + founder + "\nNothing is found");
		}
		return response.toString();
	}

}
