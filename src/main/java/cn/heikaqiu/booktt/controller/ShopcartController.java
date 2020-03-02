package cn.heikaqiu.booktt.controller;

import cn.heikaqiu.booktt.service.ShopcartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public Map<String, String> deleteShopcart(@PathVariable("id") Integer id) {
        Map<String, String> map = new HashMap<>();
        boolean isDelect = shopcartService.delectShopcart(id);
        if (isDelect) {
            map.put("message", "删除成功");
        } else {
            map.put("message", "删除失败");
        }
        return map;

    }


    @PostMapping("/toBuyList")
    @ResponseBody
    public Map<String, String> toBuyList(@RequestParam(value = "bookidList[]") String[] bookidList,
                                         @RequestParam(value = "booknumList[]") String[] booknumList,
                                         Integer userid, Float totalPrice) {
        Map<String, String> map = new HashMap<>();
        System.out.println("bookidList:" + bookidList.length);
        System.out.println("booknumList:" + booknumList.length);
        System.out.println("userid:" + userid);
        System.out.println("totalPrice:" + totalPrice);
        List<Integer> bookid = new ArrayList<>();
        List<Integer> booknum = new ArrayList<>();

        for (String id : bookidList) {
            bookid.add(Integer.valueOf(id));
        }
        for (String num : booknumList) {
            booknum.add(Integer.valueOf(num));
        }
        Long is_buy = shopcartService.addOrder(bookid, booknum, userid, totalPrice, 1);
        if (is_buy > 0L) {
            //如果添加订单成功
            map.put("message", "添加订单成功");
            map.put("orderid", is_buy.toString());
        } else if (is_buy == 0L) {
            //购买失败库存不足
            map.put("message", "库存不足");
        }

        return map;

    }
}
