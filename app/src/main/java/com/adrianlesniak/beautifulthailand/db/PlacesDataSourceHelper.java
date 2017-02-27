package com.adrianlesniak.beautifulthailand.db;

import android.content.ContentValues;
import android.database.Cursor;

import com.adrianlesniak.beautifulthailand.models.maps.Geometry;
import com.adrianlesniak.beautifulthailand.models.maps.Photo;
import com.adrianlesniak.beautifulthailand.models.maps.Place;

import java.util.Arrays;

/**
 * Created by adrian on 24/02/2017.
 */

public class PlacesDataSourceHelper {

    public static ContentValues getContentValuesFromPlace(Place place) {

        ContentValues values = new ContentValues();
        values.put(PlacesPersistenceContract.Place.COLUMN_NAME_PLACE_ID, place.placeId);
        values.put(PlacesPersistenceContract.Place.COLUMN_NAME_PLACE_NAME, place.name);
        values.put(PlacesPersistenceContract.Place.COLUMN_NAME_LAT_LNG, place.geometry.location.lat + "," + place.geometry.location.lng);
        values.put(PlacesPersistenceContract.Place.COLUMN_NAME_RATING, place.rating);
        values.put(PlacesPersistenceContract.Place.COLUMN_NAME_TYPES, Arrays.toString(place.types));
        values.put(PlacesPersistenceContract.Place.COLUMN_NAME_VICINITY, place.vicinity);
        values.put(PlacesPersistenceContract.Place.COLUMN_NAME_CLOSED, place.permanentlyClosed? 1 : 0);

        return values;
    }

    public static ContentValues getContentValuesFromPhoto(Photo photo, String placeId) {

        ContentValues values = new ContentValues();
        values.put(PlacesPersistenceContract.Photo.COLUMN_NAME_WIDTH, photo.width);
        values.put(PlacesPersistenceContract.Photo.COLUMN_NAME_HEIGHT, photo.height);
        values.put(PlacesPersistenceContract.Photo.COLUMN_NAME_PHOTO_REFERENCE, photo.photo_reference);
        values.put(PlacesPersistenceContract.Photo.COLUMN_NAME_PLACE_ID, placeId);

        return values;
    }

    public static Place initPlaceFromCursor(Cursor placeCursor) {

        if(placeCursor == null || placeCursor.getCount() == 0) {
            return null;
        }

        Place place = new Place();

        place.placeId = placeCursor.getString(placeCursor.getColumnIndexOrThrow(PlacesPersistenceContract.Place.COLUMN_NAME_PLACE_ID));
        place.name = placeCursor.getString(placeCursor.getColumnIndexOrThrow(PlacesPersistenceContract.Place.COLUMN_NAME_PLACE_NAME));

        // Geometry

        String latLng = placeCursor.getString(placeCursor.getColumnIndexOrThrow(PlacesPersistenceContract.Place.COLUMN_NAME_LAT_LNG));
        String[] latLngExploded = latLng.split(",");

        Geometry.LatLng location = new Geometry.LatLng();
        location.lat = Double.valueOf(latLngExploded[0]);
        location.lng = Double.valueOf(latLngExploded[1]);

        Geometry geometry = new Geometry();
        geometry.location = location;

        place.geometry = geometry;

        place.rating = placeCursor.getFloat(placeCursor.getColumnIndexOrThrow(PlacesPersistenceContract.Place.COLUMN_NAME_RATING));
        place.types = placeCursor.getString(placeCursor.getColumnIndexOrThrow(PlacesPersistenceContract.Place.COLUMN_NAME_TYPES)).split(",");
        place.vicinity = placeCursor.getString(placeCursor.getColumnIndexOrThrow(PlacesPersistenceContract.Place.COLUMN_NAME_VICINITY));
        place.permanentlyClosed = placeCursor.getInt(placeCursor.getColumnIndexOrThrow(PlacesPersistenceContract.Place.COLUMN_NAME_CLOSED)) == 1;

        return place;
    }

    public static Photo initPhotoFromCursor(Cursor photosCursor) {

        Photo photo = new Photo();

        photo.width = photosCursor.getInt(photosCursor.getColumnIndexOrThrow(PlacesPersistenceContract.Photo.COLUMN_NAME_WIDTH));
        photo.height = photosCursor.getInt(photosCursor.getColumnIndexOrThrow(PlacesPersistenceContract.Photo.COLUMN_NAME_HEIGHT));
        photo.photo_reference = photosCursor.getString(photosCursor.getColumnIndexOrThrow(PlacesPersistenceContract.Photo.COLUMN_NAME_PHOTO_REFERENCE));

        return photo;
    }
}
