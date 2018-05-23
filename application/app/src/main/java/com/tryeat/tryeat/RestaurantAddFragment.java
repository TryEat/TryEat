package com.tryeat.tryeat;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.tryeat.team.tryeat_service.R;
import com.tryeat.rest.model.Status;
import com.tryeat.rest.model.StatusCode;
import com.tryeat.rest.service.RestaurantService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


// Dialog 방식으로 대체
/**
 * Created by socce on 2018-05-08.
 */

public class RestaurantAddFragment extends Fragment{
    View view;

    public RestaurantAddFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.restaurant_add_fragment,container,false);
        Button addButton = (Button)view.findViewById(R.id.addButton);
        addButton.setOnClickListener(addRestaurant());
        return view;
    }

    public View.OnClickListener addRestaurant(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RestaurantService.addRestaurant(new Callback< Status>(){
                    @Override
                    public void onResponse(Call<Status> call, Response<Status> response) {
                        if (response.code() == StatusCode.ADD_RESTAURANT_SUCCESS) {
                        } else if (response.code() == StatusCode.ADD_RESTAURANT_FAIL) {
                        }
                    }
                    @Override
                    public void onFailure(Call<Status> call, Throwable t) {
                    }
                });
            }
        };
    }
}