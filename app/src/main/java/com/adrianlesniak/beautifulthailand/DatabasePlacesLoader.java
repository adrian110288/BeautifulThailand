package com.adrianlesniak.beautifulthailand;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import com.adrianlesniak.beautifulthailand.models.ListItem;

import java.util.List;

import io.realm.RealmQuery;

/**
 * Created by adrian on 23/01/2017.
 */

public class DatabasePlacesLoader extends AsyncTaskLoader<List<ListItem>> {

    private RealmQuery mQuery;

    public DatabasePlacesLoader(Context context, RealmQuery query) {
        super(context);
        this.mQuery = query;
    }

    @Override
    public List<ListItem> loadInBackground() {
        return this.mQuery.findAll();
    }
}
