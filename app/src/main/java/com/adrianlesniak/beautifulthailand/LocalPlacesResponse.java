package com.adrianlesniak.beautifulthailand;

import com.adrianlesniak.beautifulthailand.models.ListItem;
import com.adrianlesniak.beautifulthailand.models.Place;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by adrian on 21/01/2017.
 */

public class LocalPlacesResponse {

    private List<ListItem> mResults;

    private String mStatus;

    private String mNextPageToken;

    public LocalPlacesResponse(JSONObject mResponse) throws JSONException {

        this.mStatus = mResponse.getString("status");

        this.mNextPageToken = mResponse.has("next_page_token")? mResponse.getString("next_page_token") : null;

        JSONArray resultsArray = mResponse.getJSONArray("results");
        parseResults(resultsArray);
    }

    private void parseResults(JSONArray resultsArray) throws JSONException {

        this.mResults = new ArrayList<>(resultsArray.length());

        for (int index = 0; index < resultsArray.length(); index++) {
            Place place = new Place(resultsArray.getJSONObject(index));
            this.mResults.add(place);
        }
    }

    public boolean isSuccessful() {
        return this.mStatus == MapsAPIResponseStatus.OK_STATUS || this.mStatus == MapsAPIResponseStatus.ZERO_RESULTS;
    }

    public boolean hasZeroResults() {
        return this.mStatus == MapsAPIResponseStatus.ZERO_RESULTS;
    }

    public List<ListItem> getResults() {
        return this.mResults;
    }

    public String getNextPageToken() {
        return this.mNextPageToken;
    }
}
