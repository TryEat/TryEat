package com.tryeat.rest.model;

import android.graphics.Bitmap;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.sql.Timestamp;

public class Review implements Serializable {
    @SerializedName("review_id")
    private int reviewId;
    @SerializedName("restaurant_id")
    private int restaurantId;
    @SerializedName("user_login_id")
    private String writer;
    @SerializedName("restaurant_name")
    private String restaurantName;
    @SerializedName("address")
    private String address;
    @SerializedName("user_id")
    private int userId;
    @SerializedName("img")
    private Image image;
    @SerializedName("content")
    private String text;
    @SerializedName("date")
    private Timestamp date;
    @SerializedName("rate")
    private float rate;
    private Bitmap bitmap;

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public int getReviewId() {
        return reviewId;
    }

    public void setReviewId(int reviewId) {
        this.reviewId = reviewId;
    }

    public int getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(int restaurantId) {
        this.restaurantId = restaurantId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public float getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

}
