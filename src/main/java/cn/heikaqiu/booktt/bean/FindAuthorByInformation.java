package cn.heikaqiu.booktt.bean;

import java.util.Date;

/**
 * @author HeiKaQiu
 * @create 2020-02-13 12:01
 */
//是 封装作者的信息的bean对象
public class FindAuthorByInformation {

    private Integer start_id;
    private Integer last_id;

    private String name;

    //国籍
    private String nationality;

    public Integer getStart_id() {
        return start_id;
    }

    public void setStart_id(Integer start_id) {
        this.start_id = start_id;
    }

    public Integer getLast_id() {
        return last_id;
    }

    public void setLast_id(Integer last_id) {
        this.last_id = last_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;



    }

    @Override
    public String toString() {
        return "FindAuthorByInformation{" +
                "start_id=" + start_id +
                ", last_id=" + last_id +
                ", name='" + name + '\'' +
                ", nationality='" + nationality + '\'' +
                '}';
    }
}
