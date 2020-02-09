package cn.heikaqiu.booktt.bean;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//这个只代表 一本书   如果一次订单 将order_id分为组
public class Order {

    private Long id;

    //提交订单时间
    private Date submitTime;

    //提交订单时  这是 最后付款时间
    //当付款时 判断 如果小于 则更新付款时间
    //否则 则是超时付款  设为取消订单
    private Date paymentaTime;
    //完成订单时间
    private Date finishTime;

    //状态
    private State state;

    private User user;

    //此份订单有哪些信息
    private List<OrderContent> orderContents = new ArrayList<OrderContent>();

    //快递单号
    private String expressnumber;

    //订单的总数
    private Integer number;

    //订单的总价
    private Float totalPrice;


    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", submitTime=" + submitTime +
                ", paymentaTime=" + paymentaTime +
                ", finishTime=" + finishTime +
                ", state=" + state +
                ", user=" + user +
                ", orderContents=" + orderContents +
                ", expressnumber='" + expressnumber + '\'' +
                ", number=" + number +
                ", totalPrice=" + totalPrice +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getSubmitTime() {
        return submitTime;
    }

    public void setSubmitTime(Date submitTime) {
        this.submitTime = submitTime;
    }

    public Date getPaymentaTime() {
        return paymentaTime;
    }

    public void setPaymentaTime(Date paymentaTime) {
        this.paymentaTime = paymentaTime;
    }

    public Date getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
    }

    public State getState() {
        return state;
    }

    public void setState(int v) {
        this.state = State.setV(v);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<OrderContent> getOrderContents() {
        return orderContents;
    }

    public void setOrderContents(List<OrderContent> orderContents) {
        this.orderContents = orderContents;
    }

    public String getExpressnumber() {
        return expressnumber;
    }

    public void setExpressnumber(String expressnumber) {
        this.expressnumber = expressnumber;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public enum State {
        WAIT_PAYMENT("等待买家付款", 1),
        WAIT_DELIVER_GOODS("等待卖家发货", 2),
        DELIVER_GOODS("卖家已发货", 3),
        FINISH_ORDER("订单完成", 4),
        CLOSE("订单关闭", 5);
        private final String name;
        private final int value;

        private State(String name, int value) {
            this.name = name;
            this.value = value;
        }

        @Override
        public String toString() {
            return name;
        }

        public String getName() {
            return name;
        }

        public int getValue() {
            return value;
        }

        public static State setV(int i) {
            switch (i) {
                case 1:
                    return WAIT_PAYMENT;
                case 2:
                    return WAIT_DELIVER_GOODS;
                case 3:
                    return DELIVER_GOODS;
                case 5:
                    return CLOSE;
                default:
                    return FINISH_ORDER;
            }

        }
    }
}