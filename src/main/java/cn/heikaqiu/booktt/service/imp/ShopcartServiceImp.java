package cn.heikaqiu.booktt.service.imp;

import cn.heikaqiu.booktt.bean.*;
import cn.heikaqiu.booktt.mapper.BookMapper;
import cn.heikaqiu.booktt.mapper.OrderMapper;
import cn.heikaqiu.booktt.mapper.ShopcartMapper;
import cn.heikaqiu.booktt.mapper.UserMapper;
import cn.heikaqiu.booktt.service.ShopcartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author HeiKaQiu
 * @create 2020-02-04 19:18
 */
@Service
public class ShopcartServiceImp implements ShopcartService {

    @Autowired
    private ShopcartMapper shopcartMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private BookMapper bookMapper;

    @Autowired
    private OrderMapper orderMapper;

    @Override
    public List<Shopcart> getShopcartByUserId(Integer id) {

        return shopcartMapper.getShopcartByUserId(id);
    }

    @Override
    public boolean delectShopcart(Integer shopcart_id) {
        Integer num = shopcartMapper.delectShopcartById(shopcart_id);
        if (num == 1) {
            return true;
        } else {
            return false;

        }
    }

    @Override
    public Long addOrder(List<Integer> bookid, List<Integer> booknum, Integer userid, Float totalPrice, Integer orderState) {

        Integer number = 0;//订单的总本书
        //封装order
        Order order = new Order();
        Date now = new Date();

        order.setId(now.getTime());
        order.setSubmitTime(now);


        User user = new User();
        user.setId(userid);
        order.setUser(user);

        //查书的库存
        for (int i = 0; i < bookid.size(); i++) {

            Integer remainderByBookId = bookMapper.getRemainderByBookId(bookid.get(i));
            //将书和加起来
            number += booknum.get(i);
            //如果某一本书不足
            if (remainderByBookId < booknum.get(i)) {
                return 0L;
            }
        }

        //添加订单需要将订单信息存储,购物车信息删除，库存也需要扣除
        //付款时间为当前时间 再加上半小时
        order.setPaymentaTime(new Date(now.getTime() + 30 * 60 * 1000));

        //实现添加订单
        //添加订单状态
        order.setState(orderState);


        //购物车信息删除，库存也需要扣除
        for (int i = 0; i < bookid.size(); i++) {
            bookMapper.updateBookRemainder(bookid.get(i), booknum.get(i));
            shopcartMapper.delectShopcartByUseridAndBookid(userid, bookid.get(i));
        }
        //添加订单信息


        //封装orderContentList
        order.setNumber(number);
        order.setTotalPrice(totalPrice);
        order.setIsread(false);
        orderMapper.addOrder(order);

        List<OrderContent> orderContentList = new ArrayList<>();
        for (int i = 0; i < bookid.size(); i++) {
            OrderContent orderContent = new OrderContent();
            //获取每本书的单价
            String price = bookMapper.getPriceByBookId(bookid.get(i));
            //将单价和数量每条订单信息封装起来
            orderContent.setPrice(Float.valueOf(price));
            orderContent.setNumber(booknum.get(i));
            Book book = new Book();
            book.setId(bookid.get(i));
            orderContent.setBook(book);

            Order order1 = new Order();
            order1.setId(now.getTime());
            orderContent.setOrder(order1);
            orderContentList.add(orderContent);
        }
        order.setOrderContents(orderContentList);
        System.out.println(order);
        orderMapper.addOrderContent(orderContentList);


        return now.getTime();
    }
}
