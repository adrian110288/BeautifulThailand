package com.adrianlesniak.beautifulthailand.apis;

import android.location.Location;

import com.adrianlesniak.beautifulthailand.models.weather.CurrentWeatherResponse;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

/**
 * Created by adrian on 15/02/2017.
 */

public class OpenWeatherMapApiHelper {

    public static Observable<CurrentWeatherResponse> getWeatherDataByLocation(Location location) {

        return Observable.create(new ObservableOnSubscribe<CurrentWeatherResponse>() {
            @Override
            public void subscribe(final ObservableEmitter<CurrentWeatherResponse> emitter) throws Exception {

            }
        });
    }
}
