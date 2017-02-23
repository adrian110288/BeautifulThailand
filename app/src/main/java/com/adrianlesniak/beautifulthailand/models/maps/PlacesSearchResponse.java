package com.adrianlesniak.beautifulthailand.models.maps;

import com.google.gson.annotations.SerializedName;

/**
 * Created by adrian on 04/02/2017.
 */

public class PlacesSearchResponse extends GoogleMapsResponse {

    public Place results[];

    @SerializedName("next_page_token")
    public String nextPageToken;

}
