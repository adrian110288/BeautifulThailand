package com.adrianlesniak.beautifulthailand.utilities;

import com.adrianlesniak.beautifulthailand.BTApplication;
import com.google.maps.PendingResult;
import com.google.maps.PlacesApi;
import com.google.maps.model.LatLng;
import com.google.maps.model.PhotoResult;
import com.google.maps.model.PlacesSearchResponse;

/**
 * Created by adrian on 01/02/2017.
 */

public class MapsApiHelper {

    public static void getNearbyPlaces(LatLng latLng, int radius, PendingResult.Callback<PlacesSearchResponse> callback) {
        PlacesApi
                .nearbySearchQuery(BTApplication.getGeoApiContext(), latLng)
                .radius(radius)
                .setCallback(callback);
    }

    public static void getPhoto(String photoReference, int maxWidth, PendingResult.Callback<PhotoResult> callback) {
        PlacesApi
                .photo(BTApplication.getGeoApiContext(), photoReference)
                .maxWidth(maxWidth)
                .setCallback(callback);
    }
}
