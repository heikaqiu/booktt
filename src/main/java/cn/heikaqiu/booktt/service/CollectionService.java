package cn.heikaqiu.booktt.service;

/**
 * @author HeiKaQiu
 * @create 2020-02-09 10:06
 */
public interface CollectionService {

    /**
     * 添加收藏
     * @param user_id
     * @param book_id
     * @return
     */
    boolean andToCollection(Integer user_id, Integer book_id) throws Exception;


    /**
     * 是否收藏
     * @param user_id
     * @param book_id
     * @return
     */
    boolean isCollection(Integer user_id, Integer book_id);

    /**
     * 取消收藏
     * @param userid
     * @param bookid
     * @return
     */
    boolean deleteCollection(Integer userid, Integer bookid) throws Exception;
}
