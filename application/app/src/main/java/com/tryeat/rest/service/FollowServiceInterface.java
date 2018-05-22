package com.tryeat.rest.service;

import com.tryeat.rest.model.Follow;
import com.tryeat.rest.model.Status;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface FollowServiceInterface {
    @GET("follows/{user_id}")
    Call<ArrayList<Follow>> getFollowers(@Path("user_id") int userId);

    @POST("follows/")
    Call<Status> addFollower(@Body HashMap<String,Object> body);

    @DELETE("follows/")
    Call<Status> removeFollower(@Body HashMap<String,Object> body);
}
