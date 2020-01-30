package cn.heikaqiu.booktt.service.imp;

import cn.heikaqiu.booktt.bean.User;
import cn.heikaqiu.booktt.mapper.UserMapper;
import cn.heikaqiu.booktt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author HeiKaQiu
 * @create 2020-01-30 16:15
 */

@Service
public class UserServiceImp implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public boolean login(User user) {
        User findUser=userMapper.getUserByUsernameAndPassword(user.getUsername(),user.getPassword());
        if(findUser!=null){
            //不为空 表示数据库中有对象
            return true;
        }else{
            return false;
        }

    }

    @Override
    public boolean register(User user) {
        User userByUsername=userMapper.getUserByUsername(user.getUsername());
        if(userByUsername==null){
            //没有重复用户名
            userMapper.insertUser(user);
            return true;
        }
        else{
            //用户名重复
            return false;
        }
    }
}
