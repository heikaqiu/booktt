package cn.heikaqiu.booktt.service;

import cn.heikaqiu.booktt.bean.Shopcart;

import java.util.List;

/**
 * @author HeiKaQiu
 * @create 2020-02-04 19:17
 */
public interface ShopcartService {
    /**
     * 通过用户Id获取购物车的信息
     * @param id
     * @return
     */
    List<Shopcart> getShopcartByUserId(Integer id);

    /**
     * 删除一条购物车信息
     * @param shopcart_id
     * @return
     */
    boolean delectShopcart(Integer shopcart_id);

    /**
     * 购买书
     * @param bookid    书id的集合
     * @param booknum    书数量的集合
     * @param userid      用户的id
     * @param totalPrice   需扣款的总和
     * @return
     */
    Long addOrder(List<Integer> bookid, List<Integer> booknum, Integer userid, Float totalPrice,Integer orderState);
}
