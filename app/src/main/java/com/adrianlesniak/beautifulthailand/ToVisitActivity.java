package com.adrianlesniak.beautifulthailand;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;

import com.adrianlesniak.beautifulthailand.models.EmptyItemModel;
import com.adrianlesniak.beautifulthailand.models.ListModel;
import com.adrianlesniak.beautifulthailand.models.Place;

import java.util.ArrayList;
import java.util.List;

import io.realm.RealmQuery;

/**
 * Created by adrian on 23/01/2017.
 */

public class ToVisitActivity extends SavedPlacesActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected RealmQuery getQuery() {
        return this.mRealmInstance.where(Place.class).equalTo("toVisit", true);
    }

    @Override
    protected List<ListModel> getEmptyItemList() {

        List<ListModel> emptyItemList = new ArrayList<>(1);
        emptyItemList.add(new EmptyItemModel(R.drawable.ic_settings, "Nothing here"));

        return emptyItemList;
    }
}
