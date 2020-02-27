package cn.heikaqiu.booktt.bean;

import java.util.Date;

/**
 * @author HeiKaQiu
 * @create 2020-02-13 12:01
 */
//是 封装查找订单的信息的bean对象
public class FindBookByInformation {


    private Integer start_id;
    private Integer last_id;//书本id

    private String name;//书本名字 %x%x%

    private Integer booktype;//书本状态


    private Integer author_id;//作者 id

    private Integer start_remainder;
    private Integer last_remainder;//库存查找

    private Float start_price;
    private Float last_price;//订单总价

    private Boolean isshop;//是否销售

    public Integer getStart_id() {
        return start_id;
    }

    public Integer getStart_remainder() {
        return start_remainder;
    }

    public void setStart_remainder(Integer start_remainder) {
        this.start_remainder = start_remainder;
    }

    public Integer getLast_remainder() {
        return last_remainder;
    }

    public void setLast_remainder(Integer last_remainder) {
        this.last_remainder = last_remainder;
    }

    public void setStart_id(Integer start_id) {
        this.start_id = start_id;
    }

    public Integer getLast_id() {
        return last_id;
    }

    public void setLast_id(Integer last_id) {
        this.last_id = last_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getBooktype() {
        return booktype;
    }

    public void setBooktype(Integer booktype) {
        this.booktype = booktype;
    }

    public Integer getAuthor_id() {
        return author_id;
    }

    public void setAuthor_id(Integer author_id) {
        this.author_id = author_id;
    }

    public Float getStart_price() {
        return start_price;
    }

    public void setStart_price(Float start_price) {
        this.start_price = start_price;
    }

    public Float getLast_price() {
        return last_price;
    }

    public void setLast_price(Float last_price) {
        this.last_price = last_price;
    }

    public Boolean getIsshop() {
        return isshop;
    }

    public void setIsshop(Boolean isshop) {
        this.isshop = isshop;
    }


    @Override
    public String toString() {
        return "FindBookByInformation{" +
                "start_id=" + start_id +
                ", last_id=" + last_id +
                ", name='" + name + '\'' +
                ", booktype=" + booktype +
                ", author_id=" + author_id +
                ", start_remainder=" + start_remainder +
                ", last_remainder=" + last_remainder +
                ", start_price=" + start_price +
                ", last_price=" + last_price +
                ", isshop=" + isshop +
                '}';
    }
}
