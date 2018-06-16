package com.tryeat.rest.service;

import com.tryeat.rest.model.Status;
import com.tryeat.rest.model.User;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

interface UserServiceInterface {
    @GET("users/")
    Call<ArrayList<User>> getUsers();

    @GET("users/{user_id}")
    Call<User> getUser(@Path("user_id") int userId);

    @POST("users/profile")
    Call<Status> updateProfile(@Body HashMap<String,Object> body);

    @DELETE("users")
    Call<Status> deleteUser(@Body HashMap<String,Object> body);
}
