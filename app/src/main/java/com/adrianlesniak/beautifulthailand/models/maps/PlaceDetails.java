package com.adrianlesniak.beautifulthailand.models.maps;

import com.google.gson.annotations.SerializedName;

/**
 * Created by adrian on 04/02/2017.
 */

public class PlaceDetails {

    @SerializedName("formatted_address")
    public String formattedAddress;

    @SerializedName("formatted_phone_number")
    public String formattedPhoneNumber;

    public Geometry geometry;

    @SerializedName("international_phone_number")
    public String internationalPhoneNumber;

    public String name;

    @SerializedName("opening_hours")
    public OpeningHours openingHours;

    public Photo[] photos;

    @SerializedName("place_id")
    public String placeId;

    @SerializedName("permanently_closed")
    public boolean permanentlyClosed;

    public float rating;

    public Review[] reviews;

    public String[] types;

    public String url;

    public String vicinity;

    public String website;

}
