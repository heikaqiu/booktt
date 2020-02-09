package cn.heikaqiu.booktt.mapper;

import cn.heikaqiu.booktt.bean.Book;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author HeiKaQiu
 * @create 2020-01-31 18:43
 */
@Component
public interface BookMapper {

    /**
     * 通过书本类型id获取 此类型下所有的书本以及对应的作者
     * @param id
     * @return
     */
    @Select("select * from Book where booktype=#{id}")
    @Results(@Result(column="author_id",property="author",one=@One(select="cn.heikaqiu.booktt.mapper.AuthorMapper.getAuthorById")))
    List<Book> getBookByType(Integer id);

    @Select("select * from Book where booktype=#{id} limit #{index_page},#{page_num}")
    @Results(@Result(column="author_id",property="author",one=@One(select="cn.heikaqiu.booktt.mapper.AuthorMapper.getAuthorById")))
    List<Book> getBookByTypeLimit(Integer id, Integer index_page, Integer page_num);


    @Select("select count(*) from Book where booktype=#{id}")
    Integer getBookNumByType(Integer id);

    @Select("select * from Book where id=#{id}")
    @Results(@Result(column="author_id",property="author",one=@One(select="cn.heikaqiu.booktt.mapper.AuthorMapper.getAuthorById")))
    Book getBookInfoById(Integer id);

    /**
     * 通过书的id 查找库存和单价
     * @param bookId
     * @return
     */
    @Select("select remainder,price from book where id=#{bookId}")
    Book getRemainderAndPriceByBookId(Integer bookId);

    /**
     * 通过书本id更改书的库存
     * @param bookId
     * @param buyNum
     */
    @Update("UPDATE Book SET remainder = remainder-(#{buyNum}) WHERE id = #{bookId}")
    void updateBookRemainder(Integer bookId, Integer buyNum);

    /**
     * 查书的库存
     * @param bookId
     * @return
     */
    @Select("select remainder from book where id=#{bookId}")
    Integer getRemainderByBookId(Integer bookId);

    /**
     * 查书的单价
     * @param bookId
     * @return
     */
    @Select("select price from book where id=#{bookId}")
    String getPriceByBookId(Integer bookId);
}
