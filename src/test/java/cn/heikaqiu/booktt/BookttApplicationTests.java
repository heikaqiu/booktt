package cn.heikaqiu.booktt;

import cn.heikaqiu.booktt.bean.BookType;
import cn.heikaqiu.booktt.service.BookTypeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class BookttApplicationTests {

    @Autowired
    private BookTypeService bookTypeService;

    @Test
    void contextLoads() {
        List<BookType> allType = bookTypeService.getAllType();
        System.out.println(allType);

    }

}
