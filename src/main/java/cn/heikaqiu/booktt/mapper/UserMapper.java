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

//    private Integer id;
//    private String username;
//    private String password;
//    private Float balance;
//    private boolean isadmin; //管理员 1为是  0为否
//    private String address;
//    private boolean gender;//性别  1 为男    0为女
//    private Date time;
//    private String paypassword;
//    //用户收藏的书
//    private List<Book> booksCollection=new ArrayList<Book>();
//    //用户评价的书
//    private List<Book> booksComment=new ArrayList<Book>();
//    //用户回复的留言
//    private List<Comment> comments=new ArrayList<Comment>();
//    //用户购物车中的商品
//    private List<Book> books=new ArrayList<Book>();

    @Select("select * from ur where id=#{id}")
    public User getUserById(Integer id);

    @Delete("delete from ur where id=#{id}")
    public int deleteUserById(Integer id);

    @Options(useGeneratedKeys = true,keyProperty = "id")
    @Insert("insert into ur(username) values(#{username})")
    public int insertUser(User user);

    @Update("update ur set username=#{username} where id=#{id}")
    public int updateUser(User user);
}
