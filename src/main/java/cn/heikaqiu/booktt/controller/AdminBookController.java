package cn.heikaqiu.booktt.controller;

import cn.heikaqiu.booktt.bean.*;
import cn.heikaqiu.booktt.config.OtherConfig;
import cn.heikaqiu.booktt.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author HeiKaQiu
 * @create 2020-02-21 22:49
 */
@Controller
@RequestMapping("/admin")
public class AdminBookController {

    @Autowired
    private HttpSession session;

    @Autowired
    private BookService bookService;

    @Autowired
    private OtherConfig otherConfig;


    //TODO
    @RequestMapping("/bookInformation")
    @ResponseBody
    public Map<String, Object> bookInformation(FindBookByInformation findBookByInformation) {
        Map<String, Object> maps = new HashMap<>();

        if (findBookByInformation.getName().equals("")) {
            findBookByInformation.setName(null);
        }
        if (findBookByInformation.getBooktype() == -1) {
            findBookByInformation.setBooktype(null);
        }
        if (findBookByInformation.getAuthor_id() == -1) {
            findBookByInformation.setAuthor_id(null);
        }

        System.out.println(findBookByInformation);

        session.setAttribute("findBookByInformation", findBookByInformation);


        maps.put("message", "搜索成功");
        Map<String, Object> stringObjectMap = toPageBook(0);

        maps.putAll(stringObjectMap);
        return maps;
    }


    /**
     * 获取 分页第几条
     *
     * @param pageNum
     * @return
     */
    @PostMapping("/toPageBook/{pageNum}")
    @ResponseBody
    public Map<String, Object> toPageBook(@PathVariable("pageNum") Integer pageNum) {

        Map<String, Object> maps = new HashMap<>();

        System.out.println("pageNum" + pageNum);
        FindBookByInformation findBookByInformation = (FindBookByInformation) session.getAttribute("findBookByInformation");


        //有订单信息要求
        System.out.println("findBookByInformation" + findBookByInformation);
        //获取分页订单
        List<Book> bookList = bookService.getBookInfoLimit(pageNum, 5, findBookByInformation);//通过图书的信息 查找的图书
        Integer bookByInformationNum = bookService.getBookByInformationNum(findBookByInformation);//获取有查找信息的总数

        maps.put("bookList", bookList);
        maps.put("bookByInformationNum", bookByInformationNum);

        System.out.println(bookList);
        System.out.println(bookByInformationNum);

        return maps;
    }

    /**
     * 查找作者到作者信息页面
     */
    @GetMapping("/bookInfo.html/{book_id}")
    public String bookInfohtml(@PathVariable("book_id") Integer bookId, Model model) {

        //查找书的平均星
        Float AverageStar=bookService.getAverageStarByBookId(bookId);
        if(AverageStar==null){
            //没人给他评论给过
            model.addAttribute("AverageStar", "没人参与评论");
        }else{
            String AverageStarString = String.format("%.1f", AverageStar);
            model.addAttribute("AverageStar", AverageStarString);
        }

        Book book = bookService.getBookInfoByBookId(bookId);
        if (book != null) {
            model.addAttribute("book", book);
        } else {
            model.addAttribute("message", "未找到图书 id出错");
        }
        return "/admin/BookInformation";
    }


    @PostMapping("/addBook")
    @ResponseBody
    public Map<String, Object> addBook(Book book, Integer author_id, Integer booktype) {
        Map<String, Object> map = new HashMap<>();
        System.out.println(book);

        Author author = new Author();
        author.setId(author_id);
        BookType bookType = new BookType();
        bookType.setTypeid(booktype);
        book.setAuthor(author);
        book.setBookType(bookType);

        boolean successaddAuthor = bookService.addBook(book);
        if (successaddAuthor) {
            //添加成功
            map.put("message", "添加成功");
        } else {
            map.put("message", "书名重复");

        }
        return map;

    }

    @PostMapping("/addBookImg")
    @ResponseBody
    public Map<String, Object> addBookImg(@RequestParam("file") MultipartFile file) {
        Map<String, Object> map = new HashMap<>();

        String fileName = file.getOriginalFilename();
        System.out.println("fileName:" + fileName);
        String filePath = "src/main/resources/static/image/img/";
        boolean hasFile = otherConfig.findFile(filePath, fileName);
        if (hasFile) {
            //有同名文件
            map.put("message", "有同名文件");
        } else {
            if (fileName.indexOf("\\") != -1) {
                fileName = fileName.substring(fileName.lastIndexOf("\\"));
            }

            File targetFile = new File(filePath);
            if (!targetFile.exists()) {
                targetFile.mkdirs();
            }
            FileOutputStream out = null;
            try {
                out = new FileOutputStream(filePath  + fileName);
                out.write(file.getBytes());
                out.flush();
                out.close();
            } catch (Exception e) {
                e.printStackTrace();
                map.put("message", "上传失败");
                return map;
            }
            map.put("message", "上传成功");
            map.put("pathname", "img/" + fileName);
        }
        return map;

    }

    @PostMapping("/addBookImg1")
    @ResponseBody
    public Map<String, Object> addBookImg1(@RequestParam("file") MultipartFile file) {
        Map<String, Object> map = new HashMap<>();

        String fileName = file.getOriginalFilename();
        System.out.println("fileName:" + fileName);
        String filePath = "src/main/resources/static/image/img/";

        if (fileName.indexOf("\\") != -1) {
            fileName = fileName.substring(fileName.lastIndexOf("\\"));
        }

        File targetFile = new File(filePath);
        if (!targetFile.exists()) {
            targetFile.mkdirs();
        }
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(filePath + fileName);
            out.write(file.getBytes());
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
            map.put("message", "上传失败");
            return map;
        }
        map.put("message", "上传成功");
        map.put("pathname", "img/" + fileName);

        return map;

    }

    /**
     * 查找图书
     *
     * @param model
     * @return
     */
    @GetMapping("/bookInfo/{book_id}")
    @ResponseBody
    public Map<String, Object> bookInfo(@PathVariable("book_id") Integer bookId, Model model) {
        Map<String, Object> map = new HashMap<>();
        Book book = bookService.getBookInfoByBookId(bookId);
        if (book != null) {
            map.put("book", book);
        } else {
            map.put("message", "未找到作者  id错误");
        }
        return map;


    }

/**
 * 下架
 */
    @PostMapping("/deletebook")
    @ResponseBody
    public Map<String, Object> deletebook(Integer bookid) {
        Map<String, Object> map = new HashMap<>();
        boolean isshop=false;
        Boolean isishop = null;
        try {
            //TODO
            isishop = bookService.updateBookisshop(bookid,isshop);

        } catch (Exception e) {
            e.printStackTrace();
            map.put("message", "下架失败，其他问题");
        }
        if (isishop) {
            //成功删除
            map.put("message", "下架成功");
        } else {
            map.put("message", "下架失败，未找到用户 请检查id");
        }
        return map;
    }


    /**
     * 上架作者所有书
     */
    @PostMapping("/isshopbook")
    @ResponseBody
    public Map<String, Object> isshopbook( Integer bookid) {
        Map<String, Object> map = new HashMap<>();
        boolean isshop=true;
        Boolean isishop = null;
        try {
            //TODO
            isishop = bookService.updateBookisshop(bookid,isshop);

        } catch (Exception e) {
            e.printStackTrace();
            map.put("message", "上架失败，其他问题");
        }
        if (isishop) {
            //成功删除
            map.put("message", "上架成功");
        } else {
            map.put("message", "上架失败，未找到用户 请检查id");
        }
        return map;
    }




    @PostMapping("/updateBook")
    @ResponseBody
    public Map<String, Object> updateBook(Book book, Integer author_id, Integer booktype) {
        Map<String, Object> map = new HashMap<>();
        System.out.println(book);

        Author author = new Author();
        author.setId(author_id);
        BookType bookType = new BookType();
        bookType.setTypeid(booktype);
        book.setAuthor(author);
        book.setBookType(bookType);

        boolean successupdateAuthor = false;
        try {
            successupdateAuthor = bookService.updateBook(book);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (successupdateAuthor) {
            //添加成功
            map.put("message", "添加成功");
        } else {
            map.put("message", "书名重复");

        }
        return map;

    }



}
