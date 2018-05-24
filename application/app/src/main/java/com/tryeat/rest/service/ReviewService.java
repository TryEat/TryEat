package com.tryeat.rest.service;

import com.tryeat.rest.model.Review;
import com.tryeat.rest.model.Status;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Callback;

public class ReviewService {
    private static ReviewServiceInterface userServiceInterface = ServiceGenerator.createService(ReviewServiceInterface.class);

    public static void getUserReviews(int userId, Callback<ArrayList<Review>> callback) {
        userServiceInterface.getUserReviews(userId).enqueue(callback);
    }

    public static void getRestaurantReviews(int restaurantId, Callback<ArrayList<Review>> callback) {
        userServiceInterface.getRestaurantReviews(restaurantId).enqueue(callback);
    }

    public static void getRestaurantReviews(int userId,int restaurantId, Callback<ArrayList<Review>> callback) {
        userServiceInterface.getRestaurantReviews(userId,restaurantId).enqueue(callback);
    }

    public static void writeReview(int userId,int restaurantId,String content,int rate, Callback<Status> callback) {
        HashMap<String, Object> body = new HashMap<>();
        body.put("user_id",userId);
        body.put("restaurant_id",restaurantId);
        body.put("content",content);
        body.put("rate",rate);
        userServiceInterface.writeReview(body).enqueue(callback);
    }

    public static void updateReview(int userId,int restaurantId, Callback<Status> callback) {
        HashMap<String, Object> body = new HashMap<>();

        userServiceInterface.updateReview(body).enqueue(callback);
    }

    public static void deleteReview(int reviewId, Callback<Status> callback) {
        HashMap<String, Object> body = new HashMap<>();
        body.put("review_id",reviewId);
        userServiceInterface.deleteReview(body).enqueue(callback);
    }
}
