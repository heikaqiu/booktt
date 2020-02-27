package cn.heikaqiu.booktt.service.imp;

import cn.heikaqiu.booktt.bean.Reply;
import cn.heikaqiu.booktt.mapper.ReplyMapper;
import cn.heikaqiu.booktt.service.ReplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author HeiKaQiu
 * @create 2020-02-26 23:15
 */
@Service
public class ReplyServiceImp implements ReplyService {

    @Autowired
    private ReplyMapper replyMapper;

    @Override
    public boolean addReply(Reply reply) {
        if(reply.getComment()==null||reply.getComment().getId()==null){
            return false;
        }
        if(reply.getContent()==null||reply.getContent().equals("")){
            return false;
        }
        if(reply.getTime()==null){
            reply.setTime(new Date());
        }
        if(reply.getUser()==null||reply.getUser().getId()==null){
            return false;
        }

        replyMapper.addReply(reply);
        return true;
    }

    @Override
    public boolean deleteReply(Integer userid, Integer replyid) {
        if(userid==null||replyid==null||userid<=0||replyid<=0){
            return false;
        }
        Integer line=replyMapper.deleteReply(userid,replyid);
        if(line==1){
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteReply(Integer commentid) {
        if(commentid==null||commentid<=0){
            return false;
        }
        Integer line=replyMapper.deleteReplyByComment(commentid);
        if(line>0){
            return true;
        }
        return false;
    }
}
