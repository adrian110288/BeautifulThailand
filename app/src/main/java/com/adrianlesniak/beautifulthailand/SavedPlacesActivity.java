package com.adrianlesniak.beautifulthailand;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.adrianlesniak.beautifulthailand.models.ListModel;
import com.adrianlesniak.beautifulthailand.models.Place;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmQuery;

/**
 * Created by adrian on 23/01/2017.
 */

public abstract class SavedPlacesActivity extends CloseableActivity implements LoaderManager.LoaderCallbacks<List<ListModel>>, PlacesListAdapter.OnPlaceListItemClickListener {

    protected RecyclerView mPlacesList;

    protected PlacesListAdapter mAdapter;

    protected Realm mRealmInstance;

    protected RealmQuery mQuery;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_places);

        this.setup();
    }

    @Override
    protected void setup() {
        super.setup();

        this.mRealmInstance = Realm.getDefaultInstance();

        this.mPlacesList = (RecyclerView) this.findViewById(R.id.places_list);
        this.mPlacesList.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));

        List<ListModel> emptyItemList = this.getEmptyItemList();
        this.mAdapter = new PlacesListAdapter(emptyItemList, this);
        this.mPlacesList.setAdapter(this.mAdapter);

        this.mQuery = this.getQuery();

        this.getSupportLoaderManager().initLoader(1, null, this).forceLoad();
    }

    protected abstract RealmQuery getQuery();

    protected abstract List<ListModel> getEmptyItemList();

    @Override
    protected void onDestroy() {
        super.onDestroy();

        this.mRealmInstance.close();
    }

    @Override
    public void onPlaceListItemClicked(Place place) {

    }

    @Override
    public Loader<List<ListModel>> onCreateLoader(int id, Bundle args) {
        return new DatabasePlacesLoader(this, this.mQuery);
    }

    @Override
    public void onLoadFinished(Loader<List<ListModel>> loader, List<ListModel> data) {

        if(data.size() != 0) {
            this.mAdapter.swapDataSet(data);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<ListModel>> loader) {

    }
}
