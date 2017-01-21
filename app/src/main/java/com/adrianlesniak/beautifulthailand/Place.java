package com.adrianlesniak.beautifulthailand;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by adrian on 21/01/2017.
 */

public class Place {

    private String mId;

    private String mName;

    public Place(JSONObject mPlaceData) throws JSONException {

        this.mId = mPlaceData.getString("id");
        this.mName = mPlaceData.getString("name");
    }

    public String getId() {
        return this.mId;
    }

    public String getName() {
        return this.mName;
    }
}
