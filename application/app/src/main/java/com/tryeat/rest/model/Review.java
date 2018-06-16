package com.tryeat.rest.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.sql.Timestamp;

public class Review implements Serializable, Parcelable {
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

    protected Review(Parcel in) {
        reviewId = in.readInt();
        restaurantId = in.readInt();
        writer = in.readString();
        restaurantName = in.readString();
        address = in.readString();
        userId = in.readInt();
        image = in.readParcelable(Image.class.getClassLoader());
        text = in.readString();
        rate = in.readFloat();
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

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public void setRate(float rate) {
        this.rate = rate;
    }

    public static final Creator<Review> CREATOR = new Creator<Review>() {
        @Override
        public Review createFromParcel(Parcel in) {
            return new Review(in);
        }

        @Override
        public Review[] newArray(int size) {
            return new Review[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(reviewId);
        dest.writeInt(restaurantId);
        dest.writeString(writer);
        dest.writeString(restaurantName);
        dest.writeString(address);
        dest.writeInt(userId);
        dest.writeParcelable(image,flags);
        dest.writeString(text);
        dest.writeSerializable(date);
        dest.writeFloat(rate);
    }
}
