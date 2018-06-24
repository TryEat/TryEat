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

interface RestaurantServiceInterface {
    @GET("restaurants/{id}/byid")
    Call<Restaurant> getRestaurant(@Path("id") int restaurantId);

    @GET("restaurants/{name}/byname")
    Call<ArrayList<Restaurant>> getRestaurant(@Path("name") String name);

    @GET("restaurants/byrecommender/{user_id}/{lat}/{ion}/{position}/{distance}")
    Call<ArrayList<Restaurant>> getRestaurantsOrderByRecommended(@Path("user_id") int userId, @Path("lat") double lat, @Path("ion") double ion,@Path("position") int position,@Path("distance") double distance);

    @GET("restaurants/byrate/{lat}/{ion}/{position}/{distance}")
    Call<ArrayList<Restaurant>> getRestaurantsOrderByRate(@Path("lat") double lat, @Path("ion") double ion,@Path("position") int position,@Path("distance") double distance);

    @GET("restaurants/byreview/{lat}/{ion}/{position}/{distance}")
    Call<ArrayList<Restaurant>> getRestaurantsOrderByReview(@Path("lat") double lat, @Path("ion") double ion,@Path("position") int position,@Path("distance") double distance);

    @GET("restaurants/bydistance/{lat}/{ion}/{position}/{distance}")
    Call<ArrayList<Restaurant>> getRestaurantsOrderByDistance(@Path("lat") double lat, @Path("ion") double ion, @Path("position") int position, @Path("distance") double distance);

    @Multipart
    @POST("restaurants/")
    Call<Status> addRestaurant(@Part MultipartBody.Part image, @PartMap HashMap<String, Object> body);

    @DELETE("restaurants/")
    Call<Status> deleteRestaurant(@Body HashMap<String, Object> body);
}
