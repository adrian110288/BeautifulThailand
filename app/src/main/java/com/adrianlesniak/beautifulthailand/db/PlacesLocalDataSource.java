package com.adrianlesniak.beautifulthailand.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.adrianlesniak.beautifulthailand.models.NullableObject;
import com.adrianlesniak.beautifulthailand.models.maps.Photo;
import com.adrianlesniak.beautifulthailand.models.maps.Place;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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

                    try {

                        int cursorLen = allPlaceCursor.getCount();
                        for(int index=0; index<cursorLen; index++) {

                            if(allPlaceCursor.moveToPosition(index)) {

                                NullableObject nullablePlace = PlacesDataSourceHelper.initPlaceFromCursor(allPlaceCursor);

                                if(!nullablePlace.isNull()) {

                                    Place place  = (Place) nullablePlace;
                                    place.photos = getPhotosForPlaceSync(db, place.placeId);
                                    placesList.add(place);
                                }
                            }
                        }

                    } finally {

                        allPlaceCursor.close();
                    }
                }

                emitter.onNext(placesList);
                emitter.onComplete();
            }
        });
    }

    @Override
    public Observable<NullableObject> getFavouritePlaceById(@NotNull final String placeId) {

        return Observable.create(new ObservableOnSubscribe<NullableObject>() {
            @Override
            public void subscribe(final ObservableEmitter<NullableObject> emitter) throws Exception {

                SQLiteDatabase db = mDbHelper.getReadableDatabase();

                NullableObject place = getFavouritePlaceByIdSync(db, placeId);

                emitter.onNext(place);
                emitter.onComplete();
            }
        });
    }

    private NullableObject getFavouritePlaceByIdSync(@NotNull final SQLiteDatabase db, @NotNull String placeId) {

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

        NullableObject place;

        Cursor placeCursor = db.query(PlacesPersistenceContract.Place.TABLE_NAME, placeProjection, placeSelection, placeSelectionArgs, null, null, null, null);

        try {
            placeCursor.moveToFirst();
            place = PlacesDataSourceHelper.initPlaceFromCursor(placeCursor);

        } finally {

            placeCursor.close();
        }

        if(!place.isNull()) {
            ((Place)place).photos = getPhotosForPlaceSync(db, placeId);
        }

        return place;
    }

    @Override
    public Observable<Boolean> setPlaceFavourite(@NotNull final Place place, final boolean shouldBeFavourite) {

        return Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(ObservableEmitter<Boolean> emitter) throws Exception {

                SQLiteDatabase db = mDbHelper.getWritableDatabase();

                boolean jobDone = shouldBeFavourite? favouritePlaceSync(db, place) : unfavouritePlaceSync(db, place);

                emitter.onNext(jobDone ? shouldBeFavourite : !shouldBeFavourite);
                emitter.onComplete();
            }
        });
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

        List<Photo> photosList = new ArrayList<>();

        if(injectedDb == null) {
            return photosList;
        }

        String[] photoProjection = {
                PlacesPersistenceContract.Photo.COLUMN_NAME_HEIGHT,
                PlacesPersistenceContract.Photo.COLUMN_NAME_WIDTH,
                PlacesPersistenceContract.Photo.COLUMN_NAME_PHOTO_REFERENCE
        };

        String photoSelection = PlacesPersistenceContract.Photo.COLUMN_NAME_PLACE_ID + " = ?";
        String[] photoSelectionArgs = { placeId };

        injectedDb.beginTransaction();

        Cursor photosCursor = injectedDb.query(PlacesPersistenceContract.Photo.TABLE_NAME, photoProjection, photoSelection, photoSelectionArgs, null, null, null, null);

        try {

            int cursorLen = photosCursor.getCount();
            for(int index=0;index<cursorLen;index++) {

                if(photosCursor.moveToPosition(index)) {
                    Photo photo = PlacesDataSourceHelper.initPhotoFromCursor(photosCursor);
                    photosList.add(photo);
                }
            }

        } finally {

            photosCursor.close();
        }

        injectedDb.setTransactionSuccessful();
        injectedDb.endTransaction();

        return photosList;
    }

    private boolean insertPhotosForPlace(@NotNull final SQLiteDatabase db, @NotNull String placeId, @Nullable List<Photo> photosList) {

        if(photosList == null || photosList.isEmpty()){
            return true;
        }

        int insertedPhotos = 0;

        int photosListLen = photosList.size();
        for(int index=0; index<photosListLen; index++) {
            ContentValues photoValues = PlacesDataSourceHelper.getContentValuesFromPhoto(photosList.get(index), placeId);
            long rowId = db.insert(PlacesPersistenceContract.Photo.TABLE_NAME, null, photoValues);

            if(rowId != -1) {
                insertedPhotos+=1;
            }
        }

        return insertedPhotos == photosListLen;
    }

    private boolean favouritePlaceSync(@NotNull final SQLiteDatabase db, @NotNull final Place place) {

        NullableObject existingPlace = getFavouritePlaceByIdSync(db, place.placeId);

        if(!existingPlace.isNull()) {
            return true;
        }

        db.beginTransaction();

        ContentValues placeValues = PlacesDataSourceHelper.getContentValuesFromPlace(place);
        long rowId = db.insert(PlacesPersistenceContract.Place.TABLE_NAME, null, placeValues);

        boolean success = rowId > -1 && insertPhotosForPlace(db, place.placeId, place.photos);

        if(success) {
            db.setTransactionSuccessful();
        }
        db.endTransaction();

        return success;
    }

    private boolean unfavouritePlaceSync(@NotNull final SQLiteDatabase db, @NotNull final Place place) {

        db.beginTransaction();

        String placeSelection = PlacesPersistenceContract.Place.COLUMN_NAME_PLACE_ID + " LIKE ?";
        String[] placeSelectionArgs = { place.placeId };

        int placeRowsDeleted = db.delete(PlacesPersistenceContract.Place.TABLE_NAME, placeSelection, placeSelectionArgs);

        boolean success = placeRowsDeleted > 0 && deletePhotosForPlaceSync(db, place);

        if(success) {
            db.setTransactionSuccessful();
        }

        db.endTransaction();

        return success;
    }

    private boolean deletePhotosForPlaceSync(@NotNull final SQLiteDatabase db, Place place) {

        String photoSelection = PlacesPersistenceContract.Photo.COLUMN_NAME_PLACE_ID + " LIKE ?";
        String[] photoSelectionArgs = { place.placeId };

        db.beginTransaction();

        int rowsDeleted = db.delete(PlacesPersistenceContract.Photo.TABLE_NAME, photoSelection, photoSelectionArgs);

        boolean success = place.photos == null || place.photos.isEmpty() || rowsDeleted == place.photos.size();

        if(success) {
            db.setTransactionSuccessful();
        }

        db.endTransaction();

        return success;
    }

    public void closeDataSource() {
        mDbHelper.close();
    }
}
