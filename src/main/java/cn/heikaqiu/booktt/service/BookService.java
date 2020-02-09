package cn.heikaqiu.booktt.service;

import cn.heikaqiu.booktt.bean.Book;

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


}
