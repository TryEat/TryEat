package com.tryeat.tryeat;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.tryeat.rest.model.Review;
import com.tryeat.rest.service.ReviewService;
import com.tryeat.team.tryeat_service.R;

import java.util.ArrayList;
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
    ReviewListAdapter2 rAdapter2;
    LinearLayout button;

    ArrayList<Review> mListItem1;

    int restaurantId = -1;

    NestedScrollView nestedScrollView;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.review_list_fragment, container, false);

        mListItem1 = new ArrayList<>();

        nestedScrollView = view.findViewById(R.id.nested_view);

        lv = view.findViewById(R.id.listView);
        lv.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getContext());
        lv.setLayoutManager(mLayoutManager);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments().containsKey("user")) {
            //getArguments().remove("restaurant");
            rAdapter2 = new ReviewListAdapter2(mListItem1);
            rAdapter2.setOnItemClickListener(new ReviewListAdapter2.ClickListener() {
                @Override
                public void onItemClick(int position, View v) {
                    itemClick(position);
                }
            });
            lv.setAdapter(rAdapter2);

            getReviewList();
            nestedScrollView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
                @Override
                public void onScrollChanged() {
                    View view = (View) nestedScrollView.getChildAt(nestedScrollView.getChildCount() - 1);

                    int diff = (view.getBottom() - (nestedScrollView.getHeight() + nestedScrollView
                            .getScrollY()));

                    if (diff == 0) {
                        getReviewList();
                    }
                }
            });
        }

        if (getArguments().containsKey("restaurant")) {
            restaurantId = getArguments().getInt("restaurant");
            //getArguments().remove("user");
            rAdapter = new ReviewListAdapter(mListItem1);
            rAdapter.setOnItemClickListener(new ReviewListAdapter.ClickListener() {
                @Override
                public void onItemClick(int position, View v) {
                    itemClick(position);
                }
            });
            lv.setAdapter(rAdapter);

            getReviewList(restaurantId);
            nestedScrollView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
                @Override
                public void onScrollChanged() {
                    View view = (View) nestedScrollView.getChildAt(nestedScrollView.getChildCount() - 1);

                    int diff = (view.getBottom() - (nestedScrollView.getHeight() + nestedScrollView
                            .getScrollY()));

                    if (diff == 0) {
                        getReviewList(restaurantId);
                    }
                }
            });
        }

        Log.d("aa", "onViewCreated");
    }


    public void itemClick(int position) {
        Bundle bundle = new Bundle(2);
        bundle.putSerializable("reviewItem", mListItem1.get(position));
        FragmentLoader.startFragment(R.id.frament_place, ReviewDetailFragment.class, bundle, true);
    }

    public void getReviewList() {
        Log.d("aa", "getReviewList");
        ReviewService.getUserReviews(LoginToken.getId(), rAdapter2.getItemCount(), new Callback<ArrayList<Review>>() {
            @Override
            public void onResponse(Call<ArrayList<Review>> call, Response<ArrayList<Review>> response) {
                if (response.isSuccessful()) {
                    List<Review> reviews = response.body();
                    int size = reviews.size();
                    for (int i = 0; i < size; i++) {
                        Review item = reviews.get(i);
                        mListItem1.add(item);
                    }
                    rAdapter2.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Review>> call, Throwable t) {
                Log.d("sadf", t.toString());
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

    public Boolean refresh() {
        mListItem1.clear();
        if (restaurantId != -1) getReviewList(restaurantId);
        else getReviewList();
        return true;
    }
}

