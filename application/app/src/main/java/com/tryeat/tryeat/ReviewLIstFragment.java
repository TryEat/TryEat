package com.tryeat.tryeat;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.OnScrollListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;

import com.tryeat.rest.model.Restaurant;
import com.tryeat.rest.model.Review;
import com.tryeat.rest.service.RestaurantService;
import com.tryeat.rest.service.ReviewService;
import com.tryeat.team.tryeat_service.R;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by socce on 2018-05-08.
 */

public class ReviewLIstFragment extends Fragment {
    View view;
    RecyclerView lv;
    RecyclerView.LayoutManager mLayoutManager;
    ReviewListAdapter rAdapter;
    ImageButton button;

    ArrayList<Review> mListItem1;

    int restaurantId;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.review_list_fragment, container, false);

            lv = view.findViewById(R.id.listView);
            lv.setHasFixedSize(true);
            mLayoutManager = new LinearLayoutManager(getContext());

            button = (ImageButton) view.findViewById(R.id.review_add_btn);
            button.setOnClickListener(showAddReviewFragment());
        }
        Log.d("aa", "onCreateView");

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mListItem1 = new ArrayList<>();
        lv.setLayoutManager(mLayoutManager);
        rAdapter = new ReviewListAdapter(mListItem1);
        if(getArguments()==null){getReviewList();}
        else if (getArguments().containsKey("restaurant")||restaurantId!=-1) {
            restaurantId = getArguments().getInt("restaurant");
            getArguments().remove("restaurant");
            getReviewList(restaurantId);
        }else{
            restaurantId=-1;
            getReviewList();
        }
        rAdapter.setOnItemClickListener(new ReviewListAdapter.ClickListener() {
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
                    getReviewList(restaurantId);
                }
            }
        });
        Log.d("aa", "onViewCreated");
    }

    public View.OnClickListener showAddReviewFragment() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentLoader.startFragment(R.id.frament_place,ReviewAddFragment.class);
            }
        };
    }

    public void itemClick(int position) {
        Bundle bundle = new Bundle(2);
        bundle.putSerializable("item", mListItem1.get(position));
        FragmentLoader.startFragment(R.id.frament_place,ReviewDetailFragment.class,bundle);
    }

    public void getReviewList() {
        Log.d("aa", "getReviewList");
        ReviewService.getUserReviews(LoginToken.getId(), rAdapter.getItemCount(), new Callback<ArrayList<Review>>() {
            @Override
            public void onResponse(Call<ArrayList<Review>> call, Response<ArrayList<Review>> response) {
                if (response.isSuccessful()) {
                    List<Review> reviews = response.body();
                    int size = reviews.size();
                    for (int i = 0; i < size; i++) {
                        Review item = reviews.get(i);
                        mListItem1.add(item);
                        rAdapter.notifyItemInserted(rAdapter.getItemCount() - 1);
                    }
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Review>> call, Throwable t) {
                Log.d("sadf", "dfg");
            }
        });
    }

    public void getReviewList(int restaurantId) {
        ReviewService.getRestaurantReviews(restaurantId, rAdapter.getItemCount(), new Callback<ArrayList<Review>>() {
            @Override
            public void onResponse(Call<ArrayList<Review>> call, Response<ArrayList<Review>> response) {
                if (response.isSuccessful()) {
                    List<Review> reviews = response.body();
                    int size = reviews.size();
                    for (int i = 0; i < size; i++) {
                        Review item = reviews.get(i);
                        mListItem1.add(item);
                    }
                    rAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Review>> call, Throwable t) {
                Log.d("debug", "getRestaurantReviews onFailure" + t);
            }
        });
    }
}

