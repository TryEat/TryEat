package com.tryeat.tryeat;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.tryeat.rest.model.Restaurant;
import com.tryeat.rest.model.Review;
import com.tryeat.rest.model.Status;
import com.tryeat.rest.model.StatusCode;
import com.tryeat.rest.service.RestaurantService;
import com.tryeat.rest.service.ReviewService;
import com.tryeat.team.tryeat_service.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by socce on 2018-05-08.
 */


public class ReviewDetailFragment extends Fragment{
    View view;

    int reviewId;
    Review item;
    Button delete,modify;
    ImageView image;
    public ReviewDetailFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.review_detail_fragment,container,false);
        item = (Review)getArguments().getSerializable("item");

        TextView name = view.findViewById(R.id.name);
        name.setText(item.getRestaurantName());
        TextView rate = view.findViewById(R.id.rate);
        rate.setText(item.getRate()+"");
        TextView desc = view.findViewById(R.id.desc);
        desc.setText(item.getText());
        TextView date = view.findViewById(R.id.date);
        date.setText(item.getDate().toString());

        delete = view.findViewById(R.id.delete);
        modify = view.findViewById(R.id.modify);
        image = view.findViewById(R.id.image);

        BitmapLoader bitmapLoader = new BitmapLoader(image);
        bitmapLoader.execute(item.getImage());

        Button open = view.findViewById(R.id.view_restaurant_button);
        open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle(2);
                bundle.putInt("id",item.getRestaurantId());
                FragmentLoader.startFragment(R.id.frament_place,RestaurantDetailFragment.class,bundle);
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ReviewService.deleteReview(reviewId, new Callback<Status>() {
                    @Override
                    public void onResponse(Call<Status> call, Response<Status> response) {

                    }

                    @Override
                    public void onFailure(Call<Status> call, Throwable t) {

                    }
                });
            }
        });

        modify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        return view;
    }
}
