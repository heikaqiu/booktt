package cn.heikaqiu.booktt.service.imp;

import cn.heikaqiu.booktt.bean.Order;
import cn.heikaqiu.booktt.bean.OrderContent;
import cn.heikaqiu.booktt.bean.User;
import cn.heikaqiu.booktt.mapper.BookMapper;
import cn.heikaqiu.booktt.mapper.OrderMapper;
import cn.heikaqiu.booktt.mapper.UserMapper;
import cn.heikaqiu.booktt.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author HeiKaQiu
 * @create 2020-02-06 13:46
 */
@Service
public class OrderServiceImp implements OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private BookMapper bookMapper;

    @Autowired
    private UserMapper userMapper;

    @Override
    public List<Order> getAllOrderByUserId(Integer id) {

        return orderMapper.getAllOrderByUserId(id);
    }

    @Override
    public boolean deleteOrder(Long orderid) {

        try {
            //级联关系  先删除订单详情
            orderMapper.deleteOrderContent(orderid);

            //再删除订单
            orderMapper.deleteOrder(orderid);
            return true;
        }catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }

    }

    @Override
    public Order getOrderInfo(Long order_id, Integer user_id) {
        return orderMapper.getOrderInfo(order_id,user_id);
    }


    @Override
    public void updateOrderStateIfOutTime(Integer userId) {
        //获取所有为未付款的订单
        List<Order> outTimeOrder=orderMapper.getAllOrderByState(Order.State.WAIT_PAYMENT.getValue());
        for (int i = 0; i < outTimeOrder.size(); i++) {
            Order order = outTimeOrder.get(i);
            if(order.getPaymentaTime().getTime()<new Date().getTime()){
                //超时了
                //将订单的状态改为关闭 还需要把书的库存还给他
                List<OrderContent> allOrderContent = orderMapper.getAllOrderContentByOrderid(order.getId());

                for (int j = 0; j <allOrderContent.size() ; j++) {
                    bookMapper.updateBookRemainder(allOrderContent.get(j).getBook().getId(),allOrderContent.get(j).getNumber()*(-1));
                }
                orderMapper.updateOrderState(order.getId(),Order.State.CLOSE.getValue());

            }
        }



    }

    @Override
    public Integer toBuyOrder(Long order_id, Integer order_user_id, String paypassword_pass) {
        Order order = orderMapper.getOrderInfo(order_id, order_user_id);
        User user=order.getUser();
        System.out.println("haha"+order);
        if(!paypassword_pass.equals(user.getPaypassword())){
            //支付密码错误
            return 3;
        }
        if(order.getTotalPrice()>user.getBalance()){
            //余额不足
            return 2;
        }
        if(order.getPaymentaTime().getTime()<new Date().getTime()){
            //支付超时
            return 4;
        }
        //实现购买
        //将用户的余额扣除
        userMapper.updateUserBalance(user.getId(),order.getTotalPrice());
        //将订单的状态更改
        orderMapper.updateOrderState(order.getId(), Order.State.WAIT_DELIVER_GOODS.getValue());
        return 1;
    }

    @Override
    public boolean closeOrder(Long order_id) {
        try{
            orderMapper.updateOrderState(order_id,Order.State.CLOSE.getValue());
            return true;
        }catch (Exception e){
            e.getMessage();
            return false;
        }

    }
}
