package com.tryeat.tryeat;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.tryeat.rest.model.Review;
import com.tryeat.team.tryeat_service.R;


/**
 * Created by socce on 2018-05-08.
 */


public class ReviewDetailFragment extends Fragment{
    View view;

    int reviewId;
    Review item;
    Button delete,modify;


    TextView writer;
    TextView date;
    RatingBar rate;
    ImageView image;
    TextView review;
    TextView name;
    TextView address;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(view ==null) {
            view = inflater.inflate(R.layout.review_detail_fragment, container, false);
            item = (Review) getArguments().getSerializable("item");

            writer = view.findViewById(R.id.writer);
            Utils.safeSetObject(writer,item.getWriter());

            name = view.findViewById(R.id.name);
            Utils.safeSetObject(name,item.getRestaurantName());

            rate = view.findViewById(R.id.rate);
            Utils.safeSetObject(rate,item.getRate());

            review = view.findViewById(R.id.review);
            Utils.safeSetObject(review,item.getText());

            date = view.findViewById(R.id.date);
            Utils.safeSetObject(date,item.getDate().toString());

            address = view.findViewById(R.id.address);
            Utils.safeSetObject(address,item.getAddress());

            image = view.findViewById(R.id.image);
            BitmapLoader bitmapLoader = new BitmapLoader(image);
            bitmapLoader.execute(item.getImage());

            LinearLayout open = view.findViewById(R.id.view_restaurant_button);
            open.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle bundle = new Bundle(2);
                    bundle.putInt("id", item.getRestaurantId());
                    FragmentLoader.startFragment(R.id.frament_place, RestaurantDetailFragment.class, bundle, false);
                }
            });
        }
        return view;
    }
}
