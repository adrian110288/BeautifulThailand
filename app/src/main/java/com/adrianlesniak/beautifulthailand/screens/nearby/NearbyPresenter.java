package com.adrianlesniak.beautifulthailand.screens.nearby;

import android.location.Location;

import com.adrianlesniak.beautifulthailand.apis.GoogleMapsApiHelper;
import com.adrianlesniak.beautifulthailand.cache.DistanceMatrixCache;
import com.adrianlesniak.beautifulthailand.cache.LocationCache;
import com.adrianlesniak.beautifulthailand.cache.NearbyPlacesCache;
import com.adrianlesniak.beautifulthailand.models.maps.DistanceMatrixRow;
import com.adrianlesniak.beautifulthailand.models.maps.Place;
import com.adrianlesniak.beautifulthailand.utilities.PlaceComparator;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by adrian on 03/03/2017.
 */

public class NearbyPresenter implements NearbyContract.Presenter {

    private int mSearchRadius = 0;

    private NearbyPlacesCache mNearbyPlacesCache;

    private DistanceMatrixCache mDistanceMatrixCache;

    private GoogleMapsApiHelper mGoogleMapsApiHelper;

    private NearbyContract.View mView;

    public NearbyPresenter(@NotNull GoogleMapsApiHelper googleMapsApiHelper, @NotNull NearbyPlacesCache mNearbyPlacesCache, @NotNull DistanceMatrixCache distanceMatrixCache, NearbyContract.View view) {

        this.mGoogleMapsApiHelper = googleMapsApiHelper;

        this.mNearbyPlacesCache = mNearbyPlacesCache;

        this.mDistanceMatrixCache = distanceMatrixCache;

        this.mView = view;
    }

    @Override
    public void loadPlaces() {

        mView.showLoading();
        List<Place> nearbyPlacesList = this.mNearbyPlacesCache.getCache();

        this.mView.showPlaces(nearbyPlacesList);
        mView.dismissLoading();
    }

    @Override
    public void loadNearbyPlaces() {

        this.mGoogleMapsApiHelper
                .getNearbyPlaces(LocationCache.getInstance().getLocationCache(), mSearchRadius)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Place>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mView.showLoading();
                    }

                    @Override
                    public void onNext(final List<Place> nearbyPlaces) {

                        if(nearbyPlaces.isEmpty()) {
                            mView.showNearbyPlaces(nearbyPlaces);
                            return;
                        }

                        findPlaceDistanceMatrices(nearbyPlaces);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.showNearbyPlaces(new ArrayList<Place>(0));
                        mView.dismissLoading();
                    }

                    @Override
                    public void onComplete() {
                        mView.dismissLoading();
                    }
                });
    }

    @Override
    public void checkIsInThailand(Location location) {

        this.mGoogleMapsApiHelper
                .isInThailand(location)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Boolean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mView.showLoading();
                    }

                    @Override
                    public void onNext(Boolean value) {
                        mView.setIsInThailand(value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.setIsInThailand(false);
                        mView.dismissLoading();
                    }

                    @Override
                    public void onComplete() {
                        mView.dismissLoading();
                    }

                });
    }

    @Override
    public void setSearchRadius(int radius) {
        this.mSearchRadius = radius;
    }

    private void findPlaceDistanceMatrices(final List<Place> nearbyPlaces) {

        this.mGoogleMapsApiHelper
                .getDistanceToPlaces(LocationCache.getInstance().getLocationCache(), nearbyPlaces)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<DistanceMatrixRow.DistanceMatrixElement>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mView.showLoading();
                    }

                    @Override
                    public void onNext(List<DistanceMatrixRow.DistanceMatrixElement> distancesList) {

                        int distancesListCount = distancesList.size();
                        for(int index=0;index<distancesListCount;index++) {
                            mDistanceMatrixCache.putDistance(nearbyPlaces.get(index).placeId, distancesList.get(index));
                        }

                        Collections.sort(nearbyPlaces, new PlaceComparator());
                        mNearbyPlacesCache.setCache(nearbyPlaces);

                        mView.showNearbyPlaces(nearbyPlaces);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.showNearbyPlaces(nearbyPlaces);
                        mView.dismissLoading();
                    }

                    @Override
                    public void onComplete() {
                       mView.dismissLoading();
                    }
                });
    }
}
