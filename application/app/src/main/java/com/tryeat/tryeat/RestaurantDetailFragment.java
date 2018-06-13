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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tryeat.rest.model.Restaurant;
import com.tryeat.rest.model.Review;
import com.tryeat.rest.model.Status;
import com.tryeat.rest.service.FollowService;
import com.tryeat.rest.service.RestaurantService;
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


public class RestaurantDetailFragment extends Fragment {
    View view;
    RecyclerView lv;
    LinearLayoutManager mLayoutManager;
    Restaurant restaurant;
    ReviewListAdapter rAdapter;
    TextView name;
    TextView rate;
    TextView count;
    TextView address;
    TextView tel;
    TextView date;

    int restaurantId;

    ArrayList<Review> mListItem1;

    Boolean init = false;

    TextView more ;

    LinearLayout followBnt;
    LinearLayout addReviewBnt;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(view==null) {
            view = inflater.inflate(R.layout.restaurant_detail_fragment, container, false);

            name = view.findViewById(R.id.name);
            rate = view.findViewById(R.id.rate);
            count = view.findViewById(R.id.count);
            tel = view.findViewById(R.id.tel_number);
            address = view.findViewById(R.id.address);
            date = view.findViewById(R.id.date);

            followBnt = view.findViewById(R.id.followbnt);
            followBnt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FollowService.addFollower(LoginToken.getId(), restaurantId, new Callback<Status>() {
                        @Override
                        public void onResponse(Call<Status> call, Response<Status> response) {

                        }

                        @Override
                        public void onFailure(Call<Status> call, Throwable t) {

                        }
                    });
                }
            });

            addReviewBnt = view.findViewById(R.id.goriew);
            addReviewBnt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle(2);
                    bundle.putSerializable("id", restaurantId);
                    bundle.putSerializable("name", name.getText().toString());
                    FragmentLoader.startFragment(R.id.frament_place, ReviewAddFragment.class, bundle, true);
                }
            });

            more = view.findViewById(R.id.more);
            more.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle bundle = new Bundle(2);
                    bundle.putSerializable("restaurant", restaurantId);
                    FragmentLoader.startFragment(R.id.frament_place, ReviewLIstFragment.class, bundle, true);
                }
            });
        }
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments().containsKey("id")) {
            restaurantId = getArguments().getInt("id");
            //getArguments().remove("id");
        }

        if (getArguments().containsKey("item")) {
            restaurant = (Restaurant) getArguments().getSerializable("item");
            //getArguments().remove("item");
            name.setText(restaurant.getName());
            rate.setText(String.valueOf(Utils.safeDivide(restaurant.getTotalRate(), restaurant.getReviewCount())));
            count.setText(restaurant.getReviewCount() + "");

            Utils.safeSetObject(address,restaurant.getAddress());
            Utils.safeSetObject(tel,restaurant.getPhone());

            restaurantId = restaurant.getId();
            init = true;
        }

        mListItem1 = new ArrayList<>();

        lv = view.findViewById(R.id.review_list_in_detail);
        lv.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getContext());
        lv.setLayoutManager(mLayoutManager);
        rAdapter = new ReviewListAdapter(mListItem1);
        rAdapter.setOnItemClickListener(new ReviewListAdapter.ClickListener() {
            @Override
            public void onItemClick(int position, View v) {

            }
        });
        lv.setAdapter(rAdapter);

        if(!init)getData(restaurantId);
        getReviewList(restaurantId,0);
    }

    public void getData(int restaurantId){
        RestaurantService.getRestaurant(restaurantId, new Callback<Restaurant>() {
            @Override
            public void onResponse(Call<Restaurant> call, Response<Restaurant> response) {
                restaurant = response.body();
                name.setText(restaurant.getName());
                rate.setText(String.valueOf(Utils.safeDivide(restaurant.getTotalRate(), restaurant.getReviewCount())));
                count.setText(restaurant.getReviewCount()+"");
            }

            @Override
            public void onFailure(Call<Restaurant> call, Throwable t) {

            }
        });
    }

    public void getReviewList(int restaurantId, int position){
        ReviewService.getRestaurantReviews(restaurantId,position,new Callback<ArrayList<Review>>() {
            @Override
            public void onResponse(Call<ArrayList<Review>> call, Response<ArrayList<Review>> response) {
                if(response.isSuccessful()){
                    List<Review> reviews = response.body();
                    int size = reviews.size();
                    if(size==0)more.setText("리뷰가 없습니다.");
                    for(int i =0 ;i<size;i++){
                        Review item = reviews.get(i);
                        mListItem1.add(item);
                    }
                    rAdapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onFailure(Call<ArrayList<Review>> call, Throwable t) {
                Log.d("debug","getRestaurantReviews onFailure"+t);
            }
        });
    }
}
