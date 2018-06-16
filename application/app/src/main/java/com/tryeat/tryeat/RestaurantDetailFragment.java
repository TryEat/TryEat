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
    TextView bookmark;

    ImageView bookmarkimg;

    int restaurantId;

    Review myReviewItem;
    ArrayList<Review> mListItem1;

    Boolean init = false;

    TextView more;

    ImageView image;

    LinearLayout followBnt;
    LinearLayout addReviewBnt;

    TextView reviewNum;

    TextView reviewText;
    int bookmarkFlag = -1;

    LinearLayout myReviewPlace;

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

        followBnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bookmarkFlag == -1) return;
                if (bookmarkFlag == 1) {
                    BookMarkService.removeBookMark(LoginToken.getId(), restaurantId, new Callback<Status>() {
                        @Override
                        public void onResponse(Call<Status> call, Response<Status> response) {
                            if (response.code() == 201) {
                                bookmarkFlag = 0;
                                setBookmarkImg(bookmarkFlag);
                            }

                        }

                        @Override
                        public void onFailure(Call<Status> call, Throwable t) {

                        }
                    });
                } else {
                    BookMarkService.addBookMark(LoginToken.getId(), restaurantId, new Callback<Status>() {
                        @Override
                        public void onResponse(Call<Status> call, Response<Status> response) {
                            if (response.code() == 201) {
                                bookmarkFlag = 1;
                                setBookmarkImg(bookmarkFlag);
                            }
                        }

                        @Override
                        public void onFailure(Call<Status> call, Throwable t) {

                        }
                    });
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
            restaurant = (Restaurant) getArguments().getSerializable("reviewItem");
            //getArguments().remove("reviewItem");
            name.setText(restaurant.getName());
            rate.setText(String.valueOf(Utils.safeDivide(restaurant.getTotalRate(), restaurant.getReviewCount())));
            count.setText(restaurant.getReviewCount() + "");
            Utils.safeSetObject(bookmark, restaurant.getTotalBookMark());
            Utils.safeSetObject(address, restaurant.getAddress());
            Utils.safeSetObject(tel, restaurant.getPhone());
            Utils.safeSetObject(reviewNum, restaurant.getReviewCount());

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

    public void setBookmarkImg(int bookmarkFlag) {
        if (bookmarkFlag == 0) {
            Glide.with(getActivity())
                    .load(R.drawable.bookmark_on)
                    .into(bookmarkimg);
        } else if (bookmarkFlag == 1) {
            Glide.with(getActivity())
                    .load(R.drawable.bookmark_off)
                    .into(bookmarkimg);
        }

    }

    public void getMyReview(int restaurantId) {
        ReviewService.getRestaurantUserReviews(LoginToken.getId(), restaurantId, new Callback<Review>() {
            @Override
            public void onResponse(Call<Review> call, Response<Review> response) {
                if (response.isSuccessful()) {
                    if(response.code()==209){
                        myReviewPlace.setVisibility(View.GONE);
                        return;
                    }
                    myReviewItem = response.body();
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
            }

            @Override
            public void onFailure(Call<Review> call, Throwable t) {
                Log.d("sdaf",t.toString());
            }
        });
    }

    public void getBookMarkState(int restaurantId) {
        BookMarkService.isExistBookMarks(LoginToken.getId(), restaurantId, new Callback<Status>() {
            @Override
            public void onResponse(Call<Status> call, Response<Status> response) {
                if (response.isSuccessful()) {
                    bookmarkFlag = 1;
                } else {
                    bookmarkFlag = 0;
                }
                setBookmarkImg(bookmarkFlag);
            }

            @Override
            public void onFailure(Call<Status> call, Throwable t) {

            }
        });
    }

    public void getData(int restaurantId) {
        RestaurantService.getRestaurantsOrderByDistance(restaurantId, new Callback<Restaurant>() {
            @Override
            public void onResponse(Call<Restaurant> call, Response<Restaurant> response) {
                restaurant = response.body();
                name.setText(restaurant.getName());
                rate.setText(String.valueOf(Utils.safeDivide(restaurant.getTotalRate(), restaurant.getReviewCount())));
                count.setText(restaurant.getReviewCount() + "");
                Utils.safeSetObject(bookmark, restaurant.getTotalBookMark());
                Utils.safeSetObject(address, restaurant.getAddress());
                Utils.safeSetObject(tel, restaurant.getPhone());
                Utils.safeSetObject(reviewNum, restaurant.getReviewCount());
            }

            @Override
            public void onFailure(Call<Restaurant> call, Throwable t) {

            }
        });
    }

    public void itemClick(int position) {
        Bundle bundle = new Bundle(2);
        bundle.putSerializable("reviewItem", mListItem1.get(position));
        FragmentLoader.startFragment(R.id.frament_place, ReviewDetailFragment.class, bundle, true);
    }

    public void getReviewList(int restaurantId, int position) {
        ReviewService.getRestaurantReviews(restaurantId, position, new Callback<ArrayList<Review>>() {
            @Override
            public void onResponse(Call<ArrayList<Review>> call, Response<ArrayList<Review>> response) {
                if (response.isSuccessful()) {
                    List<Review> reviews = response.body();
                    int size = reviews.size();
                    if (size == 0 || size + mListItem1.size() >= restaurant.getReviewCount())
                        more.setVisibility(View.GONE);
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
