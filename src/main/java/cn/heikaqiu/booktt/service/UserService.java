package cn.heikaqiu.booktt.service;

import cn.heikaqiu.booktt.bean.User;

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
}
