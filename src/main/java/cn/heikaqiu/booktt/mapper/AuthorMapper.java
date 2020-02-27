package cn.heikaqiu.booktt.mapper;

import cn.heikaqiu.booktt.bean.Author;
import cn.heikaqiu.booktt.bean.FindAuthorByInformation;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author HeiKaQiu
 * @create 2020-02-01 15:41
 */
@Component //为了解决service 使用Autowired 对象下面还是有红线 但不影响运行的问题
public interface AuthorMapper {

    @Select("select * from author where id=#{id}")
    public Author getAuthorById(Integer id);

    /**
     * 查找所有作者的总数
     * @return
     */
    @Select("select count(*) from author ")
    Integer getCountAuthor();

    //&lt; 小于
    //&gt; 大于
    @Select({
            "<script>",
            "select * from  author <where>",
            "<if test='findAuthorByInformation.start_id != null'>   and     id &gt;= #{findAuthorByInformation.start_id}          </if>",
            "<if test='findAuthorByInformation.last_id != null'>    and     id &lt;= #{findAuthorByInformation.last_id}           </if>",
            "<if test='findAuthorByInformation.name != null'>       and     name like #{findAuthorByInformation.name}        </if>",
            "<if test='findAuthorByInformation.nationality != null'>    and     nationality = #{findAuthorByInformation.nationality}</if>",
            "</where> limit #{start_num},#{page_num}",
            "</script>"
    })
    List<Author> getAuthorInfoLimit(Integer start_num, Integer page_num, FindAuthorByInformation findAuthorByInformation);


    @Select({
            "<script>",
            "select count(*) from  author <where>",
            "<if test='start_id != null'>   and     id &gt;= #{start_id}          </if>",
            "<if test='last_id != null'>    and     id &lt;= #{last_id}           </if>",
            "<if test='name != null'>       and     name like #{name}        </if>",
            "<if test='nationality != null'>    and     nationality = #{nationality}</if>",
            "</where> ",
            "</script>"
    })
    Integer getAllCountAuthorByAuthorInfo(FindAuthorByInformation findAuthorByInformation);


    @Select("select *,id aid from author where id=#{authorId}")
    @Results({
            //写的书
            @Result(column = "aid", property = "books", many = @Many(select = "cn.heikaqiu.booktt.mapper.BookMapper.getBookByAuthorId"))
    })
    Author getAuthorInfoByAuthorId(Integer authorId);

    /**
     * 查找作者通过 名字
     * @param name
     * @return
     */
    @Select("select * from author where name=#{name}")
    Author getAuthorByname(String name);

    @Insert("insert into author(name,synopsis,nationality) " +
            "values(#{name},#{synopsis},#{nationality})")
    void addAuthor(Author author);

    @Update({
            "<script>",
            "update author  <set>",
            "<if test='name != null'>   ,name=#{name}</if>",
            "<if test='synopsis != null'>    ,synopsis=#{synopsis}           </if>",
            "<if test='nationality != null'>       ,nationality=#{nationality}        </if>",
            "</set> where id=#{id}",
            "</script>"
    })
    Integer updateAuthorInformation(Author author);

    @Select("select * from author")
    List<Author> getAllAuthor();
}
