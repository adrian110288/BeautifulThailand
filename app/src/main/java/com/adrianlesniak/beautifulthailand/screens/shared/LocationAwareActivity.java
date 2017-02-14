package com.adrianlesniak.beautifulthailand.screens.shared;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;

import com.adrianlesniak.beautifulthailand.utilities.LocationListenerAdapter;
import com.adrianlesniak.beautifulthailand.utilities.cache.LocationCache;

/**
 * Created by adrian on 14/02/2017.
 */

public class LocationAwareActivity extends AppCompatActivity {

    private static final int BT_PERMISSION_REQUEST_FINE_LOCATION = 1;

    private LocationManager mLocationManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case BT_PERMISSION_REQUEST_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    requestCurrentLocation();

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }
        }
    }

    public void requestCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, BT_PERMISSION_REQUEST_FINE_LOCATION);

            return;
        }
        this.mLocationManager.requestSingleUpdate(LocationManager.GPS_PROVIDER, new LocationListenerAdapter() {
            @Override
            public void onLocationChanged(Location location) {

                LocationCache.getInstance().setLocationCache(location);
                mLocationManager.removeUpdates(this);
            }
        }, null);
    }
}
