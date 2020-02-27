package cn.heikaqiu.booktt.bean;

import java.util.Date;

/**
 * @author HeiKaQiu
 * @create 2020-02-13 12:01
 */
//是 封装查找用户的信息的bean对象
public class FindUserByInformation {

    private Integer start_id;
    private Integer last_id;//id查找


    private String username;//用户名查找

    private Float start_balance;
    private Float last_balance;//余额查找

    //管理员 1为是  0为否  是否是管理员
    private Boolean isadmin;

    //省 查找
    private String province;
    //市 查找
    private String city;

    //性别  1 为男    0为女  性别查找
    private Boolean gender;
    //注册时间      注册时间查找
    private Date start_time;
    private Date last_time;
    //电话  电话查找
    private String telephone;
    //最后使用时间  最后活跃时间查找
    private Date start_usetime;
    private Date last_usetime;

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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Float getStart_balance() {
        return start_balance;
    }

    public void setStart_balance(Float start_balance) {
        this.start_balance = start_balance;
    }

    public Float getLast_balance() {
        return last_balance;
    }

    public void setLast_balance(Float last_balance) {
        this.last_balance = last_balance;
    }



    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Boolean getIsadmin() {
        return isadmin;
    }

    public void setIsadmin(Boolean isadmin) {
        this.isadmin = isadmin;
    }

    public Boolean getGender() {
        return gender;
    }

    public void setGender(Boolean gender) {
        this.gender = gender;
    }

    public Date getStart_time() {
        return start_time;
    }

    public void setStart_time(Date start_time) {
        this.start_time = start_time;
    }

    public Date getLast_time() {
        return last_time;
    }

    public void setLast_time(Date last_time) {
        this.last_time = last_time;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public Date getStart_usetime() {
        return start_usetime;
    }

    public void setStart_usetime(Date start_usetime) {
        this.start_usetime = start_usetime;
    }

    public Date getLast_usetime() {
        return last_usetime;
    }

    public void setLast_usetime(Date last_usetime) {
        this.last_usetime = last_usetime;
    }

    @Override
    public String toString() {
        return "FindUserByInformation{" +
                "start_id=" + start_id +
                ", last_id=" + last_id +
                ", username='" + username + '\'' +
                ", start_balance=" + start_balance +
                ", last_balance=" + last_balance +
                ", isadmin=" + isadmin +
                ", province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", gender=" + gender +
                ", start_time=" + start_time +
                ", last_time=" + last_time +
                ", telephone='" + telephone + '\'' +
                ", start_usetime=" + start_usetime +
                ", last_usetime=" + last_usetime +
                '}';
    }
}
