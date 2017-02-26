package com.adrianlesniak.beautifulthailand.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.Nullable;

import com.adrianlesniak.beautifulthailand.models.maps.Geometry;
import com.adrianlesniak.beautifulthailand.models.maps.Photo;
import com.adrianlesniak.beautifulthailand.models.maps.Place;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

/**
 * Created by adrian on 24/02/2017.
 */

public class PlacesLocalDataSource implements PlacesDataSource{

    private static PlacesLocalDataSource sInstance;

    private PlacesDbHelper mDbHelper;

    private PlacesLocalDataSource(Context context) {
        this.mDbHelper = new PlacesDbHelper(context);
    }

    public static PlacesLocalDataSource getInstance(Context context) {

        if(sInstance == null) {
            sInstance = new PlacesLocalDataSource(context);
        }

        return sInstance;
    }

    @Override
    public Observable<List<Place>> getFavouritePlaces() {
        return Observable.create(new ObservableOnSubscribe<List<Place>>() {
            @Override
            public void subscribe(final ObservableEmitter<List<Place>> emitter) throws Exception {

                SQLiteDatabase db = mDbHelper.getReadableDatabase();

                String[] placesProjection = {
                        PlacesPersistenceContract.Place.COLUMN_NAME_PLACE_ID,
                        PlacesPersistenceContract.Place.COLUMN_NAME_PLACE_NAME,
                        PlacesPersistenceContract.Place.COLUMN_NAME_LAT_LNG,
                        PlacesPersistenceContract.Place.COLUMN_NAME_RATING,
                        PlacesPersistenceContract.Place.COLUMN_NAME_TYPES,
                        PlacesPersistenceContract.Place.COLUMN_NAME_VICINITY,
                        PlacesPersistenceContract.Place.COLUMN_NAME_CLOSED
                };

                Cursor allPlaceCursor = db.query(PlacesPersistenceContract.Place.TABLE_NAME, placesProjection, null, null, null, null, null, null);
                List<Place> placesList = new ArrayList<>();

                if(allPlaceCursor != null) {

                    while (allPlaceCursor.moveToNext()) {
                        Place place = PlacesDataSourceHelper.initPlaceFromCursor(allPlaceCursor);
                        placesList.add(place);
                    }
                }

                emitter.onNext(placesList);
                emitter.onComplete();
            }
        });
    }

    @Override
    public Observable<Place> getFavouritePlaceById(@NotNull final String placeId) {

        return Observable.create(new ObservableOnSubscribe<Place>() {
            @Override
            public void subscribe(final ObservableEmitter<Place> emitter) throws Exception {

                Place place = getFavouritePlaceByIdSync(placeId);

                if(place == null) {
                    emitter.onError(new Throwable("Place id is not favourite"));

                } else {
                    emitter.onNext(place);
                }

                emitter.onComplete();
            }
        });
    }

    @Override
    public Place getFavouritePlaceByIdSync(@NotNull String placeId) {

        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String[] placeProjection = {
                PlacesPersistenceContract.Place.COLUMN_NAME_PLACE_ID,
                PlacesPersistenceContract.Place.COLUMN_NAME_PLACE_NAME,
                PlacesPersistenceContract.Place.COLUMN_NAME_LAT_LNG,
                PlacesPersistenceContract.Place.COLUMN_NAME_RATING,
                PlacesPersistenceContract.Place.COLUMN_NAME_TYPES,
                PlacesPersistenceContract.Place.COLUMN_NAME_VICINITY,
                PlacesPersistenceContract.Place.COLUMN_NAME_CLOSED
        };

        String placeSelection = PlacesPersistenceContract.Place.COLUMN_NAME_PLACE_ID + " = ?";
        String[] placeSelectionArgs = { placeId };

        Cursor placeCursor = db.query(PlacesPersistenceContract.Place.TABLE_NAME, placeProjection, placeSelection, placeSelectionArgs, null, null, null, null);

        Place place = PlacesDataSourceHelper.initPlaceFromCursor(placeCursor);
        placeCursor.close();

        if(place != null) {
            place.photos = getPhotosForPlaceSync(db, placeId);
        }

        return place;
    }

    @Override
    public Observable<Boolean> setPlaceFavourite(@NotNull final Place place, final boolean isFavourite) {

        return Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(ObservableEmitter<Boolean> emitter) throws Exception {

                boolean jobDone = isFavourite? favouritePlaceSync(place) : unfavouritePlaceSync(place);

                emitter.onNext(jobDone ? isFavourite : !isFavourite);
                emitter.onComplete();
            }
        });
    }

    private boolean favouritePlaceSync(@NotNull final Place place) {

        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        db.beginTransaction();

        ContentValues placeValues = PlacesDataSourceHelper.getContentValuesFromPlace(place);
        long rowId = db.insert(PlacesPersistenceContract.Place.TABLE_NAME, null, placeValues);

        if(rowId == -1) {
            return false;
        }

        for (Photo photo :
                place.photos) {

            ContentValues photoValues = PlacesDataSourceHelper.getContentValuesFromPhoto(photo, place);
            db.insert(PlacesPersistenceContract.Photo.TABLE_NAME, null, photoValues);
        }

        db.setTransactionSuccessful();
        db.endTransaction();

        return rowId > -1;
    }

    private boolean unfavouritePlaceSync(@NotNull final Place place) {

        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        db.beginTransaction();

        String placeSelection = PlacesPersistenceContract.Place.COLUMN_NAME_PLACE_ID + " LIKE ?";
        String[] placeSelectionArgs = { place.placeId };
        int placeRowsDeleted = db.delete(PlacesPersistenceContract.Place.TABLE_NAME, placeSelection, placeSelectionArgs);

        if(placeRowsDeleted > 0) {
            String photoSelection = PlacesPersistenceContract.Photo.COLUMN_NAME_PLACE_ID + " LIKE ?";
            String[] photoSelectionArgs = { place.placeId };
            db.delete(PlacesPersistenceContract.Photo.TABLE_NAME, photoSelection, photoSelectionArgs);
        }

        db.setTransactionSuccessful();
        db.endTransaction();

        return placeRowsDeleted > 0;
    }

    @Override
    public Observable<List<Photo>> getPhotosForPlace(final String place) {

        return Observable.create(new ObservableOnSubscribe<List<Photo>>() {
            @Override
            public void subscribe(ObservableEmitter<List<Photo>> emitter) throws Exception {

                SQLiteDatabase db = mDbHelper.getReadableDatabase();
                List<Photo> photosList = getPhotosForPlaceSync(db, place);

                emitter.onNext(photosList);
                emitter.onComplete();
            }
        });
    }

    private List<Photo> getPhotosForPlaceSync(SQLiteDatabase injectedDb, String placeId) {

        if(injectedDb == null) {
            return new ArrayList<>(0);
        }

        String[] photoProjection = {
                PlacesPersistenceContract.Photo.COLUMN_NAME_HEIGHT,
                PlacesPersistenceContract.Photo.COLUMN_NAME_WIDTH,
                PlacesPersistenceContract.Photo.COLUMN_NAME_PHOTO_REFERENCE
        };

        String photoSelection = PlacesPersistenceContract.Photo.COLUMN_NAME_PLACE_ID + " = ?";
        String[] photoSelectionArgs = { placeId };

        Cursor photosCursor = injectedDb.query(PlacesPersistenceContract.Photo.TABLE_NAME, photoProjection, photoSelection, photoSelectionArgs, null, null, null, null);
        List<Photo> photosList = new ArrayList<>(photosCursor.getCount());

        try {

            while (photosCursor.moveToNext()) {

                Photo photo = new Photo();

                photo.width = photosCursor.getInt(photosCursor.getColumnIndexOrThrow(PlacesPersistenceContract.Photo.COLUMN_NAME_WIDTH));
                photo.height = photosCursor.getInt(photosCursor.getColumnIndexOrThrow(PlacesPersistenceContract.Photo.COLUMN_NAME_HEIGHT));
                photo.photo_reference = photosCursor.getString(photosCursor.getColumnIndexOrThrow(PlacesPersistenceContract.Photo.COLUMN_NAME_PHOTO_REFERENCE));

                photosList.add(photo);
            }

        } finally {

            photosCursor.close();
        }

        return photosList;
    }

    public void closeDataSource() {
        mDbHelper.close();
    }
}
