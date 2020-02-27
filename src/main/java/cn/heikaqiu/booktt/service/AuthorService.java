package cn.heikaqiu.booktt.service;

import cn.heikaqiu.booktt.bean.Author;
import cn.heikaqiu.booktt.bean.FindAuthorByInformation;

import java.util.List;

/**
 * @author HeiKaQiu
 * @create 2020-02-20 15:46
 */
public interface AuthorService {
    /**
     * 获取全部作者的数量
     * @return
     */
    Integer getCountAuthor();


    List<Author> getAuthorInfoLimit(Integer start_num, Integer page_num, FindAuthorByInformation findAuthorByInformation);


    Integer getAuthorByInformationNum(FindAuthorByInformation findAuthorByInformation);


    /**
     * 通过作者id获取所有的作者信息
     * @param authorId
     * @return
     */
    Author getAuthorInfoByAuthorId(Integer authorId);

    Boolean updateauthorisshop(Integer authorid,boolean isshop);

    /**
     * 添加作者
     * @param author
     * @return
     */
    boolean addAuthor(Author author);

    boolean updateAuthorInformation(Author author) throws Exception;

    List<Author> getAllAuthor();
}
