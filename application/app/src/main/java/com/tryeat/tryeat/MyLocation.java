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

class MyLocation {
    private static Activity mActivity;
    private static Location mLocation;

    public static void setActivity(Activity activity) {
        mActivity = activity;
    }

    public static void searchLocation() {
        LocationManager manager = (LocationManager) mActivity.getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        GPSListener gpsListener = new GPSListener();

        if (ActivityCompat.checkSelfPermission(mActivity.getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(mActivity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 0);

        manager.requestSingleUpdate(LocationManager.NETWORK_PROVIDER, gpsListener, null);
    }

    public static Location getLocation() {
        return mLocation;
    }

    private static class GPSListener implements LocationListener {

        @Override
        public void onLocationChanged(Location location) {
            mLocation = location;
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