package com.epam.catalog.beans;

public class Film extends News {

	private FilmGenre genre;
	
	public Film() {
	}
	
	public Film(String title, String author, int year, String text, FilmGenre genre) {
		super(title, author, year, text);
		this.genre = genre;
	}

	public FilmGenre getGenre() {
		return genre;
	}

	public void setGenre(FilmGenre genre) {
		this.genre = genre;
	}
	
	

}
