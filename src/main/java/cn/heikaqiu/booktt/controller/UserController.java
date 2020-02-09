package cn.heikaqiu.booktt.controller;

import cn.heikaqiu.booktt.bean.Book;
import cn.heikaqiu.booktt.bean.Order;
import cn.heikaqiu.booktt.bean.User;
import cn.heikaqiu.booktt.config.OtherConfig;
import cn.heikaqiu.booktt.mapper.UserMapper;
import cn.heikaqiu.booktt.service.BookService;
import cn.heikaqiu.booktt.service.ShopcartService;
import cn.heikaqiu.booktt.service.UserService;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.*;


@Controller
public class UserController {


    @Autowired
    private UserService userService;

    @Autowired
    private BookService bookService;

    @Autowired
    private HttpSession session;

    @Autowired
    private OtherConfig otherConfig;

    @Autowired
    private ShopcartService shopcartService;

    /**
     * 登录
     *
     * @param user
     * @param model
     * @return
     */
    @PostMapping(value = "/login")
    public String login(User user, Model model) {

        if (userService.login(user)) {
            //登录成功
            return "redirect:/";
        } else {
            //登录失败
            // session.setAttribute("error","登录失败，用户名或者密码错误");
            model.addAttribute("error", "登录失败，用户名或者密码错误");
            session.setAttribute("page", "login");
            return "Login";
        }
    }

    /**
     * 注册
     *
     * @param user
     * @param model
     * @return
     */
    @PostMapping("/register")
    public String register(User user, Model model) {
        //将其他数据封装进user对象中
        String province = otherConfig.getProvince(Integer.valueOf(user.getProvince()));
        String city = otherConfig.getCity(Integer.valueOf(user.getProvince()), Integer.valueOf(user.getCity()));
        user.setProvince(province);
        user.setCity(city);
        user.setIsadmin(false);
        user.setBalance(0.0f);
        user.setTime(new Date());
        boolean successRegister = userService.register(user);
        if (successRegister) {
            //注册成功
            return "redirect:/";
        } else {
            model.addAttribute("error", "用户名重复");
            return "Register";
        }

    }


    /**
     * 购买一种书
     *
     * @return
     */
    @PostMapping("/buyBook")
    @ResponseBody
    public Map<String, String> buyBook(@RequestParam("book_id") String book_id,
                                       @RequestParam("buy_num") String buy_num,
                                       @RequestParam("user_id") String user_id,
                                       Integer orderState,
                                       String paypassword_pass,
                                       Float totalPrice) {
        Map<String, String> map = new HashMap<>();

        List<Integer> bookid = new ArrayList<>();
        bookid.add(Integer.valueOf(book_id));

        List<Integer> booknum = new ArrayList<>();
        booknum.add(Integer.valueOf(buy_num));

        Integer userid = Integer.valueOf(user_id);


        Integer isBuyBook = shopcartService.toBuyList(bookid, booknum, userid, totalPrice, paypassword_pass, orderState);
        if (isBuyBook == 1) {
            //如果购买成功
            if (orderState == Order.State.WAIT_PAYMENT.getValue())
                map.put("message", "付款密码错误三次，请三十分钟内完成支付");
            else if (orderState == Order.State.WAIT_DELIVER_GOODS.getValue())
                map.put("message", "付款成功，等待卖家发货");
        } else if (isBuyBook == 2) {
            //购买失败余额不足
            map.put("message", "余额不足");
        } else if (isBuyBook == 3) {
            //购买失败库存不足
            map.put("message", "库存不足");
        } else if (isBuyBook == 4) {
            map.put("message", "支付密码错误");
        }

        return map;
    }

    /**
     * 将书添加到购物车
     *
     * @return
     */
    @PostMapping("/andToCart")
    @ResponseBody
    public Map<String, String> andToCart(@RequestParam("book_id") String book_id,
                                         @RequestParam("buy_num") String buy_num,
                                         @RequestParam("user_id") String user_id) {
        Map<String, String> isAndCart = userService.andToCart(user_id, book_id, buy_num);
        return isAndCart;
    }


    /**
     * 退出登录
     *
     * @return
     */
    @PostMapping("/exitLogin")
    @ResponseBody
    public Map<String, String> exitLogin(Integer userid) {
        Map<String, String> maps = new HashMap<>();

        boolean isExit = userService.exitLogin(userid);
        if (isExit) {
            //如果退出登录
            maps.put("message", "退出登录成功");
        } else {
            maps.put("message", "退出登录失败");
        }
        return maps;
    }

    /**
     *修改用户的基本信息
     */
    @PostMapping("/updateUserInformation")
    public String updateUserInformation(User user , Model model) {
        String province = otherConfig.getProvince(Integer.valueOf(user.getProvince()));
        String city = otherConfig.getCity(Integer.valueOf(user.getProvince()), Integer.valueOf(user.getCity()));
        user.setProvince(province);
        user.setCity(city);
        System.out.println(user);
        boolean isupdate = false;
        try {
            isupdate = userService.updateUserInformation(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (isupdate) {
            //修改用户成功
            return "redirect:/userInfo.html";
        } else {
            //转到错误页面
            model.addAttribute("error_message","修改用户失败");
            return "error";
        }
    }


/**
 *修改用户的密码
 */
    @PostMapping("/updatePassword")
    public String updatePassword(Integer userid,String old_password,String password,String paypassword,Model model) {

        boolean isupdate = false;
        try {
            isupdate = userService.updateUserPassword(userid,old_password,password,paypassword);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (isupdate) {
            //修改用户成功
            return "redirect:/userInfo.html";
        } else {
            //转到错误页面
            model.addAttribute("error_message","修改用户失败");
            return "error";
        }
    }

}
