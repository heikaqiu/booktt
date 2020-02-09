package cn.heikaqiu.booktt.mapper;

import cn.heikaqiu.booktt.bean.Author;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

/**
 * @author HeiKaQiu
 * @create 2020-02-01 15:41
 */
@Component //为了解决service 使用Autowired 对象下面还是有红线 但不影响运行的问题
public interface AuthorMapper {

    @Select("select * from author where id=#{id}")
    public Author getAuthorById(Integer id);
}
