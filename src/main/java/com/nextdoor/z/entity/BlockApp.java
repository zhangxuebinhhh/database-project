package com.nextdoor.z.entity;

import java.sql.Timestamp;

public class BlockApp {
    private Integer bmaid;
    private Integer uid;
    private String username;
    private Integer bid;
    private String bmastate;
    private Timestamp bmatime;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getBmaid() {
        return bmaid;
    }

    public void setBmaid(Integer bmaid) {
        this.bmaid = bmaid;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Integer getBid() {
        return bid;
    }

    public void setBid(Integer bid) {
        this.bid = bid;
    }

    public String getBmastate() {
        return bmastate;
    }

    public void setBmastate(String bmastate) {
        this.bmastate = bmastate;
    }

    public Timestamp getBmatime() {
        return bmatime;
    }

    public void setBmatime(Timestamp bmatime) {
        this.bmatime = bmatime;
    }
}
