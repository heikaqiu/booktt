package cn.heikaqiu.booktt.controller;

import cn.heikaqiu.booktt.bean.Order;
import cn.heikaqiu.booktt.service.ShopcartService;
import com.sun.deploy.net.HttpRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * @author HeiKaQiu
 * @create 2020-02-05 12:43
 */
@Controller
public class ShopcartController {

    @Autowired
    private ShopcartService shopcartService;

    @DeleteMapping("/deleteShopcart/{id}")
    @ResponseBody
    public Map<String,String> deleteShopcart(@PathVariable("id") Integer id){
        Map<String,String> map=new HashMap<>();
        boolean isDelect=shopcartService.delectShopcart(id);
        if(isDelect){
            map.put("message","删除成功");
        }
        else{
            map.put("message","删除失败");
        }
        return map;

    }


    @PostMapping("/toBuyList")
    @ResponseBody
    public Map<String,String> toBuyList(@RequestParam(value="bookidList[]") String[] bookidList,
                                        @RequestParam(value="booknumList[]") String[] booknumList,
                                        Integer userid,Float totalPrice,String paypassword_pass,Integer orderState){
        Map<String,String> map=new HashMap<>();
        System.out.println("bookidList:"+bookidList.length);
        System.out.println("booknumList:"+booknumList.length);
        System.out.println("userid:"+userid);
        System.out.println("totalPrice:"+totalPrice);
        List<Integer> bookid=new ArrayList<>();
        List<Integer> booknum=new ArrayList<>();

        for(String id:bookidList){
            bookid.add(Integer.valueOf(id));
        }
        for(String num:booknumList){
            booknum.add(Integer.valueOf(num));
        }
        Integer is_buy=shopcartService.toBuyList(bookid,booknum,userid,totalPrice,paypassword_pass,orderState);
        if(is_buy==1){
            //如果购买成功
            if(orderState== Order.State.WAIT_PAYMENT.getValue())
            map.put("message","付款密码错误三次，请三十分钟内完成支付");
            else if(orderState== Order.State.WAIT_DELIVER_GOODS.getValue())
                map.put("message","付款成功，等待卖家发货");
        }else if(is_buy==2){
            //购买失败余额不足
            map.put("message","余额不足");
        }else if(is_buy==3){
            //购买失败库存不足
            map.put("message","库存不足");
        }else if(is_buy==4){
            map.put("message","支付密码错误");
        }

        return map;

    }
}
