package cn.heikaqiu.booktt.controller;

import cn.heikaqiu.booktt.service.CollectionService;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.PrimitiveIterator;

/**
 * @author HeiKaQiu
 * @create 2020-02-09 10:03
 */
@Controller
public class CollectionController {

    @Autowired
    private CollectionService collectionService;


    /**
     * 添加收藏
     *
     * @return
     */
    @PostMapping("/andToCollection")
    @ResponseBody
    public Map<String, String> andToCollection(Integer user_id, Integer book_id) {
        Map<String, String> maps = new HashMap<>();
        if (user_id == null || book_id == null) {
            //用户或者书本没注入 就返回错误的信息
            maps.put("message", "用户为空或者书本为空");
            return maps;
        }
        boolean iscollection = false;
        try {
            iscollection = collectionService.andToCollection(user_id, book_id);
            if (iscollection) {
                //添加收藏成功
                maps.put("message", "收藏书本成功");
            } else {
                //收藏失败
                maps.put("message", "收藏失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            maps.put("message", "添加收藏为多条，添加失败");
        }
        return  maps;

    }


    /**
     * 取消收藏
     *
     * @return
     */
    @DeleteMapping("/deleteCollection/{bookid}")
    @ResponseBody
    public Map<String, String> deleteCollection(Integer userid, @PathVariable("bookid") Integer bookid) {
        Map<String, String> maps = new HashMap<>();
        if (userid == null || bookid == null) {
            //用户或者书本没注入 就返回错误的信息
            maps.put("message", "用户为空或者书本为空");
            return maps;
        }
        boolean isdeletecollection = false;
        try {
            isdeletecollection = collectionService.deleteCollection(userid, bookid);
            if (isdeletecollection) {
                //删除收藏成功
                maps.put("message", "删除书本成功");
            } else {
                //删除失败
                maps.put("message", "删除失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            maps.put("message", "删除收藏为多条，删除失败");
        }
        return  maps;

    }
}
