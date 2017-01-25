package com.adrianlesniak.beautifulthailand;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.adrianlesniak.beautifulthailand.models.EmptyListItem;
import com.adrianlesniak.beautifulthailand.models.ListItem;
import com.adrianlesniak.beautifulthailand.models.Place;

import java.util.ArrayList;
import java.util.List;

import io.realm.RealmQuery;

public class FavouritesActivity extends DatabasePlacesActivity {

    @Override
    protected RealmQuery getQuery() {
        return this.mRealmInstance.where(Place.class).equalTo("isFavourite", true);
    }

    @Override
    protected List<ListItem> getEmptyItemList() {

        List<ListItem> emptyItemList = new ArrayList<>(1);
        emptyItemList.add(new EmptyListItem(R.drawable.ic_empty, getResources().getString(R.string.empty_list_favourite)));

        return emptyItemList;
    }
}
