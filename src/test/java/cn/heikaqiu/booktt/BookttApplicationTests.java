package cn.heikaqiu.booktt;

import cn.heikaqiu.booktt.bean.Book;
import cn.heikaqiu.booktt.bean.BookType;
import cn.heikaqiu.booktt.mapper.BookMapper;
import cn.heikaqiu.booktt.mapper.UserMapper;
import cn.heikaqiu.booktt.service.BookTypeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sound.midi.Soundbank;
import java.util.List;

@SpringBootTest
class BookttApplicationTests {

    @Autowired
    private BookTypeService bookTypeService;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private BookMapper bookMapper;
    @Test
    void contextLoads() {
        List<BookType> allType = bookTypeService.getAllType();
        System.out.println(allType);

    }

    @Test
    void contextLoads1() {
        Book remainderAndPriceByBookId = bookMapper.getRemainderAndPriceByBookId(1);
        System.out.println(remainderAndPriceByBookId.getPrice()+"----"+remainderAndPriceByBookId.getRemainder());
        bookMapper.updateBookRemainder(1,2);
        Book remainderAndPriceByBookId1 = bookMapper.getRemainderAndPriceByBookId(1);
        System.out.println(remainderAndPriceByBookId1.getPrice()+"----"+remainderAndPriceByBookId1.getRemainder());

    }

    @Test
    void contextLoads2() {
        String Balance = userMapper.getBalanceByUserId(1);
        System.out.println("1-"+Balance);
        userMapper.updateUserBalance(1,10.03f);
        String Balance2 = userMapper.getBalanceByUserId(1);
        System.out.println("2-"+Balance2);

    }

}
