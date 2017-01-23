package com.adrianlesniak.beautifulthailand;

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

    private static final class ResponseStatus {

        private static final String OK_STATUS = "OK";

        private static final String ZERO_RESULTS = "ZERO_RESULTS";
    }

    private List<Place> mResults;

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
        return this.mStatus == ResponseStatus.OK_STATUS || this.mStatus == ResponseStatus.ZERO_RESULTS;
    }

    public boolean hasZeroResults() {
        return this.mStatus == ResponseStatus.ZERO_RESULTS;
    }

    public List<Place> getResults() {
        return this.mResults;
    }

    public String getNextPageToken() {
        return this.mNextPageToken;
    }
}
