package com.tryeat.tryeat;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.RatingBar;
import android.widget.TextView;

import com.tryeat.rest.model.Review;
import com.tryeat.rest.model.Status;
import com.tryeat.rest.service.ReviewService;
import com.tryeat.team.tryeat_service.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by socce on 2018-05-08.
 */


public class ReviewDetailFragment extends Fragment {
    View view;

    int reviewId;
    Review reviewItem;
    Button delete, modify;


    TextView writer;
    TextView date;
    RatingBar rate;
    ImageView image;
    TextView review;
    TextView name;
    TextView address;
    ImageView userIcon;

    PopupMenu popupMenu;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.review_detail_fragment, container, false);
        reviewItem = (Review) getArguments().getSerializable("reviewItem");

        NavigationManager.setVisibility(View.GONE);

        userIcon = view.findViewById(R.id.userIamge);
        userIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle(2);
                bundle.putSerializable("id", reviewItem.getUserId());
                bundle.putSerializable("name", reviewItem.getWriter());
                FragmentLoader.startFragment(R.id.frament_place, UserFragment.class, bundle, false);
            }
        });

        writer = view.findViewById(R.id.writer);
        Utils.safeSetObject(writer, reviewItem.getWriter());

        name = view.findViewById(R.id.name);
        Utils.safeSetObject(name, reviewItem.getRestaurantName());

        rate = view.findViewById(R.id.rate);
        Utils.safeSetObject(rate, reviewItem.getRate());

        review = view.findViewById(R.id.review);
        Utils.safeSetObject(review, reviewItem.getText());

        date = view.findViewById(R.id.date);
        Utils.safeSetObject(date, reviewItem.getDate().toString());

        address = view.findViewById(R.id.address);
        Utils.safeSetObject(address, reviewItem.getAddress());

        image = view.findViewById(R.id.image);
        BitmapLoader bitmapLoader = new BitmapLoader(image);
        bitmapLoader.execute(reviewItem.getImage());

        ImageView back = view.findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentLoader.back();
            }
        });

        ImageView menu = view.findViewById(R.id.menu);
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupMenu = new PopupMenu(getContext(), v);
                popupMenu.inflate(R.menu.review_menu);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.m1:
                                Bundle bundle = new Bundle(2);
                                bundle.putSerializable("revise", reviewItem);
                                FragmentLoader.startFragment(R.id.frament_place, ReviewAddFragment.class, bundle, true);
                                break;
                            case R.id.m2:
                                AlertDialogBuilder.createChoiceAlert(getActivity(), "리뷰를 지우시겠습니까?", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        ReviewService.deleteReview(reviewItem.getReviewId(), new Callback<Status>() {
                                            @Override
                                            public void onResponse(Call<Status> call, Response<Status> response) {
                                                if (response.isSuccessful()) {
                                                    FragmentLoader.back();
                                                }
                                            }

                                            @Override
                                            public void onFailure(Call<Status> call, Throwable t) {

                                            }
                                        });
                                    }
                                });
                                break;
                            case R.id.m3:
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

        LinearLayout open = view.findViewById(R.id.view_restaurant_button);
        open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle(2);
                bundle.putInt("id", reviewItem.getRestaurantId());
                FragmentLoader.startFragment(R.id.frament_place, RestaurantDetailFragment.class, bundle, false);
            }
        });
        return view;
    }
}
