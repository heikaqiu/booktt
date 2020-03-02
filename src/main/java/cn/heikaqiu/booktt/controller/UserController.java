package cn.heikaqiu.booktt.controller;

import cn.heikaqiu.booktt.bean.User;
import cn.heikaqiu.booktt.config.OtherConfig;
import cn.heikaqiu.booktt.service.BookService;
import cn.heikaqiu.booktt.service.OrderService;
import cn.heikaqiu.booktt.service.ShopcartService;
import cn.heikaqiu.booktt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileOutputStream;
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


    @Autowired
    private OrderService orderService;

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

    @PostMapping(value = "/logintele")
    public String logintele(String telephone, String verification, Model model) {
        String code = (String) session.getAttribute(telephone);
        System.out.println(telephone);
        System.out.println(verification);
        if (code.equals(verification) ) {
            //验证成功登录
            boolean issuccess = userService.logintele(telephone);
            if (issuccess) {
                //登录成功
                return "redirect:/";
            } else {
                //登录失败 用户未找到
                model.addAttribute("error", "登录失败1，验证码或手机号未注册错误");
                session.setAttribute("page", "login");
                return "Login";
            }
        } else {
            //验证码错误
            model.addAttribute("error", "登录失败2，验证码或手机号未注册错误");
            session.setAttribute("page", "login");
            return "Login";
        }

    }

    /**
     * 验证用户名 是否重复
     */
    @PostMapping("/getUsername")
    @ResponseBody
    public Map<String, Object> getUsername(String username, Model model) {
        Map<String, Object> map = new HashMap<>();
        User userByusername = userService.findUserByusername(username);

        if (userByusername != null) {
            map.put("message", "存在");
        } else {
            map.put("message", "不存在");
        }

        return map;

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
        if (user.getGender()) {
            //男
            user.setImg("boyhead.jpg");
        } else {
            user.setImg("girlhead.jpg");
        }
        user.setCity(city);
        user.setIsadmin(false);
        user.setBalance(0.0f);
        user.setTime(new Date());
        boolean successRegister = userService.register(user);
        if (successRegister) {
            //注册成功
            return "redirect:/";
        } else {
            model.addAttribute("error", "用户名重复或者手机号重复");
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
                                       Float totalPrice) {
        Map<String, String> map = new HashMap<>();

        List<Integer> bookid = new ArrayList<>();
        bookid.add(Integer.valueOf(book_id));

        List<Integer> booknum = new ArrayList<>();
        booknum.add(Integer.valueOf(buy_num));

        Integer userid = Integer.valueOf(user_id);


        Long isBuyBook = shopcartService.addOrder(bookid, booknum, userid, totalPrice, 1);
        if (isBuyBook > 0L) {
            //如果添加订单成功
            map.put("message", "添加订单成功");
            map.put("orderid", isBuyBook.toString());
        } else if (isBuyBook == 0L) {
            //购买失败库存不足
            map.put("message", "库存不足");
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
     * 修改用户的基本信息
     */
    @PostMapping("/updateUserInformation")
    public String updateUserInformation(Integer id,
                                        String username,
                                        boolean gender, String telephone,
                                        Integer province,
                                        Integer city,
                                        String address,
                                        Model model) {
        User user = new User();
        String provinces = otherConfig.getProvince(province);
        String citys = otherConfig.getCity(province, city);
        user.setProvince(provinces);
        user.setCity(citys);
        user.setId(id);
        user.setUsername(username);
        user.setGender(gender);
        user.setTelephone(telephone);
        user.setAddress(address);
        System.out.println(user);
        boolean isupdate = false;
        try {
            isupdate = userService.updateUserInformation(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
        user = userService.getUserById(id);
        model.addAttribute("user", user);
        if (isupdate) {
            //修改用户成功
            otherConfig.reloadLoginUser();
            model.addAttribute("success_message", "修改用户信息成功");


            return "UserInfo";
        } else {
            //转到错误页面
            model.addAttribute("error_message", "修改用户信息失败，用户名重复");
            return "UserInfo";
        }
    }


    /**
     * 修改用户的密码
     */
    @PostMapping("/updatePassword")
    public String updatePassword(Integer userid, String old_password, String password, String paypassword, Model model) {

        boolean isupdate = false;
        try {
            isupdate = userService.updateUserPassword(userid, old_password, password, paypassword);
        } catch (Exception e) {
            e.printStackTrace();
        }
        User user = userService.getUserById(userid);
        model.addAttribute("user", user);
        if (isupdate) {
            //修改用户成功
            otherConfig.reloadLoginUser();
            model.addAttribute("success_message", "修改用户密码成功");
            return "UserInfo";
        } else {
            //转到错误页面
            model.addAttribute("error_message", "修改用户密码失败,密码错误");
            return "UserInfo";
        }
    }


    /**
     * 更换头像
     */
    @RequestMapping("/updateUserHead")
    @ResponseBody
    public Map<String, Object> updateUserHead(@RequestParam("file") MultipartFile file) {
        Map<String, Object> map = new HashMap<>();
        //获取文件本来的名字
        String fileName = file.getOriginalFilename();
        System.out.println("fileName:" + fileName);
        //获取文件本来的后缀
        String[] split = fileName.split("\\.");
        System.out.println(split.length);
        for (int i = 0; i < split.length; i++) {
            System.out.println(split[i]);
        }
        String fileNameLast = split[split.length - 1];
        //将时间戳给文件当名字
        Long fileName1 = (new Date()).getTime();
        fileName = fileName1.toString() + "." + fileNameLast;
        //去此目录下找是否有同名  ，如果有同名 就
        String filePath = "src/main/resources/static/image/head/";
        boolean hasFile = otherConfig.findFile(filePath, fileName);
        while (hasFile) {
            //有同名文件  就重新获取时间戳再去查找  知道没有同名文件为止
            fileName1 = (new Date()).getTime();
            fileName = fileName1.toString() + "." + fileNameLast;
            hasFile = otherConfig.findFile(filePath, fileName);
        }

        if (fileName.indexOf("\\") != -1) {
            fileName = fileName.substring(fileName.lastIndexOf("\\"));
        }

        File targetFile = new File(filePath);
        if (!targetFile.exists()) {
            targetFile.mkdirs();
        }
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(filePath + fileName);
            out.write(file.getBytes());
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
            map.put("message", "上传失败,文件上传失败");
            return map;
        }
        map.put("message", "上传成功");
        map.put("imgpath", "head/" + fileName);
        return map;

    }

    /**
     * 更换头像路径到数据库
     */
    @PostMapping("/updateUserImgPath")
    @ResponseBody
    public Map<String, Object> updateUserImgPath(Integer userid, String imgpath) {
        Map<String, Object> map = new HashMap<>();
        boolean isupdate = userService.updateUserImg(imgpath, userid);
        if (isupdate) {
            map.put("message", "上传成功");
        } else {
            map.put("message", "上传失败");
        }
        otherConfig.reloadLoginUser();

        return map;

    }

    @PostMapping("/getUserTele")
    @ResponseBody
    public Map<String, Object> getUserTele(String phoneNum) {
        Map<String, Object> map = new HashMap<>();
        boolean ishas = userService.hasTele(phoneNum);
        if (ishas) {
            //
            map.put("message", "存在");
        } else {
            map.put("message", "不存在");
        }
        return map;

    }


}
