package cn.heikaqiu.booktt.service.imp;

import cn.heikaqiu.booktt.bean.Comment;
import cn.heikaqiu.booktt.mapper.CommentMapper;
import cn.heikaqiu.booktt.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author HeiKaQiu
 * @create 2020-02-26 22:09
 */
@Service
public class CommentServiceImp implements CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Override
    public boolean addComment(Comment comment) {
        if (comment.getBook() == null || comment.getBook().getId() == null) {
            return false;
        }
        if (comment.getContent() == null || comment.getContent().equals("")) {
            return false;
        }
        if (comment.getStar() == null || comment.getStar() < 0 || comment.getStar() > 5) {
            return false;
        }
        if (comment.getTime() == null) {
            comment.setTime(new Date());
        }
        if (comment.getUser() == null || comment.getUser().getId() == null) {
            return false;
        }

        commentMapper.addComment(comment);
        return true;
    }

    @Override
    public Comment getCommentById(Integer commentid) {
        Comment comment = commentMapper.getCommentById(commentid);

        return comment;
    }

    @Override
    public boolean deleteComment(Integer commentid) {
        Integer line=commentMapper.deleteComment(commentid);
        if(line==1){
            return true;
        }
        return false;
    }
}
