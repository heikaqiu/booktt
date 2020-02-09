package cn.heikaqiu.booktt.service.imp;

import cn.heikaqiu.booktt.bean.Collection;
import cn.heikaqiu.booktt.mapper.CollectionMapper;
import cn.heikaqiu.booktt.service.CollectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author HeiKaQiu
 * @create 2020-02-09 10:06
 */
@Service
public class CollectionServiceImp implements CollectionService {

    @Autowired
    private CollectionMapper collectionMapper;


    @Override
    public boolean andToCollection(Integer user_id, Integer book_id) throws Exception {

        Integer line = collectionMapper.andToCollection(user_id, book_id);
        if (line == 1) {
            //添加一条收藏
            return true;
        } else if (line == 0) {
            return false;
        } else {
            throw new Exception("添加收藏为多条，添加失败");
        }
    }

    @Override
    public boolean isCollection(Integer user_id, Integer book_id) {
        Collection collection=collectionMapper.isCollection(user_id, book_id);
        System.out.println(collection);
        if(collection!=null){
            //有记录表示收藏过了
            return true;
        }else{
            //没收藏
            return false;
        }
    }

    @Override
    public boolean deleteCollection(Integer userid, Integer bookid) throws Exception {
        Integer line=collectionMapper.deleteCollection(userid,bookid);
        if (line == 1) {
            //删除一条收藏
            return true;
        } else if (line == 0) {
            return false;
        } else {
            throw new Exception("删除收藏为多条，删除失败");
        }
    }
}
