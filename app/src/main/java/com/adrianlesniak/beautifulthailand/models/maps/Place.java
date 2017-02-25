package com.adrianlesniak.beautifulthailand.models.maps;

import com.google.gson.annotations.SerializedName;

import java.net.URL;

/**
 * Created by adrian on 04/02/2017.
 */

public class Place {

    @SerializedName("place_id")
    public String placeId;

    public String name;

    public Geometry geometry;

//    public URL icon;

    public float rating;

    public String types[];

//    @SerializedName("opening_hours")
//    public OpeningHours openingHours;

    public Photo photos[];

    public String vicinity;

    @SerializedName("permanently_closed")
    public boolean permanentlyClosed;

}
