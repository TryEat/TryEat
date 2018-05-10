package com.example.socce.tryeat_app;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


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

        return view;
    }
}