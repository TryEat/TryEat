package com.tryeat.rest.service;

import com.tryeat.rest.model.Status;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

interface SignServiceInterface {
    @POST("sign/signin")
    Call<Status> signIn(@Body HashMap<String,Object> body);

    @POST("sign/signout")
    Call<Status> signOut();

    @POST("sign/signup")
    Call<Status> signUp(@Body HashMap<String,Object> body);
}
