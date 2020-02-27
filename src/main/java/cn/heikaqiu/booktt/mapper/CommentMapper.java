package cn.heikaqiu.booktt.mapper;

import cn.heikaqiu.booktt.bean.Book;
import cn.heikaqiu.booktt.bean.Comment;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author HeiKaQiu
 * @create 2020-02-18 22:19
 */
@Component
public interface CommentMapper {


    @Select("select * from comment where  user_id=#{user_id}")
    @Results(@Result(column = "book_id", property = "book", one = @One(select = "cn.heikaqiu.booktt.mapper.BookMapper.getBookInfoById")))
    List<Comment> getAllCommentsByUserId(Integer user_id);


    @Select("select *,id cid from comment where  book_id=#{book_id}")
    @Results({
            @Result(column = "cid", property = "replys", one = @One(select = "cn.heikaqiu.booktt.mapper.ReplyMapper.getAllReplysByCommentId")),
            @Result(column = "user_id", property = "user", one = @One(select = "cn.heikaqiu.booktt.mapper.UserMapper.getUserById"))
    })
    List<Comment> getAllCommentsByBookId(Integer book_id);

    @Select("select * from comment where  id=#{comment_id}")
    @Results({
            @Result(column = "book_id", property = "book", one = @One(select = "cn.heikaqiu.booktt.mapper.BookMapper.getBookInfoById")),
            @Result(column = "user_id", property = "user", one = @One(select = "cn.heikaqiu.booktt.mapper.UserMapper.getUserById"))
    })
    Comment getCommentById(Integer comment_id);


    @Insert("insert into comment(user_id,book_id,content,time,star) values(#{user.id},#{book.id},#{content},#{time},#{star})")
    void addComment(Comment comment);

    @Delete("delete from comment where id=#{commentid}")
    Integer deleteComment(Integer commentid);
}
