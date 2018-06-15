package com.tryeat.rest.model;

import com.google.gson.annotations.SerializedName;

import java.sql.Timestamp;

public class BookMark {
    @SerializedName("bookmark_id")
    private int bookmarkId;
    @SerializedName("user_id")
    private int userId;
    @SerializedName("restaurant_id")
    private int restaurantId;
    @SerializedName("restaurant_name")
    private String restaurantName;

    public int getBookmarkId() {
        return bookmarkId;
    }

    public void setBookmarkId(int bookmarkId) {
        this.bookmarkId = bookmarkId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(int restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }
}
