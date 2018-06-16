package com.tryeat.tryeat;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tryeat.rest.model.Restaurant;
import com.tryeat.rest.model.Review;
import com.tryeat.rest.model.Status;
import com.tryeat.rest.service.BookMarkService;
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
    private View view;
    private RecyclerView lv;
    private LinearLayoutManager mLayoutManager;
    private Restaurant restaurant;
    private ReviewListAdapter rAdapter;
    private TextView name;
    private TextView rate;
    private TextView count;
    private TextView address;
    private TextView tel;
    private TextView bookmark;

    private ImageView bookmarkimg;

    private int restaurantId;

    private Review myReviewItem;
    private ArrayList<Review> mListItem1;

    private Boolean init = false;

    private TextView more;

    private ImageView image;

    private LinearLayout followBnt;
    private LinearLayout addReviewBnt;

    private TextView reviewNum;

    private TextView reviewText;
    private int bookmarkFlag = -1;

    private LinearLayout myReviewPlace;

    private SwipeRefreshLayout refreshLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.restaurant_detail_fragment, container, false);

        NavigationManager.setVisibility(View.GONE);

        name = view.findViewById(R.id.name);
        rate = view.findViewById(R.id.rate);
        count = view.findViewById(R.id.count);
        tel = view.findViewById(R.id.tel_number);
        address = view.findViewById(R.id.address);
        bookmark = view.findViewById(R.id.bookmark);
        bookmarkimg = view.findViewById(R.id.bookmark_img);
        reviewNum = view.findViewById(R.id.review_num);

        reviewText = view.findViewById(R.id.reviewtext);

        image = view.findViewById(R.id.image);

        myReviewPlace = view.findViewById(R.id.my_review_place);

        followBnt = view.findViewById(R.id.addbookmark);

        refreshLayout = view.findViewById(R.id.refreshlayout);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
                refreshLayout.setRefreshing(false);
            }
        });

        followBnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bookmarkFlag == -1) return;
                if (bookmarkFlag == 1) {
                    BookMarkService.removeBookMark(LoginToken.getId(), restaurantId, new SimpleCallBack<Status>(BookMarkService.class.getSimpleName(), new SimpleCallBack.Success<Status>() {
                        @Override
                        public void toDo(Response<Status> response) {
                            bookmarkFlag = 0;
                            setBookmarkImg(bookmarkFlag);
                        }

                        @Override
                        public void exception() {

                        }
                    }));
                } else {
                    BookMarkService.addBookMark(LoginToken.getId(), restaurantId, new SimpleCallBack<Status>(BookMarkService.class.getSimpleName(), new SimpleCallBack.Success<Status>() {
                        @Override
                        public void toDo(Response<Status> response) {
                            bookmarkFlag = 1;
                            setBookmarkImg(bookmarkFlag);
                        }

                        @Override
                        public void exception() {

                        }
                    }));
                }
            }
        });

        addReviewBnt = view.findViewById(R.id.goriew);
        addReviewBnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle(2);
                if (myReviewItem != null) {
                    bundle.putSerializable("revise", myReviewItem);
                } else {
                    bundle.putSerializable("id", restaurant.getId());
                    bundle.putSerializable("name", restaurant.getName());
                }
                FragmentLoader.startFragment(R.id.frament_place, ReviewAddFragment.class, bundle, true);
            }
        });

        more = view.findViewById(R.id.more);
        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getReviewList(restaurantId, rAdapter.getItemCount());
            }
        });
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments().containsKey("id")) {
            restaurantId = getArguments().getInt("id");
            //getArguments().remove("id");
        }

        if (getArguments().containsKey("reviewItem")) {
            restaurant = (Restaurant) getArguments().getParcelable("reviewItem");
            //getArguments().remove("reviewItem");
            setData(restaurant);

            if (restaurant.getImage() != null) {
                BitmapLoader bitmapLoader = new BitmapLoader(image);
                bitmapLoader.execute(restaurant.getImage());
            } else {
                Glide.with(getActivity())
                        .load(R.drawable.list_header_image3)
                        .into(image);
            }

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
                itemClick(position);
            }
        });
        lv.setAdapter(rAdapter);

        if (!init) getData(restaurantId);
        getReviewList(restaurantId, 0);

        getMyReview(restaurantId);
        getBookMarkState(restaurantId);
    }

    private void setBookmarkImg(int bookmarkFlag) {
        Glide.with(getActivity())
                .load((bookmarkFlag == 0) ? R.drawable.bookmark_on : R.drawable.bookmark_off)
                .into(bookmarkimg);
    }

    private void getMyReview(int restaurantId) {
        ReviewService.getRestaurantUserReviews(LoginToken.getId(), restaurantId, new SimpleCallBack<Review>(ReviewService.class.getSimpleName(), new SimpleCallBack.Success<Review>() {
            @Override
            public void toDo(Response<Review> response) {
                Review review = response.body();
                if(review!=null)
                    setMyReview(review);
            }

            @Override
            public void exception() {
                myReviewPlace.setVisibility(View.GONE);
            }
        }));
    }

    private void getBookMarkState(int restaurantId) {
        BookMarkService.isExistBookMarks(LoginToken.getId(), restaurantId, new SimpleCallBack<>(BookMarkService.class.getSimpleName(), new SimpleCallBack.Success<Status>() {
            @Override
            public void toDo(Response<Status> response) {
                bookmarkFlag = 1;
                setBookmarkImg(bookmarkFlag);
            }

            @Override
            public void exception() {
                bookmarkFlag = 0;
                setBookmarkImg(bookmarkFlag);
            }
        }));
    }

    private void getData(int restaurantId) {
        RestaurantService.getRestaurant(restaurantId, new SimpleCallBack<>(ReviewService.class.getSimpleName(), new SimpleCallBack.Success<Restaurant>() {
            @Override
            public void toDo(Response<Restaurant> response) {
                restaurant = response.body();
                setData(restaurant);
            }

            @Override
            public void exception() {

            }
        }));
    }

    private void itemClick(int position) {
        Bundle bundle = new Bundle(2);
        bundle.putSerializable("reviewItem", mListItem1.get(position));
        FragmentLoader.startFragment(R.id.frament_place, ReviewDetailFragment.class, bundle, true);
    }

    private void getReviewList(int restaurantId, int position) {
        ReviewService.getRestaurantReviews(restaurantId, position, new SimpleCallBack<ArrayList<Review>>(ReviewService.class.getSimpleName(), new SimpleCallBack.Success<ArrayList<Review>>() {
            @Override
            public void toDo(Response<ArrayList<Review>> response) {
                addListItems(response.body());
            }

            @Override
            public void exception() {

            }
        }));
    }

    private void setMyReview(Review review) {
        myReviewItem = review;

        reviewText.setText("리뷰 수정");
        View myReview = view.findViewById(R.id.my_review);
        ImageView image = myReview.findViewById(R.id.image);
        TextView name = myReview.findViewById(R.id.name);
        RatingBar rate = myReview.findViewById(R.id.rate);
        TextView text = myReview.findViewById(R.id.text);

        BitmapLoader bitmapLoader = new BitmapLoader(image);
        bitmapLoader.execute(myReviewItem.getImage());

        name.setText(myReviewItem.getWriter());
        rate.setRating(myReviewItem.getRate());
        text.setText(myReviewItem.getText());

        myReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle(2);
                bundle.putSerializable("reviewItem", myReviewItem);
                FragmentLoader.startFragment(R.id.frament_place, ReviewDetailFragment.class, bundle, true);
            }
        });
    }

    private void setData(Restaurant restaurant) {
        name.setText(restaurant.getName());
        rate.setText(String.valueOf(Utils.safeDivide(restaurant.getTotalRate(), restaurant.getReviewCount())));
        count.setText(restaurant.getReviewCount() + "");
        Utils.safeSetObject(bookmark, restaurant.getTotalBookMark());
        Utils.safeSetObject(address, restaurant.getAddress());
        Utils.safeSetObject(tel, restaurant.getPhone());
        Utils.safeSetObject(reviewNum, restaurant.getReviewCount());
    }

    private void addListItems(List<Review> items) {
        mListItem1.addAll(items);
        rAdapter.notifyDataSetChanged();
        if (mListItem1.size() >= restaurant.getReviewCount())
            more.setVisibility(View.GONE);
    }

    private void refresh() {
        mListItem1.clear();
        getData(restaurantId);
        getReviewList(restaurantId, 0);
        getMyReview(restaurantId);
        getBookMarkState(restaurantId);
    }
}
