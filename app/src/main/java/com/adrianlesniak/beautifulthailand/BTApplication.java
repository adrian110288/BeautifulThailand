package com.adrianlesniak.beautifulthailand;

import android.app.Application;

import io.realm.Realm;

/**
 * Created by adrian on 22/01/2017.
 */

public class BTApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Realm.init(getApplicationContext());
    }
}
