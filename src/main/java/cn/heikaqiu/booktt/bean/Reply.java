package cn.heikaqiu.booktt.bean;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Reply {
	private int id;
	private String content;//回复内容
	private Date time;//回复时间
	
	//回复这条留言的用户
	private User user;
	
	//被回复的留言
	private Comment comment;


	@Override
	public String toString() {
		return "Reply{" +
				"id=" + id +
				", content='" + content + '\'' +
				", time=" + time +
				", user=" + user +
				", comment=" + comment +
				'}';
	}

	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Comment getComment() {
		return comment;
	}
	public void setComment(Comment comment) {
		this.comment = comment;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}
	
	
	
	
	
}
