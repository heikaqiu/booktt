package cn.heikaqiu.booktt.mapper;

import cn.heikaqiu.booktt.bean.Book;
import cn.heikaqiu.booktt.bean.Shopcart;
import cn.heikaqiu.booktt.bean.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author HeiKaQiu
 * @create 2020-02-04 16:51
 */
@Component
public interface ShopcartMapper {

    /**
     * 添加一种书到购物车中
     *
     * @param userId
     * @param bookId
     * @param buyNum
     */
    @Insert("insert into shopcart(user_id,book_id,number) values(#{userId},#{bookId},#{buyNum})")
    void addShopCart(Integer userId, Integer bookId, Integer buyNum);

    /**
     * 查询用户添加这本书的数量
     *
     * @param userId
     * @param bookId
     * @return
     */
    @Select("select number from shopcart where user_id=#{userId} AND book_id=#{bookId} ")
    Integer selectNumberShopCart(Integer userId, Integer bookId);

    /**
     * 改变购物车的数量
     *
     * @param userId
     * @param bookId
     * @param buyNum
     */
    @Update("update shopcart set number=number+#{buyNum} where user_id=#{userId} AND book_id=#{bookId}")
    void updateNumForShopCart(Integer userId, Integer bookId, Integer buyNum);

    /**
     * 通过用户ID获取购物车信息
     *
     * @param id
     * @return
     * @Results（{@Result（），@Result（）}）
     */
    @Select("select * from shopcart where user_id=#{id}")
    @Results({
            @Result(column = "user_id", property = "user", one = @One(select = "cn.heikaqiu.booktt.mapper.UserMapper.getUserById")),
            @Result(column = "book_id", property = "book", one = @One(select = "cn.heikaqiu.booktt.mapper.BookMapper.getBookInfoById"))}
    )
    List<Shopcart> getShopcartByUserId(Integer id);

    /**
     * 通过购物车的id 删除一条
     * @param shopcart_id
     * @return
     */
    @Delete("Delete from shopcart where id=#{shopcart_id}")
    Integer delectShopcartById(Integer shopcart_id);

    /**
     * 通过用户id和书本id删除
     * @param userId
     * @param bookId
     * @return
     */
    @Delete("Delete from shopcart where user_id=#{userId} AND book_id=#{bookId}")
    Integer delectShopcartByUseridAndBookid(Integer userId, Integer bookId);


    @Select("select book.* from shopcart  join book on shopcart.book_id=book.id where  user_id=#{user_id}")
    @Results(@Result(column = "author_id", property = "author", one = @One(select = "cn.heikaqiu.booktt.mapper.AuthorMapper.getAuthorById")))
    List<Book> getAllCartBookById(Integer user_id);


    @Select("select * from ur  join shopcart on shopcart.user_id=ur.id where  book_id=#{bookid}")
    List<User> getUserToShopcartByBookId(Integer bookid);
}
