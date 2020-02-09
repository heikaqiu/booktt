package cn.heikaqiu.booktt.mapper;

import cn.heikaqiu.booktt.bean.Book;
import cn.heikaqiu.booktt.bean.Collection;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author HeiKaQiu
 * @create 2020-02-09 10:12
 */
@Component
public interface CollectionMapper {

    /**
     * 添加收藏
     *
     * @param user_id
     * @param book_id
     * @return
     */
    @Insert("insert into collection(user_id,book_id) values(#{user_id},#{book_id})")
    Integer andToCollection(Integer user_id, Integer book_id);


    /**
     * 是否收藏过
     *
     * @param user_id
     * @param book_id
     * @return
     */
    @Select("select * from collection where user_id=#{user_id} AND book_id=#{book_id} ")
    Collection isCollection(Integer user_id, Integer book_id);

    @Select("select * from collection where user_id=#{user_id}")
    @Results(@Result(column = "book_id", property = "book", one = @One(select = "cn.heikaqiu.booktt.mapper.BookMapper.getBookInfoById")))
    List<Collection> getAllCollectionById(Integer user_id);

    @Select("select book.* from collection  join book on collection.book_id=book.id where  user_id=#{user_id}")
    @Results(@Result(column = "author_id", property = "author", one = @One(select = "cn.heikaqiu.booktt.mapper.AuthorMapper.getAuthorById")))
    List<Book> getAllCollectionBookById(Integer user_id);

    @Delete("delete from collection where user_id=#{userid} AND book_id =#{bookid}")
    Integer deleteCollection(Integer userid, Integer bookid);
}
