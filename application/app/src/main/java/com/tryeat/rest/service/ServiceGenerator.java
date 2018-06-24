package com.tryeat.rest.service;

import android.icu.util.TimeUnit;

import com.tryeat.tryeat.LoginToken;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceGenerator {
    private static final String URL = "http://tryeats.homedns.tv:8080/";

    private static Interceptor mTokenInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            if (LoginToken.hasLoginToken()) {
                Request.Builder requestBuilder = request.newBuilder()
                        .addHeader("id", String.valueOf(LoginToken.getId()))
                        .addHeader("Authorization", LoginToken.getToken());
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

    public static String getServerUri(){
        return URL;
    }
}
