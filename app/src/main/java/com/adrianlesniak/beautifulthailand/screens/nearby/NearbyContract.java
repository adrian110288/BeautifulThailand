package com.adrianlesniak.beautifulthailand.screens.nearby;

import android.location.Location;

import com.adrianlesniak.beautifulthailand.models.maps.Place;
import com.adrianlesniak.beautifulthailand.mvp.BasePresenter;
import com.adrianlesniak.beautifulthailand.mvp.BaseView;

import java.util.List;

/**
 * Created by adrian on 03/03/2017.
 */

public interface NearbyContract {

    interface View extends BaseView {

        void showPlaces(List<Place> places);

        void showNearbyPlaces(List<Place> places);

        void showLoading();

        void dismissLoading();

        void setIsInThailand(boolean isInThailand);
    }

    interface Presenter extends BasePresenter {

        void loadPlaces();

        void loadNearbyPlaces();

        void checkIsInThailand(Location location);

        void setSearchRadius(int radius);
    }
}
