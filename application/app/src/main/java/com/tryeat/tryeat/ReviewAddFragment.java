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

import com.tryeat.rest.model.Status;
import com.tryeat.rest.model.StatusCode;
import com.tryeat.rest.service.ReviewService;
import com.tryeat.team.tryeat_service.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by socce on 2018-05-20.
 */

public class ReviewAddFragment extends Fragment {
    View view;

    EditText header;
    EditText desc;
    RatingBar rate;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.review_add_fragment,container,false);
        Button addButton = view.findViewById(R.id.addButton);
        addButton.setOnClickListener(addReview());
        header = view.findViewById(R.id.header);
        desc = view.findViewById(R.id.desc);
        rate = view.findViewById(R.id.rate);
        return view;
    }

    public View.OnClickListener addReview(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ReviewService.writeReview(1, 1, desc.getText().toString(), rate.getNumStars(), new Callback<Status>() {
                    @Override
                    public void onResponse(Call<Status> call, Response<Status> response) {
                        int code = response.code();
                        if(code == StatusCode.WRITE_REVIEW_SUCCESS){}
                        else if(code == StatusCode.WRITE_REVIEW_FAIL){}
                    }

                    @Override
                    public void onFailure(Call<Status> call, Throwable t) {

                    }
                });
            }
        };
    }
}
