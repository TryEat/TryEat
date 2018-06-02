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
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.location.places.ui.SupportPlaceAutocompleteFragment;
import com.google.android.gms.maps.model.LatLng;
import com.tryeat.rest.model.Restaurant;
import com.tryeat.rest.model.Status;
import com.tryeat.rest.model.StatusCode;
import com.tryeat.rest.service.RestaurantService;
import com.tryeat.team.tryeat_service.R;

import java.util.ArrayList;

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
    EditText tel;
    GoogleMapFragment fragment;
    public RestaurantAddFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.restaurant_add_fragment,container,false);
        Button addButton = view.findViewById(R.id.addButton);
        addButton.setOnClickListener(addRestaurant());
        ImageButton searchButton = view.findViewById(R.id.name_search);
        searchButton.setOnClickListener(searchName());
        name = view.findViewById(R.id.name);
        tel = view.findViewById(R.id.tel_number);
        updateMap(31.1412,131.21414);

        FragmentManager fm = getFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        SupportPlaceAutocompleteFragment supportPlaceAutocompleteFragment = new SupportPlaceAutocompleteFragment();
        supportPlaceAutocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                name.getText().clear();
                name.setText(place.getName());
                tel.setText(place.getPhoneNumber());
                updateMap(place.getLatLng().latitude,place.getLatLng().longitude);
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

    public void updateMap (double latitude, double longitude){
        if(fragment==null) {
            FragmentLoader.startFragment(R.id.mapPlace,GoogleMapFragment.class);
            fragment = (GoogleMapFragment)FragmentLoader.getFragmentInstance(GoogleMapFragment.class);
        }
        fragment.setLocation(latitude,longitude);
    }

    public View.OnClickListener searchName(){
       return new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               RestaurantService.getRestaurant(name.getText().toString(), new Callback<ArrayList<Restaurant>>() {
                   @Override
                   public void onResponse(Call<ArrayList<Restaurant>> call, Response<ArrayList<Restaurant>> response) {
                       if(response.code() == StatusCode.RESTAURANT_IS_EXIST){
                           ArrayList<Restaurant> items = response.body();
                           if(!items.isEmpty()){
                               Log.d("location",items.get(0).getLat()+""+items.get(0).getLon()+"");
                               fragment.setLocation(items.get(0).getLat(),items.get(0).getLon());
                           }
                       }
                   }

                   @Override
                   public void onFailure(Call<ArrayList<Restaurant>> call, Throwable t) {

                   }
               });
           }
       } ;
    }

    public View.OnClickListener addRestaurant(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Location location = fragment.getLocation();
                RestaurantService.addRestaurant(name.getText().toString(),location.getLatitude(),location.getLongitude(),new Callback< Status>(){
                    @Override
                    public void onResponse(Call<Status> call, Response<Status> response) {
                        if (response.code() == StatusCode.ADD_RESTAURANT_SUCCESS) {
                            Log.d("debug",response.toString());
                        } else if (response.code() == StatusCode.ADD_RESTAURANT_FAIL) {
                            Log.d("debug",response.toString());
                        }
                    }
                    @Override
                    public void onFailure(Call<Status> call, Throwable t) {
                        Log.d("debug",t.toString());
                    }
                });
            }
        };
    }

}