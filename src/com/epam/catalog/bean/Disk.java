package com.epam.catalog.bean;

import java.io.Serializable;

import com.epam.catalog.bean.genre.MusicGenre;

public class Disk extends News implements Serializable{

	private static final long serialVersionUID = 1L;

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
		Disk other = (Disk) obj;
		if (genre != other.genre)
			return false;
		return true;
	}
	
	
	
}
