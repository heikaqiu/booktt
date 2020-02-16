package cn.heikaqiu.booktt.controller;

import cn.heikaqiu.booktt.bean.FindOrderByInformation;
import cn.heikaqiu.booktt.bean.Order;
import cn.heikaqiu.booktt.bean.User;
import cn.heikaqiu.booktt.service.OrderService;
import cn.heikaqiu.booktt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author HeiKaQiu
 * @create 2020-02-13 21:37
 */
@Controller
@RequestMapping("/admin")
public class AdminOrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @Autowired
    private HttpSession session;


    /**
     * 按提交订单条件的时候 将条件封装到FindOrderByInformation 并加到session 中
     *
     * @param findOrderByInformation
     * @param user_username
     * @param order_time
     * @return
     */
    @PostMapping("/orderInformation")
    @ResponseBody
    public Map<String, Object> orderInformation(FindOrderByInformation findOrderByInformation,
                                                String user_username, String order_time) {
        Map<String, Object> maps = new HashMap<>();
        if (user_username != null && !user_username.equals("")) {
            User userByusername = userService.findUserByusername(user_username);
            if (userByusername == null) {
                //如果用户是空的 那么
                maps.put("message", "用户不存在");
                return maps;
            } else {
                findOrderByInformation.setUser(userByusername);
            }
        }
        if (order_time != null && !order_time.equals("")) {

            System.out.println(order_time);

            String s1 = order_time.replace("/", "-");
            //order_time.replace(" ",""); //去除所有空格，包括首尾、中间
            String[] s = s1.split(" - ");//从-分隔开字符串

            s[0] += " 00:00:00";
            s[1] += " 24:00:00";
            System.out.println("s[0]" + s[0]);
            System.out.println("s[1]" + s[1]);

            //String str = "2019-03-13 13:54:00";
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = null;
            Date date1 = null;
            try {
                date = simpleDateFormat.parse(s[0]);
                date1 = simpleDateFormat.parse(s[1]);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            //TODO 错误
            findOrderByInformation.setFirst_time(date);
            findOrderByInformation.setLast_time(date1);
        }
        findOrderByInformation.setState(2);//当刚提交的时候 搜索的是状态为2的
        session.setAttribute("findOrderByInformation", findOrderByInformation);
        maps.put("message", "搜索成功");
        return maps;
    }


    /**
     * 获取 分页第几条 和 订单状态
     * 只有在点击订单状态的时候才更改  也就是说其他时候都是在 找session中 条件对象中的订单状态
     * TODO order_State 什么时候这个为null
     *
     * @param pageNum
     * @param order_State
     * @return
     */
    @PostMapping("/toPageOrder/{pageNum}")
    @ResponseBody
    public Map<String, Object> toPageOrder(@PathVariable("pageNum") Integer pageNum, Integer order_State) {

//        Long order_id = (Long) session.getAttribute("order_id");
//        String user_username = (String) session.getAttribute("user_username");
//        String order_time = (String) session.getAttribute("order_time");
//        Integer first_num_number = (Integer) session.getAttribute("first_num_number");
//        Integer last_num_number = (Integer) session.getAttribute("last_num_number");
//        Float first_price_number = (Float) session.getAttribute("first_price_number");
//        Float last_price_number = (Float) session.getAttribute("last_price_number");
//        Integer order_State = (Integer) session.getAttribute("order_State");
        //获取到查找订单信息的
        Map<String, Object> maps = new HashMap<>();

        System.out.println(pageNum);
        FindOrderByInformation findOrderByInformation = (FindOrderByInformation) session.getAttribute("findOrderByInformation");
        //如果订单状态不为空代表要更改状态了
        if (order_State != null) {
            findOrderByInformation.setState(order_State);
        }
        List<Order> orderList = new ArrayList<>();//通过订单的信息 查找的订单
        Long orderByInformationNum = null;//获取有查找信息的总数
        //有订单信息要求
        System.out.println("findOrderByInformation" + findOrderByInformation);
        //获取分页订单
        orderList = orderService.getOrderInfoLimit(pageNum, 5, findOrderByInformation);
        orderByInformationNum = orderService.getOrderByInformationNum(findOrderByInformation);

        maps.put("orderList", orderList);
        maps.put("orderByInformationNum", orderByInformationNum);

        return maps;
    }


    @GetMapping("/orderInfo.html/{order_id}")
    public String orderInfo(@PathVariable("order_id") Long orderId, Model model) {

        Order order = orderService.getOrderInfoByOrderId(orderId);
        if (order != null) {
            model.addAttribute("order", order);
        } else {
            model.addAttribute("order", "未找到订单可能是订单id错误");
        }
        return "/admin/OrderInformation";


    }

    @PostMapping("/toDeliverGoods")
    @ResponseBody
    public Map<String, Object> toDeliverGoods(String expressNumber,
                                              String orderid) {
        Map<String, Object> map = new HashMap<>();
        Integer toDeliverGoods = orderService.toDeliverGoods(orderid, expressNumber);

        if(toDeliverGoods==1){
            map.put("message","快递号为空");
        }else if(toDeliverGoods==2){
            map.put("message","订单号为空");
        }else if(toDeliverGoods==3){
            map.put("message","快递号已经被使用了");
        }else if(toDeliverGoods==4){
            map.put("message","订单已经有快递号了");
        }else if(toDeliverGoods==5){
            map.put("message","快递号添加成功");
        }
//        1//快递号为空
//        2//订单号为空
//        3//快递号已经被使用了
//        4//订单已经有快递号了
//        5// 快递号添加成功

        return map;


    }


}
