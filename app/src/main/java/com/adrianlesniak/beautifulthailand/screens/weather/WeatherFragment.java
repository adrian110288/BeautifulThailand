package com.adrianlesniak.beautifulthailand.screens.weather;

import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.adrianlesniak.beautifulthailand.R;
import com.adrianlesniak.beautifulthailand.models.weather.CurrentWeatherResponse;
import com.adrianlesniak.beautifulthailand.screens.shared.LocationAwareActivity;
import com.adrianlesniak.beautifulthailand.screens.shared.LocationDependentFragment;
import com.adrianlesniak.beautifulthailand.cache.LocationCache;
import com.adrianlesniak.beautifulthailand.apis.OpenWeatherMapApiHelper;
import com.adrianlesniak.beautifulthailand.cache.WeatherCache;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by adrian on 15/02/2017.
 */

public class WeatherFragment extends LocationDependentFragment {

    private CurrentWeatherResponse mWeatherData;

    private Observer<CurrentWeatherResponse> mWeatherDataObserver = new Observer<CurrentWeatherResponse>() {
        @Override
        public void onSubscribe(Disposable d) { }

        @Override
        public void onNext(CurrentWeatherResponse newWeatherData) {

            WeatherCache.getInstance().setWeatherData(newWeatherData);

            mWeatherData = newWeatherData;
        }

        @Override
        public void onError(Throwable e) {

        }

        @Override
        public void onComplete() {

        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_weather, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();

        if(LocationCache.getInstance().getLocationCache() == null || WeatherCache.getInstance().getWeatherData() == null) {
            if(getActivity() instanceof LocationAwareActivity) {
                ((LocationAwareActivity) getActivity()).requestCurrentLocation();
            }
            return;
        }

        this.mWeatherData = WeatherCache.getInstance().getWeatherData();
    }

    @Override
    public void onLocationUpdated(Location newLocation) {

        OpenWeatherMapApiHelper.getWeatherDataByLocation(newLocation)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this.mWeatherDataObserver);

    }
}
