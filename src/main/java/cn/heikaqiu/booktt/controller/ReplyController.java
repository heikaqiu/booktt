package cn.heikaqiu.booktt.controller;

import cn.heikaqiu.booktt.bean.Book;
import cn.heikaqiu.booktt.bean.Comment;
import cn.heikaqiu.booktt.bean.Reply;
import cn.heikaqiu.booktt.bean.User;
import cn.heikaqiu.booktt.service.CommentService;
import cn.heikaqiu.booktt.service.ReplyService;
import cn.heikaqiu.booktt.service.UserService;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.jws.soap.SOAPBinding;
import java.util.HashMap;
import java.util.Map;

/**
 * @author HeiKaQiu
 * @create 2020-02-26 23:09
 */
@Controller
public class ReplyController {

    @Autowired
    private ReplyService replyService;

    @Autowired
    private UserService userService;

    @Autowired
    private CommentService commentService;


    @PostMapping("/addReply")
    @ResponseBody
    public Map<String,Object> addReply(Reply Reply, Integer userid, Integer commentid){
        Map<String,Object> map=new HashMap<>();


        System.out.println(Reply);
        System.out.println(userid);
        System.out.println(commentid);

        User user = userService.getUserById(userid);
        Comment comment = commentService.getCommentById(commentid);

        System.out.println(user);
        System.out.println(comment);

        Reply.setComment(comment);
        Reply.setUser(user);

        boolean isSuccessAddReply =replyService.addReply(Reply);
        if(isSuccessAddReply){
            map.put("message","回复成功");
        }
        else{
            map.put("message","回复失败");
        }
        return map;

    }


    @DeleteMapping("/deleteReply")
    @ResponseBody
    public Map<String,Object> deleteReply(Integer userid, Integer replyid){
        Map<String,Object> map=new HashMap<>();


        System.out.println(userid);
        System.out.println(replyid);


        boolean isSuccessDeleteReply =replyService.deleteReply(userid,replyid);
        if(isSuccessDeleteReply){
            map.put("message","删除成功");
        }
        else{
            map.put("message","删除失败");
        }
        return map;

    }


}
