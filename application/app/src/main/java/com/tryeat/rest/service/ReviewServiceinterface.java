package com.tryeat.rest.service;

import com.tryeat.rest.model.Review;
import com.tryeat.rest.model.Status;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ReviewServiceInterface {
    @GET("reviews/{user_id}/user")
    Call<ArrayList<Review>> getUserReviews(@Path("user_id") int userId);

    @GET("reviews/{restaurant_id}/restaurant")
    Call<ArrayList<Review>> getRestaurantReviews(@Path("restaurant_id") int restaurantId);

    @GET("reviews/{user_id}/{restaurant_id}")
    Call<ArrayList<Review>> getRestaurantReviews(@Path("user_id") int userId, @Path("restaurant_id") int restaurantId);

    @POST("reviews")
    Call<Status> writeReview(@Body HashMap<String,Object> body);

    @PUT("reviews")
    Call<Status> updateReview(@Body HashMap<String,Object> body);

    @DELETE("reviews")
    Call<Status> deleteReview(@Body HashMap<String,Object> body);
}
