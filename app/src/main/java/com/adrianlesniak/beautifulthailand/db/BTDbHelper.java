package com.adrianlesniak.beautifulthailand.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by adrian on 24/02/2017.
 */

public class BTDbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "BeautifulThailand.db";

    private static final String SQL_CREATE_PLACE_ENTRIES =
            "CREATE TABLE " + BTContract.Place.TABLE_NAME + " (" +
                    BTContract.Place._ID + " INTEGER PRIMARY KEY," +
                    BTContract.Place.COLUMN_NAME_PLACE_ID + " TEXT," +
                    BTContract.Place.COLUMN_NAME_PLACE_NAME + " TEXT," +
                    BTContract.Place.COLUMN_NAME_LAT_LNG + " TEXT," +
                    BTContract.Place.COLUMN_NAME_RATING + " REAL," +
                    BTContract.Place.COLUMN_NAME_TYPES + " TEXT," +
                    BTContract.Place.COLUMN_NAME_VICINITY + " TEXT," +
                    BTContract.Place.COLUMN_NAME_CLOSED + " INTEGER)";

    private static final String SQL_CREATE_PHOTO_ENTRIES =
            "CREATE TABLE " + BTContract.Photo.TABLE_NAME + " (" +
                    BTContract.Photo._ID + " INTEGER PRIMARY KEY," +
                    BTContract.Photo.COLUMN_NAME_HEIGHT + " INTEGER," +
                    BTContract.Photo.COLUMN_NAME_WIDTH + " INTEGER," +
                    BTContract.Photo.COLUMN_NAME_PHOTO_REFERENCE + " TEXT," +
                    BTContract.Photo.COLUMN_NAME_PLACE_ID + " TEXT," +
                    " FOREIGN KEY (" + BTContract.Photo.COLUMN_NAME_PLACE_ID + ") REFERENCES " + BTContract.Place.TABLE_NAME + "(" + BTContract.Place.COLUMN_NAME_PLACE_ID + "))";


    private static final String SQL_DELETE_PLACE_ENTRIES =
            "DROP TABLE IF EXISTS " + BTContract.Place.TABLE_NAME;

    private static final String SQL_DELETE_PHOTO_ENTRIES =
            "DROP TABLE IF EXISTS " + BTContract.Photo.TABLE_NAME;

    public BTDbHelper(Context context) {
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
