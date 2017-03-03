package com.adrianlesniak.beautifulthailand.screens.favourites;

import com.adrianlesniak.beautifulthailand.db.PlacesDataSource;
import com.adrianlesniak.beautifulthailand.models.maps.Place;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

/**
 * Created by adrian on 03/03/2017.
 */

public class FavouritesPresenter implements FavouritesContract.Presenter {

    private PlacesDataSource mDataSource;

    private FavouritesContract.View mView;

    public FavouritesPresenter(@NotNull PlacesDataSource dataSource, @NotNull FavouritesContract.View view) {

        this.mDataSource = dataSource;

        this.mView = view;
    }

    @Override
    public void loadPlaces() {

        this.mDataSource
                .getFavouritePlaces()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Place>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mView.showLoading();
                    }

                    @Override
                    public void onNext(List<Place> placesList) {
                        mView.showPlaces(placesList);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.showLoading();
                    }

                    @Override
                    public void onComplete() {
                        mView.dismissLoading();
                    }
                });
    }
}
