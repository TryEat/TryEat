package com.tryeat.rest.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Restaurant implements Serializable {

    @SerializedName("restaurant_id")
    private int id;
    @SerializedName("img")
    private Image image;
    @SerializedName("restaurant_name")
    private String name;
    @SerializedName("phone")
    private String phone;
    @SerializedName("locate_latitude")
    private double lat;
    @SerializedName("locate_longitude")
    private double lon;
    @SerializedName("open_time")
    private String openTime;
    @SerializedName("close_time")
    private String  closeTime;
    @SerializedName("review_count")
    private int reviewCount;
    @SerializedName("total_rate")
    private float totalRate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public String getOpenTime() {
        return openTime;
    }

    public void setOpenTime(String openTime) {
        this.openTime = openTime;
    }

    public String getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(String closeTime) {
        this.closeTime = closeTime;
    }

    public int getReviewCount() {
        return reviewCount;
    }

    public void setReviewCount(int reviewCount) {
        this.reviewCount = reviewCount;
    }

    public float getTotalRate() {
        return totalRate;
    }

    public void setTotalRate(int totalRate) {
        this.totalRate = totalRate;
    }
}
