package com.tryeat.rest.service;

import android.graphics.Bitmap;

import com.tryeat.rest.model.Restaurant;
import com.tryeat.rest.model.Status;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Callback;

public class RestaurantService {
    private static RestaurantServiceInterface restaurantServiceInterface = ServiceGenerator.createService(RestaurantServiceInterface.class);

    public static void getRestaurants(Callback<ArrayList<Restaurant>> callback) {
        restaurantServiceInterface.getRestaurants().enqueue(callback);
    }

    public static void getRestaurant(int restaurantId, Callback<Restaurant> callback) {
        restaurantServiceInterface.getRestaurant(restaurantId).enqueue(callback);
    }

    public static void getRestaurants(String idList, Callback<ArrayList<Restaurant>> callback) {
        restaurantServiceInterface.getRestaurants(idList).enqueue(callback);
    }

    public static void getRestaurant(String name, Callback<ArrayList<Restaurant>> callback) {
        restaurantServiceInterface.getRestaurant(name).enqueue(callback);
    }

    public static void getRestaurant(double lat, double ion, Callback<ArrayList<Restaurant>> callback) {
        restaurantServiceInterface.getRestaurant(lat, ion).enqueue(callback);
    }

    public static void getRestaurant(String name, double lat, double ion, Callback<ArrayList<Restaurant>> callback) {
        restaurantServiceInterface.getRestaurant(name, lat, ion).enqueue(callback);
    }

    public static void isExistRestaurant(String name, Callback<Status> callback) {
        restaurantServiceInterface.isExistRestaurant(name).enqueue(callback);
    }

    public static void isExistRestaurant(double lat, double ion, Callback<ArrayList<Restaurant>> callback) {
        restaurantServiceInterface.isExistRestaurant(lat, ion).enqueue(callback);
    }

    public static void getReviewCount(int restaurantId, Callback<Status> callback) {
        restaurantServiceInterface.getReviewCount(restaurantId).enqueue(callback);
    }

    public static void addRestaurant(String name, String phone, Bitmap file, double latitude, double longitude, Callback<Status> callback) {
        RequestBody reqFile = null;
        if (file != null) {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            file.compress(Bitmap.CompressFormat.WEBP, 75, stream);
            byte[] byteArray = stream.toByteArray();

            reqFile = RequestBody.create(MediaType.parse("image/*"), byteArray);
        }
        MultipartBody.Part imageBody = MultipartBody.Part.createFormData("upload", "image", reqFile);
        HashMap<String, Object> body = new HashMap<>();
        body.put("restaurant_name", name);
        body.put("phone", phone);
        body.put("locate_latitude", latitude);
        body.put("locate_longitude", longitude);
        restaurantServiceInterface.addRestaurant(imageBody, body).enqueue(callback);
    }

    public static void updateRestaurant(Callback<Status> callback) {
        HashMap<String, Object> body = new HashMap<>();
        restaurantServiceInterface.updateRestaurant(body).enqueue(callback);
    }

    public static void deleteRestaurant(int restaurantId, Callback<Status> callback) {
        HashMap<String, Object> body = new HashMap<>();
        body.put("restaurant_id", restaurantId);
        restaurantServiceInterface.deleteRestaurant(body).enqueue(callback);
    }
}
