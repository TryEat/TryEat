package com.tryeat.rest.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.sql.Timestamp;

public class Restaurant implements Serializable ,Parcelable {

    @SerializedName("restaurant_id")
    private int id;
    @SerializedName("img_uri")
    private String imgUri;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImgUri() {
        if(imgUri==null)return null;
        return "http://tryeat.homedns.tv:8080/restaurants/image/uri/"+imgUri;
    }

    public void setImgUri(String imgUri) {
        this.imgUri = imgUri;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public int getReviewCount() {
        return reviewCount;
    }

    public void setReviewCount(int reviewCount) {
        this.reviewCount = reviewCount;
    }

    public float getTotalRate() {
        return totalRate;
    }

    public void setTotalRate(float totalRate) {
        this.totalRate = totalRate;
    }

    public int getTotalBookMark() {
        return totalBookMark;
    }

    public void setTotalBookMark(int totalBookMark) {
        this.totalBookMark = totalBookMark;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    protected Restaurant(Parcel in) {
        id = in.readInt();
        imgUri = in.readString();
        name = in.readString();
        address = in.readString();
        phone = in.readString();
        lat = in.readDouble();
        lon = in.readDouble();
        reviewCount = in.readInt();
        totalRate = in.readFloat();
        totalBookMark = in.readInt();
        date = (Timestamp) in.readSerializable();
        distance = in.readDouble();
    }

    public static final Creator<Restaurant> CREATOR = new Creator<Restaurant>() {
        @Override
        public Restaurant createFromParcel(Parcel in) {
            return new Restaurant(in);
        }

        @Override
        public Restaurant[] newArray(int size) {
            return new Restaurant[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(imgUri);
        dest.writeString(name);
        dest.writeString(address);
        dest.writeString(phone);
        dest.writeDouble(lat);
        dest.writeDouble(lon);
        dest.writeInt(reviewCount);
        dest.writeFloat(totalRate);
        dest.writeInt(totalBookMark);
        dest.writeSerializable(date);
        dest.writeDouble(distance);
    }
}
