package cn.heikaqiu.booktt.mapper;

import cn.heikaqiu.booktt.bean.FindOrderByInformation;
import cn.heikaqiu.booktt.bean.Order;
import cn.heikaqiu.booktt.bean.OrderContent;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

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
    @Insert("insert into `order` (id,user_id,submittime,state,paymentaTime,number,totalPrice,isread)" +
            " values(#{id},#{user.id},#{submitTime},#{state.value},#{paymentaTime},#{number},#{totalPrice},#{isread});")
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
     *
     * @param value
     * @return
     */
    @Select("select *,id from `order` where state=#{value}")
    @Results({
            @Result(column = "orderid", property = "orderContents", many = @Many(select = "cn.heikaqiu.booktt.mapper.OrderMapper.getAllOrderContentByOrderid"))
    })
    List<Order> getAllOrderByState(int value);

    /**
     * 更改订单的状态 ，如果是关闭订单则最后付款时间不变，如果是 付账，则最后付款时间为现在
     *
     * @param id
     * @param value
     * @param date
     */
    @Update({
            "<script>",
            "update `order` set state=#{value}",
            "<if test='date != null'>,paymentaTime=#{date}</if>",
            "where id=#{id}",
            "</script>"
    })
    void updateOrderState(Long id, int value, Date date);


    /**
     * 查找总共有多少订单
     *
     * @return
     */
    @Select("select count(*) from `order`")
    Long getAllCountOrder();


    /**
     * 查找任意时间段内 提交的订单  但不包含 未付款的和关闭的  只有付款的 才算提交的
     *
     * @param start_time
     * @param last_time
     * @return
     */
    @Select("select count(*) from `order` where paymentaTime<#{last_time} AND paymentaTime >= #{start_time} AND state!=1 AND state!=5")
    Long getAnydayCountOrder(Date start_time, Date last_time);

    /**
     * 查找订单通过条件 并分页
     *
     * @param state_num
     * @param page_num
     * @param orderByInformation
     * @return
     */
    @Select({
            "<script>",
            "select * from  `order` <where>",
            "<if test='orderByInformation.state != null'> and state=#{orderByInformation.state.value}</if>",
            "<if test='orderByInformation.order_id != null'> and  id=#{orderByInformation.order_id}</if>",
            "<if test='orderByInformation.user != null'> and  user_id=#{orderByInformation.user.id}</if>",
            "<if test='orderByInformation.first_time != null and orderByInformation.last_time != null'> and paymentaTime  &lt;  #{orderByInformation.last_time} and paymentaTime &gt;= #{orderByInformation.first_time}</if>",
            "<if test='orderByInformation.first_num_number != null and orderByInformation.last_num_number != null'> and number  &lt;= #{orderByInformation.last_num_number} and number &gt;= #{orderByInformation.first_num_number}</if>",
            "<if test='orderByInformation.first_price_number != null and orderByInformation.last_price_number != null'> and totalPrice &lt;= #{orderByInformation.last_price_number} and totalPrice &gt;= #{orderByInformation.first_price_number}</if>",
            "</where> limit #{state_num},#{page_num}",
            "</script>"
    })
    @Results({
            @Result(column = "user_id", property = "user", one = @One(select = "cn.heikaqiu.booktt.mapper.UserMapper.getUserById"))
    })
    List<Order> getOrderInfoLimit(Integer state_num, Integer page_num, FindOrderByInformation orderByInformation);


    @Select({
            "<script>",
            "select count(*) from  `order` <where>",
            "<if test='state != null'> and state=#{state.value}</if>",
            "<if test='order_id != null'> and  id=#{order_id}</if>",
            "<if test='user != null'> and  user_id=#{user.id}</if>",
            "<if test='first_time != null and last_time != null'> and paymentaTime  &lt;  #{last_time} and paymentaTime &gt;= #{first_time}</if>",
            "<if test='first_num_number != null and last_num_number != null'> and number  &lt;= #{last_num_number} and number &gt;= #{first_num_number}</if>",
            "<if test='first_price_number != null and last_price_number != null'> and totalPrice &lt;= #{last_price_number} and totalPrice &gt;= #{first_price_number}</if>",
            "</where>",
            "</script>"
    })
    Long getOrderByInformationNum(FindOrderByInformation orderByInformation);

    @Select("select *,id orderid from `order` where id=#{order_id}")
    @Results({
            @Result(column = "user_id", property = "user", one = @One(select = "cn.heikaqiu.booktt.mapper.UserMapper.getUserById")),
            @Result(column = "orderid", property = "orderContents", many = @Many(select = "cn.heikaqiu.booktt.mapper.OrderMapper.getAllOrderContentByOrderid"))
    })
    Order getOrderInfoByOrderId(Long order_id);

    @Select("select count(*) from `order` where state=#{value}")
    Long getCountByOrderState(int value);

    @Select("select count(*) from `order` where express_number=#{expressNumber}")
    Integer findExpressNumber(String expressNumber);

    @Update("update `order` set express_number=#{expressNumber} where id=#{orderid}")
    void toDeliverGoods(String orderid, String expressNumber);

    @Select("select express_number from `order` where id=#{orderid}")
    String findExpressNumberByorderid(String orderid);

    @Select({
            "<script>",
            "select number from  `order` <where>",
            "<if test='start_time != null'> and paymentaTime  &lt;  #{last_time} </if>",
            "<if test='last_time != null '> and paymentaTime &gt;= #{start_time}</if>",
            "</where>",
            "</script>"
    })
    List<Integer> getCountSellBookNum(Date start_time, Date last_time);

    @Select("select count(*) from `order` where state=2 AND isread=false")
    Integer selectNewOrderNum();

    @Update("update `order` set isread=#{b} where id=#{orderId}")
    Integer updateOrderIsread(Long orderId, boolean b);

    @Select("select * from  `order` where state=2 AND isread=false limit #{state_num},#{page_num}")
    @Results({
            @Result(column = "user_id", property = "user", one = @One(select = "cn.heikaqiu.booktt.mapper.UserMapper.getUserById"))
    })
    List<Order> getOrderInfoByNewOrder(Integer state_num,Integer page_num);
}
