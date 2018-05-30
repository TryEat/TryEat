package com.tryeat.tryeat;

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
import android.widget.TextView;

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


public class RestaurantDetailFragment extends Fragment {
    View view;
    ListView lv;
    ReviewListAdapter rAdapter;
    public RestaurantDetailFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.restaurant_detail_fragment,container,false);
        RestaurantListItem item = (RestaurantListItem)getArguments().getSerializable("item");
        TextView textView = view.findViewById(R.id.name);
        textView.setText(item.getmName());

        lv = view.findViewById(R.id.review_list_in_detail);
        rAdapter = new ReviewListAdapter(view.getContext(),R.layout.review_list_item);
        lv.setAdapter(rAdapter);
        lv.setOnItemClickListener(itemClick());
        getReviewList(item.getRestaurantId());
        return view;
    }

    public AdapterView.OnItemClickListener itemClick(){
        return new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                FragmentManager fm = getFragmentManager();
                FragmentTransaction fragmentTransaction = fm.beginTransaction();
                ReviewDetailFragment fragment = new ReviewDetailFragment();
                Bundle bundle = new Bundle(2);
                ReviewListItem item = (ReviewListItem)adapterView.getItemAtPosition(i);
                bundle.putSerializable("item",item);
                fragment.setArguments(bundle);
                fragmentTransaction.replace(R.id.frament_place,fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        };
    }

    public void getReviewList(int restaurantId){
        ReviewService.getRestaurantReviews(restaurantId,new Callback<ArrayList<Review>>() {
            @Override
            public void onResponse(Call<ArrayList<Review>> call, Response<ArrayList<Review>> response) {
                if(response.isSuccessful()){
                    List<Review> reviews = response.body();
                    int size = reviews.size();
                    for(int i =0 ;i<size;i++){
                        Review item = reviews.get(i);
                        rAdapter.addItem(new ReviewListItem(null,item.user_id+"","empty",item.content,item.rate+""));
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
