package com.adrianlesniak.beautifulthailand.models.maps;

import com.google.gson.annotations.SerializedName;

/**
 * Created by adrian on 13/02/2017.
 */

public class AddressComponent {

    @SerializedName("long_name")
    public String longName;

    @SerializedName("short_name")
    public String shortName;

    public AddressComponentType[] types;

}
