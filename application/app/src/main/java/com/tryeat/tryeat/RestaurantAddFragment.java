package com.tryeat.tryeat;


import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.location.places.ui.SupportPlaceAutocompleteFragment;
import com.tryeat.team.tryeat_service.R;
import com.tryeat.rest.model.Status;
import com.tryeat.rest.model.StatusCode;
import com.tryeat.rest.service.RestaurantService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


// Dialog 방식으로 대체
/**
 * Created by socce on 2018-05-08.
 */

public class RestaurantAddFragment extends Fragment{
    View view;
    EditText name;
    GoogleMapFragment fragment;
    public RestaurantAddFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.restaurant_add_fragment,container,false);
        Button addButton = view.findViewById(R.id.addButton);
        addButton.setOnClickListener(addRestaurant());
        ImageButton searchButton = view.findViewById(R.id.searchButton);
        searchButton.setOnClickListener(searchLacation());
        name = view.findViewById(R.id.name);

        FragmentManager fm = getFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        SupportPlaceAutocompleteFragment supportPlaceAutocompleteFragment = new SupportPlaceAutocompleteFragment();
        supportPlaceAutocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                // TODO: Get info about the selected place.
                Log.i("jkl", "Place: " + place.getName());
            }

            @Override
            public void onError(com.google.android.gms.common.api.Status status) {
                // TODO: Handle the error.
                Log.i("kl;", "An error occurred: " + status);
            }
        });
        fragmentTransaction.replace(R.id.place_autocomplete_fragment,supportPlaceAutocompleteFragment);
        fragmentTransaction.commit();

        return view;
    }

    public  View.OnClickListener searchLacation(){

        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getFragmentManager();
                FragmentTransaction fragmentTransaction = fm.beginTransaction();
                fragment = new GoogleMapFragment();
                fragmentTransaction.replace(R.id.mapPlace,fragment);
                fragmentTransaction.commit();
            }
        };
    }

    public View.OnClickListener addRestaurant(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Location location = fragment.getLocation();
                RestaurantService.addRestaurant(name.getText().toString(),location.getLatitude()+"",location.getLongitude()+"",new Callback< Status>(){
                    @Override
                    public void onResponse(Call<Status> call, Response<Status> response) {
                        if (response.code() == StatusCode.ADD_RESTAURANT_SUCCESS) {
                        } else if (response.code() == StatusCode.ADD_RESTAURANT_FAIL) {
                        }
                    }
                    @Override
                    public void onFailure(Call<Status> call, Throwable t) {
                    }
                });
            }
        };
    }
}