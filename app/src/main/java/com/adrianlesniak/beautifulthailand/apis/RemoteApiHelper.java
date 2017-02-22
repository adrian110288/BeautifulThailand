package com.adrianlesniak.beautifulthailand.apis;

import android.content.Context;

import com.google.gson.Gson;

import okhttp3.OkHttpClient;

/**
 * Created by adrian on 21/02/2017.
 */

public abstract class RemoteApiHelper {

    protected OkHttpClient mClient;

    protected Gson mGson;

    protected String API_KEY;

    protected String URL_BASE;

    protected RemoteApiHelper(Context context) {
        this.mClient = new OkHttpClient();
        this.mGson = new Gson();
        this.API_KEY = this.getApiKey(context);
        this.URL_BASE = this.getBaseUrl();
    }

    protected abstract String getApiKey(Context context);

    protected abstract String getBaseUrl();
}
