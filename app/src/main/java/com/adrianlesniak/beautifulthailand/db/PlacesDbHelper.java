package com.adrianlesniak.beautifulthailand.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by adrian on 24/02/2017.
 */

public class PlacesDbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "BeautifulThailand.db";

    private static final String SQL_CREATE_PLACE_ENTRIES =
            "CREATE TABLE " + PlacesPersistenceContract.Place.TABLE_NAME + " (" +
                    PlacesPersistenceContract.Place.COLUMN_NAME_PLACE_ID + " TEXT PRIMARY KEY," +
                    PlacesPersistenceContract.Place.COLUMN_NAME_PLACE_NAME + " TEXT," +
                    PlacesPersistenceContract.Place.COLUMN_NAME_LAT_LNG + " TEXT," +
                    PlacesPersistenceContract.Place.COLUMN_NAME_RATING + " REAL," +
                    PlacesPersistenceContract.Place.COLUMN_NAME_TYPES + " TEXT," +
                    PlacesPersistenceContract.Place.COLUMN_NAME_VICINITY + " TEXT," +
                    PlacesPersistenceContract.Place.COLUMN_NAME_CLOSED + " INTEGER)";

    private static final String SQL_CREATE_PHOTO_ENTRIES =
            "CREATE TABLE " + PlacesPersistenceContract.Photo.TABLE_NAME + " (" +
                    PlacesPersistenceContract.Photo.COLUMN_NAME_PHOTO_REFERENCE + " TEXT PRIMARY KEY," +
                    PlacesPersistenceContract.Photo.COLUMN_NAME_HEIGHT + " INTEGER," +
                    PlacesPersistenceContract.Photo.COLUMN_NAME_WIDTH + " INTEGER," +
                    PlacesPersistenceContract.Photo.COLUMN_NAME_PLACE_ID + " TEXT," +
                    " FOREIGN KEY (" + PlacesPersistenceContract.Photo.COLUMN_NAME_PLACE_ID + ") REFERENCES " + PlacesPersistenceContract.Place.TABLE_NAME + "(" + PlacesPersistenceContract.Place.COLUMN_NAME_PLACE_ID + "))";


    private static final String SQL_DELETE_PLACE_ENTRIES =
            "DROP TABLE IF EXISTS " + PlacesPersistenceContract.Place.TABLE_NAME;

    private static final String SQL_DELETE_PHOTO_ENTRIES =
            "DROP TABLE IF EXISTS " + PlacesPersistenceContract.Photo.TABLE_NAME;

    public PlacesDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_PLACE_ENTRIES);
        db.execSQL(SQL_CREATE_PHOTO_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_PLACE_ENTRIES);
        db.execSQL(SQL_DELETE_PHOTO_ENTRIES);
        onCreate(db);
    }
}
