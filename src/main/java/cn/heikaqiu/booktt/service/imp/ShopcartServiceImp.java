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
        Integer num=shopcartMapper.delectShopcartById(shopcart_id);
        if(num==1){
            return true;
        }else{
            return false;

        }
    }

    @Override
    public Integer toBuyList(List<Integer> bookid, List<Integer> booknum, Integer userid, Float totalPrice, String paypassword_pass,Integer orderState) {

        Integer number=0;//订单的总本书
        //封装order
        Order order=new Order();
        Date now=new Date();
        order.setId(now.getTime());
        order.setSubmitTime(now);


        User user = new User();
        user.setId(userid);
        order.setUser(user);

        //查书的库存
        for(int i=0;i<bookid.size();i++ ){

            Integer remainderByBookId = bookMapper.getRemainderByBookId(bookid.get(i));
            number+=booknum.get(i);
            if(remainderByBookId<booknum.get(i)){
                return 3;
            }
        }

        //当订单状态为1时支付密码时不需要的
        if(orderState== Order.State.WAIT_PAYMENT.getValue()){
            //等待买家付款：需要将订单信息存储，账户余额不用扣，购物车信息删除，库存也需要扣除，如果30分钟后未付款 则库存恢复
            order.setState(Order.State.WAIT_PAYMENT.getValue());
            //最后付款时间30分钟后
            order.setPaymentaTime(new Date(now.getTime()+(1000*60*30)));

        }else if(orderState==Order.State.WAIT_DELIVER_GOODS.getValue()){
            //等待卖家发货：需要将订单信息存储，账户余额要扣，购物车信息删除，库存也需要扣除
            //付款时间为当前时间
            order.setPaymentaTime(now);
            String Paypassword =userMapper.getPaypasswordByUserid(userid);
            if(!Paypassword.equals(paypassword_pass)){
                //支付密码错了
                return 4;
            }
            //先查用户的账户余额
            String balanceByUserId = userMapper.getBalanceByUserId(userid);
            if(Float.valueOf(balanceByUserId)<totalPrice){
                return 2;
            }
            //实现购买
            userMapper.updateUserBalance(userid,totalPrice);//更改用户余额
            order.setState(Order.State.WAIT_DELIVER_GOODS.getValue());
        }

        //购物车信息删除，库存也需要扣除
        for(int i=0;i<bookid.size();i++ ){
            bookMapper.updateBookRemainder(bookid.get(i),booknum.get(i));
            shopcartMapper.delectShopcartByUseridAndBookid(userid,bookid.get(i));
        }
        //添加订单信息


//        private long id;
//        //提交订单时间
//        private Date submitTime;
//        //提交订单时  这是 最后付款时间
//        private Date paymentaTime;
//        //完成订单时间
//        private Date finishTime;
//        //状态
//        private Order.State state;
//        private User user;
//        //此份订单有哪些信息
//        private List<OrderContent> orderContents=new ArrayList<OrderContent>();

//        private Integer id;
//        // 单价 因为这是购买时的价格 价格是会变的
//        private Float price;
//        // 买了几本 可以有多本 数量跟单价对应
//        private Integer number;
//        //哪本书
//        private Book book;
//        //哪份订单的
//        private Order order;

        //封装orderContentList
        order.setNumber(number);
        order.setTotalPrice(totalPrice);
       orderMapper.addOrder(order);

        List<OrderContent> orderContentList=new ArrayList<>();
        for(int i=0;i<bookid.size();i++ ){
            OrderContent orderContent=new OrderContent();
            String price= bookMapper.getPriceByBookId(bookid.get(i));
            orderContent.setPrice(Float.valueOf(price));

            orderContent.setNumber(booknum.get(i));

            Book book=new Book();
            book.setId(bookid.get(i));
            orderContent.setBook(book);

            Order order1=new Order();
            order1.setId(now.getTime());
            orderContent.setOrder(order1);
            orderContentList.add(orderContent);
        }
        order.setOrderContents(orderContentList);
        System.out.println(order);
        orderMapper.addOrderContent(orderContentList);



        return 1;
    }
}
