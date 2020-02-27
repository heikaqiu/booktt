package cn.heikaqiu.booktt.service;

import cn.heikaqiu.booktt.bean.Comment;
import cn.heikaqiu.booktt.bean.User;

/**
 * @author HeiKaQiu
 * @create 2020-02-26 22:08
 */
public interface CommentService {
    /**
     * 添加评论
     * @param comment
     * @return
     */
    boolean addComment(Comment comment);

    /**
     * 获取
     * @param commentid
     * @return
     */
    Comment getCommentById(Integer commentid);

    /**
     * 删除评论 通过id
     * @param commentid
     * @return
     */
    boolean deleteComment(Integer commentid);
}
