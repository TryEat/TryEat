package com.tryeat.rest.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.sql.Timestamp;

public class Restaurant implements Serializable {

    @SerializedName("restaurant_id")
    private int id;
    @SerializedName("img")
    private Image image;
    @SerializedName("restaurant_name")
    private String name;
    @SerializedName("address")
    private String address;
    @SerializedName("phone")
    private String phone;
    @SerializedName("locate_latitude")
    private double lat;
    @SerializedName("locate_longitude")
    private double lon;
    @SerializedName("review_count")
    private int reviewCount;
    @SerializedName("total_rate")
    private float totalRate;
    @SerializedName("total_bookmark")
    private int totalBookMark;
    @SerializedName("date")
    private Timestamp date;
    @SerializedName("distance")
    private double distance;

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public int getTotalBookMark() {
        return totalBookMark;
    }

    public void setTotalBookMark(int totalBookMark) {
        this.totalBookMark = totalBookMark;
    }

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setTotalRate(float totalRate) {
        this.totalRate = totalRate;
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
