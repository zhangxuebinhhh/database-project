package com.nextdoor.z.entity;

import java.sql.Timestamp;

public class User {
        private Integer id;
        private String username;
        private String password;
        private String uaddress;
        private String uphoto;
        private Timestamp lasttimevisitwebsite;
        private String uEmail;
        private Integer bid;
        private String birthday;
        private String description;
        private String occupation;
        private String birthplace;
        private String gender;
        private String status;
        private String pincline;
        private String website;
        private String religion;
        public User() {
            super();
        }
        public User(Integer id, String username, String password) {
            super();
            this.id = id;
            this.username = username;
            this.password = password;
        }
        public Integer getId() {
            return id;
        }
        public void setId(Integer id) {
            this.id = id;
        }
        public String getUsername() {
            return username;
        }
        public void setUsername(String username) {
            this.username = username;
        }
        public String getPassword() {
            return password;
        }
        public void setPassword(String password) {
            this.password = password;
        }

    public String getReligion() {
        return religion;
    }

    public void setReligion(String religion) {
        this.religion = religion;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getUEmail() {
        return uEmail;
    }

    public void setUEmail(String uEmail) {
        this.uEmail = uEmail;
    }

    public String getUaddress() {
        return uaddress;
    }

    public void setUaddress(String uaddress) {
        this.uaddress = uaddress;
    }

    public String getUphoto() {
        return uphoto;
    }

    public void setUphoto(String uphoto) {
        this.uphoto = uphoto;
    }

    public Timestamp getLasttimevisitwebsite() {
        return lasttimevisitwebsite;
    }

    public void setLasttimevisitwebsite(Timestamp lasttimevisitwebsite) {
        this.lasttimevisitwebsite = lasttimevisitwebsite;
    }

    public Integer getBid() {
        return bid;
    }

    public void setBid(Integer bid) {
        this.bid = bid;
    }

    public String getBirthday() {
        return birthday;
    }

    public String getPincline() {
        return pincline;
    }

    public void setPincline(String pincline) {
        this.pincline = pincline;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getBirthplace() {
        return birthplace;
    }

    public void setBirthplace(String birthplace) {
        this.birthplace = birthplace;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
        public String toString() {
            return "user [id=" + id + ", username=" + username + ", password=" + password + "]";
        }

}
