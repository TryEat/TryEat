package com.tryeat.rest.service;

import com.tryeat.rest.model.Review;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ReviewServiceinterface {
    @GET("reviews/{user_id}")
    Call<ArrayList<Review>> getUserReviews(@Path("user_id") int user_id);

    @GET("reviews/{restaurant_id}")
    Call<ArrayList<Review>> getRestaurantReviews(@Path("restaurant_id") int restaurant_id);

    @GET("reviews/{user_id}/{restaurant_id}")
    Call<ArrayList<Review>> getRestaurantReviews(@Path("user_id") int user_id, @Path("restaurant_id") int restaurant_id);

}
