package com.tryeat.rest.service;

import com.tryeat.rest.model.Restaurant;
import com.tryeat.rest.model.Status;

import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.MultipartBody;
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

public interface RestaurantServiceInterface {
    @GET("restaurants/{position}")
    Call<ArrayList<Restaurant>> getRestaurants(@Path("position") int position);

    @GET("restaurants/{id}/byid")
    Call<Restaurant> getRestaurant(@Path("id") int restaurantId);

    @GET("restaurants/{name}/byname")
    Call<ArrayList<Restaurant>> getRestaurant(@Path("name") String name);

    @GET("restaurants/list/{id_list}/byid")
    Call<ArrayList<Restaurant>> getRestaurants(@Path("id_list") String name);

    @GET("restaurants/{lat}/{ion}/bylocation")
    Call<ArrayList<Restaurant>> getRestaurant(@Path("lat") double lat, @Path("ion") double ion);

    @GET("restaurants/{name}/{lat}/{ion}")
    Call<ArrayList<Restaurant>> getRestaurant(@Path("name") String name,@Path("lat") double lat, @Path("ion") double ion);

    @GET("restaurants/is_exist/{name}/byname")
    Call<Status> isExistRestaurant(@Path("name") String name);

    @GET("restaurants/is_exist/{lat}/{ion}/bylocation")
    Call<ArrayList<Restaurant>> isExistRestaurant(@Path("lat") double lat, @Path("ion") double ion);

    @GET("restaurants/count/{id}")
    Call<Status> getReviewCount(@Path("id") int restaurantId);

    @Multipart
    @POST("restaurants/")
    Call<Status> addRestaurant(@Part MultipartBody.Part image, @PartMap HashMap<String, Object> body);

    @PUT("restaurants/")
    Call<Status> updateRestaurant(@Body HashMap<String, Object> body);

    @DELETE("restaurants/")
    Call<Status> deleteRestaurant(@Body HashMap<String, Object> body);
}
