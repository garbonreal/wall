package com.lyh.community.entity;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class Topic {
    private int tid;
    private String tname;
    private String tcontent="话题无简介";
    private int uid;
    private int mid;
    private String mname;
//    private int clike =0;
    private String uname;
    private int tanonymous;
    private String ttime;

    public int getTid() {
        return tid;
    }

    public void setTid(int tid) {
        this.tid = tid;
    }

    public String getTname() {
        return tname;
    }

    public void setTname(String tname) {
        this.tname = tname;
    }

    public String getTcontent() {
        return tcontent;
    }

    public void setTcontent(String tcontent) {
        this.tcontent = tcontent;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getMid() {
        return mid;
    }

    public void setMid(int mid) {
        this.mid = mid;
    }

    public String getMname() {
        return mname;
    }

    public void setMname(String mname) {
        this.mname = mname;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public int getTanonymous() {
        return tanonymous;
    }

    public void setTanonymous(int tanonymous) {
        this.tanonymous = tanonymous;
    }

    public String getTtime() {
        return ttime;
    }

    public void setTtime(String ctime) {
        this.ttime = ctime;
    }
}
