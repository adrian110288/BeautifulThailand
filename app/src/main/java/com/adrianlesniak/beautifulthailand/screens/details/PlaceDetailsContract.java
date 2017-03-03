package com.adrianlesniak.beautifulthailand.screens.details;

import com.adrianlesniak.beautifulthailand.models.maps.PlaceDetails;
import com.adrianlesniak.beautifulthailand.mvp.BasePresenter;
import com.adrianlesniak.beautifulthailand.mvp.BaseView;

/**
 * Created by adrian on 03/03/2017.
 */

public interface PlaceDetailsContract {

    interface View extends BaseView {

        void showPlaceDetails(PlaceDetails placeDetails);

        void setPlaceFavourite(boolean isFavourite);
    }

    interface Presenter extends BasePresenter {

        void loadPlaceDetails(String placeId);

        void checkIsPlaceFavourite(String placeId);
    }
}
