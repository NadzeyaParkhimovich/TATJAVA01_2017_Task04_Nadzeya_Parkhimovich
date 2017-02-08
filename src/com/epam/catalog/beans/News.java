package com.epam.catalog.beans;

public class News {
	
	private String title;
	private String author;
	private int year;
	private String text;
	
	public News() {
		
	}
	
	public News(String title,String author,int year,String text) {
		this.title = title;
		this.author = author;
		this.year = year;
		this.text = text;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}

	
}
