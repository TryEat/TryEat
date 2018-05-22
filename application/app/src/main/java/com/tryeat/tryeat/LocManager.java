package com.tryeat.tryeat;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

public class LocManager {
    private Location gpsLocation;
    LocationManager manager;
    GpsListener gpsListener;

    Context context;

    class GpsListener implements LocationListener {
        @Override
        public void onLocationChanged(Location location) {
            gpsLocation = location;
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

    public LocManager(Context context) {
        manager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        gpsListener = new GpsListener();
        this.context =context;
    }

    public void requestLocation() {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions((Activity) context, new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, 0);

        manager.requestSingleUpdate(LocationManager.NETWORK_PROVIDER, gpsListener, null);
        //Toast.makeText(context,gpsLocation.getLatitude()+"",Toast.LENGTH_LONG);
    }
}


