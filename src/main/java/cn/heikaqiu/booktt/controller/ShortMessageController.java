package cn.heikaqiu.booktt.controller;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * @author HeiKaQiu
 * @create 2020-03-01 13:51
 */
@Controller
public class ShortMessageController {

    //产品名称:云通信短信API产品,开发者无需替换
    static final String product = "Dysmsapi";
    //产品域名,开发者无需替换
    static final String domain = "dysmsapi.aliyuncs.com";

    // TODO 此处需要替换成开发者自己的AK(在阿里云访问控制台寻找)
    static final String accessKeyId = "*****************";
    static final String accessKeySecret = "******************";
    //签名名字
    private  String SignName="*******";
    //模板id
    private String TemplateCode="**********";

    @Autowired
    private HttpSession session;

//    @Autowired
//    private VerificationCodeMapper verificationCodeMapper;

    @RequestMapping("/getSsm")
    @ResponseBody
    public Map<String, Object> getSsm(String phoneNum) {

        //可自助调整超时时间
//        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
//        System.setProperty("sun.net.client.defaultReadTimeout", "10000");

        Map<String, Object> map = new HashMap<>();
        //随机生成六位验证码
        int code = (int) ((Math.random() * 9 + 1) * 1000);

        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
        IAcsClient client = new DefaultAcsClient(profile);
        JSONObject jsonObject = new JSONObject();
        //设置验证码
        jsonObject.put("code", code);
        map.put("code", code);
        //放入session域中 ，尝试半小时之内有效  或者放到数据库中
        session.setAttribute(phoneNum,code);
        CommonRequest request = new CommonRequest();
        request.setMethod(MethodType.POST);
        request.setDomain(domain);
        request.setVersion("2017-05-25");
        request.setAction("SendSms");
        request.putQueryParameter("RegionId", "cn-hangzhou");
        //设置短信号码
        request.putQueryParameter("PhoneNumbers", phoneNum);
        //签名名字
        request.putQueryParameter("SignName", SignName);
        //模版ID
        request.putQueryParameter("TemplateCode", TemplateCode);
        //设置短信验证码  json格式
        request.putQueryParameter("TemplateParam", jsonObject.toJSONString());
        try {
            CommonResponse response = client.getCommonResponse(request);
            System.out.println(response.getData());
        } catch (ClientException e) {
            e.printStackTrace();
        }

        return map;
    }
}
