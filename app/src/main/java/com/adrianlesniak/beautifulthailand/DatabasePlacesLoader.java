package com.adrianlesniak.beautifulthailand;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import com.adrianlesniak.beautifulthailand.models.ListModel;

import java.util.List;

import io.realm.RealmQuery;

/**
 * Created by adrian on 23/01/2017.
 */

public class DatabasePlacesLoader extends AsyncTaskLoader<List<ListModel>> {

    private RealmQuery mQuery;

    public DatabasePlacesLoader(Context context, RealmQuery query) {
        super(context);
        this.mQuery = query;
    }

    @Override
    public List<ListModel> loadInBackground() {
        return this.mQuery.findAll();
    }
}
