package cn.heikaqiu.booktt.config;

import cn.heikaqiu.booktt.bean.User;
import cn.heikaqiu.booktt.service.UserService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.HiddenHttpMethodFilter;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author HeiKaQiu
 * @create 2020-01-30 19:14
 */
@Configuration
public class OtherConfig {

    @Autowired
    private UserService userService;

    @Autowired
    private HttpSession session;


    /**
     * 重新将登录用户放到session中
     */
public void reloadLoginUser(){

    User login_user = (User)session.getAttribute("login_user");
    User userById = userService.getUserById(login_user.getId());
    session.setAttribute("login_user",userById);
}

    /**
     * 请求参数是XML格式的POST请求
     *
     * @param url
     * @param requestDataXml
     * @return
     */
    public static String doPostByXml(String url, String requestDataXml) {
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse httpResponse = null;

        //穿件httpClient连接对象
        httpClient = HttpClients.createDefault();
        //创建Post请求连接对象‘’
        HttpPost httpPost = new HttpPost(url);
        //创建连接请求参数对象，并设置连接参数
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(15000) //连接服务器主机超时时间
                .setConnectionRequestTimeout(60000)//连接请求超时时间
                .setSocketTimeout(60000)//设置读取相应数据超时时间
                .build();

        //为httpPost请求设置参数
        httpPost.setConfig(requestConfig);

        //将上传参数存放到entity属性中
        httpPost.setEntity(new StringEntity(requestDataXml, "UTF-8"));
        //添加头信息
        httpPost.addHeader("Content-Type", "text/xml");

        String result = "";
        try {
            //发送请求
            httpResponse = httpClient.execute(httpPost);

            //从响应对象中获取返回内容
            HttpEntity httpEntity = httpResponse.getEntity();
            result = EntityUtils.toString(httpEntity, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     *指定字符集的POST请求
     *
     * @return
     */
    public static String doPostEncoded(String url, Map<String, Object> paramMap, String charset) {
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse httpResponse = null;

        //穿件httpClient连接对象
        httpClient = HttpClients.createDefault();
        //创建Post请求连接对象‘’
        HttpPost httpPost = new HttpPost(url);
        //创建连接请求参数对象，并设置连接参数
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(15000) //连接服务器主机超时时间
                .setConnectionRequestTimeout(60000)//连接请求超时时间
                .setSocketTimeout(60000)//设置读取相应数据超时时间
                .build();

        //为httpPost请求设置参数
        httpPost.setConfig(requestConfig);

        //判断参数是否为空
        if (null != paramMap && paramMap.size() > 0) {
            List<NameValuePair> nvpsList = new ArrayList<>();
            //将map集成转换为Set集合
            Set<Map.Entry<String, Object>> entrySet = paramMap.entrySet();
            //通过EntrySet集合获取迭代器
            Iterator<Map.Entry<String, Object>> iterable = entrySet.iterator();
            //循环遍历
            while (iterable.hasNext()) {
                //遍历下一个
                Map.Entry<String, Object> mapEntry = iterable.next();
                nvpsList.add(new BasicNameValuePair(mapEntry.getKey(), mapEntry.getValue().toString()));
            }
            //将参数添加到post请求参数对象中
            //将上传参数存放到entity属性中
            try {
                httpPost.setEntity(new UrlEncodedFormEntity(nvpsList, charset));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        String result = "";
        try {
            //httpClient对象执行post请求，并返回响应参数对象
            httpResponse = httpClient.execute(httpPost);
            //从响应对象中获取响应内容
            HttpEntity entity = httpResponse.getEntity();
            result = EntityUtils.toString(entity);
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return result;
    }


    /**
     * <pre class="code"><b>{@code
     *   Title: 发送 get请求 已测试 可以传中文
     *
     * }</pre>
     *
     * @param url
     * @return 返回json格式的数据
     * @author haobin
     * @date 2018年7月16日 下午8:34:49
     */
    public static String get(String url) {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        String context = "";
        try {
            URL uRL = new URL(url);
            URI uRI = new URI(uRL.getProtocol(), uRL.getHost() + ":" + uRL.getPort(), uRL.getPath(), uRL.getQuery(),
                    null);
            // 创建httpget.
            HttpGet httpget = new HttpGet(uRI);
            System.out.println("executing request " + httpget.getURI());
            // 执行get请求.
            CloseableHttpResponse response = httpclient.execute(httpget);
            // 获取响应实体
            HttpEntity entity = response.getEntity();
            System.out.println("--------------------------------------");
            // 打印响应状态
            System.out.println(response.getStatusLine());
            if (entity != null) {
                // 打印响应内容 ，转换为utf-8格式，避免所传内容包含汉字乱码
                context = EntityUtils.toString(entity, "UTF-8");
                System.out.println(context);
            }
            response.close();
            return context;
        } catch (Exception e) {
            e.printStackTrace();
            return "Exception";
        } finally {
            // 关闭连接,释放资源
            try {
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * <pre class="code"><b>{@code
     *   Title:
     *   发送POST请求， 已测试，可以传递中文
     *   注意， 当服务器端返回的是空字符串时， 本方法返回值将会是null
     *
     *
     *   补充：
     * 如果 服务器提供方返回的json类似于：
     * {
     * "id": 1111,
     * "name": "嘿嘿",
     * "passwd": "123456",
     * "data": {
     * "aa": "第一",
     * "bb": "这是第二名"
     * }
     * }
     *
     * 则调用方想获取aa的值可以这样写：
     * JSONObject jSONObject = HttpUtils.doPost(url, XXXX);
     * if (null != jSONObject) {
     * System.out.println(jSONObject.getJSONObject("data").getString("aa")+ ",  " + jSONObject.getString("name"));
     * }
     *
     *
     * }</pre>
     *
     * @param url     url地址，随业务要求，可以附带中文参数
     * @param bodyMap POST Body要发送的数据， 以Map<String, String>格式出现；  如果没有，允许为null
     * @return 返回json格式的数据
     * @author haobin
     * @date 2018年7月16日 下午2:58:30
     */
    public static JSONObject doPost(String url, Map<String, Object> bodyMap) {
        DefaultHttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost(url);
        String result = null;
        com.alibaba.fastjson.JSONObject jSONObject = null;

        try {
            // 2018-07-16 haobin: 解决 110行 如果bodyMap为Null 时运行报错的问题
            if (null == bodyMap) {
                bodyMap = new HashMap<String, Object>();
            }

            // 2018-07-16 haobin:  最后一个参数不能省略，否则会导致服务器端接收到的中文数据出现乱码
            StringEntity s = new StringEntity(JSON.toJSONString(bodyMap), "UTF-8");   //110
            s.setContentEncoding("UTF-8");
            s.setContentType("application/json");// 发送json数据需要设置contentType
            post.setEntity(s);
            HttpResponse res = client.execute(post);
            if (res.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                HttpEntity entity = res.getEntity();
                result = EntityUtils.toString(res.getEntity());// 返回json格式：

                jSONObject = JSON.parseObject(result);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return jSONObject;
    }


    /**
     * 经测试发现， com.alibaba.druid.util.HttpClientUtils  无法得到服务器返回的json格式的数据
     */
//	public static void testAliBabaPost(){
//		HttpClientUtils.post("http://localhost:8080/highlight_springmvc4/anno/obj3?address=beijing",
//				"id=1111&name=qiaofeng&passwd=123qweasd", 6000);
//	}



    public boolean findFile(String baseDirName, String targetFileName) {
//        String before = targetFileName.substring(0, targetFileName.indexOf("[")+1);
//        System.out.println(before);
//        String after = targetFileName.substring(targetFileName.indexOf("]"));
//        System.out.println(after);
//        SimpleDateFormat sdf = new SimpleDateFormat("YYYYMMDD");
//        String nowDate = sdf.format(new Date());
//
//        String intradayFile = before + nowDate + after;//当天的文件名
        File baseDir = new File(baseDirName);//创建一个File对象
        if (!baseDir.exists() || !baseDir.isDirectory()) {//判断目录是否存在(文件不为空 || 不是一个文件夹)，因为baseDirName为一个路径，所以必须是文件夹
            System.out.println(baseDir + "文件路径为空" + "文件不是一个目录");
        }
        File[] fileList = baseDir.listFiles();
        if (fileList.length == 0) {
            System.out.println("该文件夹下面没有文件");
            return false; //没有同名文件
        } else {
            for (int i = 0; i < fileList.length; i++) {
                File tempFile = fileList[i];
                String tempName = tempFile.getName();
                if (targetFileName.equals(tempName)) {
                    return true;//有同名文件
                }
            }
        }
        return false;//没有同名文件
    }

    public List<String> getnationality() {
        List<String> nationalityList = new ArrayList<>();
        nationalityList.add("中国");
        nationalityList.add("蒙古");
        nationalityList.add("朝鲜");
        nationalityList.add("韩国");
        nationalityList.add("日本");
        nationalityList.add("菲律宾");
        nationalityList.add("越南");
        nationalityList.add("老挝");
        nationalityList.add("柬埔寨");
        nationalityList.add("缅甸");
        nationalityList.add("泰国");
        nationalityList.add("马来西亚");
        nationalityList.add("文莱");
        nationalityList.add("新加坡");
        nationalityList.add("印度尼西亚");
        nationalityList.add("东帝汶");
        nationalityList.add("尼泊尔");
        nationalityList.add("不丹");
        nationalityList.add("孟加拉国");
        nationalityList.add("印度");
        nationalityList.add("巴基斯坦");
        nationalityList.add("斯里兰卡");
        nationalityList.add("马尔代夫");
        nationalityList.add("哈萨克斯坦");
        nationalityList.add("吉尔吉斯斯坦");
        nationalityList.add("塔吉克斯坦");
        nationalityList.add("乌兹别克斯坦");
        nationalityList.add("土库曼斯坦");
        nationalityList.add("阿富汗");
        nationalityList.add("伊拉克");
        nationalityList.add("伊朗");
        nationalityList.add("叙利亚");
        nationalityList.add("约旦");
        nationalityList.add("黎巴嫩");
        nationalityList.add("以色列");
        nationalityList.add("巴勒斯坦");
        nationalityList.add("沙特阿拉伯");
        nationalityList.add("巴林");
        nationalityList.add("卡塔尔");
        nationalityList.add("科威特");
        nationalityList.add("阿拉伯联合酋长国");
        nationalityList.add("阿曼");
        nationalityList.add("也门");
        nationalityList.add("亚美尼亚");
        nationalityList.add("格鲁吉亚");
        nationalityList.add("阿塞拜疆");
        nationalityList.add("土耳其");
        nationalityList.add("塞浦路斯");
        nationalityList.add("芬兰");
        nationalityList.add("瑞典");
        nationalityList.add("挪威");
        nationalityList.add("冰岛");
        nationalityList.add("丹麦");
        nationalityList.add("法罗群岛");
        nationalityList.add("爱沙尼亚");
        nationalityList.add("拉脱维亚");
        nationalityList.add("立陶宛");
        nationalityList.add("白俄罗斯");
        nationalityList.add("俄罗斯");
        nationalityList.add("乌克兰");
        nationalityList.add("摩尔多瓦");
        nationalityList.add("波兰");
        nationalityList.add("捷克");
        nationalityList.add("斯洛伐克");
        nationalityList.add("匈牙利");
        nationalityList.add("德国");
        nationalityList.add("奥地利");
        nationalityList.add("瑞士");
        nationalityList.add("列支敦士登");
        nationalityList.add("英国");
        nationalityList.add("爱尔兰");
        nationalityList.add("荷兰");
        nationalityList.add("比利时");
        nationalityList.add("卢森堡");
        nationalityList.add("法国");
        nationalityList.add("摩纳哥");
        nationalityList.add("罗马尼亚");
        nationalityList.add("保加利亚");
        nationalityList.add("塞尔维亚");
        nationalityList.add("马其顿");
        nationalityList.add("阿尔巴尼亚");
        nationalityList.add("希腊");
        nationalityList.add("斯洛文尼亚");
        nationalityList.add("克罗地亚");
        nationalityList.add("波斯尼亚");
        nationalityList.add("墨塞哥维那");
        nationalityList.add("意大利");
        nationalityList.add("梵蒂冈");
        nationalityList.add("圣马力诺");
        nationalityList.add("马耳他");
        nationalityList.add("西班牙");
        nationalityList.add("葡萄牙");
        nationalityList.add("安道尔");
        nationalityList.add("埃及");
        nationalityList.add("利比亚");
        nationalityList.add("苏丹");
        nationalityList.add("突尼斯");
        nationalityList.add("阿尔及利亚");
        nationalityList.add("摩洛哥");
        nationalityList.add("亚速尔群岛（葡）");
        nationalityList.add("马德拉群岛（葡）");
        nationalityList.add("埃塞俄比亚");
        nationalityList.add("厄立特里亚");
        nationalityList.add("索马里");
        nationalityList.add("吉布提");
        nationalityList.add("肯尼亚");
        nationalityList.add("坦桑尼亚");
        nationalityList.add("乌干达");
        nationalityList.add("卢旺达");
        nationalityList.add("布隆迪");
        nationalityList.add("塞舌尔");
        nationalityList.add("乍得");
        nationalityList.add("中非");
        nationalityList.add("喀麦隆");
        nationalityList.add("赤道几内亚");
        nationalityList.add("加蓬");
        nationalityList.add("刚果共和国");
        nationalityList.add("刚果民主共和国");
        nationalityList.add("圣多美及普林西比");
        nationalityList.add("毛里塔尼亚");
        nationalityList.add("西撒哈拉");
        nationalityList.add("塞内加尔");
        nationalityList.add("冈比亚");
        nationalityList.add("马里");
        nationalityList.add("布基纳法索");
        nationalityList.add("几内亚");
        nationalityList.add("几内亚比绍");
        nationalityList.add("佛得角");
        nationalityList.add("塞拉利昂");
        nationalityList.add("利比里亚");
        nationalityList.add("科特迪瓦");
        nationalityList.add("加纳");
        nationalityList.add("多哥");
        nationalityList.add("贝宁");
        nationalityList.add("尼日尔");
        nationalityList.add("加那利群岛（西）");
        nationalityList.add("赞比亚");
        nationalityList.add("安哥拉");
        nationalityList.add("津巴布韦");
        nationalityList.add("马拉维");
        nationalityList.add("莫桑比克");
        nationalityList.add("博茨瓦纳");
        nationalityList.add("纳米比亚");
        nationalityList.add("南非");
        nationalityList.add("斯威士兰");
        nationalityList.add("莱索托");
        nationalityList.add("马达加斯加");
        nationalityList.add("科摩罗");
        nationalityList.add("毛里求斯");
        nationalityList.add("留尼旺（法）");
        nationalityList.add("圣赫勒拿（英）");
        nationalityList.add("澳大利亚");
        nationalityList.add("新西兰");
        nationalityList.add("巴布亚新几内亚");
        nationalityList.add("所罗门群岛");
        nationalityList.add("瓦努阿图");
        nationalityList.add("密克罗尼西亚");
        nationalityList.add("马绍尔群岛");
        nationalityList.add("帕劳");
        nationalityList.add("瑙鲁");
        nationalityList.add("基里巴斯");
        nationalityList.add("图瓦卢");
        nationalityList.add("萨摩亚");
        nationalityList.add("斐济群岛");
        nationalityList.add("汤加");
        nationalityList.add("库克群岛（新）");
        nationalityList.add("关岛（美）");
        nationalityList.add("新喀里多尼亚（法）");
        nationalityList.add("法属波利尼西亚");
        nationalityList.add("皮特凯恩岛（英）");
        nationalityList.add("瓦利斯与富图纳（法）");
        nationalityList.add("纽埃（新）");
        nationalityList.add("托克劳（新）");
        nationalityList.add("美属萨摩亚");
        nationalityList.add("北马里亚纳（美）");
        nationalityList.add("加拿大");
        nationalityList.add("美国");
        nationalityList.add("墨西哥");
        nationalityList.add("格陵兰");
        nationalityList.add("危地马拉");
        nationalityList.add("伯利兹");
        nationalityList.add("萨尔瓦多");
        nationalityList.add("洪都拉斯");
        nationalityList.add("尼加拉瓜");
        nationalityList.add("哥斯达黎加");
        nationalityList.add("巴拿马");
        nationalityList.add("巴哈马");
        nationalityList.add("古巴");
        nationalityList.add("牙买加");
        nationalityList.add("海地");
        nationalityList.add("多米尼加共和国");
        nationalityList.add("安提瓜和巴布达");
        nationalityList.add("圣基茨和尼维斯");
        nationalityList.add("多米尼克");
        nationalityList.add("圣卢西亚");
        nationalityList.add("圣文森特和格林纳丁斯");
        nationalityList.add("格林纳达");
        nationalityList.add("巴巴多斯");
        nationalityList.add("特立尼达和多巴哥");
        nationalityList.add("波多黎各（美）");
        nationalityList.add("英属维尔京群岛");
        nationalityList.add("美属维尔京群岛");
        nationalityList.add("安圭拉（英）");
        nationalityList.add("蒙特塞拉特（英）");
        nationalityList.add("瓜德罗普（法）");
        nationalityList.add("马提尼克（法）");
        nationalityList.add("荷属安的列斯");
        nationalityList.add("阿鲁巴（荷）");
        nationalityList.add("特克斯和凯科斯群岛（英）");
        nationalityList.add("开曼群岛（英）");
        nationalityList.add("百慕大（英）");
        nationalityList.add("哥伦比亚");
        nationalityList.add("委内瑞拉");
        nationalityList.add("圭亚那");
        nationalityList.add("法属圭亚那");
        nationalityList.add("苏里南");
        nationalityList.add("厄瓜多尔");
        nationalityList.add("秘鲁");
        nationalityList.add("玻利维亚");
        nationalityList.add("巴西");
        nationalityList.add("智利");
        nationalityList.add("阿根廷");
        nationalityList.add("乌拉圭");
        nationalityList.add("巴拉圭");
        return nationalityList;
    }


    public void testLogin() {
        User user1 = new User();
        user1.setUsername("heikaqiu");
        user1.setPassword("123");
        userService.login(user1);
    }

    public Date[] StringtoDate(String time) {
        String s1 = time.replace("/", "-");
        //order_time.replace(" ",""); //去除所有空格，包括首尾、中间
        String[] s = s1.split(" - ");//从-分隔开字符串

        s[0] += " 00:00:00";
        s[1] += " 24:00:00";
        System.out.println("s[0]" + s[0]);
        System.out.println("s[1]" + s[1]);

        //String str = "2019-03-13 13:54:00";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date[] dates = new Date[2];
        try {
            dates[0] = simpleDateFormat.parse(s[0]);
            dates[1] = simpleDateFormat.parse(s[1]);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dates;
    }


    public Map<String, Long> getTime() {
        Long current = System.currentTimeMillis();//当前时间毫秒数
        //零点是24小时轮回的零界点。所以我们把当前时间戳取24小时毫秒数取余，然后用当前毫秒时间戳减这个余就行。
        Long today_zero = current / (1000 * 3600 * 24) * (1000 * 3600 * 24) - TimeZone.getDefault().getRawOffset();//今天00:00:00的毫秒数
        Long twelve = today_zero + 24 * 60 * 60 * 1000 - 1000;//今天23:59:59的毫秒数
        Long yesterday = System.currentTimeMillis() - 24 * 60 * 60 * 1000;//昨天的这一时间的毫秒数
        Long yesterday_zero = today_zero - (1000 * 60 * 60 * 24);//昨天00:00:00
        Long tomorrow_zero = today_zero + 24 * 60 * 60 * 1000;//明天00:00:00


        Map<String, Long> timeMap = new HashMap<>();
        timeMap.put("yesterday_zero", yesterday_zero);//昨天00:00:00
        timeMap.put("yesterday", yesterday);//昨天这个时候的毫秒数
        timeMap.put("today_zero", today_zero);//今天00:00:00
        timeMap.put("current", current);//现在的毫秒数
        timeMap.put("twelve", twelve);//今天23:59:59
        timeMap.put("tomorrow_zero", tomorrow_zero);//明天00:00:00


        return timeMap;

    }


    /**
     * 用于解决 前端发送DELETE 以及PUT 请求 后端必须使用RequestMapping的问题
     *
     * @return
     */
    @Bean
    public HiddenHttpMethodFilter hiddenHttpMethodFilter() {
        HiddenHttpMethodFilter hiddenHttpMethodFilter = new HiddenHttpMethodFilter();
        hiddenHttpMethodFilter.setBeanName("HiddenHttpMethodFilter");
        hiddenHttpMethodFilter.setMethodParam("_method");
        return hiddenHttpMethodFilter;
    }

    public String getProvince(int province_num) {
        String[] pArr = new String[]{"北京市", "天津市", "上海市", "重庆市", "新疆自治区", "西藏自治区", "宁夏自治区", "内蒙古自治区", "广西自治区", "黑龙江省", "吉林省", "辽宁省", "河北省", "山东省", "江苏省", "安徽省", "浙江省", "福建省", "广东省", "海南省", "云南省", "贵州省", "四川省", "湖南省", "湖北省", "河南省", "山西省", "陕西省", "甘肃省", "青海省", "江西省", "台湾省", "香港特别行政区", "澳门特别行政区"};
        return pArr[province_num];
    }

    public String getCity(int province_num, int city_num) {
        String[][] provinceArr = new String[34][];
        provinceArr[0] = new String[]{"北京市"};
        provinceArr[1] = new String[]{"天津市"};
        provinceArr[2] = new String[]{"上海市"};
        provinceArr[3] = new String[]{"重庆市"};
        provinceArr[4] = new String[]{"乌鲁木齐", "克拉玛依"};
        provinceArr[5] = new String[]{"拉萨"};
        provinceArr[6] = new String[]{"银川", "石嘴山", "吴忠", "固原", "中卫"};
        provinceArr[7] = new String[]{"呼和浩特", "包头", "乌海", "赤峰", "通辽", "鄂尔多斯", "呼伦贝尔", "巴彦淖尔", "乌兰察布"};
        provinceArr[8] = new String[]{"南宁", "柳州", "桂林", "梧州", "北海", "崇左", "来宾", "贺州", "玉林", "百色", "河池", "钦州", "防城港", "贵港"};
        provinceArr[9] = new String[]{"哈尔滨", "大庆", "齐齐哈尔", "佳木斯", "鸡西", "鹤岗", "双鸭山", "牡丹江", "伊春", "七台河", "黑河", "绥化"};
        provinceArr[10] = new String[]{"长春", "吉林", "四平", "辽源", "通化", "白山", "松原", "白城"};
        provinceArr[11] = new String[]{"沈阳", "大连", "鞍山", "抚顺", "本溪", "丹东", "锦州", "营口", "阜新", "辽阳", "盘锦", "铁岭", "朝阳", "葫芦岛"};
        provinceArr[12] = new String[]{"石家庄", "唐山", "邯郸", "秦皇岛", "保定", "张家口", "承德", "廊坊", "沧州", "衡水", "邢台"};
        provinceArr[13] = new String[]{"济南", "青岛", "淄博", "枣庄", "东营", "烟台", "潍坊", "济宁", "泰安", "威海", "日照", "莱芜", "临沂", "德州", "聊城", "菏泽", "滨州"};
        provinceArr[14] = new String[]{"南京", "镇江", "常州", "无锡", "苏州", "徐州", "连云港", "淮安", "盐城", "扬州", "泰州", "南通", "宿迁"};
        provinceArr[15] = new String[]{"合肥", "蚌埠", "芜湖", "淮南", "亳州", "阜阳", "淮北", "宿州", "滁州", "安庆", "巢湖", "马鞍山", "宣城", "黄山", "池州", "铜陵"};
        provinceArr[16] = new String[]{"杭州", "嘉兴", "湖州", "宁波", "金华", "温州", "丽水", "绍兴", "衢州", "舟山", "台州"};
        provinceArr[17] = new String[]{"福州", "厦门", "泉州", "三明", "南平", "漳州", "莆田", "宁德", "龙岩"};
        provinceArr[18] = new String[]{"广州", "深圳", "汕头", "惠州", "珠海", "揭阳", "佛山", "河源", "阳江", "茂名", "湛江", "梅州", "肇庆", "韶关", "潮州", "东莞", "中山", "清远", "江门", "汕尾", "云浮"};
        provinceArr[19] = new String[]{"海口", "三亚"};
        provinceArr[20] = new String[]{"昆明", "曲靖", "玉溪", "保山", "昭通", "丽江", "普洱", "临沧"};
        provinceArr[21] = new String[]{"贵阳", "六盘水", "遵义", "安顺"};
        provinceArr[22] = new String[]{"成都", "绵阳", "德阳", "广元", "自贡", "攀枝花", "乐山", "南充", "内江", "遂宁", "广安", "泸州", "达州", "眉山", "宜宾", "雅安", "资阳"};
        provinceArr[23] = new String[]{"长沙", "株洲", "湘潭", "衡阳", "岳阳", "郴州", "永州", "邵阳", "怀化", "常德", "益阳", "张家界", "娄底"};
        provinceArr[24] = new String[]{"武汉", "襄樊", "宜昌", "黄石", "鄂州", "随州", "荆州", "荆门", "十堰", "孝感", "黄冈", "咸宁"};
        provinceArr[25] = new String[]{"郑州", "洛阳", "开封", "漯河", "安阳", "新乡", "周口", "三门峡", "焦作", "平顶山", "信阳", "南阳", "鹤壁", "濮阳", "许昌", "商丘", "驻马店"};
        provinceArr[26] = new String[]{"太原", "大同", "忻州", "阳泉", "长治", "晋城", "朔州", "晋中", "运城", "临汾", "吕梁"};
        provinceArr[27] = new String[]{"西安", "咸阳", "铜川", "延安", "宝鸡", "渭南", "汉中", "安康", "商洛", "榆林"};
        provinceArr[28] = new String[]{"兰州", "天水", "平凉", "酒泉", "嘉峪关", "金昌", "白银", "武威", "张掖", "庆阳", "定西", "陇南"};
        provinceArr[29] = new String[]{"西宁"};
        provinceArr[30] = new String[]{"南昌", "九江", "赣州", "吉安", "鹰潭", "上饶", "萍乡", "景德镇", "新余", "宜春", "抚州"};
        provinceArr[31] = new String[]{"台北", "台中", "基隆", "高雄", "台南", "新竹", "嘉义"};
        provinceArr[32] = new String[]{"香港特别行政区"};
        provinceArr[33] = new String[]{"澳门特别行政区"};
        return provinceArr[province_num][city_num];
    }


}
