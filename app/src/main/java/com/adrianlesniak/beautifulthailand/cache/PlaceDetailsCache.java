package com.adrianlesniak.beautifulthailand.cache;

import android.util.ArrayMap;

import com.adrianlesniak.beautifulthailand.models.maps.PlaceDetails;

/**
 * Created by adrian on 10/02/2017.
 */

public class PlaceDetailsCache {

    private static PlaceDetailsCache sInstance;

    private ArrayMap<String, PlaceDetails> mCache = new ArrayMap<>();

    private PlaceDetailsCache() {}

    public static PlaceDetailsCache getInstance() {

        if(sInstance == null) {
            sInstance = new PlaceDetailsCache();
        }

        return sInstance;
    }

    public void addPlaceDetails(PlaceDetails place) {

        this.mCache.put(place.placeId, place);

//        if(!this.mCache.containsKey(place.placeId)) {
//
//        }
    }

    public PlaceDetails getPlaceDetails(String placeId) {
        return this.mCache.get(placeId);
    }

    public boolean isCached(String placeId) {
        return this.mCache.containsKey(placeId);
    }

    public int size() {
        return this.mCache.size();
    }
}
