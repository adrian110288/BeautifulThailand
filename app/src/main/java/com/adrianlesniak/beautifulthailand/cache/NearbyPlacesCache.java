package com.adrianlesniak.beautifulthailand.cache;

import com.adrianlesniak.beautifulthailand.models.maps.Place;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by adrian on 10/02/2017.
 */

public class NearbyPlacesCache {

    private static NearbyPlacesCache sInstance;

    private List<Place> mCache = new ArrayList<>();

    private NearbyPlacesCache() {}

    public static NearbyPlacesCache getsInstance() {

        if(sInstance == null) {
            sInstance = new NearbyPlacesCache();
        }

        return sInstance;
    }

    public void setCache(List<Place> list) {
        this.mCache = list;
    }

    public List<Place> getCache() {
        return this.mCache;
    }

    public void clearCache() {
        this.mCache.clear();
    }

    public boolean isCacheEmpty() {
        return this.mCache.size() == 0;
    }

}
