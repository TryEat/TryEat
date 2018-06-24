package com.tryeat.rest.service;

import com.tryeat.rest.model.Status;
import com.tryeat.rest.model.User;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.GET;
import retrofit2.http.Path;

public class UserService {
    private static UserServiceInterface userServiceInterface = ServiceGenerator.createService(UserServiceInterface.class);

    public static void getUser(int userId, Callback<User> callback) {
        userServiceInterface.getUser(userId).enqueue(callback);
    }

    interface UserServiceInterface {
        @GET("users/{user_id}")
        Call<User> getUser(@Path("user_id") int userId);
    }

}
