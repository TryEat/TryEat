package com.tryeat.tryeat;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tryeat.rest.model.Restaurant;
import com.tryeat.rest.service.RestaurantService;
import com.tryeat.team.tryeat_service.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by socce on 2018-05-08.
 */


public class RestaurantListFragment extends Fragment{
    View view;
    RecyclerView lv;
    RecyclerView.LayoutManager mLayoutManager;
    RestaurantListAdapter rAdapter;

    ArrayList<Restaurant> mListItem1;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(view==null) {
            mListItem1 = new ArrayList<>();

            view = inflater.inflate(R.layout.restaurant_list_fragment, container, false);
            lv = view.findViewById(R.id.listView);
            lv.setHasFixedSize(true);
            mLayoutManager = new LinearLayoutManager(getContext());
            lv.setLayoutManager(mLayoutManager);
            lv = view.findViewById(R.id.listView);
            rAdapter = new RestaurantListAdapter(mListItem1);
            rAdapter.setOnItemClickListener(new RestaurantListAdapter.ClickListener() {
                @Override
                public void onItemClick(int position, View v) {
                    itemClick(position);
                }
            });
            lv.setAdapter(rAdapter);
            lv.setOnScrollChangeListener(new View.OnScrollChangeListener() {
                @Override
                public void onScrollChange(View view, int i, int i1, int i2, int i3) {
                    if(!lv.canScrollVertically(1)) {
                        //getRestaurantList();
                    }
                }
            });
        }
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if(mListItem1.size()==0)
            getRestaurantList();

    }

    public void getRestaurantList(){
        RestaurantService.getRestaurants(new Callback<ArrayList<Restaurant>>() {
            @Override
            public void onResponse(Call<ArrayList<Restaurant>> call, Response<ArrayList<Restaurant>> response) {
                if(response.isSuccessful()){
                    List<Restaurant> restaurants = response.body();
                    int size = restaurants.size();
                    for(int i =0 ;i<size;i++){
                        Restaurant item = restaurants.get(i);
                        mListItem1.add(item);
                    }
                    rAdapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onFailure(Call<ArrayList<Restaurant>> call, Throwable t) {
                Log.d("debug","getRestaurantList onFailure"+t);
            }
        });
    }

    public void itemClick(int position) {
        Bundle bundle = new Bundle(2);
        Restaurant item =  mListItem1.get(position);
        bundle.putSerializable("item",item);
        FragmentLoader.startFragment(R.id.frament_place,RestaurantDetailFragment.class,bundle,true);
    }
}

