package com.tryeat.tryeat;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.tryeat.rest.model.Status;
import com.tryeat.rest.model.User;
import com.tryeat.rest.service.SignService;
import com.tryeat.rest.service.UserService;
import com.tryeat.team.tryeat_service.R;

import retrofit2.Response;

public class UserFragment extends Fragment {

    private View view;

    private TextView name;

    private TextView bookMarkNum;
    private TextView reviewNum;

    private LinearLayout bookmark;
    private LinearLayout review;

    private FrameLayout fragmentView;

    private NestedScrollView nsView;

    private SwipeRefreshLayout refreshLayout;

    private Fragment fragment;

    private PopupMenu popupMenu;

    private int userId;

    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.user_fragment, container, false);

        userId = getArguments().getInt("user");

        NavigationManager.setVisibility(View.VISIBLE);

        name = view.findViewById(R.id.name);

        ImageView option = view.findViewById(R.id.option);
        option.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupMenu = new PopupMenu(getContext(), v);
                popupMenu.inflate(R.menu.user_menu);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.m1:
                                signOut();
                                break;
                            case R.id.m2:
                                popupMenu.dismiss();
                                break;
                            default:
                                break;
                        }
                        return false;
                    }
                });
                popupMenu.show();
            }
        });

        refreshLayout = view.findViewById(R.id.refreshlayout);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (fragment instanceof BookMarkListFragment)
                    ((BookMarkListFragment) fragment).refresh();
                else if (fragment instanceof ReviewLIstFragment)
                    ((ReviewLIstFragment) fragment).refresh();
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
                bundle.putInt("user", userId);
                fragment = FragmentLoader.startFragment(R.id.fragment_view, ReviewLIstFragment.class, bundle, false);
            }
        });

        bookmark = view.findViewById(R.id.bookmark);
        bookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle(2);
                bundle.putInt("user", userId);
                fragment = FragmentLoader.startFragment(R.id.fragment_view, BookMarkListFragment.class, bundle, false);
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle bundle = new Bundle(2);
        bundle.putSerializable("user", userId);
        fragment = FragmentLoader.startFragment(R.id.fragment_view, ReviewLIstFragment.class, bundle, false);

        UserService.getUser(userId, new SimpleCallBack<>(UserService.class.getSimpleName(), new SimpleCallBack.Success<User>() {
            @Override
            public void toDo(Response<User> response) {
                User user = response.body();
                Utils.safeSetObject(name,user.getUser_login_id());
                Utils.safeSetObject(reviewNum, user.getReview_count());
                Utils.safeSetObject(bookMarkNum, user.getBookmark_count());
            }

            @Override
            public void exception() {

            }
        }));
    }

    public void signOut() {
        SignService.signOut(new SimpleCallBack<>(SignService.class.getSimpleName(),
                new SimpleCallBack.Success<Status>() {
                    @Override
                    public void toDo(Response<Status> response) {
                        LoginToken.removeToken();
                        startSignInActivity();
                    }

                    @Override
                    public void exception() {

                    }
                }
        ));
    }

    public void startSignInActivity(){
        Intent intent = new Intent(getActivity(), SignInActivity.class);
        intent.putExtra("logout", true);
        startActivity(intent);
        getActivity().finish();
        getActivity().overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }
}
