package com.adrianlesniak.beautifulthailand.cache;

import android.support.v4.util.ArrayMap;

import com.adrianlesniak.beautifulthailand.models.maps.DistanceMatrixElement;

/**
 * Created by adrian on 12/02/2017.
 */

public class DistanceMatrixCache {

    private static DistanceMatrixCache sIntance = new DistanceMatrixCache();

    private ArrayMap<String, DistanceMatrixElement> mCache = new ArrayMap<>();

    private DistanceMatrixCache() {}

    public static DistanceMatrixCache getInstance() {
        return sIntance;
    }

    public void putDistance(String placeId, DistanceMatrixElement distanceMatrixElement) {
        this.mCache.put(placeId, distanceMatrixElement);
    }

    public DistanceMatrixElement getDistance(String placeId) {
        return this.mCache.get(placeId);
    }

    public void invalidateCache() {
        this.mCache.clear();
    }
}
