package com.adrianlesniak.beautifulthailand;

import android.location.Location;

/**
 * Created by adrian on 25/01/2017.
 */
public class CurrentLocation {
    private static CurrentLocation sInstance = new CurrentLocation();

    private Location mCurrentLocation;

    public static CurrentLocation getInstance() {
        return sInstance;
    }

    private CurrentLocation() {
    }

    public void setCurrentLocation(Location location) {
        this.mCurrentLocation = location;
    }

    public Location getCurrentLocation() {
        return this.mCurrentLocation;
    }

}
