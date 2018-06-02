package com.tryeat.rest.service;

import com.tryeat.rest.model.Restaurant;
import com.tryeat.rest.model.Status;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Callback;

public class RestaurantService {
    private static RestaurantServiceInterface userServiceInterface = ServiceGenerator.createService(RestaurantServiceInterface.class);

    public static void getRestaurants(Callback<ArrayList<Restaurant>> callback) {
        userServiceInterface.getRestaurants().enqueue(callback);
    }

    public static void getRestaurant(int restaurantId, Callback<Restaurant> callback) {
        userServiceInterface.getRestaurant(restaurantId).enqueue(callback);
    }

    public static void getRestaurants(String idList, Callback<ArrayList<Restaurant>> callback) {
        userServiceInterface.getRestaurants(idList).enqueue(callback);
    }

    public static void getRestaurant(String name, Callback<ArrayList<Restaurant>> callback) {
        userServiceInterface.getRestaurant(name).enqueue(callback);
    }

    public static void getRestaurant(double lat, double ion, Callback<ArrayList<Restaurant>> callback) {
        userServiceInterface.getRestaurant(lat,ion).enqueue(callback);
    }

    public static void isExistRestaurant(String name, Callback<Status> callback) {
        userServiceInterface.isExistRestaurant(name).enqueue(callback);
    }

    public static void isExistRestaurant(double lat, double ion,Callback<ArrayList<Restaurant>> callback) {
        userServiceInterface.isExistRestaurant(lat,ion).enqueue(callback);
    }

    public static void getReviewCount(int restaurantId, Callback<Status> callback) {
        userServiceInterface.getReviewCount(restaurantId).enqueue(callback);
    }

    public static void addRestaurant(String name,double latitude,double longitude,Callback<Status> callback) {
        HashMap<String,Object> body = new HashMap<>();
        body.put("restaurant_name",name);
        body.put("locate_latitude",latitude);
        body.put("locate_longitude",longitude);
        userServiceInterface.addRestaurant(body).enqueue(callback);
    }

    public static void updateRestaurant(Callback<Status> callback) {
        HashMap<String,Object> body = new HashMap<>();
        userServiceInterface.updateRestaurant(body).enqueue(callback);
    }

    public static void deleteRestaurant(int restaurantId, Callback<Status> callback) {
        HashMap<String,Object> body = new HashMap<>();
        body.put("restaurant_id",restaurantId);
        userServiceInterface.deleteRestaurant(body).enqueue(callback);
    }
}
