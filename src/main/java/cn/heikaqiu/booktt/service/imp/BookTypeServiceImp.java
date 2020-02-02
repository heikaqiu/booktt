package cn.heikaqiu.booktt.service.imp;

import cn.heikaqiu.booktt.bean.BookType;
import cn.heikaqiu.booktt.mapper.BookTypeMapper;
import cn.heikaqiu.booktt.service.BookTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author HeiKaQiu
 * @create 2020-01-31 17:55
 */
@Service
public class BookTypeServiceImp implements BookTypeService {

    @Autowired
    private BookTypeMapper bookTypeMapper;


    @Override
    public List<BookType> getAllType() {
        return bookTypeMapper.getAllType();
    }
}
