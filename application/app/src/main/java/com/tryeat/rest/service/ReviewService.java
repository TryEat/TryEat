package com.tryeat.rest.service;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.tryeat.rest.model.Review;
import com.tryeat.rest.model.Status;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Callback;
import retrofit2.http.Multipart;

public class ReviewService {
    private static ReviewServiceInterface userServiceInterface = ServiceGenerator.createService(ReviewServiceInterface.class);

    public static void getUserReviews(int userId, int position, Callback<ArrayList<Review>> callback) {
        userServiceInterface.getUserReviews(userId, position).enqueue(callback);
    }

    public static void getRestaurantReviews(int restaurantId, int position, Callback<ArrayList<Review>> callback) {
        userServiceInterface.getRestaurantReviews(restaurantId, position).enqueue(callback);
    }

    public static void getRestaurantUserReviews(int userId, int restaurantId, Callback<ArrayList<Review>> callback) {
        userServiceInterface.getRestaurantUserReviews(userId, restaurantId).enqueue(callback);
    }

    public static void getRestaurantReviewsImage(int reviewId, Callback<Review> callback) {
        userServiceInterface.getRestaurantReviewsImage(reviewId).enqueue(callback);
    }

    public static void writeReview(final int userId, final int restaurantId, final String content, final Bitmap file, final float rate, final Callback<Status> callback) {
        byte[] byteArray = new byte[]{};
        if (file != null) {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            file.compress(Bitmap.CompressFormat.WEBP, 75, stream);
            byteArray = stream.toByteArray();
        }
        RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), byteArray);
        MultipartBody.Part imageBody = MultipartBody.Part.createFormData("upload", "image", reqFile);

        HashMap<String, Object> body = new HashMap<>();
        body.put("user_id", userId);
        body.put("restaurant_id", restaurantId);
        body.put("content", content);
        body.put("rate", rate);
        userServiceInterface.writeReview(imageBody, body).enqueue(callback);
    }

    public static void updateReview(int userId, int restaurantId, Callback<Status> callback) {
        HashMap<String, Object> body = new HashMap<>();

        userServiceInterface.updateReview(body).enqueue(callback);
    }

    public static void deleteReview(int reviewId, Callback<Status> callback) {
        HashMap<String, Object> body = new HashMap<>();
        body.put("review_id", reviewId);
        userServiceInterface.deleteReview(body).enqueue(callback);
    }
}
