package com.tryeat.tryeat;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.tryeat.rest.model.Review;
import com.tryeat.rest.model.Status;
import com.tryeat.rest.service.ReviewService;
import com.tryeat.team.tryeat_service.R;

import retrofit2.Response;

/**
 * Created by socce on 2018-05-20.
 */

public class ReviewAddFragment extends Fragment implements View.OnClickListener{
    private View view;

    private TextView name;
    private EditText desc;
    private RatingBar rate;

    private int reviewId;
    private int restaurantId;

    private ImageAddFragment imageAddFragment;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.review_add_fragment, container, false);
            Button addButton = view.findViewById(R.id.addButton);
            addButton.setOnClickListener(this);

            NavigationManager.setVisibility(View.GONE);

            name = view.findViewById(R.id.name);
            desc = view.findViewById(R.id.review);
            rate = view.findViewById(R.id.rate);

            imageAddFragment = (ImageAddFragment) getChildFragmentManager().findFragmentById(R.id.image_fragment);

            if (getArguments().containsKey("revise")) {
                Review review = (Review) getArguments().getSerializable("revise");
                reviewId = review.getReviewId();
                restaurantId = review.getRestaurantId();
                name.setText(review.getRestaurantName());
                rate.setRating(review.getRate());
                desc.setText(review.getText());
                imageAddFragment.setImage(review.getImgUri());
            } else {
                reviewId = -1;
                restaurantId = getArguments().getInt("id");
                name.setText(getArguments().getString("name"));
            }

        }
        return view;
    }

    @Override
    public void onClick(View v) {
        ProgressDialogManager.show(getActivity(),"리뷰 등록중입니다...");
        if (reviewId == -1) {
            ReviewService.writeReview(LoginToken.getId(), restaurantId, desc.getText().toString(), imageAddFragment.getImageBitmap(), rate.getRating(), new SimpleCallBack<Status>(
                            ReviewService.class.getSimpleName(), new SimpleCallBack.Success<Status>() {
                        @Override
                        public void toDo(Response<Status> response) {
                            ProgressDialogManager.dismiss();
                            getFragmentManager().popBackStackImmediate();
                        }

                        @Override
                        public void exception() {
                            ProgressDialogManager.dismiss();
                            Toast.makeText(getContext(),"다시 시도해 주십시오...",Toast.LENGTH_LONG);
                        }
                    })
            );
        } else {
            ReviewService.updateReview(reviewId, desc.getText().toString(), imageAddFragment.getImageBitmap(), rate.getRating(), new SimpleCallBack<Status>(
                            ReviewService.class.getSimpleName(), new SimpleCallBack.Success<Status>() {
                        @Override
                        public void toDo(Response<Status> response) {
                            ProgressDialogManager.dismiss();
                            getFragmentManager().popBackStackImmediate();
                        }

                        @Override
                        public void exception() {
                            ProgressDialogManager.dismiss();
                            Toast.makeText(getContext(),"다시 시도해 주십시오...",Toast.LENGTH_LONG);
                        }
                    })
            );
        }
    }
}