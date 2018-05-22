package com.tryeat.rest.service;

import com.tryeat.rest.model.User;

import java.util.ArrayList;

import retrofit2.Callback;

public class UserService {
    public static UserServiceInterface userServiceInterface = ServiceGenerator.createService(UserServiceInterface.class);

    public static void getUsers(Callback<ArrayList<User>> callback) {
        userServiceInterface.getUsers().enqueue(callback);
    }

    public static void getUser(int userId, Callback<User> callback) {
        userServiceInterface.getUser(userId).enqueue(callback);
    }
}
