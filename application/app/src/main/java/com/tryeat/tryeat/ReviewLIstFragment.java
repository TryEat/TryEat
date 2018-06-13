package com.tryeat.tryeat;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
    LinearLayout button;

    ArrayList<Review> mListItem1;

    ImageView header;

    int restaurantId;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.review_list_fragment, container, false);

            mListItem1 = new ArrayList<>();



            header = view.findViewById(R.id.header);

            button = view.findViewById(R.id.review_add_btn);
            button.setOnClickListener(showAddReviewFragment());

            Glide.with(view)
                    .load(R.drawable.list_header_image1)
                    .into(header);

            lv = view.findViewById(R.id.listView);
            lv.setHasFixedSize(true);
            mLayoutManager = new LinearLayoutManager(getContext());
            lv.setLayoutManager(mLayoutManager);
            rAdapter = new ReviewListAdapter(mListItem1);
            rAdapter.setOnItemClickListener(new ReviewListAdapter.ClickListener() {
                @Override
                public void onItemClick(int position, View v) {
                    itemClick(position);
                }
            });
            lv.setAdapter(rAdapter);
        }
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if(getArguments().containsKey("user")){
            //getArguments().remove("restaurant");
            getReviewList();
            lv.setOnScrollChangeListener(new View.OnScrollChangeListener() {
                @Override
                public void onScrollChange(View view, int i, int i1, int i2, int i3) {
                    if(!lv.canScrollVertically(1)) {
                        getReviewList();
                    }
                }
            });
        }

        if (getArguments().containsKey("restaurant")) {
            restaurantId = getArguments().getInt("restaurant");
            //getArguments().remove("user");
            getReviewList(restaurantId);
            lv.setOnScrollChangeListener(new View.OnScrollChangeListener() {
                @Override
                public void onScrollChange(View view, int i, int i1, int i2, int i3) {
                    if(!lv.canScrollVertically(1)) {
                        getReviewList(restaurantId);
                    }
                }
            });
        }

        Log.d("aa", "onViewCreated");
    }



    public View.OnClickListener showAddReviewFragment() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                AutoSearchFragment dialogFragment = new AutoSearchFragment();
                dialogFragment.show(fm, "frament_place");
            }
        };
    }

    public void itemClick(int position) {
        Bundle bundle = new Bundle(2);
        bundle.putSerializable("item", mListItem1.get(position));
        FragmentLoader.startFragment(R.id.frament_place,ReviewDetailFragment.class,bundle,true);
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
                    }
                    rAdapter.notifyDataSetChanged();
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
}

