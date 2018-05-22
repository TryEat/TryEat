package com.tryeat.rest.service;

import com.tryeat.rest.model.Status;
import com.tryeat.rest.model.User;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Callback;

public class UserService {
    private static UserServiceInterface userServiceInterface = ServiceGenerator.createService(UserServiceInterface.class);

    public static void getUsers(Callback<ArrayList<User>> callback) {
        userServiceInterface.getUsers().enqueue(callback);
    }

    public static void getUser(int userId, Callback<User> callback) {
        userServiceInterface.getUser(userId).enqueue(callback);
    }

    public static void signIn(String id, String pwd, Callback<Status> callback) {
        HashMap<String, Object> body = new HashMap<>();
        body.put("user_login_id",id);
        body.put("user_pwd",pwd);
        userServiceInterface.signIn(body).enqueue(callback);
    }

    public static void signUp(String id, String pwd, Callback<Status> callback) {
        HashMap<String, Object> body = new HashMap<>();
        body.put("user_login_id",id);
        body.put("user_pwd",pwd);
        userServiceInterface.signUp(body).enqueue(callback);
    }

    public static void updateProfile(int userId, String profile, Callback<Status> callback) {
        HashMap<String, Object> body = new HashMap<>();
        body.put("user_id",userId);
        body.put("profile",profile);
        userServiceInterface.updateProfile(body).enqueue(callback);
    }

    public static void deleteUser(int userId, String pwd, Callback<Status> callback) {
        HashMap<String, Object> body = new HashMap<>();
        body.put("user_id",userId);
        body.put("profile",pwd);
        userServiceInterface.deleteUser(body).enqueue(callback);
    }
}
