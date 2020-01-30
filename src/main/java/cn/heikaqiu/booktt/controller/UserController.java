package cn.heikaqiu.booktt.controller;

import cn.heikaqiu.booktt.bean.User;
import cn.heikaqiu.booktt.config.OtherConfig;
import cn.heikaqiu.booktt.mapper.UserMapper;
import cn.heikaqiu.booktt.service.UserService;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;

/**
 * @author HeiKaQiu
 * @create 2020-01-27 下午 18:06
 */

@Controller
public class UserController {


    @Autowired
    private UserService userService;

    @Autowired
    private HttpSession session;

    @Autowired
    private OtherConfig otherConfig;


    /**
     * 登录
     * @param user
     * @param model
     * @return
     */
    @PostMapping(value = "/login")
    public String login(User user, Model model){
        if(userService.login(user)){
            //登录成功
            session.setAttribute("login_user",user);
            return "redirect:/";
        }
        else{
            //登录失败
           // session.setAttribute("error","登录失败，用户名或者密码错误");
            model.addAttribute("error","登录失败，用户名或者密码错误");
            session.setAttribute("page","login");
            return "Login";
        }
    }

    @PostMapping("/register")
    public String register(User user, Model model){
        //将其他数据封装进user对象中
        String province = otherConfig.getProvince(Integer.valueOf(user.getProvince()));
        String city = otherConfig.getCity(Integer.valueOf(user.getProvince()), Integer.valueOf(user.getCity()));
        user.setProvince(province);
        user.setCity(city);
        user.setIsadmin(false);
        user.setBalance(0.0f);
        user.setTime(new Date());
        boolean successRegister=userService.register(user);
        if(successRegister){
            //注册成功
            session.setAttribute("login_user",user);
            return "redirect:/";
        }else{
            model.addAttribute("error","用户名重复");
            return "Register";
        }

    }






}
