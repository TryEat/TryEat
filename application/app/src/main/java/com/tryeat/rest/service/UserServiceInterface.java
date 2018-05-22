package com.tryeat.rest.service;

import com.tryeat.rest.model.User;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface UserServiceInterface {
    @GET("users/")
    Call<ArrayList<User>> getUsers();

    @GET("users/{user_id}")
    Call<User> getUser(@Path("user_id") int user_id);
}
