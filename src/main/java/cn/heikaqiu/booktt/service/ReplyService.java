package cn.heikaqiu.booktt.service;

import cn.heikaqiu.booktt.bean.Reply;
import org.springframework.stereotype.Component;

/**
 * @author HeiKaQiu
 * @create 2020-02-26 23:14
 */
public interface ReplyService {

    /**
     * 添加回复
     * @param reply
     * @return
     */
    boolean addReply(Reply reply);

    /**
     * 通过用户id与回复id删除回复
     * @param userid
     * @param replyid
     * @return
     */
    boolean deleteReply(Integer userid, Integer replyid);

    /**
     * 通过评论id删除回复
     * @param commentid
     * @return
     */
    boolean deleteReply(Integer commentid);
}
