package cn.heikaqiu.booktt.service.imp;

import cn.heikaqiu.booktt.bean.Author;
import cn.heikaqiu.booktt.bean.FindAuthorByInformation;
import cn.heikaqiu.booktt.bean.User;
import cn.heikaqiu.booktt.mapper.AuthorMapper;
import cn.heikaqiu.booktt.mapper.BookMapper;
import cn.heikaqiu.booktt.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author HeiKaQiu
 * @create 2020-02-20 15:46
 */
@Service
public class AuthorServiceImp implements AuthorService {

    @Autowired
    private AuthorMapper authorMapper;

    @Autowired
    private BookMapper bookMapper;

    @Override
    public Integer getCountAuthor() {
        Integer authorNum= authorMapper.getCountAuthor();
        if(authorNum==null){
            authorNum=0;
        }
        return authorNum;
    }

    @Override
    public List<Author> getAuthorInfoLimit(Integer start_num, Integer page_num, FindAuthorByInformation findAuthorByInformation) {
        List<Author> list = new ArrayList<>();
        if (start_num == null || page_num == null) {
            return list;
        }
        if (findAuthorByInformation == null) {
            findAuthorByInformation = new FindAuthorByInformation();
        }
        list = authorMapper.getAuthorInfoLimit(start_num, page_num, findAuthorByInformation);

        return list;
    }

    @Override
    public Integer getAuthorByInformationNum(FindAuthorByInformation findAuthorByInformation) {
        Integer allCountAuthor = 0;
        if (findAuthorByInformation == null) {
            //查找所有的
            allCountAuthor = authorMapper.getCountAuthor();
        } else {
            //条件查找
            allCountAuthor = authorMapper.getAllCountAuthorByAuthorInfo(findAuthorByInformation);
        }
        return allCountAuthor;
    }

    @Override
    public Author getAuthorInfoByAuthorId(Integer authorId) {
        return authorMapper.getAuthorInfoByAuthorId(authorId);
    }

    @Override
    public  Boolean updateauthorisshop(Integer authorid,boolean isshop){
        //级联操作 先删除书本 但是书本可能会在 收藏夹购物车 订单中  不好删
        //所以 这里我们只是将此作者的书全部下架  以至于用户不能进行购买
        bookMapper.updateBookisshopByAuthorid(authorid,isshop);
        return true;
    }

    @Override
    public boolean addAuthor(Author author) {
        if(author==null){
            return false;
        }
        Author author1=authorMapper.getAuthorByname(author.getName());
        if(author1==null){
            //表示没有重复名字

            authorMapper.addAuthor(author);
            return true;
        }else{
            return false;
        }

    }

    @Override
    public boolean updateAuthorInformation(Author author) throws Exception {
        Author author1 = authorMapper.getAuthorByname(author.getName());
        if (author1 != null&& author1.getId()!=author.getId()) {
            return false;//名字已经有人注册了
        }
        Integer line = authorMapper.updateAuthorInformation(author);
        System.out.println("line: " + line);
        if (line == 1) {
            return true;
        } else {
            throw new Exception("修改作者错误");

        }
    }

    @Override
    public List<Author> getAllAuthor() {
        return authorMapper.getAllAuthor();
    }

}
