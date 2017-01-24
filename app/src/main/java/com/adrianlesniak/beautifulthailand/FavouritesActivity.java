package com.adrianlesniak.beautifulthailand;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.adrianlesniak.beautifulthailand.models.EmptyItemModel;
import com.adrianlesniak.beautifulthailand.models.ListModel;
import com.adrianlesniak.beautifulthailand.models.Place;
import com.android.internal.util.Predicate;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmQuery;

public class FavouritesActivity extends SavedPlacesActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected RealmQuery getQuery() {
        return this.mRealmInstance.where(Place.class).equalTo("isFavourite", true);
    }

    @Override
    protected List<ListModel> getEmptyItemList() {

        List<ListModel> emptyItemList = new ArrayList<>(1);
        emptyItemList.add(new EmptyItemModel(R.drawable.ic_empty, getResources().getString(R.string.empty_list_favourite)));

        return emptyItemList;
    }
}
