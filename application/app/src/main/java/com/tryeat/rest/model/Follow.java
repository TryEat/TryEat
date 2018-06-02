package com.tryeat.rest.model;

import com.google.gson.annotations.SerializedName;

public class Follow {
    @SerializedName("follow_id")
    private int followId;
    @SerializedName("user_id")
    private int userId;
    @SerializedName("target_id")
    private int targetId;
    @SerializedName("user_login_id")
    private String targetName;

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

    public int getTargetId() {
        return targetId;
    }

    public void setTargetId(int targetId) {
        this.targetId = targetId;
    }

    public String getTargetName() {
        return targetName;
    }

    public void setTargetName(String targetName) {
        this.targetName = targetName;
    }
}
