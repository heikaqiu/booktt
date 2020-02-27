package cn.heikaqiu.booktt.bean;

import java.util.Date;

/**
 * @author HeiKaQiu
 * @create 2020-02-24 12:39
 */
public class Advice {

    private Integer id;
    private User user;

    private String title;// 标题
    private String content;//正文

    private Date time;//提交时间

    private Date adviceTime;//处理完成时间

    //是否已读
    private Boolean isread;
    //是否处理
    private Boolean ishandle;


    public Date getAdviceTime() {
        return adviceTime;
    }

    public void setAdviceTime(Date adviceTime) {
        this.adviceTime = adviceTime;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Boolean getIsread() {
        return isread;
    }

    public void setIsread(Boolean isread) {
        this.isread = isread;
    }

    public Boolean getIshandle() {
        return ishandle;
    }

    public void setIshandle(Boolean ishandle) {
        this.ishandle = ishandle;
    }

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Advice{" +
                "id=" + id +
                ", user=" + user +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", time=" + time +
                ", adviceTime=" + adviceTime +
                ", isread=" + isread +
                ", ishandle=" + ishandle +
                '}';
    }
}
