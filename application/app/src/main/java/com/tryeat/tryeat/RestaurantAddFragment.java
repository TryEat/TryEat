package com.tryeat.tryeat;


import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;

import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlacePhotoMetadata;
import com.google.android.gms.location.places.PlacePhotoMetadataBuffer;
import com.google.android.gms.location.places.PlacePhotoMetadataResponse;
import com.google.android.gms.location.places.PlacePhotoResponse;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.location.places.ui.SupportPlaceAutocompleteFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.tryeat.rest.model.Status;
import com.tryeat.rest.model.StatusCode;
import com.tryeat.rest.service.RestaurantService;
import com.tryeat.team.tryeat_service.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


// Dialog 방식으로 대체

/**
 * Created by socce on 2018-05-08.
 */

public class RestaurantAddFragment extends Fragment {
    View view;
    EditText name;
    EditText tel;

    GoogleMapFragment fragment;
    ImageAddFragment imageFragment;

    ScrollView mainScrollView;
    ImageView transparentImageView;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.restaurant_add_fragment, container, false);
            Button addButton = view.findViewById(R.id.addButton);
            addButton.setOnClickListener(addRestaurant());

            name = view.findViewById(R.id.name);
            tel = view.findViewById(R.id.tel_number);

            mainScrollView = (ScrollView) view.findViewById(R.id.scrollView);
            transparentImageView = (ImageView) view.findViewById(R.id.transparent_image);

            transparentImageView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    int action = event.getAction();
                    switch (action) {
                        case MotionEvent.ACTION_DOWN:
                            mainScrollView.requestDisallowInterceptTouchEvent(true);
                            return false;
                        case MotionEvent.ACTION_UP:
                            mainScrollView.requestDisallowInterceptTouchEvent(false);
                            return true;
                        case MotionEvent.ACTION_MOVE:
                            mainScrollView.requestDisallowInterceptTouchEvent(true);
                            return false;
                        default:
                            return true;
                    }
                }
            });

            fragment = (GoogleMapFragment) getChildFragmentManager().findFragmentById(R.id.map_fragment);
            imageFragment = (ImageAddFragment) getChildFragmentManager().findFragmentById(R.id.image_fragment);
        }
        return view;
    }

    public View.OnClickListener addRestaurant() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Location location = fragment.getLocation();
                if (location == null) {
                    AlertDialogBuilder.createAlert(getActivity(), "음식점 위치를 지정해 주세요.");
                    return;
                }

                Geocoder myLocation = new Geocoder(getActivity(), Locale.getDefault());
                List<Address> myList = new ArrayList<>();

                try {
                    myList = myLocation.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                RestaurantService.addRestaurant(name.getText().toString(), "", tel.getText().toString(), imageFragment.getImageBitmap(), location.getLatitude(), location.getLongitude(), new Callback<Status>() {
                    @Override
                    public void onResponse(Call<Status> call, Response<Status> response) {
                        if (response.code() == StatusCode.ADD_RESTAURANT_SUCCESS) {
                            Log.d("debug", response.toString());
                        } else if (response.code() == StatusCode.ADD_RESTAURANT_FAIL) {
                            Log.d("debug", response.toString());
                        }
                        getFragmentManager().popBackStackImmediate();
                    }

                    @Override
                    public void onFailure(Call<Status> call, Throwable t) {
                        Log.d("debug", t.toString());
                    }
                });
            }
        };
    }
}