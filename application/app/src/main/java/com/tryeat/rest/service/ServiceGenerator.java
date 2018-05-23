package com.tryeat.rest.service;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceGenerator {
    //private static final String URL = "http://tryeat.homedns.tv:8080/";
    private static final String URL = "http://192.168.1.20:8080/";
    private static String mToken = "!@!#!";

    private static Interceptor mTokenInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            if (mToken != null) {
                Request.Builder requestBuilder = request.newBuilder().addHeader("Authorization", mToken);
                Request newRequest = requestBuilder.build();

                return chain.proceed(newRequest);
            }
            return chain.proceed(request);
        }
    };

    private static OkHttpClient httpClient = new OkHttpClient.Builder()
            .addNetworkInterceptor(mTokenInterceptor)
            .build();

    private static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient)
            .build();

    public static <S> S createService(Class<S> serviceClass) {
        return retrofit.create(serviceClass);
    }
}
