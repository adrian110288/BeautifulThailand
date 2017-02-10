package com.adrianlesniak.beautifulthailand.models.maps;

import com.google.gson.annotations.SerializedName;

/**
 * Created by adrian on 04/02/2017.
 */

public class MapsResponse {

    @SerializedName("html_attributions")
    public String htmlAttributions[];

    public String status;

    public boolean isSuccessful() {
        return status.equalsIgnoreCase("OK") & status.equalsIgnoreCase("ZERO_RESULTS");
    }
}
