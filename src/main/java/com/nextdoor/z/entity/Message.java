package com.nextdoor.z.entity;

public class Message {

    private Integer id;
    private Integer parent;
    private Integer mid;
    private Integer creator;
    private String fullname;
    private String created;
    private String content;
    private String latitude;
    private String longitude;
    private String feed;
    private Integer to_uid;
    private String profile_picture_url;

    public Integer getMid() {
        return mid;
    }

    public void setMid(Integer mid) {
        this.mid = mid;
    }

    public String getProfile_picture_url(){
        return profile_picture_url;
    }

    public void setProfile_picture_url(String profile_picture_url) {
        this.profile_picture_url = profile_picture_url;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getParent() {
        return this.parent;
    }

    public void setParent(Integer id) {
        this.parent = id;
    }

    public Integer getCreator() {
        return creator;
    }

    public void setCreator(Integer uid) {
        this.creator = uid;
        this.profile_picture_url = "../images/avatar-"+ creator + ".jpg";
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String name) {
        this.fullname = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String s) {
        this.content = s;
    }

    public String getLongtitude() {
        return longitude;
    }

    public void setLongtitude(String s) {
        this.longitude = s;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String s) {
        this.latitude = s;
    }

    public String getFeed() { return feed; }

    public void setFeed(String s) {
        this.feed = s;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String s) {
        this.created = s;
    }

    public Integer getTo_uid() {
        return to_uid;
    }

    public void setTo_uid(Integer s) {
        this.to_uid = s;
    }

    public String toString() { return "message [id=" + id + ",mid =" + mid + ", parent=" + parent + ", creator=" + creator + "]"; }

}
