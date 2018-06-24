package com.tryeat.rest.service;

import com.tryeat.rest.model.GoogleAutoComplete;
import com.tryeat.rest.model.GoogleDetail;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class GoogleApiService {
    private static final String GOOGLE_API_KEY = "GOOGLE_PLACE_API_WEB_KEY";

    private static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://maps.googleapis.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    private static GooApiServiceInterface googleApiService = retrofit.create(GooApiServiceInterface.class);

    public static void getRestaurant(String name, double lat, double lon, int radius, Callback<GoogleAutoComplete> callback) {
        String location = Double.toString(lat)+','+Double.toString(lon);
        googleApiService.getRestaurant(name,"ko", location,radius,GOOGLE_API_KEY).enqueue(callback);
    }

    public static void getDetail(String placeId, Callback<GoogleDetail> callback) {
        googleApiService.getDetail(placeId,"ko",GOOGLE_API_KEY).enqueue(callback);
    }

    interface GooApiServiceInterface {
        @GET("maps/api/place/autocomplete/json")
        Call<GoogleAutoComplete> getRestaurant(@Query("input") String name,@Query("language") String language,@Query("location") String location, @Query("radius") int radius, @Query("key") String key);

        @GET("maps/api/place/details/json")
        Call<GoogleDetail> getDetail(@Query("placeid") String placeId, @Query("language") String language,@Query("key") String key);
    }
}
