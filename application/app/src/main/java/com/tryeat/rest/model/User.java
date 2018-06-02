package com.tryeat.rest.model;

import java.sql.Timestamp;

public class User {
    private int user_id;
    private String user_login_id;
    private String user_pwd;
    private String profile;
    private Timestamp signdate;

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getUser_login_id() {
        return user_login_id;
    }

    public void setUser_login_id(String user_login_id) {
        this.user_login_id = user_login_id;
    }

    public String getUser_pwd() {
        return user_pwd;
    }

    public void setUser_pwd(String user_pwd) {
        this.user_pwd = user_pwd;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public Timestamp getSigndate() {
        return signdate;
    }

    public void setSigndate(Timestamp signdate) {
        this.signdate = signdate;
    }
}

