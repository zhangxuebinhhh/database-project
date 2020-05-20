package com.nextdoor.z.entity;

import java.sql.Timestamp;

public class FriendApp {
    private int faid;
    private int uid1;
    private int uid2;
    private Timestamp fatime;
    private String is_approved;

    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getFaid() {
        return faid;
    }

    public void setFaid(int faid) {
        this.faid = faid;
    }

    public int getUid1() {
        return uid1;
    }

    public void setUid1(int uid1) {
        this.uid1 = uid1;
    }

    public int getUid2() {
        return uid2;
    }

    public void setUid2(int uid2) {
        this.uid2 = uid2;
    }

    public Timestamp getFatime() {
        return fatime;
    }

    public void setFatime(Timestamp fatime) {
        this.fatime = fatime;
    }

    public String getIs_approved() {
        return is_approved;
    }

    public void setIs_approved(String is_approved) {
        this.is_approved = is_approved;
    }
}
