package com.adrianlesniak.beautifulthailand.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteTransactionListener;

import com.adrianlesniak.beautifulthailand.models.maps.Geometry;
import com.adrianlesniak.beautifulthailand.models.maps.Photo;
import com.adrianlesniak.beautifulthailand.models.maps.Place;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by adrian on 24/02/2017.
 */

public class BTDbAccessor {

    private static BTDbAccessor sInstance;

    private BTDbHelper mDbHelper;

    private BTDbAccessor(Context context) {
        this.mDbHelper = new BTDbHelper(context);
    }

    public static BTDbAccessor getInstance(Context context) {

        if(sInstance == null) {
            sInstance = new BTDbAccessor(context);
        }

        return sInstance;
    }

    public boolean putPlace(Place place) {

        SQLiteDatabase db = this.mDbHelper.getWritableDatabase();

        db.beginTransaction();

        ContentValues placeValues = BTContentValuesHelper.getContentValuesFromPlace(place);
        long rowId = db.insert(BTContract.Place.TABLE_NAME, null, placeValues);

        for (Photo photo :
                place.photos) {

            ContentValues photoValues = BTContentValuesHelper.getContentValuesFromPhoto(photo, place);

            try {

                db.insertOrThrow(BTContract.Photo.TABLE_NAME, null, photoValues);

            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        }

        db.setTransactionSuccessful();
        db.endTransaction();

        return rowId > -1;
    }

    public boolean removePlace(String placeId) {

        SQLiteDatabase db = this.mDbHelper.getWritableDatabase();

        db.beginTransaction();

        String placeSelection = BTContract.Place.COLUMN_NAME_PLACE_ID + " LIKE ?";
        String[] placeSelectionArgs = { placeId };
        int placeRowsDeleted = db.delete(BTContract.Place.TABLE_NAME, placeSelection, placeSelectionArgs);

        String photoSelection = BTContract.Photo.COLUMN_NAME_PLACE_ID + " LIKE ?";
        String[] photoSelectionArgs = { placeId };
        db.delete(BTContract.Photo.TABLE_NAME, photoSelection, photoSelectionArgs);

        db.setTransactionSuccessful();
        db.endTransaction();

        return placeRowsDeleted > 0;
    }

    public List<Place> getAllPlaces() {

        SQLiteDatabase db = this.mDbHelper.getReadableDatabase();

        List<Place> places;

        String[] placeProjection = {
            BTContract.Place.COLUMN_NAME_PLACE_ID,
            BTContract.Place.COLUMN_NAME_PLACE_NAME,
            BTContract.Place.COLUMN_NAME_LAT_LNG,
            BTContract.Place.COLUMN_NAME_RATING,
            BTContract.Place.COLUMN_NAME_TYPES,
            BTContract.Place.COLUMN_NAME_VICINITY,
            BTContract.Place.COLUMN_NAME_CLOSED
        };

        String[] photoProjection = {
                BTContract.Photo.COLUMN_NAME_HEIGHT,
                BTContract.Photo.COLUMN_NAME_WIDTH,
                BTContract.Photo.COLUMN_NAME_PHOTO_REFERENCE
        };

        String photoSelection = BTContract.Photo.COLUMN_NAME_PLACE_ID + " = ?";
        String[] photoSelectionArgs = new String[1];

        db.beginTransaction();

        Cursor placesCursor = db.query(BTContract.Place.TABLE_NAME, placeProjection, null, null, null, null, null, null);
        places = new ArrayList<>(placesCursor.getCount());

        try {

            while(placesCursor.moveToNext()) {
                Place place = new Place();
                place.placeId = placesCursor.getString(placesCursor.getColumnIndexOrThrow(BTContract.Place.COLUMN_NAME_PLACE_ID));
                place.name = placesCursor.getString(placesCursor.getColumnIndexOrThrow(BTContract.Place.COLUMN_NAME_PLACE_NAME));

                // Geometry

                String latLng = placesCursor.getString(placesCursor.getColumnIndexOrThrow(BTContract.Place.COLUMN_NAME_LAT_LNG));
                String[] latLngExploded = latLng.split(",");

                Geometry.LatLng location = new Geometry.LatLng();
                location.lat = Double.valueOf(latLngExploded[0]);
                location.lng = Double.valueOf(latLngExploded[1]);

                Geometry geometry = new Geometry();
                geometry.location = location;

                place.geometry = geometry;
                place.rating = placesCursor.getFloat(placesCursor.getColumnIndexOrThrow(BTContract.Place.COLUMN_NAME_RATING));
                place.types = placesCursor.getString(placesCursor.getColumnIndexOrThrow(BTContract.Place.COLUMN_NAME_TYPES)).split(",");
                place.vicinity = placesCursor.getString(placesCursor.getColumnIndexOrThrow(BTContract.Place.COLUMN_NAME_VICINITY));
                place.permanentlyClosed = placesCursor.getInt(placesCursor.getColumnIndexOrThrow(BTContract.Place.COLUMN_NAME_CLOSED)) == 1;

                photoSelectionArgs[0] = place.placeId;

                Cursor photosCursor = db.query(BTContract.Photo.TABLE_NAME, photoProjection, photoSelection, photoSelectionArgs, null, null, null, null);
                List<Photo> photosList = new ArrayList<>(photosCursor.getCount());

                try {

                    while(photosCursor.moveToNext()) {
                        Photo photo = new Photo();

                        photo.width = photosCursor.getInt(photosCursor.getColumnIndexOrThrow(BTContract.Photo.COLUMN_NAME_WIDTH));
                        photo.height = photosCursor.getInt(photosCursor.getColumnIndexOrThrow(BTContract.Photo.COLUMN_NAME_HEIGHT));
                        photo.photo_reference = photosCursor.getString(photosCursor.getColumnIndexOrThrow(BTContract.Photo.COLUMN_NAME_PHOTO_REFERENCE));

                        photosList.add(photo);
                    }

                } finally {

                    photosCursor.close();
                }

                Photo[] photos = new Photo[photosList.size()];
                place.photos = photosList.toArray(photos);

                places.add(place);
            }

        } finally {

            placesCursor.close();
        }

        db.setTransactionSuccessful();
        db.endTransaction();

        return places;
    }

    public void closeDb() {
        mDbHelper.close();
    }
}
