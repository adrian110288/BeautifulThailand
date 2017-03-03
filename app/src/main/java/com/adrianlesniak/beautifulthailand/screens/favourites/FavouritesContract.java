package com.adrianlesniak.beautifulthailand.screens.favourites;

import com.adrianlesniak.beautifulthailand.models.maps.Place;
import com.adrianlesniak.beautifulthailand.mvp.BasePresenter;
import com.adrianlesniak.beautifulthailand.mvp.BaseView;

import java.util.List;

/**
 * Created by adrian on 03/03/2017.
 */

public interface FavouritesContract {

    interface View extends BaseView {

        void showPlaces(List<Place> places);

        void showLoading();

        void dismissLoading();
    }

    interface Presenter extends BasePresenter {

        void loadPlaces();
    }
}
