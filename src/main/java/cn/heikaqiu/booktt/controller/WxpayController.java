package cn.heikaqiu.booktt.controller;

import cn.heikaqiu.booktt.bean.Order;
import cn.heikaqiu.booktt.bean.OrderContent;
import cn.heikaqiu.booktt.config.OtherConfig;
import cn.heikaqiu.booktt.service.OrderService;
import com.alibaba.fastjson.JSONObject;
import com.github.wxpay.sdk.WXPayUtil;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author HeiKaQiu
 * @create 2020-02-28 19:26
 */
@Controller
@RequestMapping("/wxpay")
public class WxpayController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private OtherConfig otherConfig;

    @RequestMapping("/goWxpay")
    public @ResponseBody  Object goWxpay(HttpServletRequest request, @RequestParam(value="orderid") String orderid) {

        System.out.println(orderid);
        Order order = orderService.getOrderInfoByOrderId(Long.valueOf(orderid));
        System.out.println(order);
        System.out.println("-------wxpay----goWxpay");
        Map<String, String> requestDataMap = new HashMap<>();

        requestDataMap.put("appid", "*********");//公众账号Id
        requestDataMap.put("mch_id", "*********");//微信支付分配的商户号
        //生成随机字符串
        String nonceStr = WXPayUtil.generateNonceStr();
        requestDataMap.put("nonce_str", nonceStr);//随机字符串

        List<OrderContent> orderContents = order.getOrderContents();
        String body = orderContents.get(0).getBook().getName();
        for (int i = 1; i < orderContents.size(); i++) {
            body += "," + orderContents.get(i).getBook().getName();
        }

        requestDataMap.put("body", body);//商品描述
        requestDataMap.put("out_trade_no", order.getId().toString());//商户订单号
        System.out.println(Math.round(order.getTotalPrice()*100));
        requestDataMap.put("total_fee", Math.round(order.getTotalPrice()*100)+"");//订单总金额，单位为分
        InetAddress localHost = null;
        String hostAddress = "";
        try {
            localHost = InetAddress.getLocalHost();
            hostAddress = localHost.getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        requestDataMap.put("spbill_create_ip", hostAddress);//Native支付填调用微信支付API的机器IP 支持IPV4和IPV6两种格式的IP地址。用户的客户端IP
        requestDataMap.put("notify_url", "http://118.178.190.156/wxpay/wxpayNotify");//异步接收微信支付结果通知的回调地址，通知url必须为外网可访问的url，不能携带参数。
        requestDataMap.put("trade_type", "NATIVE");//交易类型 JSAPI -JSAPI支付 NATIVE -Native支付 APP -APP支付
        requestDataMap.put("product_id", order.getId().toString());//商品ID  trade_type=NATIVE时，此参数必传。此参数为二维码中包含的商品ID，商户自行定义。

        String signValue="";
        //生成签名值
        try {
            signValue=WXPayUtil.generateSignature(requestDataMap,"***************");
        } catch (Exception e) {
            e.printStackTrace();
        }
        requestDataMap.put("sign",signValue);//签名
        //将类型为map的参数转换为xml格式
        String requestDataXml ="";
        try {
            requestDataXml = WXPayUtil.mapToXml(requestDataMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //发送调用统一下单API接口，响应的是xml格式的结果
        String responseDataXml = OtherConfig.doPostByXml("https://api.mch.weixin.qq.com/pay/unifiedorder", requestDataXml);
        System.out.println(responseDataXml);
        Map<String, String> responseDataMap=new HashMap<>();
        try {
             responseDataMap = WXPayUtil.xmlToMap(responseDataXml);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return responseDataMap;
    }




    //支付成功
    @RequestMapping("/wxpayNotify")
    public @ResponseBody  Object wxpayNotityUrl() {
        return null;
    }


    /**
     * 去微信支付方式页面
     */
    @RequestMapping("/goWxPay1/{orderid}")
    public void goWxPay1(@PathVariable("orderid") String orderid, HttpServletResponse response,HttpServletRequest request) {

        //调用 微信支付接口

        //封装参数
        Map<String,Object> paramMap=new ConcurrentHashMap<>();
        paramMap.put("orderid",orderid);

        //调用goWxpay方法
        //获取域名
        String serverName = request.getServerName();
        String url="http://"+serverName+"/wxpay/goWxpay";
        System.out.println(url);
        //微信支付接口返回的是json格式的字符串 就是https://api.mch.weixin.qq.com/pay/unifiedorder  返回的结果
        String result= otherConfig.doPostEncoded(url,paramMap,"UTF-8");
        System.out.println("result:"+result);

        //解析result
        JSONObject jsonObject1 = JSONObject.parseObject(result);

        //获取return_code
        String return_code=jsonObject1.getString("return_code");

        //判断通信是否成功
        if("SUCCESS".equals(return_code)){

            //获取result_code业务处理结果
            String result_code=jsonObject1.getString("result_code");
            //判断业务处理结果
            if("SUCCESS".equals(result_code)){
                //获取code_url
                String code_url=jsonObject1.getString("code_url");
                //将code_url值生成二维码图片
                //二维码的宽高
                int width=300;
                int hight=300;

                //创建map集合
                Map<EncodeHintType,Object> hints=new HashMap<>();
                hints.put(EncodeHintType.CHARACTER_SET,"UTF-8");
                try {
                    //创建一个矩阵对象
                    BitMatrix bitMatrix=new MultiFormatWriter().encode(code_url, BarcodeFormat.QR_CODE,width,hight,hints);

                    //将矩阵对象生成一个图片  本来是writeToPath 到一个路径下的 现在不了  现在使用输出流
                    //创建一个字节数组输出流
                    ByteArrayOutputStream imageOut=new ByteArrayOutputStream();
                    //将矩阵对象转换为流响应到页面
                    MatrixToImageWriter.writeToStream(bitMatrix,"jpg",imageOut);

                    //字节数组输入流
                    ByteArrayInputStream imageIn=new ByteArrayInputStream(imageOut.toByteArray());

                    //创建图片缓存对象
                    BufferedImage bImage= ImageIO.read(imageIn);

                    //输出流对象
                    OutputStream outputStream= response.getOutputStream();
                    ImageIO.write(bImage,"jpg",outputStream);

                    bImage.flush();
                    outputStream.flush();
                    outputStream.close();
                } catch (WriterException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }else{
                String contextPath=request.getContextPath();
                try {
                    //支付失败  TODO
                    response.sendRedirect(contextPath+"/payError.html");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }else{
            String contextPath=request.getContextPath();
            try {
                //支付失败  TODO
                response.sendRedirect(contextPath+"/payError.html");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
