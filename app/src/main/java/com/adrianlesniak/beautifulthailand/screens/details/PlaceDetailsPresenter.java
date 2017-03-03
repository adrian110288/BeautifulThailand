package com.adrianlesniak.beautifulthailand.screens.details;

import com.adrianlesniak.beautifulthailand.apis.GoogleMapsApiHelper;
import com.adrianlesniak.beautifulthailand.cache.PlaceDetailsCache;
import com.adrianlesniak.beautifulthailand.db.PlacesDataSource;
import com.adrianlesniak.beautifulthailand.db.PlacesLocalDataSource;
import com.adrianlesniak.beautifulthailand.models.NullableObject;
import com.adrianlesniak.beautifulthailand.models.maps.PlaceDetails;
import com.adrianlesniak.beautifulthailand.utilities.ObserverAdapter;

import org.jetbrains.annotations.NotNull;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by adrian on 03/03/2017.
 */

public class PlaceDetailsPresenter implements PlaceDetailsContract.Presenter {

    private PlacesDataSource mDataSource;

    private GoogleMapsApiHelper mGoogleMapsApiHelper;

    private PlaceDetailsContract.View mView;

    public PlaceDetailsPresenter(@NotNull PlacesDataSource mDataSource, @NotNull GoogleMapsApiHelper googleMapsApiHelper, @NotNull PlaceDetailsContract.View view) {

        this.mDataSource = mDataSource;

        this.mGoogleMapsApiHelper = googleMapsApiHelper;

        this.mView = view;
    }

    @Override
    public void loadPlaceDetails(String placeId) {

        this.mGoogleMapsApiHelper
                .getPlaceDetails(placeId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<PlaceDetails>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(PlaceDetails placeDetails) {

                        PlaceDetailsCache.getInstance().addPlaceDetails(placeDetails);
                        mView.showPlaceDetails(placeDetails);

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void checkIsPlaceFavourite(String placeId) {

        this.mDataSource
                .getFavouritePlaceById(placeId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ObserverAdapter() {

                    @Override
                    public void onNext(Object value) {
                        mView.setPlaceFavourite(!((NullableObject)value).isNull());
                    }
                });
    }
}