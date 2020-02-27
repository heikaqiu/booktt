package cn.heikaqiu.booktt.controller;

import cn.heikaqiu.booktt.bean.Book;
import cn.heikaqiu.booktt.bean.Comment;
import cn.heikaqiu.booktt.bean.User;
import cn.heikaqiu.booktt.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * @author HeiKaQiu
 * @create 2020-02-26 21:59
 */
@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private UserService userService;

    @Autowired
    private BookService bookService;

    @Autowired
    private ReplyService replyService;

    @PostMapping("/addComment")
    @ResponseBody
    public Map<String, Object> addComment(Comment comment, Integer userid, Integer bookid) {
        Map<String, Object> map = new HashMap<>();
        User user = userService.getUserById(userid);
        Book book = bookService.getBookInfoByBookId(bookid);
        comment.setBook(book);
        comment.setUser(user);
        System.out.println(comment);
        System.out.println(user);
        System.out.println(book);

        boolean isSuccessAddComment = commentService.addComment(comment);
        if (isSuccessAddComment) {
            map.put("message", "评论成功");
        } else {
            map.put("message", "评论失败");
        }
        return map;

    }


    @DeleteMapping("/deleteComment")
    @ResponseBody
    public Map<String, Object> deleteComment(Integer commentid, Integer userid) {
        Map<String, Object> map = new HashMap<>();


        System.out.println(userid);
        System.out.println(commentid);
        //通过评论的id获取到用户
        Comment comment = commentService.getCommentById(commentid);
        //判定条件是否为空
        if (comment != null && comment.getUser() != null && comment.getUser().getId() != null && userid != null && userid > 0) {
            if (comment.getUser().getId() == userid) {
                //先删除此评论下的回复
                boolean isSuccessDeleteReply = replyService.deleteReply(commentid);
                if (isSuccessDeleteReply) {
                    //删除回复成功
                    boolean isSuccessDeleteComment = commentService.deleteComment(commentid);
                    if (isSuccessDeleteComment) {
                        //删除评论成功
                        map.put("message", "删除成功");
                    } else {
                        map.put("message", "删除失败,评论删除失败");
                    }
                } else {
                    map.put("message", "删除失败，回复删除失败");
                }
            } else {
                map.put("message", "删除失败，此评论不是此用户的评论");
            }
        } else {
            map.put("message", "删除失败，评论或者用户不存在");
        }
        return map;

    }
}
