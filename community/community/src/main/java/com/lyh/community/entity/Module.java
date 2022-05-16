package com.lyh.community.entity;

public class Module {
    private int mid;
    private String mname;
    private String mintro="板块无简介";
    private int uid;

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

    public String getIntro() {
        return mintro;
    }

    public void setIntro(String intro) {
        this.mintro = mintro;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }
}
