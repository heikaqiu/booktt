package cn.heikaqiu.booktt.bean;

public class Shopcart {
	private Integer id;
	private Integer number;// 此类商品加入购物车的数量
	
	//此用户添加购物车
	private User user;
	
	//此商品被添加
	private Book book;
	
	
	
	
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
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getNumber() {
		return number;
	}
	public void setNumber(Integer number) {
		this.number = number;
	}
	@Override
	public String toString() {
		return "Shopcart [id=" + id + ", number=" + number + ", book=" + book + "]";
	}

}
