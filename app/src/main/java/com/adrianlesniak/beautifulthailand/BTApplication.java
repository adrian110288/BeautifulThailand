package com.adrianlesniak.beautifulthailand;

import android.app.Application;

import com.google.maps.GeoApiContext;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by adrian on 22/01/2017.
 */

public class BTApplication extends Application {

    private static GeoApiContext sGeoApiContext;

    @Override
    public void onCreate() {
        super.onCreate();

        sGeoApiContext = new GeoApiContext().setApiKey(getResources().getString(R.string.api_key));

        Realm.init(getApplicationContext());
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder().build();
        Realm.setDefaultConfiguration(realmConfiguration);
    }

    public static GeoApiContext getGeoApiContext() {
        return sGeoApiContext;
    }
}
