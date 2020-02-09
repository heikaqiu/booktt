package cn.heikaqiu.booktt.mapper;

import cn.heikaqiu.booktt.bean.Order;
import cn.heikaqiu.booktt.bean.OrderContent;
import cn.heikaqiu.booktt.bean.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author HeiKaQiu
 * @create 2020-02-06 13:44
 */
@Component
public interface OrderMapper {

    /**
     * 将订单添加到数据库
     *
     * @param order
     * @return
     */
    @Insert("insert into `order` (id,user_id,submittime,state,paymentaTime,number,totalPrice)" +
            " values(#{id},#{user.id},#{submitTime},#{state.value},#{paymentaTime},#{number},#{totalPrice});")
    void addOrder(Order order);


    /**
     * 添加订单的详细
     *
     * @param orderContentList
     */
    @Insert({
            "<script>",
            "insert into ordercontent(book_id,number,price,order_id) values ",
            "<foreach collection='orderContentList' item='orderContent' index='index' separator=','>",
            "(#{orderContent.book.id},#{orderContent.number},#{orderContent.price},#{orderContent.order.id})",
            "</foreach>",
            "</script>"
    })
    void addOrderContent(@Param("orderContentList") List<OrderContent> orderContentList);


    /**
     * 获取此用户所有的订单信息
     *
     * @param id
     * @return
     */
    @Select("select * from `order` where user_id=#{id}")
    List<Order> getAllOrderByUserId(Integer id);

    /**
     * 删除订单详情
     *
     * @param orderid
     */
    @Delete("delete from ordercontent where order_id=#{orderid}")
    void deleteOrderContent(Long orderid);

    @Delete("delete from `order` where id=#{orderid}")
    void deleteOrder(Long orderid);

    /**
     * 通过userid 与 orderid查找 订单信息
     * ,id orderid 为什么加这个因为column = "orderid" 这个类似关联条件 如果填的是id 那么我的order对象就会没有id
     *
     * @param order_id
     * @param user_id
     * @return
     */
    @Select("select *,id orderid from `order` where id=#{order_id} AND user_id=#{user_id}")
    @Results({
            @Result(column = "user_id", property = "user", one = @One(select = "cn.heikaqiu.booktt.mapper.UserMapper.getUserById")),
            @Result(column = "orderid", property = "orderContents", many = @Many(select = "cn.heikaqiu.booktt.mapper.OrderMapper.getAllOrderContentByOrderid"))
    })
    Order getOrderInfo(Long order_id, Integer user_id);


    @Select("select * from ordercontent where order_id=#{orderid}")
    @Results({
            @Result(column = "book_id", property = "book", one = @One(select = "cn.heikaqiu.booktt.mapper.BookMapper.getBookInfoById"))
    })
    List<OrderContent> getAllOrderContentByOrderid(Long orderid);

    /**
     * 获取订单通过状态
     * @param value
     * @return
     */
    @Select("select *,id from `order` where state=#{value}")
    @Results({
            @Result(column = "orderid", property = "orderContents", many = @Many(select = "cn.heikaqiu.booktt.mapper.OrderMapper.getAllOrderContentByOrderid"))
    })
    List<Order> getAllOrderByState(int value);


    @Update("update `order` set state=#{value} where id=#{id}")
    void updateOrderState(Long id, int value);
}
