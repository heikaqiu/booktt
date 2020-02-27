package cn.heikaqiu.booktt.service;

import cn.heikaqiu.booktt.bean.Advice;

import java.util.List;

/**
 * @author HeiKaQiu
 * @create 2020-02-24 17:36
 */
public interface AdviceService {
    /**
     * 添加建议
     * @param advice
     * @return
     */
    boolean toAdvice(Advice advice) throws Exception;



    /**
     * 将所有建议标位已读
     */
    void toAllReadAdvice();

    /**
     * 通过是否阅读 是否处理的条件查看 建议的数量
     * @param isread
     * @param isHandle
     * @return
     */
    Integer getAllAdviceNum(Boolean isread, Boolean isHandle);

    /**
     * 通过是否阅读 是否处理的条件  获得建议
     * @param isread
     * @param isHandle
     * @param start_num 第几条
     * @param page_num  每页几条
     * @return
     */
    List<Advice> getAdvice(Boolean isread, Boolean isHandle, Integer start_num,  Integer page_num);


    /**
     * 将指定建议的  是否已读变为已读
     * @param adviceId
     * @param isread
     * @return
     */
    boolean updateAdviceRead(Integer adviceId, boolean isread);

    /**
     * 获取指定id的建议详细信息
     * @param adviceId
     * @return
     */
    Advice getAdviceById(Integer adviceId);

    /**
     * 修改建议是否处理的信息
     * @param adviceid
     * @param isHandle
     * @return
     */
    boolean updateAdviceHandle(Integer adviceid, boolean isHandle);
}
