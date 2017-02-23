package com.adrianlesniak.beautifulthailand.cache;

import com.adrianlesniak.beautifulthailand.models.weather.CurrentWeatherResponse;

/**
 * Created by adrian on 15/02/2017.
 */

public class WeatherCache {

    private static WeatherCache sInstance;

    private CurrentWeatherResponse mWeatherData;

    private long mLastUpdate = 0;

    private WeatherCache() {}

    public static WeatherCache getInstance() {

        if(sInstance == null) {
            sInstance = new WeatherCache();
        }

        return sInstance;
    }

    public void setWeatherData(CurrentWeatherResponse weatherData) {
        this.mWeatherData = weatherData;
        this.mLastUpdate = System.currentTimeMillis();
    }

    public CurrentWeatherResponse getWeatherData() {
        return this.mWeatherData;
    }

    public void invalidateCache() {
        this.mWeatherData = null;
        this.mLastUpdate = 0;
    }
}
