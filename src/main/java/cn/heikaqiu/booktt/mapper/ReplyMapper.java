package cn.heikaqiu.booktt.mapper;

import cn.heikaqiu.booktt.bean.Book;
import cn.heikaqiu.booktt.bean.Reply;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author HeiKaQiu
 * @create 2020-02-18 22:19
 */
@Component
public interface ReplyMapper {

    @Select("select * from reply where  user_id=#{user_id}")
    @Results(@Result(column = "comment_id", property = "comment", one = @One(select = "cn.heikaqiu.booktt.mapper.CommentMapper.getCommentById")))
    List<Reply> getAllReplysByUserId(Integer user_id);


    @Select("select * from reply where  comment_id=#{comment_id}")
    @Results(@Result(column = "user_id", property = "user", one = @One(select = "cn.heikaqiu.booktt.mapper.UserMapper.getUserById")))
    List<Reply> getAllReplysByCommentId(Integer comment_id);

    @Insert("insert into reply(user_id,content,time,comment_id) values(#{user.id},#{content},#{time},#{comment.id})")
    void addReply(Reply reply);

    @Delete("delete from reply where user_id=#{userid} AND id=#{replyid}")
    Integer deleteReply(Integer userid, Integer replyid);

    @Delete("delete from reply where comment_id=#{commentid}")
    Integer deleteReplyByComment(Integer commentid);
}
