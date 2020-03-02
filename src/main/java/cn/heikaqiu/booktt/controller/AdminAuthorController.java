package cn.heikaqiu.booktt.controller;

import cn.heikaqiu.booktt.bean.Author;
import cn.heikaqiu.booktt.bean.FindAuthorByInformation;
import cn.heikaqiu.booktt.config.OtherConfig;
import cn.heikaqiu.booktt.service.AuthorService;
import cn.heikaqiu.booktt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author HeiKaQiu
 * @create 2020-02-20 19:42
 */
@Controller
@RequestMapping("/admin")
public class AdminAuthorController {


    @Autowired
    private UserService userService;

    @Autowired
    private HttpSession session;

    @Autowired
    private OtherConfig otherConfig;

    @Autowired
    private AuthorService authorService;


    @RequestMapping("/authorInformation")
    @ResponseBody
    public Map<String, Object> authorInformation(FindAuthorByInformation findAuthorByInformation) {

        System.out.println(findAuthorByInformation);
        Map<String, Object> maps = new HashMap<>();
        if (findAuthorByInformation.getNationality().equals("-1")) {
            //如果是-1 代表没有
            findAuthorByInformation.setNationality(null);
        }
        if (findAuthorByInformation.getName().equals("")) {
            //如果是-1 代表没有
            findAuthorByInformation.setName(null);
        }

        session.setAttribute("findAuthorByInformation", findAuthorByInformation);
        maps.put("message", "搜索成功");
        Map<String, Object> stringObjectMap = toPageAuthor(0);

        maps.putAll(stringObjectMap);
        return maps;
    }


    /**
     * 获取 分页第几条
     *
     * @param pageNum
     * @return
     */
    @PostMapping("/toPageAuthor/{pageNum}")
    @ResponseBody
    public Map<String, Object> toPageAuthor(@PathVariable("pageNum") Integer pageNum) {

        Map<String, Object> maps = new HashMap<>();

        System.out.println("pageNum" + pageNum);
        FindAuthorByInformation findAuthorByInformation = (FindAuthorByInformation) session.getAttribute("findAuthorByInformation");

        List<Author> authorList = new ArrayList<>();//通过订单的信息 查找的订单
        Integer authorByInformationNum = null;//获取有查找信息的总数
        //有订单信息要求
        System.out.println("findAuthorByInformation" + findAuthorByInformation);
        //获取分页订单
        authorList = authorService.getAuthorInfoLimit(pageNum, 5, findAuthorByInformation);
        authorByInformationNum = authorService.getAuthorByInformationNum(findAuthorByInformation);

        maps.put("authorList", authorList);
        maps.put("authorByInformationNum", authorByInformationNum);

        System.out.println(authorList);
        System.out.println(authorByInformationNum);

        return maps;
    }

    /**
     * 查找作者到作者信息页面
     */
    @GetMapping("/authorInfo.html/{author_id}")
    public String authorInfohtml(@PathVariable("author_id") Integer authorId, Model model) {

        Author author = authorService.getAuthorInfoByAuthorId(authorId);
        if (author != null) {
            model.addAttribute("author", author);
        } else {
            model.addAttribute("message", "未找到作者 id出错");
        }
        return "admin/AuthorInformation";
    }


    /**
     * 查找作者
     *
     * @param model
     * @return
     */
    @GetMapping("/authorInfo/{author_id}")
    @ResponseBody
    public Map<String, Object> authorInfo(@PathVariable("author_id") Integer authorId, Model model) {
        Map<String, Object> map = new HashMap<>();
        Author author = authorService.getAuthorInfoByAuthorId(authorId);
        if (author != null) {
            map.put("author", author);
        } else {
            map.put("message", "未找到作者  id错误");
        }
        return map;


    }


    @PostMapping("/addAuthor")
    @ResponseBody
    public Map<String, Object> addAuthor(Author author) {
        Map<String, Object> map = new HashMap<>();
        if (author.getNationality().equals("-1")) {
            //如果是-1 报错
            map.put("message", "未选择国家");
            return map;
        }
        System.out.println(author);
        boolean successaddAuthor = authorService.addAuthor(author);
        if (successaddAuthor) {
            //注册成功
            map.put("message", "添加成功");
        } else {
            map.put("message", "作者名重复");

        }
        return map;

    }

    /**
     * 修改用户的基本信息
     */
    @PostMapping("/updateAuthorInformation")
    @ResponseBody
    public Map<String, Object> updateAuthorInformation(Author author) {
        Map<String, Object> map = new HashMap<>();
        if (author.getNationality().equals("-1")) {
            //如果是-1 报错
            map.put("message", "未选择国家");
            return map;
        }

        System.out.println(author);
        boolean isupdate = false;
        try {
            isupdate = authorService.updateAuthorInformation(author);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (isupdate) {
            //修改用户成功
            map.put("message", "修改成功");
        } else {
            //转到错误页面
            map.put("message", "修改失败，可能因为作者名重复或者其他");

        }
        return map;
    }

    /**
     * 下架
     */
    @PostMapping("/deleteauthor")
    @ResponseBody
    public Map<String, Object> deleteauthor(Integer authorid) {
        Map<String, Object> map = new HashMap<>();
        boolean isshop=false;
        Boolean isishop = null;
        try {

            isishop = authorService.updateauthorisshop(authorid,isshop);

        } catch (Exception e) {
            e.printStackTrace();
            map.put("message", "下架失败，其他问题");
        }
        if (isishop) {
            //成功删除
            map.put("message", "下架成功");
        } else {
            map.put("message", "下架失败，未找到用户 请检查id");
        }
        return map;
    }

    /**
     * 上架作者所有书
     */
    @PostMapping("/isshopauthor")
    @ResponseBody
    public Map<String, Object> isshopauthor( Integer authorid) {
        Map<String, Object> map = new HashMap<>();
    boolean isshop=true;
        Boolean isishop = null;
        try {

            isishop = authorService.updateauthorisshop(authorid,isshop);

        } catch (Exception e) {
            e.printStackTrace();
            map.put("message", "上架失败，其他问题");
        }
        if (isishop) {
            //成功删除
            map.put("message", "上架成功");
        } else {
            map.put("message", "上架失败，未找到用户 请检查id");
        }
        return map;
    }

}
