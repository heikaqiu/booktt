package cn.heikaqiu.booktt.controller;

import cn.heikaqiu.booktt.bean.*;
import cn.heikaqiu.booktt.config.OtherConfig;
import cn.heikaqiu.booktt.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;
import java.util.Map;

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

    @Autowired
    private AuthorService authorService;

    @Autowired
    private BookService bookService;

    @Autowired
    private BookTypeService bookTypeService;

    @Autowired
    private AdviceService adviceService;


    //后台首页
    @RequestMapping(value = {"/"})
    public String index() {

        User user = (User) session.getAttribute("login_user");
        if (user == null) {
            //没有登录的人 则转到后台登录界面
            return "admin/login";
        } else {
            if (user.getIsadmin() == null || !user.getIsadmin()) {
                //不是管理员 转到前台首页
                return "redirect:/";

            } else {
                //是管理员 到后台首页
                return "admin/index";
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
        return "admin/login";
    }


    /**
     * 到订单统计页面
     *
     * @return
     */
    @RequestMapping("/allOrder.html")
    public String allOrder(Model model) {


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
            Double growthDifference = (todayOrderNum - yesterdayOrderNum) / (yesterdayOrderNum * 1.0);
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
        return "admin/AllOrderManage";
    }

    /**
     * 封装订单状态为2的 订单信息bean 放入session中 并查找出orderList与orderByInformationNum 放入model中
     *
     * @param model
     */
    private void packagingOrderInfo(Model model) {
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
    public String allUser(Model model) {


        //去工具类中找到时间段
        Map<String, Long> time = otherConfig.getTime();


        //查找用户总数
        Long allUserNum = userService.getAllCountUser();
        //查找昨日活跃用户
        Long yesterdayUserNum = userService.getAnydayLastUserCountUser(time.get("yesterday_zero"), time.get("today_zero")); //从今天的0开始到明天0
        //查找今日活跃用户
        Long todayUserNum = userService.getAnydayLastUserCountUser(time.get("today_zero"), time.get("tomorrow_zero"));
        //活跃比  今日/昨日
        if (yesterdayUserNum == 0L) {
            //底数为0
            model.addAttribute("growthDifference", "底数为0");
        } else {
            Double growthDifference = (todayUserNum) / (yesterdayUserNum * 1.0);
            System.out.println("growthDifference" + growthDifference);
            model.addAttribute("growthDifference", growthDifference);
        }
        System.out.println("allUserNum" + allUserNum);
        System.out.println("yesterdayUserNum" + yesterdayUserNum);
        System.out.println("todayUserNum" + todayUserNum);

        FindUserByInformation userByInformation = new FindUserByInformation();

        session.setAttribute("findUserByInformation", userByInformation);
        //获取分页用户    分0到5个  无条件
        List<User> userList = userService.getUserInfoLimit(0, 5, userByInformation);
        //获取有查找信息的用户总数
        Long userByInformationNum = userService.getUserByInformationNum(userByInformation);

        //通过条件查找用户
        model.addAttribute("userList", userList);
        //通过条件查找用户的总数
        model.addAttribute("userByInformationNum", userByInformationNum);


        model.addAttribute("allUserNum", allUserNum);
        model.addAttribute("yesterdayUserNum", yesterdayUserNum);
        model.addAttribute("todayUserNum", todayUserNum);

        return "admin/AllUser";
    }

    /**
     * 到用户管理页面
     *
     * @return
     */
    @RequestMapping("/allUserManage.html")
    public String allUserManage() {
        
        return "admin/AllUserManage";
    }

    /**
     * 到作者统计页面
     *
     * @return
     */
    @RequestMapping("/allAuthor.html")
    public String allAuthor(Model model) {
        

        Integer authorNum = authorService.getCountAuthor();
        model.addAttribute("authorNum", authorNum);


        FindAuthorByInformation findAuthorByInformation = new FindAuthorByInformation();

        session.setAttribute("findAuthorByInformation", findAuthorByInformation);
        //获取分页作者    分0到5个  无条件
        List<Author> authorList = authorService.getAuthorInfoLimit(0, 5, findAuthorByInformation);
        //获取有查找信息的作者总数
        Integer authorByInformationNum = authorService.getAuthorByInformationNum(findAuthorByInformation);

        //通过条件查找作者
        model.addAttribute("authorList", authorList);
        //通过条件查找作者的总数
        model.addAttribute("authorByInformationNum", authorByInformationNum);

        List<String> getnationality = otherConfig.getnationality();
        model.addAttribute("nationality", getnationality);

        return "admin/AllAuthor";
    }

    /**
     * 到作者管理页面
     *
     * @return
     */
    @RequestMapping("/allAuthorManage.html")
    public String allAuthorManage(Model model) {
        

        List<String> getnationality = otherConfig.getnationality();
        model.addAttribute("nationality", getnationality);


        return "admin/AllAuthorManage";
    }

    /**
     * 到图书统计页面
     *
     * @return
     */
    @RequestMapping("/allBook.html")
    public String allBook(Model model) {
        

//        <h4 class="title">总图书数</h4>
//        <p>[[${allBookNum}]]</p>
//
//        <h4 class="title">总计库存</h4>
//                                    <p>[[${allBookRemainderNum}]]</p>
//                <h4 class="title">上架中的图书</h4>
//                                    <p>[[${isshopBookNum}]]</p>
//                <h4 class="title">总计出售几本</h4>
//                                    <p>[[${allSellBookNum}]]</p>
//                <h4 class="title">昨日出售几本</h4>
//                                    <p>[[${yesterdaySellBookNum}]]</p>
//                 <h4 class="title">今日出售几本</h4>
//                                    <p>[[${todaySellBookNum}]]</p>

        //去工具类中找到时间段
        Map<String, Long> time = otherConfig.getTime();

        //获取所有的图书数
        Integer allBookNum = bookService.getCountAllBookNum();
        model.addAttribute("allBookNum", allBookNum);


        //获取上架中的图书数
        boolean isshop = true;
        Integer isshopBookNum = bookService.getIsshopBookNum(isshop);
        model.addAttribute("isshopBookNum", isshopBookNum);


        //获取所有的图书库存数
        Long allBookRemainderNum = bookService.getCountAllBookRemainderNum();
        model.addAttribute("allBookRemainderNum", allBookRemainderNum);

        Date start_time = null;
        Date last_time = null;
        //总计出售几本
        Long allSellBookNum = bookService.getCountSellBookNum(start_time, last_time);
        model.addAttribute("allSellBookNum", allSellBookNum);

        start_time = new Date(time.get("yesterday_zero"));
        last_time = new Date(time.get("today_zero"));
        //昨日出售几本
        Long yesterdaySellBookNum = bookService.getCountSellBookNum(start_time, last_time);
        model.addAttribute("yesterdaySellBookNum", yesterdaySellBookNum);

        start_time = last_time;
        last_time = new Date(time.get("tomorrow_zero"));
        //今日出售几本
        Long todaySellBookNum = bookService.getCountSellBookNum(start_time, last_time);
        model.addAttribute("todaySellBookNum", todaySellBookNum);


        FindBookByInformation findBookByInformation = new FindBookByInformation();

        session.setAttribute("findBookByInformation", findBookByInformation);
        //获取分页书本    分0到5个  无条件
        List<Book> bookList = bookService.getBookInfoLimit(0, 5, findBookByInformation);
        //获取有查找信息的书本总数
        Integer bookByInformationNum = bookService.getBookByInformationNum(findBookByInformation);

        //通过条件查找书本
        model.addAttribute("bookList", bookList);
        //通过条件查找书本的总数
        model.addAttribute("bookByInformationNum", bookByInformationNum);

        //获取所有的书本类型
        List<BookType> bookTypeList = bookTypeService.getAllType();
        model.addAttribute("bookTypeList", bookTypeList);

        //获取所有的作者
        List<Author> authorList = authorService.getAllAuthor();
        model.addAttribute("authorList", authorList);

        return "admin/AllBook";
    }

    /**
     * 到图书管理页面
     *
     * @return
     */
    @RequestMapping("/allBookManager.html")
    public String allBookManager(Model model) {
        

        //获取所有的书本类型
        List<BookType> bookTypeList = bookTypeService.getAllType();
        model.addAttribute("bookTypeList", bookTypeList);

        //获取所有的作者
        List<Author> authorList = authorService.getAllAuthor();
        model.addAttribute("authorList", authorList);
        return "admin/AllBookManager";
    }

    /**
     * 到通知页面
     *
     * @return
     */
    @RequestMapping("/notice.html")
    public String notice() {
        
        return "admin/Notice";
    }

    /**
     * 到新增订单页面
     *
     * @return
     */
    @RequestMapping("/newOrder.html")
    public String newOrder(Model model) {
        

        //到新增订单页面的时候  将订单状态为2  并且是未读的展示
        List<Order> orderList = orderService.getOrderInfoByNewOrder(0, 5);
        Integer orderListNum = orderService.selectNewOrderNum();
        model.addAttribute("orderList", orderList);
        model.addAttribute("orderListNum", orderListNum);

        return "admin/NewOrder";
    }

    /**
     * 到建议页面
     *
     * @return
     */
    @RequestMapping("/advice.html")
    public String advice(Model model) {
        
        Boolean isread = null;//读过的
        Boolean isHandle = null;//处理的

        //总建议数
        Integer allAdviceNum = adviceService.getAllAdviceNum(isread, isHandle);
        //未查看建议
        isread = false;
        isHandle = false;
        Integer noReadAdviceNum = adviceService.getAllAdviceNum(isread, isHandle);
        //未处理建议
        isread = null;
        isHandle = false;
        Integer NoHandleAdviceNum = adviceService.getAllAdviceNum(isread, isHandle);
        //处理完成建议
        isread = true;
        isHandle = true;
        Integer HandleAdviceNum = adviceService.getAllAdviceNum(isread, isHandle);
        model.addAttribute("allAdviceNum", allAdviceNum);
        model.addAttribute("noReadAdviceNum", noReadAdviceNum);
        model.addAttribute("NoHandleAdviceNum", NoHandleAdviceNum);
        model.addAttribute("HandleAdviceNum", HandleAdviceNum);

        //查找 未处理的订单不论是否查看过的  并分页
        isread = null;
        isHandle = false;
        List<Advice> adviceList = adviceService.getAdvice(isread, isHandle, 0, 5);

        model.addAttribute("adviceList", adviceList);
        model.addAttribute("adviceListNum", NoHandleAdviceNum);
        return "admin/Advice";
    }

}
