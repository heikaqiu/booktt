package cn.heikaqiu.booktt;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//启动

@SpringBootApplication
@MapperScan(value = "cn.heikaqiu.booktt.mapper")
public class BookttApplication {

    public static void main(String[] args) {
        try {
            SpringApplication.run(BookttApplication.class, args);
        }catch (Exception e){
            System.out.println("<-------------------->"+e.getMessage());
        }

    }

}
