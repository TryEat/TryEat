package com.tryeat.tryeat;


import android.util.Log;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SimpleCallBack<T> implements Callback<T> {

    String mServiceName;
    Success<T> mRequestSuccess;

    interface Success<T>{
        void toDo(Response<T> response);
        void exception();
    }

    SimpleCallBack(String serviceName, Success<T> requestSuccess){
        this.mServiceName = serviceName;
        this.mRequestSuccess = requestSuccess;
    }

    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        if(response.isSuccessful()){
            mRequestSuccess.toDo(response);
        }else{
            mRequestSuccess.exception();
        }
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        Log.d(mServiceName,t.toString());
        mRequestSuccess.exception();
    }
}
