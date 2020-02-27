package cn.heikaqiu.booktt.controller;

import cn.heikaqiu.booktt.bean.Advice;
import cn.heikaqiu.booktt.bean.Author;
import cn.heikaqiu.booktt.bean.FindAuthorByInformation;
import cn.heikaqiu.booktt.service.AdviceService;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * @author HeiKaQiu
 * @create 2020-02-25 16:40
 */
@Controller
@RequestMapping("/admin")
public class AdminAdviceController {

    @Autowired
    private AdviceService adviceService;

    /**
     * 获取建议 分页第几条
     *
     * @param pageNum
     * @return
     */
    @PostMapping("/changeAdvice/{pageNum}")
    @ResponseBody
    public Map<String, Object> changeAdvice(@PathVariable("pageNum") Integer pageNum,Boolean isHandle) {


        Map<String, Object> maps = new HashMap<>();

        System.out.println("pageNum" + pageNum);
        Boolean isread=null;
        Boolean ishandle=null;

        if(isHandle==null){
            //不管是否处理

        }else if(isHandle==true){
            //已处理的
            isread=true;
            ishandle=true;
        }else{
            //没处理的
            isread=null;
            ishandle=false;
        }

        List<Advice> adviceList = adviceService.getAdvice(isread, ishandle, pageNum, 5);
        Integer adviceListNum = adviceService.getAllAdviceNum(isread, ishandle);
        maps.put("adviceList",adviceList);
        maps.put("adviceListNum",adviceListNum);
        maps.put("message","搜索成功");
        return maps;
    }



    /**
     * 转到详细建议内容
     *
     * @param adviceId
     * @return
     */
    @GetMapping("/adviceInfo.html/{adviceId}")
    public String adviceInfo(@PathVariable("adviceId") Integer adviceId, Model model) {

        if(adviceId==null||adviceId<0){
            model.addAttribute("error","建议的ID错误");
        }else{
            //现将这个建议 变为已读
            boolean isread=true;
            boolean isupdate=adviceService.updateAdviceRead(adviceId,isread);
            if(isupdate){
                //成功将它变为 已读 再获取他的详细信息
                Advice advice=adviceService.getAdviceById(adviceId);
                System.out.println(advice);
                model.addAttribute("advice",advice);
            }else{
                model.addAttribute("error","读取失败");
            }
        }

        return "/admin/AdviceInformation";
    }


/**
 * 建议处理
 *
 * @param adviceid
 * @return
 */
    @PostMapping("/toHandleAdvice")
    @ResponseBody
    public Map<String, Object> toHandleAdvice(Integer adviceid,Boolean isHandle) {


        Map<String, Object> maps = new HashMap<>();

        System.out.println("adviceid" + adviceid);


        boolean isupdate = adviceService.updateAdviceHandle(adviceid, isHandle);
        if(isupdate){

            maps.put("message","成功");
        }
        else{
            maps.put("message","失败");
        }
        return maps;
    }
}
