package com.epam.catalog.bean;

import java.io.Serializable;

import com.epam.catalog.bean.genre.BookGenre;

public class Book extends News implements Serializable {

	private static final long serialVersionUID = 1L;
	
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((genre == null) ? 0 : genre.hashCode());
		result = prime * result + numberOfPages;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Book other = (Book) obj;
		if (genre != other.genre)
			return false;
		if (numberOfPages != other.numberOfPages)
			return false;
		return true;
	}
	
	
}
