package com.tryeat.tryeat;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.places.AddPlaceRequest;
import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlacePhotoMetadata;
import com.google.android.gms.location.places.PlacePhotoMetadataBuffer;
import com.google.android.gms.location.places.PlacePhotoMetadataResponse;
import com.google.android.gms.location.places.PlacePhotoResponse;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.location.places.ui.SupportPlaceAutocompleteFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.tryeat.rest.model.Restaurant;
import com.tryeat.rest.model.Review;
import com.tryeat.rest.model.Status;
import com.tryeat.rest.model.StatusCode;
import com.tryeat.rest.service.RestaurantService;
import com.tryeat.rest.service.ReviewService;
import com.tryeat.rest.service.SignService;
import com.tryeat.team.tryeat_service.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by socce on 2018-05-20.
 */

public class ReviewAddFragment extends Fragment {
    View view;

    TextView name;
    EditText desc;
    RatingBar rate;

    int reviewId;
    int restaurantId;

    ImageAddFragment imageAddFragment;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        if(view == null) {
            view = inflater.inflate(R.layout.review_add_fragment, container, false);
            Button addButton = view.findViewById(R.id.addButton);
            addButton.setOnClickListener(addReview());

            NavigationManager.setVisibility(View.GONE);

            name = view.findViewById(R.id.name);
            desc = view.findViewById(R.id.review);
            rate = view.findViewById(R.id.rate);

            imageAddFragment = (ImageAddFragment) getChildFragmentManager().findFragmentById(R.id.image_fragment);

            if(getArguments().containsKey("revise")){
                Review review = (Review) getArguments().get("revise");
                reviewId = review.getReviewId();
                restaurantId = review.getRestaurantId();
                name.setText(review.getRestaurantName());
                rate.setRating(review.getRate());
                desc.setText(review.getText());
                imageAddFragment.setImage(review.getImage());
            }else {
                reviewId=-1;
                restaurantId = getArguments().getInt("id");
                name.setText(getArguments().getString("name"));
            }

        }
        return view;
    }

    public View.OnClickListener addReview() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(reviewId==-1) {
                    ReviewService.writeReview(LoginToken.getId(), restaurantId, desc.getText().toString(), imageAddFragment.getImageBitmap(), rate.getRating(), new Callback<Status>() {
                        @Override
                        public void onResponse(Call<Status> call, Response<Status> response) {
                            int code = response.code();
                            if (code == StatusCode.WRITE_REVIEW_SUCCESS) {
                            } else if (code == StatusCode.WRITE_REVIEW_FAIL) {
                            }

                        }

                        @Override
                        public void onFailure(Call<Status> call, Throwable t) {
                            Log.d("onFailure", t.toString());
                        }
                    });
                }else{
                    ReviewService.updateReview(reviewId, desc.getText().toString(), imageAddFragment.getImageBitmap(), rate.getRating(), new Callback<Status>() {
                        @Override
                        public void onResponse(Call<Status> call, Response<Status> response) {

                        }

                        @Override
                        public void onFailure(Call<Status> call, Throwable t) {

                        }
                    });
                }
                getFragmentManager().popBackStackImmediate();
            }
        };
    }
}