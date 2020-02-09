package cn.heikaqiu.booktt.bean;

import java.util.ArrayList;
import java.util.List;

public class Book {
	private Integer id;
	private String name;//书名
	private String synopsis;//书简介
	private Float price;
	private Integer remainder;//库存几本
	private String img;//书的图片
	
	private boolean isshop;// 真就是 卖  假就是不卖了(已下架)   库存不足 就是等上架(库存不足)
	
	//这本书的作者
	private  Author author;

	//这本书的类型
	private BookType bookType;
	
	
	//收藏这本书的用户
	private List<User> userCollection=new ArrayList<User>();
	
	//评价这本书的留言
	private List<Comment> comments=new ArrayList<Comment>();
	
	//把这本书加入购物车的用户
	private List<User> usersShopcart=new ArrayList<User>();

	@Override
	public String toString() {
		return "Book{" +
				"id=" + id +
				", name='" + name + '\'' +
				", synopsis='" + synopsis + '\'' +
				", price=" + price +
				", remainder=" + remainder +
				", img='" + img + '\'' +
				", isshop=" + isshop +
				", author=" + author +
				", bookType=" + bookType +

				'}';
	}

	public BookType getBookType() {
		return bookType;
	}

	public void setBookType(BookType bookType) {
		this.bookType = bookType;
	}

	public boolean isIsshop() {
		return isshop;
	}
	public void setIsshop(boolean isshop) {
		this.isshop = isshop;
	}
	public List<Comment> getComments() {
		return comments;
	}
	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}
	public Author getAuthor() {
		return author;
	}
	public void setAuthor(Author author) {
		this.author = author;
	}
	public List<User> getUserCollection() {
		return userCollection;
	}
	public void setUserCollection(List<User> userCollection) {
		this.userCollection = userCollection;
	}
	public List<User> getUsersShopcart() {
		return usersShopcart;
	}
	public void setUsersShopcart(List<User> usersShopcart) {
		this.usersShopcart = usersShopcart;
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
	public Float getPrice() {
		return price;
	}
	public void setPrice(Float price) {
		this.price = price;
	}
	public Integer getRemainder() {
		return remainder;
	}
	public void setRemainder(Integer remainder) {
		this.remainder = remainder;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	
	
	
	
}
