package cn.heikaqiu.booktt.controller;

import cn.heikaqiu.booktt.bean.User;
import cn.heikaqiu.booktt.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;

/**
 * @author HeiKaQiu
 * @create 2020-01-27 下午 18:06
 */

@Controller
public class UserController {


    @Autowired
    private UserMapper userMapper;



    @ResponseBody
    @RequestMapping("/login/{id}")
    public User getUser(@PathVariable("id") Integer id){
        return userMapper.getUserById(id);
    }

    @RequestMapping("/")
    public String index(){
        return "Login";
    }
}
