package com.tryeat.tryeat;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

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


public class RestaurantListFragment extends Fragment {
    View view;
    ListView lv;
    RestaurantListAdapter rAdapter;

    public RestaurantListFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.restaurant_list_fragment, container, false);
        lv = view.findViewById(R.id.listView);
        rAdapter = new RestaurantListAdapter(view.getContext(), R.layout.restaurant_list_item);
        lv.setAdapter(rAdapter);
        lv.setOnItemClickListener(itemClick());
        getRestaurantList();

        return view;
    }

    public AdapterView.OnItemClickListener itemClick(){
        return new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                FragmentManager fm = getFragmentManager();
                FragmentTransaction fragmentTransaction = fm.beginTransaction();
                RestaurantDetailFragment fragment = new RestaurantDetailFragment();
                Bundle bundle = new Bundle(2);
                RestaurantListItem item = (RestaurantListItem)adapterView.getItemAtPosition(i);
                bundle.putSerializable("item",item);
                fragment.setArguments(bundle);
                fragmentTransaction.replace(R.id.frament_place,fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        };
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
                        rAdapter.addItem(new RestaurantListItem(null,item.restaurant_id,item.restaurant_name,safeDivide(item.total_rate,item.review_count)));
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

    private double safeDivide(int a, int b){
        if(a!=0&&b!=0)return a/b;
        return 0;
    }
}

