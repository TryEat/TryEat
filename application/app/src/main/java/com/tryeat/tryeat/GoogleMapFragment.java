package com.tryeat.tryeat;

import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.HorizontalScrollView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class GoogleMapFragment extends SupportMapFragment implements OnMapReadyCallback {
    GoogleMap gMap;
    Marker marker;
    Location _location;

    @Override
    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        gMap = googleMap;
        gMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        gMap.getUiSettings().setZoomControlsEnabled(true);

        gMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng point) {
                setLocation(point.latitude,point.longitude);
                if(marker!=null)marker.remove();
                marker = gMap.addMarker(new MarkerOptions()
                        .position(point)
                        .title("Here!!"));
            }
        });

        setLocation(MyLocation.getLocation());
    }

    public void update(){
        if(_location==null)return;
        gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(_location.getLatitude(), _location.getLongitude()),gMap.getCameraPosition().zoom));
    }

    public Location getLocation() {
        return _location;
    }

    public void setLocation(Location location) {
        _location=location;
        update();
    }

    public void setLocation(double latitude, double longitude) {
        if(_location==null)_location=new Location(LocationManager.NETWORK_PROVIDER);
        this._location.setLatitude(latitude);
        this._location.setLongitude(longitude);
        update();
    }

}
