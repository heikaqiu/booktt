package cn.heikaqiu.booktt.service.imp;

import cn.heikaqiu.booktt.bean.Book;
import cn.heikaqiu.booktt.mapper.BookMapper;
import cn.heikaqiu.booktt.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author HeiKaQiu
 * @create 2020-01-31 18:42
 */
@Service
public class BookServiceImp implements BookService {

    @Autowired
    private BookMapper bookMapper;

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
}
