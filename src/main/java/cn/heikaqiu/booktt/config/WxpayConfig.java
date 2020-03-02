package cn.heikaqiu.booktt.config;

import com.alibaba.fastjson.JSONObject;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author HeiKaQiu
 * @create 2020-02-28 17:58
 */
@Configuration
public class WxpayConfig {

    /**
     * 生成二维码  并返回二维码文件的位置
     */
    public String generateQRCode(JSONObject jsonObject) {
        //定义一个json格式的字符串，使用fastJson
        //1.创建一个jsonObject对象
//        JSONObject jsonObject = new JSONObject();
//
//        //2.给寄送Object对象中存放数据
//        jsonObject.put("company","http://booktt.heikaqiu.cn");
//        jsonObject.put("companyName","booktt");
//        jsonObject.put("author","heikaqiu");
//        jsonObject.put("address","杭州");

        //3.json对象转为json格式的字符串
        String jsonString = jsonObject.toString();
        System.out.println(jsonString);


        //二维码的宽高
        int width=200;
        int hight=200;

        //创建map集合
        Map<EncodeHintType,Object> hints=new HashMap<>();
        hints.put(EncodeHintType.CHARACTER_SET,"UTF-8");
        Date now=new Date();


        try {
            //创建一个矩阵对象
            BitMatrix bitMatrix=new MultiFormatWriter().encode(jsonString, BarcodeFormat.QR_CODE,width,hight,hints);

            //生成的路径
            String filePath="F:/IDEAProject/booktt/src/main/resources/static/image/QRCodeImg/";
            String fileName=now.getTime()+".jpg";

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

        return now.getTime()+".jpg";
    }
}
