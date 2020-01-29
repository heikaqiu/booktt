package cn.heikaqiu.booktt.bean;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class User {
	
	private Integer id;
	private String username;
	private String password;
	private Float balance;
	private boolean isadmin; //管理员 1为是  0为否
	private String address;
	private boolean gender;//性别  1 为男    0为女
	private Date time;
	private String paypassword;
	
	//用户收藏的书
	private List<Book> booksCollection=new ArrayList<Book>();
	
	//用户评价的书
	private List<Book> booksComment=new ArrayList<Book>();
	
	//用户回复的留言
	private List<Comment> comments=new ArrayList<Comment>();
	
	
	//用户购物车中的商品
	private List<Book> books=new ArrayList<Book>();
	
	
	
	
	
	public String getPaypassword() {
		return paypassword;
	}
	public void setPaypassword(String paypassword) {
		this.paypassword = paypassword;
	}
	public List<Book> getBooksCollection() {
		return booksCollection;
	}
	public void setBooksCollection(List<Book> booksCollection) {
		this.booksCollection = booksCollection;
	}
	public List<Book> getBooksComment() {
		return booksComment;
	}
	public void setBooksComment(List<Book> booksComment) {
		this.booksComment = booksComment;
	}
	public List<Comment> getComments() {
		return comments;
	}
	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}
	public List<Book> getBooks() {
		return books;
	}
	public void setBooks(List<Book> books) {
		this.books = books;
	}
	public String getTime() {
		return new Order().getCreatetime(this.time);
	}
	public void setTime(Date time) {
		this.time = time;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Float getBalance() {
		return balance;
	}
	public void setBalance(Float balance) {
		this.balance = balance;
	}
	public boolean isIsadmin() {
		return isadmin;
	}
	public void setIsadmin(boolean isadmin) {
		this.isadmin = isadmin;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	public boolean isGender() {
		return gender;
	}
	public void setGender(boolean gender) {
		this.gender = gender;
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", password=" + password + ", balance=" + balance
				+ ", isadmin=" + isadmin + ", address=" + address + ", gender=" + gender + ", time=" + time
				+ ", paypassword=" + paypassword + ", booksCollection=" + booksCollection + ", booksComment="
				+ booksComment + ", comments=" + comments + ", books=" + books + "]";
	}
	
	
	

}
