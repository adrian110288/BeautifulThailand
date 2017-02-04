package com.adrianlesniak.beautifulthailand.models;

import com.google.gson.annotations.SerializedName;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by adrian on 04/02/2017.
 */

public class PlacesSearchResponse {

    public Place results[];

    @SerializedName("html_attributions")
    public String htmlAttributions[];

    @SerializedName("next_page_token")
    public String nextPageToken;

    public String status;


//    public PlacesSearchResponse(JSONObject responseObject) {
//        try {
//            this.nextPageToken = responseObject.has("next_page_token")? responseObject.getString("next_page_token") : null;
//            this.status = responseObject.has("status")? responseObject.getString("status") : null;
//
//            if(responseObject.has("results")) {
//                JSONArray places = responseObject.getJSONArray("results");
//
//                for(int index=0; index < places.length(); index++) {
//                    this.results[index] = new Place(places.getJSONObject(index));
//                }
//            }
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//    }
}
