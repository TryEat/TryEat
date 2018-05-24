package com.tryeat.tryeat;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;

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
    ListView lv;
    ReviewListAdapter rAdapter;
    ImageButton button;
    public ReviewLIstFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.review_list_fragment,container,false);
        lv = view.findViewById(R.id.listView);
        rAdapter = new ReviewListAdapter(view.getContext(),R.layout.review_list_item);
        lv.setAdapter(rAdapter);
        lv.setOnItemClickListener(itemClick());
        //review_add 버튼 이벤트 - ReviewAddFragment
        button = (ImageButton) view.findViewById(R.id.review_add_btn);
        button.setOnClickListener(showAddReviewFragment());
        getReviewList();
        return view;
    }

    public View.OnClickListener showAddReviewFragment(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getFragmentManager();
                FragmentTransaction fragmentTransaction = fm.beginTransaction();
                ReviewAddFragment fragment = new ReviewAddFragment();
                fragmentTransaction.replace(R.id.frament_place,fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        };
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

    public void getReviewList(){
        ReviewService.getUserReviews(1, new Callback<ArrayList<Review>>() {
            @Override
            public void onResponse(Call<ArrayList<Review>> call, Response<ArrayList<Review>> response) {
                if(response.isSuccessful()){
                    List<Review> reviews = response.body();
                    int size = reviews.size();
                    for(int i =0 ;i<size;i++){
                        Review item = reviews.get(i);
                        rAdapter.addItem(new ReviewListItem(null,item.user_id+"","header",item.content,item.rate+""));
                    }
                    rAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Review>> call, Throwable t) {

            }
        });
    }
}

