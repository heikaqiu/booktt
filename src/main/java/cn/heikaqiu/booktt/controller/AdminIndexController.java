package cn.heikaqiu.booktt.controller;

import cn.heikaqiu.booktt.bean.FindOrderByInformation;
import cn.heikaqiu.booktt.bean.Order;
import cn.heikaqiu.booktt.bean.OrderContent;
import cn.heikaqiu.booktt.bean.User;
import cn.heikaqiu.booktt.config.OtherConfig;
import cn.heikaqiu.booktt.service.OrderService;
import cn.heikaqiu.booktt.service.UserService;
import com.sun.jmx.snmp.Timestamp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import javax.xml.crypto.Data;
import java.util.*;

/**
 * @author HeiKaQiu
 * @create 2020-02-10 16:15
 */
@Controller
@RequestMapping("/admin")
public class AdminIndexController {

    @Autowired
    private HttpSession session;

    @Autowired
    private UserService userService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private OtherConfig otherConfig;


    //后台首页
    @RequestMapping(value = {"/"})
    public String index() {


        //测试使用的登录 TODO
        otherConfig.testLogin();


        User user = (User) session.getAttribute("login_user");
        if (user == null) {
            //没有登录的人 则转到后台登录界面
            return "/admin/login";
        } else {
            if (user.isIsadmin()) {
                //是管理员 到后台首页
                return "/admin/index";
            } else {
                //不是管理员 转到前台首页
                return "redirect:/";
            }
        }


    }

    /**
     * 到后台登录页面
     *
     * @return
     */
    @RequestMapping("/login.html")
    public String login() {
        return "/admin/login";
    }


    /**
     * 到订单统计页面
     *
     * @return
     */
    @RequestMapping("/allOrder.html")
    public String allOrder(Model model) {

        //测试使用的登录 TODO
        otherConfig.testLogin();


        Map<String, Long> time = otherConfig.getTime();

        //获取订单总数
        Long allOrderNum = orderService.getAllCountOrder();
        //获取昨日订单数
        Long yesterdayOrderNum = orderService.getAnydayCountOrder(time.get("yesterday_zero"), time.get("today_zero")); //从今天的0开始到明天0
        //获取今天订单数
        Long todayOrderNum = orderService.getAnydayCountOrder(time.get("today_zero"), time.get("tomorrow_zero"));


        //封装需要查找的订单的信息
        packagingOrderInfo(model);

        if (yesterdayOrderNum == 0L) {
            //底数为0
            model.addAttribute("growthDifference", "底数为0");
        } else {
            Long growthDifference = (todayOrderNum - yesterdayOrderNum) / yesterdayOrderNum;
            model.addAttribute("growthDifference", growthDifference);
        }


        System.out.println(allOrderNum);
        System.out.println(yesterdayOrderNum);
        System.out.println(todayOrderNum);

        //所有订单总数
        model.addAttribute("allOrderNum", allOrderNum);
        //昨天订单数
        model.addAttribute("yesterdayOrderNum", yesterdayOrderNum);
        //今日订单数
        model.addAttribute("todayOrderNum", todayOrderNum);


        return "admin/AllOrder";
    }


    /**
     * 到订单管理页面
     *
     * @return
     */
    @RequestMapping("/allOrderManage.html")
    public String allOrderManage(Model model) {

        //测试使用的登录 TODO
        otherConfig.testLogin();

        //封装需要查找的订单的信息
        packagingOrderInfo(model);

        Long WAIT_PAYMENT = orderService.getCountByOrderState(Order.State.WAIT_PAYMENT);
        Long WAIT_DELIVER_GOODS = orderService.getCountByOrderState(Order.State.WAIT_DELIVER_GOODS);
        Long DELIVER_GOODS = orderService.getCountByOrderState(Order.State.DELIVER_GOODS);
        Long FINISH_ORDER = orderService.getCountByOrderState(Order.State.FINISH_ORDER);
        Long CLOSE = orderService.getCountByOrderState(Order.State.CLOSE);

        model.addAttribute("WAIT_PAYMENT", WAIT_PAYMENT);
        model.addAttribute("WAIT_DELIVER_GOODS", WAIT_DELIVER_GOODS);
        model.addAttribute("DELIVER_GOODS", DELIVER_GOODS);
        model.addAttribute("FINISH_ORDER", FINISH_ORDER);
        model.addAttribute("CLOSE", CLOSE);
        return "/admin/AllOrderManage";
    }

    /**
     * 封装订单状态为2的 订单信息bean 放入session中 并查找出orderList与orderByInformationNum 放入model中
     * @param model
     */
    private void packagingOrderInfo(Model model){
        FindOrderByInformation orderByInformation = new FindOrderByInformation();
        orderByInformation.setState(Order.State.WAIT_DELIVER_GOODS.getValue());
        session.setAttribute("findOrderByInformation", orderByInformation);

        //获取分页订单   从等待卖家发货 分0到5个
        List<Order> orderList = orderService.getOrderInfoLimit(0, 5, orderByInformation);
        //获取有查找信息的总数
        Long orderByInformationNum = orderService.getOrderByInformationNum(orderByInformation);

        for (int i = 0; i < orderList.size(); i++) {
            System.out.println(orderList.get(i));
        }
        //通过条件查找订单
        model.addAttribute("orderList", orderList);
        //通过条件查找订单的总数
        model.addAttribute("orderByInformationNum", orderByInformationNum);

    }

    /**
     * 到用户统计页面
     *
     * @return
     */
    @RequestMapping("/allUser.html")
    public String allUser() {

        //测试使用的登录 TODO
        otherConfig.testLogin();
        return "/admin/AllUser";
    }

    /**
     * 到用户管理页面
     *
     * @return
     */
    @RequestMapping("/allUserManage.html")
    public String allUserManage() {
        //测试使用的登录 TODO
        otherConfig.testLogin();
        return "/admin/AllUserManage";
    }

    /**
     * 到作者统计页面
     *
     * @return
     */
    @RequestMapping("/allAuthor.html")
    public String allAuthor() {
        //测试使用的登录 TODO
        otherConfig.testLogin();
        return "/admin/AllAuthor";
    }

    /**
     * 到作者管理页面
     *
     * @return
     */
    @RequestMapping("/allAuthorManage.html")
    public String allAuthorManage() {
        //测试使用的登录 TODO
        otherConfig.testLogin();
        return "/admin/AllAuthorManage";
    }

    /**
     * 到图书统计页面
     *
     * @return
     */
    @RequestMapping("/allBook.html")
    public String allBook() {
        //测试使用的登录 TODO
        otherConfig.testLogin();
        return "/admin/AllBook";
    }

    /**
     * 到图书管理页面
     *
     * @return
     */
    @RequestMapping("/allBookManager.html")
    public String allBookManager() {
        //测试使用的登录 TODO
        otherConfig.testLogin();
        return "/admin/AllBookManager";
    }

    /**
     * 到邮件页面
     *
     * @return
     */
    @RequestMapping("/mail.html")
    public String mail() {
        //测试使用的登录 TODO
        otherConfig.testLogin();
        return "/admin/Mail";
    }

    /**
     * 到设备页面
     *
     * @return
     */
    @RequestMapping("/advise.html")
    public String advise() {
        //测试使用的登录 TODO
        otherConfig.testLogin();
        return "/admin/Advise";
    }

}
