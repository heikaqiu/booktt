package cn.heikaqiu.booktt.controller;

import cn.heikaqiu.booktt.bean.Advice;
import cn.heikaqiu.booktt.bean.User;
import cn.heikaqiu.booktt.service.AdviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author HeiKaQiu
 * @create 2020-02-24 17:13
 */
@Controller
public class AdviceController {
    @Autowired
    private HttpSession session;

    @Autowired
    private AdviceService adviceService;
    /**
     * 提交建议
     *
     * @return
     */
    @PostMapping("/toAdvice")
    public String toAdvice(Advice advice, Model model) {

        if(advice.getContent().equals("")||advice.getContent()==null){
            model.addAttribute("error","请输入正文");
            return "Advice";
        }if(advice.getTitle().equals("")||advice.getTitle()==null){
            model.addAttribute("error","请输入标题");
            return "Advice";
        }



        User login_user = (User) session.getAttribute("login_user");
        if(login_user==null){
            model.addAttribute("error","请登录");
        }else{
            //有登录的
            advice.setUser(login_user);
            advice.setTime(new Date());
            advice.setIshandle(false);
            advice.setIsread(false);
            boolean istoadvice = false;
            try {
                istoadvice = adviceService.toAdvice(advice);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if(istoadvice){
                //添加了
                model.addAttribute("success","提交建议成功");

            }else{
                model.addAttribute("error","提交建议失败，请重试");

            }
        }
        return "Advice";

    }


    /**
     * 查看未读建议的数量
     * @return
     */
    @GetMapping("/selectNewAdviceNum")
    @ResponseBody
    public Map<String,Object> selectNewAdviceNum() {

        //没有读过的 建议 当然同时也是未处理的
        Integer NewAdviceNum=adviceService.getAllAdviceNum(false,false);
        Map<String,Object> map=new HashMap<>();
        map.put("Num",NewAdviceNum);
        return map;

    }


    /**
     * 全标已读
     * @return
     */
    @GetMapping("/toAllReadAdvice")
    @ResponseBody
    public Map<String,Object> toAllReadAdvice() {
        adviceService.toAllReadAdvice();
        Map<String,Object> map=new HashMap<>();
        return map;

    }


}
