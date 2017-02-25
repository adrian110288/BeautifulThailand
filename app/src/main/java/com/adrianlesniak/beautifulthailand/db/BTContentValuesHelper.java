package com.adrianlesniak.beautifulthailand.db;

import android.content.ContentValues;

import com.adrianlesniak.beautifulthailand.models.maps.Photo;
import com.adrianlesniak.beautifulthailand.models.maps.Place;

import java.util.Arrays;

/**
 * Created by adrian on 24/02/2017.
 */

public class BTContentValuesHelper {

    public static ContentValues getContentValuesFromPlace(Place place) {

        ContentValues values = new ContentValues();
        values.put(BTContract.Place.COLUMN_NAME_PLACE_ID, place.placeId);
        values.put(BTContract.Place.COLUMN_NAME_PLACE_NAME, place.name);
        values.put(BTContract.Place.COLUMN_NAME_LAT_LNG, place.geometry.location.lat + "," + place.geometry.location.lng);
        values.put(BTContract.Place.COLUMN_NAME_RATING, place.rating);
        values.put(BTContract.Place.COLUMN_NAME_TYPES, Arrays.toString(place.types));
        values.put(BTContract.Place.COLUMN_NAME_VICINITY, place.vicinity);
        values.put(BTContract.Place.COLUMN_NAME_CLOSED, place.permanentlyClosed? 1 : 0);

        return values;
    }

    public static ContentValues getContentValuesFromPhoto(Photo photo, Place place) {

        ContentValues values = new ContentValues();
        values.put(BTContract.Photo.COLUMN_NAME_WIDTH, photo.width);
        values.put(BTContract.Photo.COLUMN_NAME_HEIGHT, photo.height);
        values.put(BTContract.Photo.COLUMN_NAME_PHOTO_REFERENCE, photo.photo_reference);
        values.put(BTContract.Photo.COLUMN_NAME_PLACE_ID, place.placeId);

        return values;
    }
}
