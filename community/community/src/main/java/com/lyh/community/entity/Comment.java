package com.lyh.community.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class Comment {
    private int cid;
    private String ccontent;
    private String ctime;
    private int uid;
    private int tid;
    private int canonymous;
    private String uname;
    private int clike=0;
    
    public int getClike() {
        return clike;
    }
    
    public void setCanonymous(int canonymous) {
        this.canonymous = canonymous;
    }
    
    public void setClike(int clike) {
        this.clike = clike;
    }
    
    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public String getCcontent() {
        return ccontent;
    }

    public void setCcontent(String ccontent) {
        this.ccontent = ccontent;
    }

    public String getCtime() {
        return ctime;
    }

    public void setCtime(String ctime) {
        this.ctime = ctime;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getTid() {
        return tid;
    }

    public void setTid(int tid) {
        this.tid = tid;
    }

    public int getCanonymous() {
        return canonymous;
    }

    public void setCcanonymous(int ccanonymous) {
        this.canonymous = ccanonymous;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }
}
