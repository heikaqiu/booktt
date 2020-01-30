package cn.heikaqiu.booktt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.ConditionalOnRepositoryType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import javax.websocket.Session;

/**
 * @author HeiKaQiu
 * @create 2020-01-29 18:43
 */
@Controller
public class IndexController {

    @Autowired
    private HttpSession session;
    /**
     * 我的订单页面
     * @return
     */
    @RequestMapping("/order.html")
    public String order() {
        session.setAttribute("page","order");
        return "Order";
    }

    /**
     * 个人中心
     * @return
     */
    @RequestMapping("/userInfo.html")
    public String userInfo() {
        session.setAttribute("page","userInfo");
        return "UserInfo";
    }

    /**
     * 友情链接
     * @return
     */
    @RequestMapping("/friendLink.html")
    public String friendLink() {
        session.setAttribute("page","friendLink");
        return "FriendLink";
    }

    /**
     * 购物车页面
     * @return
     */
    @RequestMapping("/cart.html")
    public String cart() {
        session.setAttribute("page","cart");
        return "Cart";
    }

    /**
     * 转到登录页面
     * @return
     */
    @RequestMapping("/login.html")
    public String login() {
        session.setAttribute("page","login");
        return "Login";
    }


    /**
     * 首页
     * @return
     */
    @RequestMapping("/")
    public String index() {
        session.setAttribute("page","/");
        return "Index";
    }

    /**
     * 注册页面
     * @return
     */
    @RequestMapping("/register.html")
    public String register() {
        session.setAttribute("page","register");
        return "Register";
    }
}
