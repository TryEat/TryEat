package com.tryeat.tryeat;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import android.support.v4.app.FragmentManager;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.tryeat.rest.model.Restaurant;
import com.tryeat.rest.service.RestaurantService;
import com.tryeat.team.tryeat_service.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;

/**
 * Created by socce on 2018-05-08.
 */


public class RestaurantListFragment extends Fragment{
    private View view;
    private RecyclerView lv;
    private RecyclerView.LayoutManager mLayoutManager;
    private RestaurantListAdapter rAdapter;

    private ArrayList<Restaurant> mListItem1;

    private ImageView header;

    private NestedScrollView nestedScrollView;

    private SwipeRefreshLayout refreshLayout;

    private ImageView filter;

    private boolean getFlag = false;

    private int mDistance=5;
    private int mType=0;
    private double[] mDistanceValue = {0.1,0.5,1,3,5,0};

    SimpleCallBack<ArrayList<Restaurant>> callBack;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mListItem1 = new ArrayList<>();

        NavigationManager.setVisibility(View.VISIBLE);

        callBack = new SimpleCallBack<>(Restaurant.class.getSimpleName(), new SimpleCallBack.Success<ArrayList<Restaurant>>() {
            @Override
            public void toDo(Response<ArrayList<Restaurant>> response) {
                List<Restaurant> restaurants = response.body();
                addItems(restaurants);
            }

            @Override
            public void exception() {
                getFlag = false;
            }
        });

        view = inflater.inflate(R.layout.restaurant_list_fragment, container, false);

        filter = view.findViewById(R.id.filter);
        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                FilterDialogFragment dialogFragment = new FilterDialogFragment();
                Bundle bundle = new Bundle(2);
                bundle.putSerializable("type",mType);
                bundle.putSerializable("distance",mDistance);
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
                .load(R.drawable.list_header_image1)
                .into(header);

        lv = view.findViewById(R.id.listView);
        lv.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getContext());
        lv.setLayoutManager(mLayoutManager);
        rAdapter = new RestaurantListAdapter(mListItem1);
        rAdapter.setActivity(getActivity());
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
                View view = nestedScrollView.getChildAt(nestedScrollView.getChildCount() - 1);

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

    private void addItems(List<Restaurant> items){
        for(Restaurant item : items){
            if(!mListItem1.contains(item)) {
                mListItem1.add(item);
            }
        }
        rAdapter.notifyDataSetChanged();
        getFlag = false;
    }

    private void getRestaurantListOrderByDistance() {
        if (getFlag) return;
        getFlag = true;
        RestaurantService.getRestaurantsOrderByDistance(MyLocation.getLocation().getLatitude(),MyLocation.getLocation().getLongitude(),rAdapter.getItemCount(),mDistanceValue[mDistance], callBack);
    }

    private void getRestaurantListOrderByRate() {
        if (getFlag) return;
        getFlag = true;
        RestaurantService.getRestaurantsOrderByRate(MyLocation.getLocation().getLatitude(),MyLocation.getLocation().getLongitude(),rAdapter.getItemCount(),mDistanceValue[mDistance], callBack);
    }

    private void getRestaurantListOrderByReview() {
        if (getFlag) return;
        getFlag = true;
        RestaurantService.getRestaurantsOrderByReview(MyLocation.getLocation().getLatitude(),MyLocation.getLocation().getLongitude(),rAdapter.getItemCount(),mDistanceValue[mDistance], callBack);
    }

    private void getRestaurantsOrderByRecommend() {
        if (getFlag) return;
        getFlag = true;
        RestaurantService.getRestaurantsOrderByRecommended(LoginToken.getId(),MyLocation.getLocation().getLatitude(),MyLocation.getLocation().getLongitude(),rAdapter.getItemCount(),mDistanceValue[mDistance], callBack);
    }

    private void itemClick(int position) {
        if(mListItem1.size()>position) {
            Bundle bundle = new Bundle(2);
            Restaurant item = mListItem1.get(position);
            bundle.putParcelable("reviewItem", item);
            FragmentLoader.startFragment(R.id.frament_place, RestaurantDetailFragment.class, bundle, true);
        }
    }

    private void setSearchTypeSetting(int type, int distance) {
        mDistance = distance;
        mType = type;
        mListItem1.clear();
        rAdapter.notifyDataSetChanged();
        getData();
    }

    private void getData(){
        switch (mType){
            case 0:
                getRestaurantsOrderByRecommend();
                break;
            case 1:
                getRestaurantListOrderByReview();
                break;
            case 2:
                getRestaurantListOrderByDistance();
                break;
            case 3:
                getRestaurantListOrderByRate();
                break;
        }
    }


}

