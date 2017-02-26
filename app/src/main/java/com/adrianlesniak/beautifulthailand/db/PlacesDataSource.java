package com.adrianlesniak.beautifulthailand.db;

import com.adrianlesniak.beautifulthailand.models.maps.Photo;
import com.adrianlesniak.beautifulthailand.models.maps.Place;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;

/**
 * Created by adrian on 26/02/2017.
 */

public interface PlacesDataSource {

    Observable<List<Place>> getFavouritePlaces();

    Observable<Place> getFavouritePlaceById(@NotNull String placeId);

    Place getFavouritePlaceByIdSync(@NotNull String placeId);

    Observable<Boolean> setPlaceFavourite(@NotNull Place place, boolean isFavourite);

    Observable<List<Photo>> getPhotosForPlace(String placeId);

}
