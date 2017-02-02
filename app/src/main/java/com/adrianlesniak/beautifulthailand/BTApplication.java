package com.adrianlesniak.beautifulthailand;

import android.app.Application;

import com.google.maps.GeoApiContext;

/**
 * Created by adrian on 22/01/2017.
 */

public class BTApplication extends Application {

    private static GeoApiContext sGeoApiContext;

    @Override
    public void onCreate() {
        super.onCreate();
        sGeoApiContext = new GeoApiContext().setApiKey(getResources().getString(R.string.api_key));
    }

    public static GeoApiContext getGeoApiContext() {
        return sGeoApiContext;
    }
}
