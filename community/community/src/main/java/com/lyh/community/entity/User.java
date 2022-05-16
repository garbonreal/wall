package com.lyh.community.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class User {
    private int uid;
    private String uname;
    private String password;
    private byte sex=0;
    private byte age=-1;
//    private String motto="这个用户很懒，什么都没说！";
    private String email;
//    @DateTimeFormat(pattern = "yyyy-MM-dd")
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
//    private Date ctime;


    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public byte getSex() {
        return sex;
    }

    public void setSex(byte sex) {
        this.sex = sex;
    }

    public byte getAge() {
        return age;
    }

    public void setAge(byte age) {
        this.age = age;
    }

//    public String getMotto() {
//        return motto;
//    }
//
//    public void setMotto(String motto) {
//        this.motto = motto;
//    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

//    public Date getCtime() {
//        return ctime;
//    }
//
//    public void setCtime(Date ctime) {
//        this.ctime = ctime;
//    }
}
