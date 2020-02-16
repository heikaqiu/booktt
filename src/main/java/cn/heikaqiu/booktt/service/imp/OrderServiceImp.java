package cn.heikaqiu.booktt.service.imp;

import cn.heikaqiu.booktt.bean.FindOrderByInformation;
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
    public Order getOrderInfoByOrderId(Long order_id) {
        return orderMapper.getOrderInfoByOrderId(order_id);
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
                orderMapper.updateOrderState(order.getId(),Order.State.CLOSE.getValue(),null);

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
        //将订单的状态更改  并将最后付款时间更改为现在
        orderMapper.updateOrderState(order.getId(), Order.State.WAIT_DELIVER_GOODS.getValue(),new Date());
        return 1;
    }

    @Override
    public boolean closeOrder(Long order_id) {
        try{
            orderMapper.updateOrderState(order_id,Order.State.CLOSE.getValue(),null);
            return true;
        }catch (Exception e){
            e.getMessage();
            return false;
        }

    }

    @Override
    public Long getAllCountOrder() {
        Long AllCountOrder=orderMapper.getAllCountOrder();
        if(AllCountOrder==null||AllCountOrder<0L){
            AllCountOrder= 0L;
        }
        return AllCountOrder;
    }

    @Override
    public Long getAnydayCountOrder(Long start_time, Long last_time) {
        Long AnydayCountOrder=0L;
        if(start_time==null||last_time==null){
            //有一个为空
            AnydayCountOrder = -1L;
            return AnydayCountOrder;
        }
        if(last_time>start_time){
            //后面的时间大于前面的时间
            AnydayCountOrder=orderMapper.getAnydayCountOrder(new Date(start_time),new Date(last_time));

            if(AnydayCountOrder==null||AnydayCountOrder<0L){
                AnydayCountOrder=0L;
            }
            return AnydayCountOrder;
        }else {
            //前者大于后者  显然是不对的  -1 表示错误
            AnydayCountOrder=-1L;
            return AnydayCountOrder;
        }



    }

    @Override
    public List<Order> getOrderInfoLimit(Integer state_num, Integer page_num, FindOrderByInformation orderByInformation) {
        if(state_num<0){
            state_num=0;
        }
        return orderMapper.getOrderInfoLimit(state_num,page_num,orderByInformation);
    }

    @Override
    public Long getOrderByInformationNum(FindOrderByInformation orderByInformation) {
        return orderMapper.getOrderByInformationNum(orderByInformation);
    }

    @Override
    public Long getCountByOrderState(Order.State state) {

        return orderMapper.getCountByOrderState(state.getValue());

    }

    @Override
    public Integer toDeliverGoods(String orderid, String expressNumber) {
        if(expressNumber==null||expressNumber.equals("")){
            return 1;//快递号为空
        }
        if(orderid==null||orderid.equals("")){
            return  2;//订单号为空
        }
        //查找快递单号是否存在了
        Integer ExpressNumber=orderMapper.findExpressNumber(expressNumber);
        if(ExpressNumber>0){
            return  3;//快递号已经被使用了
        }else {
            //查找订单是否有快递单号了
            String ExpressNumberByorderid=orderMapper.findExpressNumberByorderid(orderid);
            if(ExpressNumberByorderid==null||ExpressNumberByorderid.equals("")){
                orderMapper.toDeliverGoods( orderid, expressNumber);
                //改变订单状态
                orderMapper.updateOrderState(Long.valueOf(orderid), Order.State.DELIVER_GOODS.getValue(),null);
                return  5;// 快递号添加成功
            }
            else{
                return 4;//订单已经有快递号了
            }

        }

    }

}
