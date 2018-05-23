package com.tryeat.rest.service;

import com.tryeat.rest.model.Status;

import java.util.HashMap;

import retrofit2.Callback;

public class SignService {
    private static SignServiceInterface signServiceInterface = ServiceGenerator.createService(SignServiceInterface.class);

    public static void signIn(String id, String pwd, Callback<Status> callback) {
        HashMap<String, Object> body = new HashMap<>();
        body.put("user_login_id",id);
        body.put("user_pwd",pwd);
        signServiceInterface.signIn(body).enqueue(callback);
    }

    public static void signOut(Callback<Status>callback){
        signServiceInterface.signOut().enqueue(callback);
    }

    public static void signUp(String id, String pwd, Callback<Status> callback) {
        HashMap<String, Object> body = new HashMap<>();
        body.put("user_login_id",id);
        body.put("user_pwd",pwd);
        signServiceInterface.signUp(body).enqueue(callback);
    }
}
