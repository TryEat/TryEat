package com.tryeat.tryeat;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import com.tryeat.rest.model.Review;
import com.tryeat.rest.service.ReviewService;
import com.tryeat.team.tryeat_service.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;


/**
 * Created by socce on 2018-05-08.
 */

public class ReviewLIstFragment extends Fragment {
    private View view;
    private RecyclerView lv;
    private RecyclerView.LayoutManager mLayoutManager;
    private SimpleAdapter<Review> rAdapter;

    private ArrayList<Review> mListItem1;

    private NestedScrollView nestedScrollView;

    SimpleCallBack<ArrayList<Review>> callBack;

    int userId;

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

        callBack = new SimpleCallBack<>(ReviewService.class.getSimpleName(), new SimpleCallBack.Success<ArrayList<Review>>() {
            @Override
            public void toDo(Response<ArrayList<Review>> response) {
                List<Review> reviews = response.body();
                addItems(reviews);
            }

            @Override
            public void exception() {

            }
        });

        if (getArguments().containsKey("user")) {
            rAdapter = new ReviewListAdapter2(mListItem1);
            userId = getArguments().getInt("user");
            getReviewList();
        }

        rAdapter.setActivity(getActivity());
        rAdapter.setOnItemClickListener(new ReviewListAdapter.ClickListener() {
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
                    getReviewList();
                }
            }
        });
    }

    private void itemClick(int position) {
        Bundle bundle = new Bundle(2);
        bundle.putSerializable("reviewItem", mListItem1.get(position));
        FragmentLoader.startFragment(R.id.frament_place, ReviewDetailFragment.class, bundle, true);
    }

    public void getReviewList() {
        ReviewService.getUserReviews(userId, rAdapter.getItemCount(), callBack);
    }

    private void addItems(List<Review> items) {
        for(Review item : items){
            if(!mListItem1.contains(item)) {
                mListItem1.add(item);
            }
        }
        rAdapter.notifyDataSetChanged();
    }

    public Boolean refresh() {
        mListItem1.clear();
        getReviewList();
        return true;
    }
}

