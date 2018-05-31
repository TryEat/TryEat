package com.tryeat.tryeat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.LatLng;

public class GoogleMapFragment extends SupportMapFragment implements OnMapReadyCallback {
    GoogleMap gMap;
    GroundOverlayOptions videoMark;
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
                videoMark = new GroundOverlayOptions().image(
                        BitmapDescriptorFactory.fromResource(android.R.drawable.presence_video_busy))
                        .position(point, 100f, 100f);
                gMap.addGroundOverlay(videoMark);
            }
        });

        update();
    }

    public void update(){
        if(gMap==null)return;
        gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(_location.getLatitude(), _location.getLongitude()), 16));
    }
    public void searchLocation(){
        LocationManager manager = (LocationManager)getActivity().getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        GPSListenter gpsListener = new GPSListenter();

        if (ActivityCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(getActivity(), new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, 0);

        manager.requestSingleUpdate(LocationManager.NETWORK_PROVIDER, gpsListener, null);
    }

    public Location getLocation() {
        return _location;
    }

    public void setLocation(String name){

    }

    public void setLocation(double latitude, double longitude) {
        if(_location==null)_location=new Location(LocationManager.NETWORK_PROVIDER);
        this._location.setLatitude(latitude);
        this._location.setLongitude(longitude);
        update();
    }

    private class GPSListenter implements LocationListener{

        @Override
        public void onLocationChanged(Location location) {
            setLocation(location.getLatitude(),location.getLongitude());
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        @Override
        public void onProviderEnabled(String s) {

        }

        @Override
        public void onProviderDisabled(String s) {

        }
    }
}
