package cn.heikaqiu.booktt.mapper;

import cn.heikaqiu.booktt.bean.Book;
import cn.heikaqiu.booktt.bean.Comment;
import cn.heikaqiu.booktt.bean.FindUserByInformation;
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


    @Select("select *,id uid from ur where id=#{id}")
    @Results({
            //获取收藏的书
            @Result(column = "uid", property = "booksCollection", many = @Many(select = "cn.heikaqiu.booktt.mapper.CollectionMapper.getAllCollectionBookById")),
            //用户购物车中的商品
            @Result(column = "uid", property = "books", many = @Many(select = "cn.heikaqiu.booktt.mapper.ShopcartMapper.getAllCartBookById")),
            //用户的评价
            @Result(column = "uid", property = "comments", many = @Many(select = "cn.heikaqiu.booktt.mapper.CommentMapper.getAllCommentsByUserId")),
            //用户的回复
            @Result(column = "uid", property = "replys", many = @Many(select = "cn.heikaqiu.booktt.mapper.ReplyMapper.getAllReplysByUserId"))
    })
    public User getUserAllInfoById(Integer id);


    @Select("select *,id uid from ur where id=#{user_id}")
    @Results({
            @Result(column = "uid", property = "booksCollection", many = @Many(select = "cn.heikaqiu.booktt.mapper.CollectionMapper.getAllCollectionBookById"))
    })
    public User getUserAndCollectionBookById(Integer user_id);

    @Delete("delete from ur where id=#{id}")
    public int deleteUserById(Integer id);

    /**
     * 注册
     * 返回的是行数啊 不是id TODO
     * @param user
     * @return
     */
    @Options(useGeneratedKeys = true, keyProperty = "id")
    @Insert("insert into ur(username,password,balance,isadmin,province,city,address,gender,time,paypassword,telephone,lastusetime,img) " +
            "values(#{username},#{password},#{balance},#{isadmin},#{province},#{city},#{address},#{gender},#{time},#{paypassword},#{telephone},#{lastusetime},#{img})")
    public int insertUser(User user);

    @Update("update ur set username=#{username} where id=#{id}")
    public int updateUser(User user);

    /**
     * 登录
     *
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
     *
     * @param userId
     * @return
     */
    @Select("select balance from ur where id=#{userId}")
    String getBalanceByUserId(Integer userId);

    /**
     * 通过用户id更改余额
     *
     * @param userId
     * @param v
     */
    @Update("UPDATE ur SET balance = balance-#{v} WHERE id = #{userId}")
    void updateUserBalance(Integer userId, float v);

    /**
     * 查找用户的支付密码
     *
     * @param userid
     * @return
     */
    @Select("select paypassword from ur where id=#{userId}")
    String getPaypasswordByUserid(Integer userid);

    @Update("UPDATE ur SET username = #{username},province=#{province},city=#{city},address=#{address},gender=#{gender},telephone=#{telephone} WHERE id = #{id}")
    Integer updateUserInformationByUser(User user);

    /**
     * 通过用户id查看密码
     *
     * @param userid
     * @return
     */
    @Select("select password from ur where id=#{userId}")
    String getUserPasswordByUserid(Integer userid);

    @Update("UPDATE ur SET password = #{password},paypassword=#{paypassword} WHERE id = #{userid}")
    Integer updateUserPasswordByUserid(Integer userid, String password, String paypassword);

    @Update("UPDATE ur SET lastusetime = #{lastusetime} WHERE id = #{userid}")
    void updateLastUseTime(Integer userid, Date lastusetime);


    @Select("select count(*) from ur")
    Long getAllCountUser();

    /**
     * 获取任意时间段内的活跃用户
     *
     * @param start_time
     * @param last_time
     * @return
     */
    @Select("select count(*) from ur where lastusetime<#{last_time} AND lastusetime >= #{start_time}")
    Long getAnydayLastUserCountUser(Date start_time, Date last_time);


    //&lt; 小于
    //&gt; 大于
    @Select({
            "<script>",
            "select * from  ur <where>",
            "<if test='userByInformation.start_id != null'>         and     id &gt;= #{userByInformation.start_id}          </if>",
            "<if test='userByInformation.last_id != null'>          and     id &lt;= #{userByInformation.last_id}           </if>",
            "<if test='userByInformation.username != null'>         and     username like #{userByInformation.username}        </if>",
            "<if test='userByInformation.start_balance != null'>    and     balance &gt;= #{userByInformation.start_balance}</if>",
            "<if test='userByInformation.last_balance != null'>     and     balance &lt;= #{userByInformation.last_balance} </if>",
            "<if test='userByInformation.isadmin != null'>          and     isadmin=#{userByInformation.isadmin}            </if>",
            "<if test='userByInformation.gender != null'>           and     gender=#{userByInformation.gender}              </if>",
            "<if test='userByInformation.province != null'>         and     province=#{userByInformation.province}          </if>",
            "<if test='userByInformation.city != null'>             and     city=#{userByInformation.city}                  </if>",
            "<if test='userByInformation.telephone != null'>        and     telephone=#{userByInformation.telephone}        </if>",
            "<if test='userByInformation.start_time != null and userByInformation.last_time != null'>       and time  &lt;  #{userByInformation.last_time} and time &gt;= #{userByInformation.start_time}</if>",
            "<if test='userByInformation.start_usetime != null and userByInformation.last_usetime != null'> and lastusetime  &lt;= #{userByInformation.last_usetime} and lastusetime &gt;= #{userByInformation.start_usetime}</if>",
            "</where> limit #{start_num},#{page_num}",
            "</script>"
    })
    List<User> getUserInfoLimit(Integer start_num, Integer page_num, FindUserByInformation userByInformation);

    @Select({
            "<script>",
            "select count(*) from  ur <where>",
            "<if test='start_id != null'> and id &gt;= #{start_id}</if>",
            "<if test='last_id != null'> and id &lt;= #{last_id}</if>",
            "<if test='username != null'> and  username like #{username}</if>",
            "<if test='start_balance != null'> and  balance &gt;= #{start_balance}</if>",
            "<if test='last_balance != null'> and  balance &lt;= #{last_balance}</if>",
            "<if test='isadmin != null'> and  isadmin=#{isadmin}</if>",
            "<if test='gender != null'> and  gender=#{gender}</if>",
            "<if test='province != null'> and  province=#{province}</if>",
            "<if test='city != null'> and  city=#{city}</if>",
            "<if test='telephone != null'> and  telephone=#{telephone}</if>",
            "<if test='start_time != null and last_time != null'> and time  &lt;  #{last_time} and time &gt;= #{start_time}</if>",
            "<if test='start_usetime != null and last_usetime != null'> and lastusetime  &lt;= #{last_usetime} and lastusetime &gt;= #{start_usetime}</if>",
            "</where> ",
            "</script>"
    })
    Long getAllCountUserByUserInfo(FindUserByInformation userByInformation);


    @Update("UPDATE ur SET username = #{username},password=#{password},province=#{province},city=#{city},address=#{address},gender=#{gender},telephone=#{telephone},paypassword=#{paypassword} WHERE id = #{id}")
    Integer updateUserAllInformationByUser(User user);

    @Update("UPDATE ur SET img=#{s} WHERE id = #{id}")
    void updateUserImg(String s,Integer id);

    @Select("select * from ur where telephone=#{telephone}")
    User getUserByTele(String telephone);
}
