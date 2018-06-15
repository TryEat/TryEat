package com.tryeat.tryeat;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tryeat.rest.model.User;
import com.tryeat.rest.service.UserService;
import com.tryeat.team.tryeat_service.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserFragment extends Fragment {

    View view;

    TextView name;

    TextView bookMarkNum;
    TextView reviewNum;

    LinearLayout bookmark;
    LinearLayout review;

    FrameLayout fragmentView;

    NestedScrollView nsView;

    SwipeRefreshLayout refreshLayout;

    Fragment fragment;

    int userId;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.user_fragment, container, false);

        userId = getArguments().getInt("id");

        NavigationManager.setVisibility(View.VISIBLE);

        name = view.findViewById(R.id.name);

        refreshLayout = view.findViewById(R.id.refreshlayout);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(fragment instanceof FollowListFragment)((FollowListFragment) fragment).refresh();
                else if(fragment instanceof ReviewLIstFragment)((ReviewLIstFragment) fragment).refresh();
                refreshLayout.setRefreshing(false);
            }
        });

        bookMarkNum = view.findViewById(R.id.bookmark_num);
        reviewNum = view.findViewById(R.id.review_num);

        fragmentView = view.findViewById(R.id.fragment_view);

        nsView = view.findViewById(R.id.nested_view);

        review = view.findViewById(R.id.review);
        review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle(2);
                bundle.putSerializable("user",userId);
                fragment  =  FragmentLoader.startFragment(R.id.fragment_view, ReviewLIstFragment.class,bundle,false);
            }
        });

        bookmark = view.findViewById(R.id.bookmark);
        bookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle(2);
                bundle.putSerializable("user",userId);
                fragment=  FragmentLoader.startFragment(R.id.fragment_view, FollowListFragment.class,bundle,false);
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle bundle = new Bundle(2);
        bundle.putSerializable("user",userId);
        fragment  =  FragmentLoader.startFragment(R.id.fragment_view, ReviewLIstFragment.class,bundle,false);

        UserService.getUser(userId, new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                User user = response.body();
                name.setText(user.getUser_login_id());
                Utils.safeSetObject(reviewNum,user.getReview_count());
                Utils.safeSetObject(bookMarkNum,user.getBookmark_count());
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.d("sdf",t.toString());
            }
        });
    }
}
