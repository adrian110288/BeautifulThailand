package com.adrianlesniak.beautifulthailand.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by adrian on 04/02/2017.
 */

public class MapsResponse {

    @SerializedName("html_attributions")
    public String htmlAttributions[];

    public String status;
}
