package cn.heikaqiu.booktt.mapper;

import cn.heikaqiu.booktt.bean.Book;
import cn.heikaqiu.booktt.bean.FindBookByInformation;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author HeiKaQiu
 * @create 2020-01-31 18:43
 */
@Component
public interface BookMapper {

    /**
     * 通过书本类型id获取 此类型下所有的书本以及对应的作者  是在限售的
     *
     * @param id
     * @return
     */
    @Select("select * from Book where booktype=#{id} AND isshop=true" )
    @Results(@Result(column = "author_id", property = "author", one = @One(select = "cn.heikaqiu.booktt.mapper.AuthorMapper.getAuthorById")))
    List<Book> getBookByType(Integer id);

    //是在限售的
    @Select("select * from Book where booktype=#{id}  AND isshop=true limit #{index_page},#{page_num}")
    @Results(@Result(column = "author_id", property = "author", one = @One(select = "cn.heikaqiu.booktt.mapper.AuthorMapper.getAuthorById")))
    List<Book> getBookByTypeLimit(Integer id, Integer index_page, Integer page_num);

    //是在限售的
    @Select("select count(*) from  Book where booktype=#{id} AND isshop=true")
    Integer getBookNumByType(Integer id);

    @Select("select *,id bid from Book where id=#{id}")
    @Results({
            @Result(column = "booktype", property = "bookType", one = @One(select = "cn.heikaqiu.booktt.mapper.BookTypeMapper.getBookTypeByid")),
            @Result(column = "author_id", property = "author", one = @One(select = "cn.heikaqiu.booktt.mapper.AuthorMapper.getAuthorById")),
            //书的评论
            @Result(column = "bid", property = "comments", many = @Many(select = "cn.heikaqiu.booktt.mapper.CommentMapper.getAllCommentsByBookId")),

    })
    Book getBookInfoById(Integer id);

    /**
     * 通过书的id 查找库存和单价
     *
     * @param bookId
     * @return
     */
    @Select("select remainder,price from book where id=#{bookId}")
    Book getRemainderAndPriceByBookId(Integer bookId);

    /**
     * 通过书本id更改书的库存
     *
     * @param bookId
     * @param buyNum
     */
    @Update("UPDATE Book SET remainder = remainder-(#{buyNum}) WHERE id = #{bookId}")
    void updateBookRemainder(Integer bookId, Integer buyNum);

    /**
     * 查书的库存
     *
     * @param bookId
     * @return
     */
    @Select("select remainder from book where id=#{bookId}")
    Integer getRemainderByBookId(Integer bookId);

    /**
     * 查书的单价
     *
     * @param bookId
     * @return
     */
    @Select("select price from book where id=#{bookId}")
    String getPriceByBookId(Integer bookId);


    /**
     * 通过作者id查找Book
     *
     * @param authorId
     * @return
     */
    @Select("select * from book where author_id=#{authorId}")
    @Results(@Result(column = "booktype", property = "bookType", one = @One(select = "cn.heikaqiu.booktt.mapper.BookTypeMapper.getBookTypeByid")))
    List<Book> getBookByAuthorId(Integer authorId);

    @Update("UPDATE Book SET isshop = #{isShop} WHERE author_id = #{authorid}")
    void updateBookisshopByAuthorid(Integer authorid, boolean isShop);

    @Select("select count(*) from book ")
    Integer getCountAllBookNum();

    @Select("select remainder from book ")
    List<Integer> getBookRemainder();

    //&lt; 小于
    //&gt; 大于
    @Select({
            "<script>",
            "select * from  book <where>",
            "<if test='findBookByInformation.start_id != null'>   and     id &gt;= #{findBookByInformation.start_id}          </if>",
            "<if test='findBookByInformation.last_id != null'>    and     id &lt;= #{findBookByInformation.last_id}           </if>",
            "<if test='findBookByInformation.name != null'>       and     name like #{findBookByInformation.name}        </if>",
            "<if test='findBookByInformation.booktype != null'>   and    booktype = #{findBookByInformation.booktype}</if>",
            "<if test='findBookByInformation.author_id != null'>   and    author_id = #{findBookByInformation.author_id}</if>",
            "<if test='findBookByInformation.start_price != null'>   and    price &gt;= #{findBookByInformation.start_price}</if>",
            "<if test='findBookByInformation.last_price != null'>   and    price &lt;= #{findBookByInformation.last_price}</if>",
            "<if test='findBookByInformation.start_remainder != null'>   and    remainder &gt;= #{findBookByInformation.start_remainder}</if>",
            "<if test='findBookByInformation.last_remainder != null'>   and    remainder &lt;= #{findBookByInformation.last_remainder}</if>",
            "<if test='findBookByInformation.isshop != null'>   and    isshop = #{findBookByInformation.isshop}</if>",
            "</where> limit #{start_num},#{page_num}",
            "</script>"
    })
    @Results({
            @Result(column = "booktype", property = "bookType", one = @One(select = "cn.heikaqiu.booktt.mapper.BookTypeMapper.getBookTypeByid")),
            @Result(column = "author_id", property = "author", one = @One(select = "cn.heikaqiu.booktt.mapper.AuthorMapper.getAuthorById"))
    })
    List<Book> getBookInfoLimit(Integer start_num, Integer page_num, FindBookByInformation findBookByInformation);

    @Select({
            "<script>",
            "select count(*) from  book <where>",
            "<if test='start_id != null'>   and     id &gt;= #{start_id}          </if>",
            "<if test='last_id != null'>    and     id &lt;= #{last_id}           </if>",
            "<if test='name != null'>       and     name like #{name}        </if>",
            "<if test='booktype != null'>   and    booktype = #{booktype}</if>",
            "<if test='author_id != null'>   and    author_id = #{author_id}</if>",
            "<if test='start_price != null'>   and    price &gt;= #{start_price}</if>",
            "<if test='last_price != null'>   and    price &lt;= #{last_price}</if>",
            "<if test='start_remainder != null'>   and    remainder &gt;= #{start_remainder}</if>",
            "<if test='last_remainder != null'>   and    remainder &lt;= #{last_remainder}</if>",
            "<if test='isshop != null'>   and    isshop = #{isshop}</if>",
            "</where>",
            "</script>"
    })
    Integer getAllCountBookByBookInfo(FindBookByInformation findBookByInformation);

    @Select("select count(*) from book where isshop=#{isshop}")
    Integer getIsshopBookNum(Boolean isshop);


    // 需要查找什么呢  书所有的基本信息  以及几个人把他加入了收藏夹  几个人把它加入了 购物车， 总计售出几本
    //此书的评论 以及 回复
    @Select("select *,id bid from book where id=#{bookId}")
    @Results({
            @Result(column = "booktype", property = "bookType", one = @One(select = "cn.heikaqiu.booktt.mapper.BookTypeMapper.getBookTypeByid")),
            @Result(column = "author_id", property = "author", one = @One(select = "cn.heikaqiu.booktt.mapper.AuthorMapper.getAuthorById")),
            //收藏的用户
            @Result(column = "bid", property = "userCollection", many = @Many(select = "cn.heikaqiu.booktt.mapper.CollectionMapper.getUserToCollectionByBookId")),
            //加入购物车的用户
            @Result(column = "bid", property = "usersShopcart", many = @Many(select = "cn.heikaqiu.booktt.mapper.ShopcartMapper.getUserToShopcartByBookId")),
            //书的评论
            @Result(column = "bid", property = "comments", many = @Many(select = "cn.heikaqiu.booktt.mapper.CommentMapper.getAllCommentsByBookId")),
    })
    Book getBookInfoByBookId(Integer bookId);

    @Options(useGeneratedKeys = true, keyProperty = "id")
    @Insert("insert into book(name,synopsis,price,remainder,img,isshop,author_id,bookType) values(#{name},#{synopsis},#{price},#{remainder},#{img},#{isshop},#{author.id},#{bookType.typeid})")
    Integer addBook(Book book);

    @Select("select * from book where name=#{name}")
    Book getBookByBookName(String name);

    @Update({
            "<script>",
            "update book  <set>",
            "<if test='name != null'>   ,name=#{name}</if>",
            "<if test='synopsis != null'>    ,synopsis=#{synopsis}           </if>",
            "<if test='price != null'>       ,price=#{price}        </if>",
            "<if test='remainder != null'>       ,remainder=#{remainder}        </if>",
            "<if test='img != null'>       ,img=#{img}        </if>",
            "<if test='isshop != null'>       ,isshop=#{isshop}        </if>",
            "<if test='author!= null'>       ,author_id=#{author.id}        </if>",
            "<if test='bookType != null'>       ,booktype=#{bookType.typeid}        </if>",
            "</set> where id=#{id}",
            "</script>"
    })
    Integer updateBook(Book book);

    @Update("UPDATE Book SET isshop = #{isshop} WHERE id = #{bookid}")
    void updateBookisshopByBookid(Integer bookid, boolean isshop);

    @Select("select sum(star) from comment where book_id=#{id}")
    Integer getAllStar(Integer id);

    @Select("select count(*) from comment where book_id=#{id}")
    Integer getCountStar(Integer id);
}
