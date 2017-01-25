package com.adrianlesniak.beautifulthailand;

import com.adrianlesniak.beautifulthailand.models.EmptyListItem;
import com.adrianlesniak.beautifulthailand.models.ListItem;
import com.adrianlesniak.beautifulthailand.models.Place;

import java.util.ArrayList;
import java.util.List;

import io.realm.RealmQuery;

/**
 * Created by adrian on 23/01/2017.
 */

public class ToVisitActivity extends DatabasePlacesActivity {

    @Override
    protected RealmQuery getQuery() {
        return this.mRealmInstance.where(Place.class).equalTo("toVisit", true);
    }

    @Override
    protected List<ListItem> getEmptyItemList() {

        List<ListItem> emptyItemList = new ArrayList<>(1);
        emptyItemList.add(new EmptyListItem(R.drawable.ic_settings, "Nothing here"));

        return emptyItemList;
    }
}
