package cn.heikaqiu.booktt.controller;

import cn.heikaqiu.booktt.bean.User;
import cn.heikaqiu.booktt.service.AdminUserService;
import cn.heikaqiu.booktt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;

/**
 * @author HeiKaQiu
 * @create 2020-02-11 10:07
 */
@Controller
@RequestMapping("/admin")
public class AdminUserController {

    @Autowired
    private AdminUserService adminUserService;

    @Autowired
    private UserService userService;

    @Autowired
    private HttpSession session;


    @PostMapping("/login")
    public String login(User user , Model model){
        if(user!=null){
            boolean login = userService.login(user);
            if(login){
                return "redirect:/admin/";
            }
            else{
                //登录失败
                model.addAttribute("message","账号或者密码错误");
                return "/admin/login";
            }
        }
        //TODO 没接收到用户
        return "/admin/login";
    }

    @RequestMapping(value = "/exitLogin")
    public String exitLogin(){
        //清除登录 转到登录页面
        session.removeAttribute("login_user");
        return "redirect:/admin/login.html";
    }


}
