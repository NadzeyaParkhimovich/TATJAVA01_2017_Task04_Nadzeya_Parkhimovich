package com.epam.catalog.beans;

public class Book extends News {

	private BookGenre genre;
	private int numberOfPages;
	
	public Book() {
		
	}
	
	public Book(String title, String author, int year, String text, BookGenre genre, int numberOfPages) {
		super(title, author, year, text);
		this.genre = genre;
		this.numberOfPages = numberOfPages;
	}

	public BookGenre getGenre() {
		return genre;
	}

	public void setGenre(BookGenre genre) {
		this.genre = genre;
	}

	public int getNumberOfPages() {
		return numberOfPages;
	}

	public void setNumberOfPages(int numberOfPages) {
		this.numberOfPages = numberOfPages;
	}
	
}
