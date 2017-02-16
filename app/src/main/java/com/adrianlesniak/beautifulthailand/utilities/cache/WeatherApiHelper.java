package com.adrianlesniak.beautifulthailand.utilities.cache;

import android.location.Location;

import com.adrianlesniak.beautifulthailand.models.weather.WeatherData;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

/**
 * Created by adrian on 15/02/2017.
 */

public class WeatherApiHelper {

    public static Observable<WeatherData> getWeatherDataByLocation(Location location) {

        return Observable.create(new ObservableOnSubscribe<WeatherData>() {
            @Override
            public void subscribe(final ObservableEmitter<WeatherData> emitter) throws Exception {

            }
        });
    }
}
