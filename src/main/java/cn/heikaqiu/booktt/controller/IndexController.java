package cn.heikaqiu.booktt.controller;

import cn.heikaqiu.booktt.bean.*;
import cn.heikaqiu.booktt.config.OtherConfig;
import cn.heikaqiu.booktt.config.WxpayConfig;
import cn.heikaqiu.booktt.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * @author HeiKaQiu
 * @create 2020-01-29 18:43
 */
@Controller
public class IndexController {

    @Autowired
    private HttpSession session;

    @Autowired
    private BookTypeService bookTypeService;
    @Autowired
    private BookService bookService;
    @Autowired
    private ShopcartService shopcartService;
    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @Autowired
    private CollectionService collectionService;

    @Autowired
    private OtherConfig otherConfig;

    @Autowired
    private WxpayConfig wxpayConfig;


    /**
     * 管理员首页
     */
    @RequestMapping("/admin")
    public String admin() {
        return "redirect:/admin/";
    }


    /**
     * 首页
     *
     * @return
     */
    @RequestMapping("/")
    public String index(Model model) {


        //获取所有书本的类型
        List<BookType> bookTypes = bookTypeService.getAllType();
        model.addAttribute("bookTypes", bookTypes);
        session.setAttribute("page", "/");

        return "Index";
    }

    /**
     * 我的订单页面
     *
     * @return
     */
    @RequestMapping("/order.html")
    public String order(Model model) {
        session.setAttribute("page", "order");


        User login_user = (User) session.getAttribute("login_user");


        if (login_user == null) {
            model.addAttribute("message", "请登录");
        } else {

            //首先更改订单状态
            orderService.updateOrderStateIfOutTime(login_user.getId());

            List<Order> orderList = orderService.getAllOrderByUserId(login_user.getId());
            model.addAttribute("orderList", orderList);
        }

        return "Order";
    }

    /**
     * 订单详情
     *
     * @return
     */
    @RequestMapping("/orderInfo.html")
    public String orderInfo() {
        session.setAttribute("page", "orderInfo");
        return "OrderInfo";
    }

    /**
     * 个人中心
     *
     * @return
     */
    @RequestMapping("/userInfo.html")
    public String userInfo(Integer userid, Model model) {
        if (userid != null) {
            System.out.println(userid);
        }else{
            //如果没传进来 默认是用session中的
            userid = ((User) session.getAttribute("login_user")).getId();
        }


        System.out.println(userid);
        User user = userService.getUserById(userid);
        System.out.println("user:" + user);
        session.setAttribute("page", "userInfo");
        model.addAttribute("user", user);
        return "UserInfo";

    }

    /**
     * 友情链接
     *
     * @return
     */
    @RequestMapping("/friendLink.html")
    public String friendLink() {
        session.setAttribute("page", "friendLink");
        return "FriendLink";
    }

    /**
     * 建议
     *
     * @return
     */
    @RequestMapping("/advice.html")
    public String advice() {
        session.setAttribute("page", "advice");
        return "Advice";
    }

    /**
     * 购物车页面
     *
     * @return
     */
    @RequestMapping("/cart.html")
    public String cart(Model model) {
        session.setAttribute("page", "cart");
        List<Shopcart> shopcartList = new ArrayList<>();

        User login_user = (User) session.getAttribute("login_user");
        if (login_user != null) {
            //用户已经登录
            shopcartList = shopcartService.getShopcartByUserId(login_user.getId());
            for (Shopcart shopcart : shopcartList) {
                System.out.println(shopcart);
            }
            model.addAttribute("shopcartList", shopcartList);
        } else {
            //用户没登陆
            model.addAttribute("message", "未登录，无购物车信息");
        }

        return "Cart";
    }

    /**
     * 转到登录页面
     *
     * @return
     */
    @RequestMapping("/login.html")
    public String login() {
        session.setAttribute("page", "login");
        return "Login";
    }


    /**
     * 注册页面
     *
     * @return
     */
    @RequestMapping("/register.html")
    public String register() {
        session.setAttribute("page", "register");
        return "Register";
    }

    /**
     * 书本信息页面
     *
     * @return
     */
    @RequestMapping("/bookInfo.html")
    public String bookInfo(Model model) {

        session.setAttribute("page", "bookInfo");
        Book book = bookService.getBookInfoById(1);
        model.addAttribute("book", book);


        return "BookInfo";

    }

    /**
     * 去选择支付方式页面
     */
    @RequestMapping("/toCheckPay/{orderid}")
    public String toCheckPay(Model model, @PathVariable("orderid") String orderid) {
        session.setAttribute("page", "order");
        Order order = orderService.getOrderInfoByOrderId(Long.valueOf(orderid));
        model.addAttribute("order", order);
        return "ToCheckPay";

    }

    /**
     * 去微信支付方式页面
     */
    @RequestMapping("/goWxPay/{orderid}")
    public String goWxPay(Model model, @PathVariable("orderid") String orderid, HttpServletResponse response) {

        session.setAttribute("page", "order");
        Order order = orderService.getOrderInfoByOrderId(Long.valueOf(orderid));
//        String WxPayImg=(String)session.getAttribute("WxPayImg"+orderid);

//        if(WxPayImg!=null){
//            //代表了不是第一次提交这个订单了  有可能是恶意刷新 所以不用再创建图片了
//        }else{


//            //为空代表第一次提交  创建图片
//            JSONObject jsonObject = new JSONObject();
//
//            //2.给寄送Object对象中存放数据
//            jsonObject.put("company","http://booktt.heikaqiu.cn");
//            jsonObject.put("companyName","booktt");
//            jsonObject.put("author","heikaqiu");
//            jsonObject.put("address","杭州");
//            jsonObject.put("orderid",orderid);
//            WxPayImg = wxpayConfig.generateQRCode(jsonObject);
//            //第一次 为了这个订单 将图片路径放到 session中
//            session.setAttribute("WxPayImg"+orderid,WxPayImg);
//        }


        model.addAttribute("order", order);
//        model.addAttribute("QRCode", WxPayImg);
        return "WxPay";

    }


}
