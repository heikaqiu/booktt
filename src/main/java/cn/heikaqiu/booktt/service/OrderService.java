package cn.heikaqiu.booktt.service;

import cn.heikaqiu.booktt.bean.FindOrderByInformation;
import cn.heikaqiu.booktt.bean.Order;

import java.util.List;

/**
 * @author HeiKaQiu
 * @create 2020-02-06 13:45
 */
public interface OrderService {
    /**
     * 获取此用户的所有订单信息 但不包含详细
     * @param id
     * @return
     */
    List<Order> getAllOrderByUserId(Integer id);

    /**
     * 删除订单
     * @param orderid
     * @return
     */
    boolean deleteOrder(Long orderid);

    /**
     * 通过orderid查找 订单信息
     * @param order_id
     * @return
     */
    Order getOrderInfoByOrderId(Long order_id);

    /**
     * 通过userid 与 orderid查找 订单信息
     * @param order_id
     * @param user_id
     * @return
     */
    Order getOrderInfo(Long order_id, Integer user_id);


    /**
     * 查看此用户下的未支付订单如果超时就把订单取消
     */
    void updateOrderStateIfOutTime(Integer userId);

    /**
     * 从未付款订单  完成付款
     * @param order
     * @param order_user_id
     * @param paypassword_pass
     * @return
     */
    Integer toBuyOrder(Long order_id, Integer order_user_id, String paypassword_pass);


    /**
     * 关闭订单
     * @param order
     * @return
     */
    boolean closeOrder(Long order_id);

    /**
     * 获取全部订单的总数
     * @return
     */
    Long getAllCountOrder();

    /**
     * 获取任意时间内的订单总数
     * @param start_time
     * @param last_time
     * @return
     */
    Long getAnydayCountOrder(Long start_time, Long last_time);


    /**
     * 需要查找订单 并分页
     * @param state_num
     * @param page_num
     * @param orderByInformation
     * @return
     */
    List<Order> getOrderInfoLimit(Integer state_num, Integer page_num, FindOrderByInformation orderByInformation);

    /**
     * 需要查找订单 的总数
     * @param orderByInformation
     * @return
     */
    Long getOrderByInformationNum(FindOrderByInformation orderByInformation);

    Long getCountByOrderState(Order.State state);

    /**
     * 发货
     * @param orderid
     * @param expressNumber
     * @return
     */
    Integer toDeliverGoods(String orderid, String expressNumber);

    /**
     * 查找新订单 新的订单意思是 订单的状态是等待卖家发货，并且卖家买有看过的
     * @return
     */
    Integer selectNewOrderNum();

    /**
     * 将订单标为已读
     * @param orderId
     * @param b
     * @return
     */
    boolean updateOrderIsread(Long orderId, boolean b);

    /**
     * 查找新订单
     * @return
     */
    List<Order> getOrderInfoByNewOrder(Integer state_num,Integer page_num);
}
