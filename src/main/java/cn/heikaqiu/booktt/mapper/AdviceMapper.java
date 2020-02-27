package cn.heikaqiu.booktt.mapper;

import cn.heikaqiu.booktt.bean.Advice;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * @author HeiKaQiu
 * @create 2020-02-24 17:41
 */
@Component
public interface AdviceMapper {

    @Insert("insert into advice(user_id,title,content,isread,ishandle,time) values(#{user.id},#{title},#{content},#{isread},#{ishandle},#{time});")
    Integer toAdvice(Advice advice);

    @Select("select count(*) from advice where isread=false;")
    Integer getNotReadAdviceNum();

    @Update("update advice SET isread = true;")
    void toAllReadAdvice();

    @Select({
            "<script>",
            "select count(*) from  advice <where>",
            "<if test='isread!= null'> and isread=#{isread}</if>",
            "<if test='isHandle != null'> and  ishandle=#{isHandle}</if>",
            "</where>",
            "</script>"
    })
    Integer getAllAdviceNum(Boolean isread, Boolean isHandle);


    @Select({
            "<script>",
            "select * from  advice <where>",
            "<if test='isread!= null'> and isread=#{isread}</if>",
            "<if test='isHandle != null'> and  ishandle=#{isHandle}</if>",
            "</where> limit #{start_num},#{page_num}",
            "</script>"
    })
    @Results({
            @Result(column = "user_id", property = "user", one = @One(select = "cn.heikaqiu.booktt.mapper.UserMapper.getUserById"))
    })
    List<Advice> getAdvice(Boolean isread, Boolean isHandle, Integer start_num, Integer page_num);


    @Update("update advice SET isread = #{isread} where id=#{adviceId};")
    Integer updateAdviceRead(Integer adviceId, boolean isread);

    @Select("select * from advice where id=#{adviceId}")
    @Results({
            @Result(column = "user_id", property = "user", one = @One(select = "cn.heikaqiu.booktt.mapper.UserMapper.getUserById"))
    })
    Advice getAdviceById(Integer adviceId);

    @Update("update advice SET ishandle = #{isHandle},advice_time=#{date} where id=#{adviceid};")
    Integer updateAdviceHandle(Integer adviceid, boolean isHandle, Date date);
}
