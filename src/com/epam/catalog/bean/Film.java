package com.epam.catalog.bean;

import java.io.Serializable;

import com.epam.catalog.bean.genre.FilmGenre;

public class Film extends News implements Serializable {

	private static final long serialVersionUID = 1L;
		
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((genre == null) ? 0 : genre.hashCode());
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
		Film other = (Film) obj;
		if (genre != other.genre)
			return false;
		return true;
	}
	
	

}
