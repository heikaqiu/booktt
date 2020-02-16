package cn.heikaqiu.booktt.bean;

import java.util.Date;

/**
 * @author HeiKaQiu
 * @create 2020-02-13 12:01
 */
//是 封装查找订单的信息的bean对象
public class FindOrderByInformation {

    private Order.State state;//订单状态
    private Long order_id;//订单id
    private User user;//用户
    private Date first_time;
    private Date last_time;//日期
    private Integer first_num_number;
    private Integer last_num_number;//订单数量
    private Float first_price_number;
    private Float last_price_number;//订单总价

    public Order.State getState() {
        return state;
    }

    public void setState(int v) {
        this.state = Order.State.setV(v);
    }

    public Long getOrder_id() {
        return order_id;
    }

    public void setOrder_id(Long order_id) {
        this.order_id = order_id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getFirst_time() {
        return first_time;
    }

    public void setFirst_time(Date first_time) {
        this.first_time = first_time;
    }

    public Date getLast_time() {
        return last_time;
    }

    public void setLast_time(Date last_time) {
        this.last_time = last_time;
    }

    public Integer getFirst_num_number() {
        return first_num_number;
    }

    public void setFirst_num_number(Integer first_num_number) {
        this.first_num_number = first_num_number;
    }

    public Integer getLast_num_number() {
        return last_num_number;
    }

    public void setLast_num_number(Integer last_num_number) {
        this.last_num_number = last_num_number;
    }

    public Float getFirst_price_number() {
        return first_price_number;
    }

    public void setFirst_price_number(Float first_price_number) {
        this.first_price_number = first_price_number;
    }

    public Float getLast_price_number() {
        return last_price_number;
    }

    public void setLast_price_number(Float last_price_number) {
        this.last_price_number = last_price_number;
    }
}
