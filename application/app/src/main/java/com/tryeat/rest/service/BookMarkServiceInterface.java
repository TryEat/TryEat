package com.tryeat.rest.service;

import com.tryeat.rest.model.BookMark;
import com.tryeat.rest.model.Restaurant;
import com.tryeat.rest.model.Status;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface BookMarkServiceInterface {
    @GET("bookmarks/{user_id}/{position}")
    Call<ArrayList<Restaurant>> getBookMarks(@Path("user_id") int userId,@Path("position") int position);

    @POST("bookmarks/")
    Call<Status> addBookMark(@Body HashMap<String,Object> body);

    @DELETE("bookmarks/")
    Call<Status> removeBookMark(@Body HashMap<String,Object> body);
}
