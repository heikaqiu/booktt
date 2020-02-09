package cn.heikaqiu.booktt.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.HiddenHttpMethodFilter;

/**
 * @author HeiKaQiu
 * @create 2020-01-30 19:14
 */
@Configuration
public class OtherConfig {

    /**
     * 用于解决 前端发送DELETE 以及PUT 请求 后端必须使用RequestMapping的问题
     * @return
     */
    @Bean
    public HiddenHttpMethodFilter hiddenHttpMethodFilter() {
        HiddenHttpMethodFilter hiddenHttpMethodFilter = new HiddenHttpMethodFilter();
        hiddenHttpMethodFilter.setBeanName("HiddenHttpMethodFilter");
        hiddenHttpMethodFilter.setMethodParam("_method");
        return hiddenHttpMethodFilter;
    }

    public String getProvince(int province_num){
        String[] pArr=new String[]{"北京市","天津市","上海市","重庆市","新疆自治区","西藏自治区","宁夏自治区","内蒙古自治区","广西自治区","黑龙江省","吉林省","辽宁省","河北省","山东省","江苏省","安徽省","浙江省","福建省","广东省","海南省","云南省","贵州省","四川省","湖南省","湖北省","河南省","山西省","陕西省","甘肃省","青海省","江西省","台湾省","香港特别行政区","澳门特别行政区"};
        return pArr[province_num];
    }
    public String getCity(int province_num,int city_num){
        String[][] provinceArr=new String[34][];
        provinceArr[0]=new String[]{"北京市"};
        provinceArr[1]=new String[]{"天津市"};
        provinceArr[2]=new String[]{"上海市"};
        provinceArr[3]=new String[]{"重庆市"};
        provinceArr[4]=new String[]{"乌鲁木齐","克拉玛依"};
        provinceArr[5]=new String[]{"拉萨"};
        provinceArr[6]=new String[]{"银川","石嘴山","吴忠","固原","中卫"};
        provinceArr[7]=new String[]{"呼和浩特","包头","乌海","赤峰","通辽","鄂尔多斯","呼伦贝尔","巴彦淖尔","乌兰察布"};
        provinceArr[8]=new String[]{"南宁","柳州","桂林","梧州","北海","崇左","来宾","贺州","玉林","百色","河池","钦州","防城港","贵港"};
        provinceArr[9]=new String[]{"哈尔滨","大庆","齐齐哈尔","佳木斯","鸡西","鹤岗","双鸭山","牡丹江","伊春","七台河","黑河","绥化"};
        provinceArr[10]=new String[]{"长春","吉林","四平","辽源","通化","白山","松原","白城"};
        provinceArr[11]=new String[]{"沈阳","大连","鞍山","抚顺","本溪","丹东","锦州","营口","阜新","辽阳","盘锦","铁岭","朝阳","葫芦岛"};
        provinceArr[12]=new String[]{"石家庄","唐山","邯郸","秦皇岛","保定","张家口","承德","廊坊","沧州","衡水","邢台"};
        provinceArr[13]=new String[]{"济南","青岛","淄博","枣庄","东营","烟台","潍坊","济宁","泰安","威海","日照","莱芜","临沂","德州","聊城","菏泽","滨州"};
        provinceArr[14]=new String[]{"南京","镇江","常州","无锡","苏州","徐州","连云港","淮安","盐城","扬州","泰州","南通","宿迁"};
        provinceArr[15]=new String[]{"合肥","蚌埠","芜湖","淮南","亳州","阜阳","淮北","宿州","滁州","安庆","巢湖","马鞍山","宣城","黄山","池州","铜陵"};
        provinceArr[16]=new String[]{"杭州","嘉兴","湖州","宁波","金华","温州","丽水","绍兴","衢州","舟山","台州"};
        provinceArr[17]=new String[]{"福州","厦门","泉州","三明","南平","漳州","莆田","宁德","龙岩"};
        provinceArr[18]=new String[]{"广州","深圳","汕头","惠州","珠海","揭阳","佛山","河源","阳江","茂名","湛江","梅州","肇庆","韶关","潮州","东莞","中山","清远","江门","汕尾","云浮"};
        provinceArr[19]=new String[]{"海口","三亚"};
        provinceArr[20]=new String[]{"昆明","曲靖","玉溪","保山","昭通","丽江","普洱","临沧"};
        provinceArr[21]=new String[]{"贵阳","六盘水","遵义","安顺"};
        provinceArr[22]=new String[]{"成都","绵阳","德阳","广元","自贡","攀枝花","乐山","南充","内江","遂宁","广安","泸州","达州","眉山","宜宾","雅安","资阳"};
        provinceArr[23]=new String[]{"长沙","株洲","湘潭","衡阳","岳阳","郴州","永州","邵阳","怀化","常德","益阳","张家界","娄底"};
        provinceArr[24]=new String[]{"武汉","襄樊","宜昌","黄石","鄂州","随州","荆州","荆门","十堰","孝感","黄冈","咸宁"};
        provinceArr[25]=new String[]{"郑州","洛阳","开封","漯河","安阳","新乡","周口","三门峡","焦作","平顶山","信阳","南阳","鹤壁","濮阳","许昌","商丘","驻马店"};
        provinceArr[26]=new String[]{"太原","大同","忻州","阳泉","长治","晋城","朔州","晋中","运城","临汾","吕梁"};
        provinceArr[27]=new String[]{"西安","咸阳","铜川","延安","宝鸡","渭南","汉中","安康","商洛","榆林"};
        provinceArr[28]=new String[]{"兰州","天水","平凉","酒泉","嘉峪关","金昌","白银","武威","张掖","庆阳","定西","陇南"};
        provinceArr[29]=new String[]{"西宁"};
        provinceArr[30]=new String[]{"南昌","九江","赣州","吉安","鹰潭","上饶","萍乡","景德镇","新余","宜春","抚州"};
        provinceArr[31]=new String[]{"台北","台中","基隆","高雄","台南","新竹","嘉义"};
        provinceArr[32]=new String[]{"香港特别行政区"};
        provinceArr[33]=new String[]{"澳门特别行政区"};
        return provinceArr[province_num][city_num];
    }
    
    
   
}
