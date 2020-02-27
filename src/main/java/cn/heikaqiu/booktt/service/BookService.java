package cn.heikaqiu.booktt.service;

import cn.heikaqiu.booktt.bean.Book;
import cn.heikaqiu.booktt.bean.FindBookByInformation;

import java.util.Date;
import java.util.List;

/**
 * @author HeiKaQiu
 * @create 2020-01-31 18:40
 */
public interface BookService {

    /**
     * 获取指定类型的所有书本
     * @param id
     * @return
     */
    List<Book> getBookByType(Integer id,Integer index_num,Integer page_num);


    /**
     *获取此书本类型下书本的数量
     * @param id
     * @return
     */
    Integer getBookNumByType(Integer id);

    /**
     * 通过ID获取书本的信息
     * @param id
     * @return
     */
    Book getBookInfoById(Integer id);


    /**
     * 获取总共有多少种书
     * @return
     */
    Integer getCountAllBookNum();

    /**
     * 获取总共书有多少库存
     * @return
     */
    Long getCountAllBookRemainderNum();

    /**
     * 两个时间之间查找共卖出多少本书
     * @param start_time
     * @param last_time
     * @return
     */
    Long getCountSellBookNum(Date start_time, Date last_time);

    /**
     * 有条件 并分页查找图书
     * @param start_num
     * @param page_num
     * @param findBookByInformation
     * @return
     */
    List<Book> getBookInfoLimit(Integer start_num, Integer page_num, FindBookByInformation findBookByInformation);

    /**
     * 有条件的获取 符合书本的总数
     * @param findBookByInformation
     * @return
     */
    Integer getBookByInformationNum(FindBookByInformation findBookByInformation);


    Integer getIsshopBookNum(Boolean isshop);

    /**
     * 获取图书所有信息
     * @param bookId
     * @return
     */
    Book getBookInfoByBookId(Integer bookId);

    /**
     * 添加书本
     * @param book
     * @return
     */
    boolean addBook(Book book);

    /**
     * 修改图书信息
     * @param book
     * @return
     */
    boolean updateBook(Book book) throws Exception;

    Boolean updateBookisshop(Integer bookid, boolean isshop);

    /**
     * 获取平均星
     * @param id
     * @return
     */
    Float getAverageStarByBookId(Integer id);
}
