package cn.heikaqiu.booktt.controller;

import cn.heikaqiu.booktt.bean.FindUserByInformation;
import cn.heikaqiu.booktt.bean.User;
import cn.heikaqiu.booktt.config.OtherConfig;
import cn.heikaqiu.booktt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * @author HeiKaQiu
 * @create 2020-02-11 10:07
 */
@Controller
@RequestMapping("/admin")
public class AdminUserController {

    @Autowired
    private UserService userService;

    @Autowired
    private HttpSession session;

    @Autowired
    private OtherConfig otherConfig;


    @PostMapping("/login")
    public String login(User user, Model model) {
        if (user != null) {
            boolean login = userService.login(user);
            if (login) {
                return "redirect:/admin/";
            } else {
                //登录失败
                model.addAttribute("message", "账号或者密码错误");
                return "admin/login";
            }
        }

        return "admin/login";
    }

    @RequestMapping(value = "/exitLogin")
    public String exitLogin() {
        //清除登录 转到登录页面
        session.removeAttribute("login_user");
        return "redirect:/admin/login.html";
    }

    /**
     * 按提交用户条件的时候 将条件封装到 FindUserByInformation 并加到session 中
     *
     * @param findUserByInformation
     * @param time
     * @param lastusetime
     * @return
     */
    @RequestMapping("/userInformation")
    @ResponseBody
    public Map<String, Object> userInformation(FindUserByInformation findUserByInformation,
                                               String time, String lastusetime) {

        System.out.println(findUserByInformation);
        Map<String, Object> maps = new HashMap<>();
        //为什么要加这些 限制 因为他们都是String  注入的都是""  使得他们都不是null
        if(findUserByInformation.getUsername().equals("")) {
            findUserByInformation.setUsername(null);
        }
        if(findUserByInformation.getProvince().equals("")) {
            findUserByInformation.setProvince(null);
        }
        if(findUserByInformation.getCity().equals("")) {
            findUserByInformation.setCity(null);
        }if(findUserByInformation.getTelephone().equals("")) {
            findUserByInformation.setTelephone(null);
        }


        if (time != null && !time.equals("")) {

            System.out.println(time);
            Date[] dates = otherConfig.StringtoDate(time);
            findUserByInformation.setStart_time(dates[0]);
            findUserByInformation.setLast_time(dates[1]);
        }

        if (lastusetime != null && !lastusetime.equals("")) {

            System.out.println(lastusetime);

            Date[] dates = otherConfig.StringtoDate(lastusetime);

            findUserByInformation.setStart_usetime(dates[0]);
            findUserByInformation.setLast_usetime(dates[1]);
        }


        session.setAttribute("findUserByInformation", findUserByInformation);
        maps.put("message", "搜索成功");
        Map<String, Object> stringObjectMap = toPageUser(0);

        maps.putAll(stringObjectMap);
        return maps;
    }


    /**
     * 获取 分页第几条
     * @param pageNum
     * @return
     */
    @PostMapping("/toPageUser/{pageNum}")
    @ResponseBody
    public Map<String, Object> toPageUser(@PathVariable("pageNum") Integer pageNum) {

        Map<String, Object> maps = new HashMap<>();

        System.out.println(pageNum);
        FindUserByInformation findUserByInformation = (FindUserByInformation) session.getAttribute("findUserByInformation");

        List<User> userList = new ArrayList<>();//通过订单的信息 查找的订单
        Long userByInformationNum = null;//获取有查找信息的总数
        //有订单信息要求
        System.out.println("findUserByInformation" + findUserByInformation);
        //获取分页订单
        userList = userService.getUserInfoLimit(pageNum, 5, findUserByInformation);
        userByInformationNum = userService.getUserByInformationNum(findUserByInformation);

        maps.put("userList", userList);
        maps.put("userByInformationNum", userByInformationNum);

        return maps;
    }

    /**
     * 查找用户到用户信息界面
     * @param userId
     * @param model
     * @return
     */
    @GetMapping("/userInfo.html/{user_id}")
    public String userInfohtml(@PathVariable("user_id") Integer userId, Model model) {

        User user = userService.getUserInfoByUserId(userId);
        if (user != null) {
            model.addAttribute("user", user);
        } else {
            model.addAttribute("message", "未找到用户  id错误");
        }
        return "admin/UserInformation";


    }

    /**
     * 查找用户
     * @param userId
     * @param model
     * @return
     */
    @GetMapping("/userInfo/{user_id}")
    @ResponseBody
    public Map<String,Object> userInfo(@PathVariable("user_id") Integer userId, Model model) {
        Map<String,Object> map=new HashMap<>();
        User user = userService.getUserSimpleById(userId);
        if (user != null) {
            map.put("user", user);
        } else {
            map.put("message", "未找到用户  id错误");
        }
        return map;


    }


    @PostMapping("/register")
    @ResponseBody
    public Map<String,Object> register(User user) {
        System.out.println(user);
        Map<String,Object> map=new HashMap<>();
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
            map.put("message", "注册成功");
        } else {
            map.put("message", "用户名重复");

        }
        return map;

    }

    /**
     *修改用户的基本信息
     */
    @PostMapping("/updateUserInformation")
    @ResponseBody
    public Map<String,Object> updateUserInformation(User user) {
        Map<String,Object> map=new HashMap<>();
        String province = otherConfig.getProvince(Integer.valueOf(user.getProvince()));
        String city = otherConfig.getCity(Integer.valueOf(user.getProvince()), Integer.valueOf(user.getCity()));
        user.setProvince(province);
        user.setCity(city);
        System.out.println(user);
        boolean isupdate = false;
        try {
            isupdate = userService.updateUserAllInformation(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (isupdate) {
            //修改用户成功
            map.put("message","修改成功");
        } else {
            //转到错误页面
            map.put("message","修改失败，可能因为用户名重复或者其他");

        }
        return map;
    }

/**
 *删除用户
 */
    @PostMapping("/deleteuser/{userid}")
    @ResponseBody
    public Map<String,Object> deleteuser(@PathVariable("userid") Integer userId) {
        Map<String,Object> map=new HashMap<>();

        Boolean isdelete= null;
        try {
            isdelete = userService.deleteuser(userId);

        } catch (Exception e) {
            e.printStackTrace();
            map.put("message","删除失败，其他问题");
        }
        if(isdelete){
            //成功删除
            map.put("message","删除成功");
        }else{
            map.put("message","删除失败，未找到用户 请检查id");
        }
        return map;
    }




}
