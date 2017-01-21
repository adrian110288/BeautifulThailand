package com.adrianlesniak.beautifulthailand;

/**
 * Created by adrian on 21/01/2017.
 */

public final class Location {

    private double mLatitude;

    private double mLongitude;

    public Location(double lat, double lng) {
        this.mLatitude = lat;
        this.mLongitude = lng;
    }

    public double getLatitude() {
        return mLatitude;
    }

    public void setLatitude(float mLatitude) {
        this.mLatitude = mLatitude;
    }

    public double getLongitude() {
        return mLongitude;
    }

    public void setLongitude(float mLongitude) {
        this.mLongitude = mLongitude;
    }
}
