package cn.heikaqiu.booktt.service;

import cn.heikaqiu.booktt.bean.User;

import java.util.Map;

/**
 * @author HeiKaQiu
 * @create 2020-01-30 16:14
 */
public interface UserService {

    /**
     * 登录
     * @param user
     * @return
     */
    public boolean login(User user);

    /**
     * 注册
     * @param user
     * @return
     */
    public boolean register(User user);



    /**
     * 是否将书添加到购物车
     * @param user_id
     * @param book_id
     * @param buy_num
     * @return
     */
    Map<String, String> andToCart(String user_id, String book_id, String buy_num);

    /**
     * 获取user通过id
     * @param userid
     * @return
     */
    User getUserById(Integer userid);

    /**
     * 退出登录
     * @param userid
     * @return
     */
    boolean exitLogin(Integer userid);

    /**
     * 修改用户信息
     * @param user
     * @return
     */
    boolean updateUserInformation(User user) throws Exception;


    /**
     * 修改用户的密码
     * @param userid
     * @param old_password
     * @param password
     * @param paypassword
     * @return
     */
    boolean updateUserPassword(Integer userid, String old_password, String password, String paypassword) throws Exception;

    /**
     *通过用户名查找用户
     * @param user_username
     * @return
     */
    User findUserByusername(String user_username);
}
