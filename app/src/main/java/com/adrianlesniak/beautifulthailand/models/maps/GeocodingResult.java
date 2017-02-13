package com.adrianlesniak.beautifulthailand.models.maps;

import com.google.gson.annotations.SerializedName;

/**
 * Created by adrian on 13/02/2017.
 */

public class GeocodingResult {

    @SerializedName("address_components")
    public AddressComponent[] addressComponents;

    @SerializedName("formatted_address")
    public String formattedAddress;

    @SerializedName("postcode_localities")
    public String[] postcodeLocalities;

    public Geometry geometry;

    public AddressType[] types;

    @SerializedName("partial_match")
    public boolean partialMatch;

    @SerializedName("place_id")
    public String placeId;
}
