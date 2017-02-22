package com.adrianlesniak.beautifulthailand.apis;

import android.content.Context;
import android.location.Location;

import com.adrianlesniak.beautifulthailand.R;
import com.adrianlesniak.beautifulthailand.models.weather.WeatherData;

import java.io.IOException;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by adrian on 15/02/2017.
 */

public class OpenWeatherMapApiHelper extends RemoteApiHelper{

    private static OpenWeatherMapApiHelper sInstance;

    private OpenWeatherMapApiHelper(Context context) {
        super(context);
    }

    public static OpenWeatherMapApiHelper getInstance(Context context) {

        if(sInstance == null) {
            sInstance = new OpenWeatherMapApiHelper(context);
        }

        return sInstance;
    }

    @Override
    protected String getApiKey(Context context) {
        return context.getResources().getString(R.string.open_weather_map_api_key);
    }

    @Override
    protected String getBaseUrl() {
        return "http://api.openweathermap.org/data/2.5/weather?";
    }

    public Observable<WeatherData> getWeatherDataByLocation(final Location location) {

        return Observable.create(new ObservableOnSubscribe<WeatherData>() {
            @Override
            public void subscribe(final ObservableEmitter<WeatherData> emitter) throws Exception {

                String url = URL_BASE + "lat=" + location.getLatitude() + "&lon=" + location.getLongitude() + "&appid=" + API_KEY;

                Request request = new Request.Builder()
                    .url(url)
                    .build();

                Response response = mClient.newCall(request).execute();
                WeatherData weatherData = mGson.fromJson(response.body().string(), WeatherData.class);
                emitter.onNext(weatherData);

            }
        });
    }
}
