package cn.heikaqiu.booktt.mapper;

import cn.heikaqiu.booktt.bean.Author;
import org.apache.ibatis.annotations.Select;

/**
 * @author HeiKaQiu
 * @create 2020-02-01 15:41
 */
public interface AuthorMapper {

    @Select("select * from author where id=#{id}")
    public Author getAuthorById(Integer id);
}
