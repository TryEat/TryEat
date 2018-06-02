package com.tryeat.tryeat;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.tryeat.rest.model.Follow;
import com.tryeat.rest.service.FollowService;
import com.tryeat.team.tryeat_service.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FollowListFragment extends Fragment{
    View view;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    FollowListAdapter rAdapter;

    ArrayList<Follow> mListItem;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.follow_list_fragment, container, false);

            mListItem = new ArrayList<>();

            recyclerView = view.findViewById(R.id.listView);
            recyclerView.setHasFixedSize(true);
            mLayoutManager = new LinearLayoutManager(getContext());
            recyclerView.setLayoutManager(mLayoutManager);
            rAdapter = new FollowListAdapter(getContext(), mListItem);
            rAdapter.setOnItemClickListener(new FollowListAdapter.ClickListener() {
                @Override
                public void onItemClick(int position, View v) {

                }
            });
            recyclerView.setAdapter(rAdapter);
        }
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getFollowList();
    }

    public void getFollowList(){
        FollowService.getFollowers(LoginToken.getId(),new Callback<ArrayList<Follow>>() {
            @Override
            public void onResponse(Call<ArrayList<Follow>> call, Response<ArrayList<Follow>> response) {
                if(response.isSuccessful()){
                    List<Follow> restaurants = response.body();
                    int size = restaurants.size();
                    for(int i =0 ;i<size;i++){
                        Follow item = restaurants.get(i);
                        mListItem.add(item);
                    }
                    rAdapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onFailure(Call<ArrayList<Follow>> call, Throwable t) {
                Log.d("debug","getRestaurantList onFailure"+t);
            }
        });
    }
}
