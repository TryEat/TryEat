package com.tryeat.rest.service;

import com.tryeat.rest.model.Follow;
import com.tryeat.rest.model.Status;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Callback;

public class FollowService {
    private static FollowServiceInterface userServiceInterface = ServiceGenerator.createService(FollowServiceInterface.class);

    public static void getFollowers(int userId, Callback<ArrayList<Follow>> callback) {
        userServiceInterface.getFollowers(userId).enqueue(callback);
    }

    public static void addFollower(int userId,int targetId, Callback<Status> callback) {
        HashMap<String,Object> body = new HashMap<>();
        body.put("user_id",userId);
        body.put("target_id",targetId);
        userServiceInterface.addFollower(body).enqueue(callback);
    }

    public static void removeFollower(int userId, int targetId, Callback<Status> callback) {
        HashMap<String,Object> body = new HashMap<>();
        body.put("user_id",userId);
        body.put("target_id",targetId);
        userServiceInterface.removeFollower(body).enqueue(callback);
    }
}
