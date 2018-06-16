package com.tryeat.tryeat;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import android.support.v4.app.FragmentManager;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
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
    RecyclerView lv;
    RecyclerView.LayoutManager mLayoutManager;
    RestaurantListAdapter rAdapter;

    ArrayList<Restaurant> mListItem1;

    ImageView header;

    NestedScrollView nestedScrollView;

    SwipeRefreshLayout refreshLayout;

    LinearLayout filter;

    boolean getFlag = false;

    int mDistance=0, mType=0;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mListItem1 = new ArrayList<>();

        NavigationManager.setVisibility(View.VISIBLE);

        view = inflater.inflate(R.layout.restaurant_list_fragment, container, false);

        filter = view.findViewById(R.id.filter);
        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                FilterDialogFragment dialogFragment = new FilterDialogFragment();
                Bundle bundle = new Bundle(2);
                bundle.putInt("type",mType);
                bundle.putInt("distance",mDistance);

                dialogFragment.setArguments(bundle);
                dialogFragment.setInterface(new FilterDialogFragment.FilterInterface() {
                    @Override
                    public void setSetting(int type, int distance) {
                        setSearchTypeSetting(type, distance);
                    }
                });
                dialogFragment.show(fm, "frament_place");
            }
        });

        header = view.findViewById(R.id.header);

        refreshLayout = view.findViewById(R.id.refreshlayout);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mListItem1.clear();
                getData();
                refreshLayout.setRefreshing(false);
            }
        });

        nestedScrollView = view.findViewById(R.id.nested_view);

        Glide.with(view)
                .load(R.drawable.list_header_image2)
                .into(header);

        lv = view.findViewById(R.id.listView);
        lv.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getContext());
        lv.setLayoutManager(mLayoutManager);
        rAdapter = new RestaurantListAdapter(mListItem1);
        rAdapter.setOnItemClickListener(new RestaurantListAdapter.ClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                itemClick(position);
            }
        });

        lv.setAdapter(rAdapter);

        nestedScrollView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                View view = (View) nestedScrollView.getChildAt(nestedScrollView.getChildCount() - 1);

                int diff = (view.getBottom() - (nestedScrollView.getHeight() + nestedScrollView
                        .getScrollY()));

                if (diff == 0) {
                    getData();
                }
            }
        });
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (mListItem1.size() == 0)
            getData();

    }

    public void getRestaurantListOrderByDistacne() {
        if (getFlag == true) return;
        getFlag = true;
        RestaurantService.getRestaurantsOrderByDistance(MyLocation.getLocation().getLatitude(),MyLocation.getLocation().getLongitude(),rAdapter.getItemCount(),mDistance, new Callback<ArrayList<Restaurant>>() {
            @Override
            public void onResponse(Call<ArrayList<Restaurant>> call, Response<ArrayList<Restaurant>> response) {
                if (response.isSuccessful()) {
                    List<Restaurant> restaurants = response.body();
                    int size = restaurants.size();
                    for (int i = 0; i < size; i++) {
                        Restaurant item = restaurants.get(i);
                        mListItem1.add(item);
                    }
                    rAdapter.notifyDataSetChanged();
                    getFlag = false;
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Restaurant>> call, Throwable t) {
                Log.d("debug", "getRestaurantList onFailure" + t);
                getFlag = false;
            }
        });
    }

    public void getRestaurantListOrderByRate() {
        if (getFlag == true) return;
        getFlag = true;
        RestaurantService.getRestaurantsOrderByRate(MyLocation.getLocation().getLatitude(),MyLocation.getLocation().getLongitude(),rAdapter.getItemCount(),mDistance, new Callback<ArrayList<Restaurant>>() {
            @Override
            public void onResponse(Call<ArrayList<Restaurant>> call, Response<ArrayList<Restaurant>> response) {
                if (response.isSuccessful()) {
                    List<Restaurant> restaurants = response.body();
                    int size = restaurants.size();
                    for (int i = 0; i < size; i++) {
                        Restaurant item = restaurants.get(i);
                        mListItem1.add(item);
                    }
                    rAdapter.notifyDataSetChanged();
                    getFlag = false;
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Restaurant>> call, Throwable t) {
                Log.d("debug", "getRestaurantList onFailure" + t);
                getFlag = false;
            }
        });
    }

    public void getRestaurantListOrderByReview() {
        if (getFlag == true) return;
        getFlag = true;
        RestaurantService.getRestaurantsOrderByReview(MyLocation.getLocation().getLatitude(),MyLocation.getLocation().getLongitude(),rAdapter.getItemCount(),mDistance, new Callback<ArrayList<Restaurant>>() {
            @Override
            public void onResponse(Call<ArrayList<Restaurant>> call, Response<ArrayList<Restaurant>> response) {
                if (response.isSuccessful()) {
                    List<Restaurant> restaurants = response.body();
                    int size = restaurants.size();
                    for (int i = 0; i < size; i++) {
                        Restaurant item = restaurants.get(i);
                        mListItem1.add(item);
                    }
                    rAdapter.notifyDataSetChanged();
                    getFlag = false;
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Restaurant>> call, Throwable t) {
                Log.d("debug", "getRestaurantList onFailure" + t);
                getFlag = false;
            }
        });
    }

    public void getRestaurantsOrderByRecommander() {
        if (getFlag == true) return;
        getFlag = true;
        RestaurantService.getRestaurantsOrderByRecommander(LoginToken.getId(),MyLocation.getLocation().getLatitude(),MyLocation.getLocation().getLongitude(),rAdapter.getItemCount(),mDistance, new Callback<ArrayList<Restaurant>>() {
            @Override
            public void onResponse(Call<ArrayList<Restaurant>> call, Response<ArrayList<Restaurant>> response) {
                if (response.isSuccessful()) {
                    List<Restaurant> restaurants = response.body();
                    int size = restaurants.size();
                    for (int i = 0; i < size; i++) {
                        Restaurant item = restaurants.get(i);
                        mListItem1.add(item);
                    }
                    rAdapter.notifyDataSetChanged();
                    getFlag = false;
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Restaurant>> call, Throwable t) {
                Log.d("debug", "getRestaurantList onFailure" + t);
                getFlag = false;
            }
        });
    }

    public void itemClick(int position) {
        if(mListItem1.size()>position) {
            Bundle bundle = new Bundle(2);
            Restaurant item = mListItem1.get(position);
            bundle.putSerializable("reviewItem", item);
            FragmentLoader.startFragment(R.id.frament_place, RestaurantDetailFragment.class, bundle, true);
        }
    }

    public void setSearchTypeSetting(int type, int distance) {
        mDistance = distance;
        mType = type;
        mListItem1.clear();
        getData();
    }

    public void getData(){
        // 리뷰, 거리, 평점
        switch (mType){
            case 0:
                getRestaurantsOrderByRecommander();
                break;
            case 1:
                getRestaurantListOrderByReview();
                break;
            case 2:
                getRestaurantListOrderByRate();
                break;
            case 3:
                getRestaurantListOrderByDistacne();
                break;
        }
    }
}

