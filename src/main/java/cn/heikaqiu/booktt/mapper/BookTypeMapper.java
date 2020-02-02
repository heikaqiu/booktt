package cn.heikaqiu.booktt.mapper;

import cn.heikaqiu.booktt.bean.BookType;
import cn.heikaqiu.booktt.bean.User;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author HeiKaQiu
 * @create 2020-01-31 17:57
 */
public interface BookTypeMapper {

    @Select("select * from booktype")
    public List<BookType> getAllType();
}
