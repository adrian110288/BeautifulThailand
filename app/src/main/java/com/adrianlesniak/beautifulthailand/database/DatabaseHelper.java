package com.adrianlesniak.beautifulthailand.database;

import com.adrianlesniak.beautifulthailand.models.Place;

import io.realm.Realm;

/**
 * Created by adrian on 24/01/2017.
 */
public class DatabaseHelper {
    private static DatabaseHelper sInstance = new DatabaseHelper();

    private Realm mRealmInstance;

    public static DatabaseHelper getInstance() {
        return sInstance;
    }

    private DatabaseHelper() {
        this.mRealmInstance = Realm.getDefaultInstance();
    }

    public Place setPlaceFavourite(Place place, boolean favourite) {

        this.mRealmInstance.beginTransaction();
        place.setIsFavourite(favourite);
        place = this.mRealmInstance.copyToRealmOrUpdate(place);
        this.mRealmInstance.commitTransaction();

        return place;
    }

    public Place setPlaceToVisit(Place place, boolean toVisit) {

        this.mRealmInstance.beginTransaction();
        place.setToVisit(toVisit);
        place = this.mRealmInstance.copyToRealmOrUpdate(place);
        this.mRealmInstance.commitTransaction();

        return place;
    }

    public void closeRealm() {
        this.mRealmInstance.close();
    }
}
