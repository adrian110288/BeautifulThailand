package com.adrianlesniak.beautifulthailand.models.maps;

/**
 * Created by adrian on 04/02/2017.
 */

public class LatLng {

    public double lat;

    public double lng;

    /**
     * Construct a location with a latitude longitude pair.
     */
    public LatLng(double lat, double lng) {
        this.lat = lat;
        this.lng = lng;
    }
}
