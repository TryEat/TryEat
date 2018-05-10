package com.example.socce.tryeat_app;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * Created by socce on 2018-05-08.
 */


public class RestaurantDetailFragment extends Fragment {
    View view;

    public RestaurantDetailFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.restaurant_detail_fragment,container,false);

        return view;
    }
}
