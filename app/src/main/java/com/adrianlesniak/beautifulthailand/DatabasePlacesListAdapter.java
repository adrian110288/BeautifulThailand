package com.adrianlesniak.beautifulthailand;

import android.support.v7.widget.RecyclerView;

import com.adrianlesniak.beautifulthailand.models.EmptyListItem;
import com.adrianlesniak.beautifulthailand.models.ListItem;
import com.adrianlesniak.beautifulthailand.models.Place;

import java.util.List;

/**
 * Created by adrian on 25/01/2017.
 */

public class DatabasePlacesListAdapter extends PlacesListAdapter {

    public interface OnPlaceListItemRemoveListener {
        void onPlaceListItemRemove(Place place);
    }

    public DatabasePlacesListAdapter(List<ListItem> dataSet, List<ListItem> emptyDataSet, OnPlaceListItemClickListener listener) {
        super(dataSet, emptyDataSet, listener);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        int viewType = holder.getItemViewType();

        if(viewType == ListItem.TYPE_EMPTY) {

            EmptyItemViewHolder emptyItemViewHolder = (EmptyItemViewHolder) holder;
            EmptyListItem emptyItemModel = (EmptyListItem) this.mEmptyDataSet.get(position);

            emptyItemViewHolder.bindData(emptyItemModel);

        } else {

            Place place = (Place) this.mDataSet.get(position);

            PlaceViewHolder placeViewHolder = (PlaceViewHolder) holder;
            placeViewHolder.setOnPlaceListItemRemoveListener(new OnPlaceListItemRemoveListener() {
                @Override
                public void onPlaceListItemRemove(Place place) {
                    DatabasePlacesListAdapter.this.notifyItemRemoved(DatabasePlacesListAdapter.this.mDataSet.indexOf(place));

                    if(DatabasePlacesListAdapter.this.mDataSet.isEmpty()) {
                        DatabasePlacesListAdapter.this.swapDataSet(DatabasePlacesListAdapter.this.mEmptyDataSet);
                    }
                }

            });
            placeViewHolder.bindData(place, this.getOnItemClickListener(place));
        }
    }
}
