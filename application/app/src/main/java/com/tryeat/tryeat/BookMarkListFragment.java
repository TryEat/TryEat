package com.tryeat.tryeat;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import com.tryeat.rest.model.Restaurant;
import com.tryeat.rest.service.BookMarkService;
import com.tryeat.team.tryeat_service.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookMarkListFragment extends Fragment{
    private View view;
    private RecyclerView lv;
    private RecyclerView.LayoutManager mLayoutManager;
    private BookMarkListAdapter rAdapter;

    private ArrayList<Restaurant> mListItem1;

    private NestedScrollView nestedScrollView;

    private int userId;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.bookmark_list_fragment, container, false);

            userId = getArguments().getInt("user");

            mListItem1 = new ArrayList<>();

            nestedScrollView = view.findViewById(R.id.nested_view);

            lv = view.findViewById(R.id.listView);
            lv.setHasFixedSize(true);
            mLayoutManager = new LinearLayoutManager(getContext());
            lv.setLayoutManager(mLayoutManager);
            rAdapter = new BookMarkListAdapter(mListItem1);
            rAdapter.setActivity(getActivity());
            rAdapter.setOnItemClickListener(new BookMarkListAdapter.ClickListener() {
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
                        getFollowList();
                    }
                }
            });

        }
        return view;
    }

    private void itemClick(int position) {
        Bundle bundle = new Bundle(2);
        Restaurant item =  mListItem1.get(position);
        bundle.putParcelable("reviewItem",item);
        FragmentLoader.startFragment(R.id.frament_place,RestaurantDetailFragment.class,bundle,true);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getFollowList();
    }

    private void getFollowList(){
        BookMarkService.getBookMarks(userId,rAdapter.getItemCount(),new Callback<ArrayList<Restaurant>>() {
            @Override
            public void onResponse(Call<ArrayList<Restaurant>> call, Response<ArrayList<Restaurant>> response) {
                if(response.isSuccessful()){
                    List<Restaurant> restaurants = response.body();
                    int size = restaurants.size();
                    for(int i =0 ;i<size;i++){
                        Restaurant item = restaurants.get(i);
                        mListItem1.add(item);
                    }
                    rAdapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onFailure(Call<ArrayList<Restaurant>> call, Throwable t) {
                Log.d("debug","getRestaurantList onFailure"+t);
            }
        });
    }

    public Boolean refresh(){
        mListItem1.clear();
        getFollowList();
        return true;
    }
}
