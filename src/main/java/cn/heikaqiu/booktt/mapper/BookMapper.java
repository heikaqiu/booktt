package cn.heikaqiu.booktt.mapper;

import cn.heikaqiu.booktt.bean.Book;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author HeiKaQiu
 * @create 2020-01-31 18:43
 */
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
    Book getBookInfoById(Integer id);
}
