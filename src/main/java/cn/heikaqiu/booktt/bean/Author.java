package cn.heikaqiu.booktt.bean;

import java.util.ArrayList;
import java.util.List;

public class Author {
	private Integer id;
	private String name;
	//作者简介
	private String synopsis;
	
	//作者创作的书
	private List<Book> books=new ArrayList<Book>();
	
	
	
	
	
	public List<Book> getBooks() {
		return books;
	}
	public void setBooks(List<Book> books) {
		this.books = books;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSynopsis() {
		return synopsis;
	}
	public void setSynopsis(String synopsis) {
		this.synopsis = synopsis;
	}
	
	
	
	

}
