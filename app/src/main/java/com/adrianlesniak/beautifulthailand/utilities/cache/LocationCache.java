package com.adrianlesniak.beautifulthailand.utilities.cache;

import android.location.Location;

import com.adrianlesniak.beautifulthailand.models.maps.LatLng;

import java.util.List;
import java.util.Set;

/**
 * Created by adrian on 14/02/2017.
 */

public class LocationCache {

    public interface OnLocationUpdateListener {
        void onLocationUpdated(Location newLocation);
    }

    private static LocationCache sInstance;

    private Location mLocationCache;

    private long mLocationUpdateTime = 0;

    private Set<OnLocationUpdateListener> mLocationUpdateListenerPool;

    private LocationCache() {}

    public static LocationCache getInstance() {

        if(sInstance == null) {
            sInstance = new LocationCache();
        }

        return sInstance;
    }

    public void setOnLocationUpdateListener(OnLocationUpdateListener listener) {
        this.mLocationUpdateListenerPool.add(listener);
    }

    public void setLocationCache(Location location) {
        this.mLocationCache = location;
        this.mLocationUpdateTime = System.currentTimeMillis();
        informListeners();
    }

    public Location getLocationCache() {
        return this.mLocationCache;
    }

    public void invalidateCache() {
        this.mLocationCache = null;
    }

    private void informListeners() {
        for (OnLocationUpdateListener listener :
                mLocationUpdateListenerPool) {

            if(listener != null) {
                listener.onLocationUpdated(this.mLocationCache);
            }
        }
    }
}
