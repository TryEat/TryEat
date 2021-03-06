package com.tryeat.tryeat;


import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Toast;

import com.tryeat.rest.model.Status;
import com.tryeat.rest.service.RestaurantService;
import com.tryeat.team.tryeat_service.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Response;


// Dialog 방식으로 대체

/**
 * Created by socce on 2018-05-08.
 */

public class RestaurantAddFragment extends Fragment implements View.OnClickListener{
    private View view;
    private EditText name;
    private EditText tel;

    private GoogleMapFragment fragment;
    private ImageAddFragment imageFragment;

    private ScrollView mainScrollView;
    private ImageView transparentImageView;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.restaurant_add_fragment, container, false);
            Button addButton = view.findViewById(R.id.addButton);
            addButton.setOnClickListener(this);

            NavigationManager.setVisibility(View.GONE);

            name = view.findViewById(R.id.name);
            tel = view.findViewById(R.id.tel_number);

            mainScrollView = view.findViewById(R.id.scrollView);
            transparentImageView = view.findViewById(R.id.transparent_image);

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

    @Override
    public void onClick(View v) {
        ProgressDialogManager.show(getActivity(),"음식점 등록 중입니다...");
        Location location = fragment.getLocation();
        if (location == null) {
            ProgressDialogManager.dismiss();
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

        String adress = myList.get(0).getAddressLine(0);

        RestaurantService.addRestaurant(name.getText().toString(), adress, tel.getText().toString(),"", imageFragment.getImageBitmap(), location.getLatitude(), location.getLongitude(),
                new SimpleCallBack<>(RestaurantService.class.getSimpleName(), new SimpleCallBack.Success<Status>() {
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