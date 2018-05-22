package com.tryeat.rest.service;

import com.tryeat.rest.model.Review;

import java.util.ArrayList;

import retrofit2.Callback;

public class ReviewService {
    public static ReviewServiceinterface userServiceInterface = ServiceGenerator.createService(ReviewServiceinterface.class);

    public static void getUserReviews(int userId, Callback<ArrayList<Review>> callback) {
        userServiceInterface.getUserReviews(userId).enqueue(callback);
    }

    public static void getRestaurantReviews(int restaurantId, Callback<ArrayList<Review>> callback) {
        userServiceInterface.getRestaurantReviews(restaurantId).enqueue(callback);
    }

    public static void getRestaurantReviews(int userId,int restaurantId, Callback<ArrayList<Review>> callback) {
        userServiceInterface.getRestaurantReviews(userId,restaurantId).enqueue(callback);
    }
}
