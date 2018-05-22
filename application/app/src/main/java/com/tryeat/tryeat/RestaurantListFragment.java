package com.tryeat.tryeat;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.socce.tryeat_app.R;
import com.tryeat.rest.model.User;
import com.tryeat.rest.service.UserService;

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

    ArrayList<RestaurantListItem> mList = new ArrayList<>();

    public RestaurantListFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.restaurant_list_fragment, container, false);
        lv = view.findViewById(R.id.listView);
        rAdapter = new RestaurantListAdapter(view.getContext(), R.layout.restaurant_list_item, mList);
        lv.setAdapter(rAdapter);

        UserService.getUsers(new Callback<ArrayList<User>>() {
            @Override
            public void onResponse(Call<ArrayList<User>> call, Response<ArrayList<User>> response) {
                if(response.isSuccessful()){
                    List<User> users = response.body();
                    for(int i =0 ;i<users.size();i++){
                        mList.add(new RestaurantListItem(null,users.get(i).user_login_id+"",null));
                    }
                    rAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<User>> call, Throwable t) {

            }
        });

        return view;
    }
}

