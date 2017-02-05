package com.adrianlesniak.beautifulthailand.models;

import com.google.gson.annotations.SerializedName;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by adrian on 04/02/2017.
 */

public class PlacesSearchResponse extends MapsResponse{

    public Place results[];

    @SerializedName("next_page_token")
    public String nextPageToken;

}
