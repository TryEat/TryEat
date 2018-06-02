package com.tryeat.rest.service;

import com.tryeat.rest.model.Review;
import com.tryeat.rest.model.Status;

import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.MultipartBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

public interface ReviewServiceInterface {
    @GET("reviews/{user_id}/{position}/user")
    Call<ArrayList<Review>> getUserReviews(@Path("user_id") int userId, @Path("position") int position);

    @GET("reviews/{restaurant_id}/{position}/restaurant")
    Call<ArrayList<Review>> getRestaurantReviews(@Path("restaurant_id") int restaurantId, @Path("position") int position);

    @GET("reviews/{review_id}")
    Call<Review> getRestaurantReviewsImage(@Path("review_id") int reviewId);

    @GET("reviews/{user_id}/{restaurant_id}")
    Call<ArrayList<Review>> getRestaurantUserReviews(@Path("user_id") int userId, @Path("restaurant_id") int restaurantId);

    @Multipart
    @POST("reviews")
    Call<Status> writeReview(@Part MultipartBody.Part image, @PartMap HashMap<String, Object> body);

    @PUT("reviews")
    Call<Status> updateReview(@Body HashMap<String,Object> body);

    @DELETE("reviews")
    Call<Status> deleteReview(@Body HashMap<String,Object> body);
}
