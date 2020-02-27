package cn.heikaqiu.booktt.service.imp;

import cn.heikaqiu.booktt.bean.FindUserByInformation;
import cn.heikaqiu.booktt.bean.User;
import cn.heikaqiu.booktt.mapper.BookMapper;
import cn.heikaqiu.booktt.mapper.ShopcartMapper;
import cn.heikaqiu.booktt.mapper.UserMapper;
import cn.heikaqiu.booktt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.*;

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
            userMapper.updateLastUseTime(findUser.getId(), new Date());//更改账户最后使用的时间
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
            user.setLastusetime(new Date());
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
        } else {
            return false;
        }

    }

    @Override
    public boolean updateUserInformation(User user) throws Exception {
        User userByUsername = userMapper.getUserByUsername(user.getUsername());
        if (userByUsername != null) {
            return false;//名字已经有人注册了
        }
        Integer line = userMapper.updateUserInformationByUser(user);
        System.out.println("line: " + line);
        if (line == 1) {
            return true;
        } else {
            throw new Exception("修改用户错误");

        }
    }

    @Override
    public boolean updateUserPassword(Integer userid, String old_password, String password, String paypassword) throws Exception {

        //对比旧密码是否一致
        String userpassword = userMapper.getUserPasswordByUserid(userid);
        if (userpassword.equals(old_password)) {
            //旧密码正确
            Integer line = userMapper.updateUserPasswordByUserid(userid, password, paypassword);
            System.out.println("Passwordline: " + line);
            if (line == 1) {
                return true;
            } else {
                //更改的不止一行或者 没有修改
                throw new Exception("修改用户密码错误");
            }
        } else {
            //旧密码错误
            return false;
        }
    }

    @Override
    public User findUserByusername(String user_username) {
        User userByUsername = userMapper.getUserByUsername(user_username);
        if (user_username != null) {
            return userByUsername;
        } else {
            return null;
        }

    }

    @Override
    public Long getAllCountUser() {
        Long allCountUser = userMapper.getAllCountUser();
        if (allCountUser == null || allCountUser < 0L) {
            allCountUser = 0L;
        }
        return allCountUser;
    }

    @Override
    public Long getAnydayLastUserCountUser(Long start_time, Long last_time) {
        Long anydayCountUser = 0L;
        if (start_time == null || last_time == null) {
            //有一个为空
            anydayCountUser = -1L;
            return anydayCountUser;
        }
        if (last_time > start_time) {
            //后面的时间大于前面的时间
            anydayCountUser = userMapper.getAnydayLastUserCountUser(new Date(start_time), new Date(last_time));

            if (anydayCountUser == null || anydayCountUser < 0L) {
                anydayCountUser = 0L;
            }
            return anydayCountUser;
        } else {
            //前者大于后者  显然是不对的  交换
            anydayCountUser = userMapper.getAnydayLastUserCountUser(new Date(last_time), new Date(start_time));

            return anydayCountUser;
        }
    }

    @Override
    public List<User> getUserInfoLimit(Integer start_num, Integer page_num, FindUserByInformation userByInformation) {
        List<User> list = new ArrayList<>();
        if (start_num == null || page_num == null) {
            return list;
        }
        if (userByInformation == null) {
            userByInformation = new FindUserByInformation();
        }
        list = userMapper.getUserInfoLimit(start_num, page_num, userByInformation);
        if (list.size() > 0) {
            System.out.println(list.get(0).getBalance());
        }

        return list;
    }

    @Override
    public Long getUserByInformationNum(FindUserByInformation userByInformation) {
        Long allCountUser = 0L;
        if (userByInformation == null) {
            //查找所有的
            allCountUser = userMapper.getAllCountUser();
        } else {
            //条件查找
            allCountUser = userMapper.getAllCountUserByUserInfo(userByInformation);
        }
        return allCountUser;
    }

    @Override
    public User getUserInfoByUserId(Integer userId) {

        User user = userMapper.getUserAllInfoById(userId);
        System.out.println(user);
        return user;
    }

    @Override
    public User getUserSimpleById(Integer userId) {
        return userMapper.getUserById(userId);
    }

    @Override
    public boolean updateUserAllInformation(User user) throws Exception {
        User userByUsername = userMapper.getUserByUsername(user.getUsername());
        if (userByUsername != null&&userByUsername.getId()!=user.getId()) {
            return false;//名字已经有人注册了
        }
        Integer line = userMapper.updateUserAllInformationByUser(user);
        System.out.println("line: " + line);
        if (line == 1) {
            return true;
        } else {
            throw new Exception("修改用户错误");

        }
    }

    @Override
    public Boolean deleteuser(Integer userId) throws Exception {


        User userById = userMapper.getUserById(userId);
        if (userById != null) {
            //执行删除
            int line = userMapper.deleteUserById(userId);
            if (line == 1) {
                //只删除了一条
                return true;
            } else {
                //其余数量
                throw new Exception("删除用户错误");
            }
        } else {
            return false;
        }
    }

    @Override
    public boolean updateUserImg(String s,Integer id) {
        if(s==null||s.equals("")){
            return  false;
        }else{
            userMapper.updateUserImg(s,id);
            return true;
        }

    }


}
