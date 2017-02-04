package com.adrianlesniak.beautifulthailand.models;

import com.google.gson.annotations.SerializedName;

import org.json.JSONObject;

import java.net.URL;

/**
 * Created by adrian on 04/02/2017.
 */

public class Place {

    public Geometry geometry;

    public String name;

    public URL icon;

    @SerializedName("place_id")
    public String placeId;

    public float rating;

    public String types[];

    @SerializedName("opening_hours")
    public OpeningHours openingHours;

    public Photo photos[];

    public String vicinity;

    @SerializedName("permanently_closed")
    public boolean permanentlyClosed;

}
