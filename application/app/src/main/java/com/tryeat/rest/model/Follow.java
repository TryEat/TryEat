package com.tryeat.rest.model;

import com.google.gson.annotations.SerializedName;

public class Follow {
    @SerializedName("follow_id")
    private int followId;
    @SerializedName("user_id")
    private int userId;
    @SerializedName("restaurant_id")
    private int restaurantId;
    @SerializedName("restaurant_name")
    private String restaurantName;

    public int getFollowId() {
        return followId;
    }

    public void setFollowId(int followId) {
        this.followId = followId;
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
