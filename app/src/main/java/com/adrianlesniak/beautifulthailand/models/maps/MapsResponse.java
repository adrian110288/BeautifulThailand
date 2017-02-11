package com.adrianlesniak.beautifulthailand.models.maps;

import com.google.gson.annotations.SerializedName;

/**
 * Created by adrian on 04/02/2017.
 */

public class MapsResponse {

    private static final String STATUS_OK = "OK";

    private static final String STATUS_ZERO_RESULTS = "ZERO_RESULTS";

    @SerializedName("html_attributions")
    public String htmlAttributions[];

    public String status;

    public boolean isSuccessful() {
        return status.equalsIgnoreCase(STATUS_OK) | status.equalsIgnoreCase(STATUS_ZERO_RESULTS);
    }
}
