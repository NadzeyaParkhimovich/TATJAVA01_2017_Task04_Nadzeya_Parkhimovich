package com.epam.catalog.view;

import java.util.Scanner;
import com.epam.catalog.controller.Controller;

public class View {
	
	public static void main(String[] args) {
		
		Controller controller = new Controller();
		Scanner sc = new Scanner(System.in);
		System.out.println("Available commands with examples:");
		System.out.println("FIND_ALL       find_all@book/film/disk\n" +
				 "FIND_BY_TITLE  find_by_title@book/film/disk@your title\n" +
				 "FIND_BY_AUTHOR find_by_author@book/film/disk@your author\n" +
				 "FIND_BY_YEAR   find_by_year@book/film/disk@1900\n" +
				 "FIND_BY_TEXT   find_by_text@book/film/disk@your text\n" +
				 "FIND_BY_GENRE  find_by_genre@book/film/disk@GENRE\n" +
				 "ADD_NEWS       add_news@book/film/disk@title@author@year@text@GENRE@numberOfPages(only for book)\n" +
				 "EXIT 			 exit");
		System.out.println("Enter you request");
		String request;
		do {
			request = sc.nextLine();
			if(request.length() > 1) {
				System.out.println(controller.executeTask(request));
			}
		} while (!request.equalsIgnoreCase("exit"));
		sc.close();
	}
	
}
