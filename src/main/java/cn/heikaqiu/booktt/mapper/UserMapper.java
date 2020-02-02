package cn.heikaqiu.booktt.mapper;

import cn.heikaqiu.booktt.bean.Book;
import cn.heikaqiu.booktt.bean.Comment;
import cn.heikaqiu.booktt.bean.User;
import org.apache.ibatis.annotations.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


//指定这是一个操作数据库的mapper
//@Mapper
public interface UserMapper {

    @Select("select * from ur where id=#{id}")
    public User getUserById(Integer id);

    @Delete("delete from ur where id=#{id}")
    public int deleteUserById(Integer id);

    @Options(useGeneratedKeys = true,keyProperty = "id")
    @Insert("insert into ur(username,password,balance,isadmin,province,city,address,gender,time,paypassword,telephone) " +
            "values(#{username},#{password},#{balance},#{isadmin},#{province},#{city},#{address},#{gender},#{time},#{paypassword},#{telephone})")
    public int insertUser(User user);

    @Update("update ur set username=#{username} where id=#{id}")
    public int updateUser(User user);

    @Select("select * from ur where username=#{username} and password=#{password}")
    User getUserByUsernameAndPassword(String username, String password);

    @Select("select * from ur where username=#{username}")
    User getUserByUsername(String username);
}
