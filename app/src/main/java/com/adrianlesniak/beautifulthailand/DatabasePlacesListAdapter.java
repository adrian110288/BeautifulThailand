package com.adrianlesniak.beautifulthailand;

import android.support.v7.widget.RecyclerView;

import com.adrianlesniak.beautifulthailand.database.DatabaseHelper;
import com.adrianlesniak.beautifulthailand.models.EmptyListItem;
import com.adrianlesniak.beautifulthailand.models.ListItem;
import com.adrianlesniak.beautifulthailand.models.Place;

import java.util.List;

/**
 * Created by adrian on 25/01/2017.
 */

public class DatabasePlacesListAdapter extends PlacesListAdapter {

    public DatabasePlacesListAdapter(List<ListItem> dataSet, OnPlaceListItemClickListener listener) {
        super(dataSet, listener);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        int viewType = holder.getItemViewType();

        if(viewType == ListItem.TYPE_EMPTY) {

            EmptyItemViewHolder emptyItemViewHolder = (EmptyItemViewHolder) holder;
            EmptyListItem emptyItemModel = (EmptyListItem) this.mDataSet.get(position);

            emptyItemViewHolder.bindData(emptyItemModel);

        } else {

            Place place = (Place) this.mDataSet.get(position);
            ((PlaceViewHolder) holder).bindData(place, this.getOnItemClickListener(place));
        }
    }
}
