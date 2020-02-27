package cn.heikaqiu.booktt.bean;

import java.util.List;

//收藏
public class Collection {
	
	private Integer id;
	
	//收藏的用户
	private User user;
	//被收藏的书
	private Book book;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Book getBook() {
		return book;
	}
	public void setBook(Book book) {
		this.book = book;
	}

	@Override
	public String toString() {
		return "Collection{" +
				"id=" + id +
				", user=" + user +
				", book=" + book +
				'}';
	}
}
