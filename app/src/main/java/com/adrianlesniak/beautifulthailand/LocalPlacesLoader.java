package com.adrianlesniak.beautifulthailand;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by adrian on 21/01/2017.
 */

public class LocalPlacesLoader extends AsyncTaskLoader<LocalPlacesResponse> {

    public static final String BUNDLE_LAT = "lat";

    public static final String BUNDLE_LNG = "lng";

    private Context mContext;

    private OkHttpClient mClient;

    private Location mLocation;

    private int mRadius;

    public LocalPlacesLoader(Context context, Location location, int radius) {
        super(context);

        this.mContext = context;
        this.mClient = new OkHttpClient();
        this.mLocation = location;
        mRadius = radius;
    }

    @Override
    public LocalPlacesResponse loadInBackground() {

        String url = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=" + String.valueOf(mLocation.getLatitude()) + "," + String.valueOf(mLocation.getLongitude()) + "&radius=" + String.valueOf(mRadius) + "&key=" + mContext.getString(R.string.api_key);

        Request request = new Request.Builder()
                .url(url)
                .build();

        try {
            Response response = mClient.newCall(request).execute();

            try {
                JSONObject responseJson = new JSONObject(response.body().string());

                LocalPlacesResponse parsedResponse = new LocalPlacesResponse(responseJson);

                return parsedResponse;

            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;

        } catch (IOException e) {
            e.printStackTrace();
        }


        return null;
    }

    @Override
    protected void onStopLoading() {
        cancelLoad();
    }
}
