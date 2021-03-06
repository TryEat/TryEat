package com.tryeat.rest.service;

import com.tryeat.rest.model.Restaurant;
import com.tryeat.rest.model.Status;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Callback;

public class BookMarkService {
    private static BookMarkServiceInterface bookMarkServiceInterface = ServiceGenerator.createService(BookMarkServiceInterface.class);

    public static void getBookMarks(int userId,int position, Callback<ArrayList<Restaurant>> callback) {
        bookMarkServiceInterface.getBookMarks(userId,position).enqueue(callback);
    }

    public static void isExistBookMarks(int userId,int restaurantId, Callback<Status> callback) {
        bookMarkServiceInterface.isExistBookMarks(userId,restaurantId).enqueue(callback);
    }

    public static void addBookMark(int userId, int restaurantId, Callback<Status> callback) {
        HashMap<String,Object> body = new HashMap<>();
        body.put("user_id",userId);
        body.put("restaurant_id",restaurantId);
        bookMarkServiceInterface.addBookMark(body).enqueue(callback);
    }

    public static void removeBookMark(int userId, int restaurantId, Callback<Status> callback) {
        HashMap<String,Object> body = new HashMap<>();
        body.put("user_id",userId);
        body.put("restaurant_id",restaurantId);
        bookMarkServiceInterface.removeBookMark(body).enqueue(callback);
    }
}
