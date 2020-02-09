package cn.heikaqiu.booktt.service.imp;

import cn.heikaqiu.booktt.bean.Book;
import cn.heikaqiu.booktt.bean.User;
import cn.heikaqiu.booktt.mapper.BookMapper;
import cn.heikaqiu.booktt.mapper.ShopcartMapper;
import cn.heikaqiu.booktt.mapper.UserMapper;
import cn.heikaqiu.booktt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * @author HeiKaQiu
 * @create 2020-01-30 16:15
 */

@Service
public class UserServiceImp implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private BookMapper bookMapper;

    @Autowired
    private ShopcartMapper shopcartMapper;

    @Autowired
    private HttpSession session;

    @Override
    public boolean login(User user) {
        User findUser = userMapper.getUserByUsernameAndPassword(user.getUsername(), user.getPassword());
        if (findUser != null) {
            //不为空 表示数据库中有对象
            session.setAttribute("login_user", findUser);
            return true;
        } else {
            return false;
        }

    }

    @Override
    public boolean register(User user) {
        User userByUsername = userMapper.getUserByUsername(user.getUsername());
        if (userByUsername == null) {
            //没有重复用户名
            int id = userMapper.insertUser(user);
            user.setId(id);
            session.setAttribute("login_user", user);
            return true;
        } else {
            //用户名重复
            return false;
        }
    }


    @Override
    public Map<String, String> andToCart(String user_id, String book_id, String buy_num) {
        Map<String, String> map = new HashMap<>();
        Integer userId = Integer.valueOf(user_id);
        Integer bookId = Integer.valueOf(book_id);
        Integer buyNum = Integer.valueOf(buy_num);

        //1.查书的库存
        Integer remainder = bookMapper.getRemainderByBookId(bookId);
        if (remainder < buyNum) {
            //库存不足
            map.put("message", "此书库存不足,无法添加到购物车");
            return map;
        } else {
            //库存充足，就将书添加到此账户的购物车中

            //1.先查找用户是否已经添加到购物车中了
            Integer number = shopcartMapper.selectNumberShopCart(userId, bookId);
            if (number != null && number > 0) {
                //如果存在就增加数量
                shopcartMapper.updateNumForShopCart(userId, bookId, buyNum);
            } else {
                shopcartMapper.addShopCart(userId, bookId, buyNum);
            }
            map.put("message", "success");
            return map;
        }


    }

    @Override
    public User getUserById(Integer userid) {
        return userMapper.getUserAndCollectionBookById(userid);
    }

    @Override
    public boolean exitLogin(Integer userid) {

        User login_user = (User) session.getAttribute("login_user");
        if (login_user.getId() == userid) {
            session.removeAttribute("login_user");
            return true;
        }else {
            return false;
        }

    }

    @Override
    public boolean updateUserInformation(User user) throws Exception {
        User userByUsername = userMapper.getUserByUsername(user.getUsername());
        if(userByUsername!=null){
            return false;//名字已经有人注册了
        }
        Integer line=userMapper.updateUserInformationByUser(user);
        System.out.println("line: "+line);
        if(line==1){
            return true;
        }else{
            throw new Exception("修改用户错误");

        }
    }

    @Override
    public boolean updateUserPassword(Integer userid, String old_password, String password, String paypassword) throws Exception {

        //对比旧密码是否一致
        String userpassword=userMapper.getUserPasswordByUserid(userid);
        if(userpassword.equals(old_password)){
            //旧密码正确
            Integer line=userMapper.updateUserPasswordByUserid(userid,password,paypassword);
            System.out.println("Passwordline: "+line);
            if(line==1){
                return true;
            }else{
                //更改的不止一行或者 没有修改
                throw new Exception("修改用户密码错误");
            }
        }
        else{
            //旧密码错误
            return false;
        }
    }


}
