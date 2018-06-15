package com.tryeat.rest.model;

import com.google.gson.annotations.SerializedName;

import java.sql.Timestamp;

public class User {
    private int user_id;
    private String user_login_id;
    private String user_pwd;
    private String profile;
    @SerializedName("review_count")
    private int review_count;
    @SerializedName("bookmark_count")
    private int bookmark_count;
    private Timestamp signdate;

    public int getReview_count() {
        return review_count;
    }

    public void setReview_count(int review_count) {
        this.review_count = review_count;
    }

    public int getBookmark_count() {
        return bookmark_count;
    }

    public void setBookmark_count(int bookmark_count) {
        this.bookmark_count = bookmark_count;
    }

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

