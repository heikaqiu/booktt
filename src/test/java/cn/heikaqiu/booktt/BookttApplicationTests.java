package cn.heikaqiu.booktt;

import cn.heikaqiu.booktt.bean.Book;
import cn.heikaqiu.booktt.bean.BookType;
import cn.heikaqiu.booktt.config.OtherConfig;
import cn.heikaqiu.booktt.config.WxpayConfig;
import cn.heikaqiu.booktt.mapper.BookMapper;
import cn.heikaqiu.booktt.mapper.UserMapper;
import cn.heikaqiu.booktt.service.BookTypeService;
import com.alibaba.druid.util.HttpClientUtils;
import com.alibaba.fastjson.JSONObject;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sound.midi.Soundbank;
import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
class BookttApplicationTests {

    @Autowired
    private BookTypeService bookTypeService;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private BookMapper bookMapper;

    @Autowired
    private OtherConfig otherConfig;

    @Autowired
    private WxpayConfig wxpayConfig;




    @Test
    void testHttpClientUtils() {


    }
    @Test
    void testgenerateQRCode() {
        JSONObject jsonObject = new JSONObject();

        //2.给寄送Object对象中存放数据
        jsonObject.put("company","http://booktt.heikaqiu.cn");
        jsonObject.put("companyName","booktt");
        jsonObject.put("author","heikaqiu");
        jsonObject.put("address","杭州");
//        String s = wxpayConfig.generateQRCode(jsonObject);
//        System.out.println(s);
    }



    /**
     * 生成二维码
     */
    @Test
    void generateQRCode() {
       //定义一个json格式的字符串，使用fastJson
        //1.创建一个jsonObject对象
        JSONObject jsonObject = new JSONObject();

        //2.给寄送Object对象中存放数据
        jsonObject.put("company","http://booktt.heikaqiu.cn");
        jsonObject.put("companyName","booktt");
        jsonObject.put("author","heikaqiu");
        jsonObject.put("address","杭州");

        //3.json对象转为json格式的字符串
        String jsonString = jsonObject.toString();
        System.out.println(jsonString);


        //二维码的宽高
        int width=200;
        int hight=200;

        //创建map集合
        Map<EncodeHintType,Object> hints=new HashMap<>();
        hints.put(EncodeHintType.CHARACTER_SET,"UTF-8");



        try {
            //创建一个矩阵对象
            BitMatrix bitMatrix=new MultiFormatWriter().encode("weixin://wxpay/bizpayurl?pr=ApUFeAa", BarcodeFormat.QR_CODE,width,hight,hints);
            //生成的路径
            String filePath="D://";
            String fileName="QRCode.jpg";

            //创建一个路径对象
            Path path= FileSystems.getDefault().getPath(filePath,fileName);

            //将矩阵对象生成一个图片
            MatrixToImageWriter.writeToPath(bitMatrix,"jpg",path);
        } catch (WriterException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("成功生成二维码图片");


    }

    @Test
    void testnationality() {
        List<String> getnationality = otherConfig.getnationality();
        System.out.println(getnationality.size());
        System.out.println(getnationality);
    }


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

    @Test
    void textGetTime(){
        Map<String, Long> time = otherConfig.getTime();
        Long today_zero = time.get("today_zero");
        Date date = new Date(today_zero);
        System.out.println(date);


    }

}
