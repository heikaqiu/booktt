package cn.heikaqiu.booktt.mapper;

import cn.heikaqiu.booktt.bean.Book;
import cn.heikaqiu.booktt.bean.Comment;
import cn.heikaqiu.booktt.bean.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.Mapping;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


//指定这是一个操作数据库的mapper
//@Mapper
@Component
public interface UserMapper {

    @Select("select * from ur where id=#{id}")
    public User getUserById(Integer id);


    @Select("select *,id uid from ur where id=#{user_id}")
    @Results({
         @Result(column = "uid", property = "booksCollection", many = @Many(select = "cn.heikaqiu.booktt.mapper.CollectionMapper.getAllCollectionBookById"))
    })
    public User getUserAndCollectionBookById(Integer user_id);

    @Delete("delete from ur where id=#{id}")
    public int deleteUserById(Integer id);

    /**
     * 注册
     * @param user
     * @return
     */
    @Options(useGeneratedKeys = true,keyProperty = "id")
    @Insert("insert into ur(username,password,balance,isadmin,province,city,address,gender,time,paypassword,telephone) " +
            "values(#{username},#{password},#{balance},#{isadmin},#{province},#{city},#{address},#{gender},#{time},#{paypassword},#{telephone})")
    public int insertUser(User user);

    @Update("update ur set username=#{username} where id=#{id}")
    public int updateUser(User user);

    /**
     * 登录
     * @param username
     * @param password
     * @return
     */
    @Select("select * from ur where username=#{username} and password=#{password}")
    User getUserByUsernameAndPassword(String username, String password);

    @Select("select * from ur where username=#{username}")
    User getUserByUsername(String username);

    /**
     * 通过用户的id查找用户的余额
     * @param userId
     * @return
     */
    @Select("select balance from ur where id=#{userId}")
    String getBalanceByUserId(Integer userId);

    /**
     * 通过用户id更改余额
     * @param userId
     * @param v
     */
    @Update("UPDATE ur SET balance = balance-#{v} WHERE id = #{userId}")
    void updateUserBalance(Integer userId, float v);

    /**
     * 查找用户的支付密码
     * @param userid
     * @return
     */
    @Select("select paypassword from ur where id=#{userId}")
    String getPaypasswordByUserid(Integer userid);

    @Update("UPDATE ur SET username = #{username},province=#{province},city=#{city},address=#{address},gender=#{gender},telephone=#{telephone} WHERE id = #{id}")
    Integer updateUserInformationByUser(User user);

    /**
     * 通过用户id查看密码
     * @param userid
     * @return
     */
    @Select("select password from ur where id=#{userId}")
    String getUserPasswordByUserid(Integer userid);

    @Update("UPDATE ur SET password = #{password},paypassword=#{paypassword} WHERE id = #{userid}")
    Integer updateUserPasswordByUserid(Integer userid, String password, String paypassword);
}
