package cn.heikaqiu.booktt.service.imp;

import cn.heikaqiu.booktt.bean.Author;
import cn.heikaqiu.booktt.bean.Book;
import cn.heikaqiu.booktt.bean.FindAuthorByInformation;
import cn.heikaqiu.booktt.bean.FindBookByInformation;
import cn.heikaqiu.booktt.mapper.BookMapper;
import cn.heikaqiu.booktt.mapper.OrderMapper;
import cn.heikaqiu.booktt.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author HeiKaQiu
 * @create 2020-01-31 18:42
 */
@Service
public class BookServiceImp implements BookService {

    @Autowired
    private BookMapper bookMapper;

    @Autowired
    private OrderMapper orderMapper;

    @Override
    public List<Book> getBookByType(Integer id,Integer index_page,Integer page_num) {
        if(index_page==null&&page_num==null){
            //查询所有不是分页查询
            return bookMapper.getBookByType(id);
        }
        else{
            //是分页查询
            return bookMapper.getBookByTypeLimit(id,index_page,page_num);
        }

    }

    @Override
    public Integer getBookNumByType(Integer id) {
        return bookMapper.getBookNumByType(id);
    }


    @Override
    public Book getBookInfoById(Integer id) {
        return bookMapper.getBookInfoById(id);
    }

    @Override
    public Integer getCountAllBookNum() {
        return bookMapper.getCountAllBookNum();
    }

    @Override
    public Long getCountAllBookRemainderNum() {
        List<Integer> bookRemainderList=bookMapper.getBookRemainder();
        Long AllBookRemainderNum=0l;
        if(bookRemainderList.size()>0){
            //找到了库存
            for (int i = 0; i <bookRemainderList.size() ; i++) {
                AllBookRemainderNum+=bookRemainderList.get(i);
            }
        }

        return AllBookRemainderNum;
    }

    @Override
    public Long getCountSellBookNum(Date start_time, Date last_time) {
        List<Integer> SellBookNumList= orderMapper.getCountSellBookNum(start_time,last_time);

        Long CountSellBookNum=0l;
        if(SellBookNumList.size()>0){
            //找到了库存
            for (int i = 0; i <SellBookNumList.size() ; i++) {
                CountSellBookNum+=SellBookNumList.get(i);
            }
        }

        return CountSellBookNum;
    }

    @Override
    public List<Book> getBookInfoLimit(Integer start_num, Integer page_num, FindBookByInformation findBookByInformation) {
        List<Book> list = new ArrayList<>();
        if (start_num == null || page_num == null) {
            return list;
        }
        if (findBookByInformation == null) {
            findBookByInformation = new FindBookByInformation();
        }
        list = bookMapper.getBookInfoLimit(start_num, page_num, findBookByInformation);

        return list;
    }

    @Override
    public Integer getBookByInformationNum(FindBookByInformation findBookByInformation) {
        Integer allCountBook = 0;
        if (findBookByInformation == null) {
            //查找所有的
            allCountBook = bookMapper.getCountAllBookNum();
        } else {
            //条件查找
            allCountBook = bookMapper.getAllCountBookByBookInfo(findBookByInformation);
        }
        return allCountBook;
    }


    @Override
    public Integer getIsshopBookNum(Boolean isshop) {
        if(isshop==null){
            isshop=false;
        }

        return bookMapper.getIsshopBookNum(isshop);
    }

    @Override
    public Book getBookInfoByBookId(Integer bookId) {
        // 需要查找什么呢  书所有的基本信息  以及几个人把他加入了收藏夹  几个人把它加入了 购物车， 总计售出几本
        //此书的评论 以及 回复
        Book book=bookMapper.getBookInfoByBookId(bookId);
        return book;
    }

    @Override
    public boolean addBook(Book book) {

        Book book1=bookMapper.getBookByBookName(book.getName());
        if(book1!=null){
            //表示有书了
            return false;
        }
        Integer bookid=bookMapper.addBook(book);
        if(bookid!=null && bookid>0){
            return true;
        }else{
            return false;
        }

    }

    @Override
    public boolean updateBook(Book book) throws Exception {
        Book book1=bookMapper.getBookByBookName(book.getName());
        if(book1!=null&& book.getId()!=book1.getId()){
            //表示有书了
            return false;
        }
        Integer line=bookMapper.updateBook(book);
        System.out.println("line: " + line);
        if (line == 1) {
            return true;
        } else {
            throw new Exception("修改图书信息错误");

        }
    }

    @Override
    public Boolean updateBookisshop(Integer bookid, boolean isshop) {
        bookMapper.updateBookisshopByBookid(bookid,isshop);
        return true;
    }

    @Override
    public Float getAverageStarByBookId(Integer id) {
        Integer Num=bookMapper.getCountStar(id);
        if(Num==null||Num==0){
            //没人参与评论
            return null;
        }
        Integer starNum=bookMapper.getAllStar(id);
        return Float.valueOf(starNum)/Float.valueOf(Num);


    }
}
