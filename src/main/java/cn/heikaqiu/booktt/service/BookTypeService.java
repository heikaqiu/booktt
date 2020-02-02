package cn.heikaqiu.booktt.service;

import cn.heikaqiu.booktt.bean.BookType;

import java.util.List;

/**
 * @author HeiKaQiu
 * @create 2020-01-31 7:53
 */
public interface BookTypeService {

    /**
     * 获取所有书本类型
     * @return
     */
    public List<BookType> getAllType();
}
