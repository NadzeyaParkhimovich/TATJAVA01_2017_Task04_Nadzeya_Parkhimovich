package com.epam.catalog.beans;

public class Disk extends News{

	private MusicGenre genre;
	
	public Disk() {
		
	}
	
	public Disk(String title, String author, int year, String text, MusicGenre genre) {
		super(title, author, year, text);
		this.genre = genre;
	}

	public MusicGenre getGenre() {
		return genre;
	}

	public void setGenre(MusicGenre genre) {
		this.genre = genre;
	}
	
}
