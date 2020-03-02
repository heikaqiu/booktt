package cn.heikaqiu.booktt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//启动

//@MapperScan(value = "cn.heikaqiu.booktt.mapper")
@SpringBootApplication
//@EnableAutoConfiguration
public class BookttApplication {

    public static void main(String[] args) {
        try {
            SpringApplication.run(BookttApplication.class, args);
        }catch (Exception e){
            System.out.println("<-------------------->"+e.getMessage());
        }

    }

}
