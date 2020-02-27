package cn.heikaqiu.booktt.service.imp;

import cn.heikaqiu.booktt.bean.Advice;
import cn.heikaqiu.booktt.mapper.AdviceMapper;
import cn.heikaqiu.booktt.service.AdviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author HeiKaQiu
 * @create 2020-02-24 17:36
 */
@Service
public class AdviceServiceImp implements AdviceService {
    @Autowired
    private AdviceMapper adviceMapper;

    @Override
    public boolean toAdvice(Advice advice) throws Exception {
        if(advice.getTitle()==null||advice.getTitle().equals("")){
            return false;
        }
        if(advice.getContent()==null||advice.getContent().equals("")){
            return false;
        }
        if(advice.getUser()==null){
            return false;
        }
        if(advice.getIshandle()==null){
            return false;
        }
        if(advice.getIsread()==null){
            return false;
        }
        if(advice.getTime()==null){
            return false;
        }
        Integer line=adviceMapper.toAdvice(advice);
        if(line==1){
            return true;
        }else{
            throw new Exception("添加建议服务器错误");
        }
    }



    @Override
    public void toAllReadAdvice() {
        adviceMapper.toAllReadAdvice();
    }

    @Override
    public Integer getAllAdviceNum(Boolean isread, Boolean isHandle) {

        return adviceMapper.getAllAdviceNum(isread,isHandle);
    }

    @Override
    public List<Advice> getAdvice(Boolean isread, Boolean isHandle, Integer start_num, Integer page_num) {
        return adviceMapper.getAdvice(isread,isHandle,start_num,page_num);
    }

    @Override
    public boolean updateAdviceRead(Integer adviceId, boolean isread) {
        Integer line=adviceMapper.updateAdviceRead(adviceId,isread);
        if (line == 1) {
            return true;
        }else{
            return false;
        }

    }

    @Override
    public Advice getAdviceById(Integer adviceId) {
        return adviceMapper.getAdviceById(adviceId);
    }

    @Override
    public boolean updateAdviceHandle(Integer adviceid, boolean isHandle) {
        Integer line=0;
        if(isHandle){
            //要处理完成
            line=adviceMapper.updateAdviceHandle(adviceid,isHandle,new Date());
        }else{
            //将处理完成给取消
            line=adviceMapper.updateAdviceHandle(adviceid,isHandle,null);
        }

        if(line==1){
            return true;
        }
        return false;
    }


}
