package cn.heikaqiu.booktt.controller;

import cn.heikaqiu.booktt.bean.Order;
import cn.heikaqiu.booktt.bean.User;
import cn.heikaqiu.booktt.service.OrderService;
import com.sun.javafx.collections.MappingChange;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author HeiKaQiu
 * @create 2020-02-06 13:47
 */
@Controller
public class OrderController {

    @Autowired
    private OrderService orderService;


    @DeleteMapping("/deleteOrder/{orderid}")
    @ResponseBody
    public Map<String, String> deleteOrder(@PathVariable("orderid") String orderid) {
        Map<String, String> map = new HashMap<>();
        Long order_id = Long.valueOf(orderid);
        boolean isDelete = orderService.deleteOrder(order_id);
        if (isDelete) {
            map.put("message", "删除成功");
        } else {
            map.put("message", "删除失败");
        }
        return map;

    }

    @PostMapping("/orderInfo")
    public String orderInfo(String orderid, String userid, Model model) {

        Long order_id = Long.valueOf(orderid);
        Integer user_id = Integer.valueOf(userid);

        //首先更改订单状态
        orderService.updateOrderStateIfOutTime(user_id);


        Order order = orderService.getOrderInfo(order_id, user_id);

        if (order != null) {
            //找到了
            model.addAttribute("order", order);
            System.out.println(order);
        } else {
            //没找到
            model.addAttribute("message", "查找订单失败");
        }
        return "OrderInfo";

    }


    @PostMapping("/toBuyOrder")
    @ResponseBody
    public Map<String, String> toBuyOrder(Long order_id,String paypassword,Integer order_user_id) {
        Map<String, String> map = new HashMap<>();
        Integer is_buy = orderService.toBuyOrder(order_id, order_user_id, paypassword);
        if (is_buy == 1) {
            //如果购买成功

            map.put("message", "付款成功，等待卖家发货");

        } else if (is_buy == 2) {
            //购买失败余额不足
            map.put("message", "余额不足");
        } else if (is_buy == 3) {
            map.put("message", "错误过多，关闭订单");
        } else if (is_buy == 4) {
            map.put("message", "超过订单最后支付时间，订单已关闭");
        }
        return map;

    }

    @PostMapping("/closeOrder")
    @ResponseBody
    public Map<String, String> closeOrder(Long order_id) {

        Map<String, String> map = new HashMap<>();

        System.out.println("order:" + order_id);

        boolean is_close = orderService.closeOrder(order_id);
        if (is_close) {
            //如果购买成功
            map.put("message", "错误次数过多已关闭订单");

        } else {
            map.put("message", "关闭订单失败");
        }
        return map;

    }


}
