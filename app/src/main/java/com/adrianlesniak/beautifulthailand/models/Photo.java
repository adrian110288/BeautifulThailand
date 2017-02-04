package com.adrianlesniak.beautifulthailand.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by adrian on 04/02/2017.
 */

public class Photo {

    public int height;

    @SerializedName("html_attributions")
    public String[] htmlAttributions;

    @SerializedName("photo_reference")
    public String photo_reference;

    public int width;


}
