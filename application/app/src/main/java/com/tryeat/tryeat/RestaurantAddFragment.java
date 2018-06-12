package com.tryeat.tryeat;


import android.app.ProgressDialog;
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
    ImageAddFragment imageFragment;
    SupportPlaceAutocompleteFragment supportPlaceAutocompleteFragment;

    GeoDataClient geoDataClient;

    ProgressDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(view==null) {
            view = inflater.inflate(R.layout.restaurant_add_fragment, container, false);
            Button addButton = view.findViewById(R.id.addButton);
            addButton.setOnClickListener(addRestaurant());

            name = view.findViewById(R.id.name);
            tel = view.findViewById(R.id.tel_number);

            geoDataClient = Places.getGeoDataClient(getActivity());

            fragment =  (GoogleMapFragment)getChildFragmentManager().findFragmentById(R.id.map_fragment);
            imageFragment = (ImageAddFragment) getChildFragmentManager().findFragmentById(R.id.image_fragment);
            updateMap();
        }
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        supportPlaceAutocompleteFragment = (SupportPlaceAutocompleteFragment)getChildFragmentManager().findFragmentById(R.id.place_fragment);

        Location location = MyLocation.getLocation();
        if (location != null) {
            supportPlaceAutocompleteFragment.setBoundsBias(new LatLngBounds(
                    new LatLng(location.getLatitude() - 0.3, location.getLongitude() - 0.3),
                    new LatLng(location.getLatitude() + 0.3, location.getLongitude() + 0.3)));
        }

        supportPlaceAutocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
  @Override
            public void onPlaceSelected(final Place place) {
                progressDialog = new ProgressDialog(getContext(), R.style.AppTheme_Dark_Dialog);
                progressDialog.setIndeterminate(true);
                progressDialog.setMessage("음식점 확인 중...");
                progressDialog.show();

                addNewRestaurant(place);
            }

            @Override
            public void onError(com.google.android.gms.common.api.Status status) {

            }
        });
    }

    public void addNewRestaurant(final Place place){
        geoDataClient = Places.getGeoDataClient(getActivity());

        Task<PlacePhotoMetadataResponse> photoResponse =
                geoDataClient.getPlacePhotos(place.getId());

        photoResponse.addOnCompleteListener(new OnCompleteListener<PlacePhotoMetadataResponse>() {
            @Override
            public void onComplete(@NonNull Task<PlacePhotoMetadataResponse> task) {
                PlacePhotoMetadataResponse photos = task.getResult();
                PlacePhotoMetadataBuffer photoMetadataBuffer = photos.getPhotoMetadata();

                if (photoMetadataBuffer.getCount() != 0) {
                    PlacePhotoMetadata metadata = photoMetadataBuffer.get(0).freeze();
                    Task<PlacePhotoResponse> photoResponse = geoDataClient.getPhoto(metadata);
                    photoResponse.addOnCompleteListener(new OnCompleteListener<PlacePhotoResponse>() {
                        @Override
                        public void onComplete(@NonNull Task<PlacePhotoResponse> task) {
                            PlacePhotoResponse photo = task.getResult();
                            Bitmap photoBitmap = photo.getBitmap();
                            String phone = place.getPhoneNumber().toString();
                            phone.replace("+82 ","0");
                            RestaurantService.addRestaurant(place.getName().toString(), phone, photoBitmap, place.getLatLng().latitude, place.getLatLng().longitude, new Callback<Status>() {
                                @Override
                                public void onResponse(Call<Status> call, Response<Status> response) {
                                    //restaurantId = response.body().payLoadInt;
                                    progressDialog.dismiss();
                                }

                                @Override
                                public void onFailure(Call<Status> call, Throwable t) {
                                    progressDialog.dismiss();
                                }
                            });
                        }
                    });
                }

                photoMetadataBuffer.release();
            }
        });
    }


    public void updateMap (){
        MyLocation.searchLocation();
    }

    public View.OnClickListener addRestaurant(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Location location = fragment.getLocation();
                if(location == null){AlertDialogBuilder.createAlert(getActivity(),"음식점 위치를 지정해 주세요."); return; }
                RestaurantService.addRestaurant(name.getText().toString(),tel.getText().toString(),imageFragment.getImageBitmap(),location.getLatitude(),location.getLongitude(),new Callback< Status>(){
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