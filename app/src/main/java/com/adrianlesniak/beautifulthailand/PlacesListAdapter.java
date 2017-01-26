package com.adrianlesniak.beautifulthailand;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.adrianlesniak.beautifulthailand.models.EmptyListItem;
import com.adrianlesniak.beautifulthailand.models.ListItem;
import com.adrianlesniak.beautifulthailand.models.Place;

import java.util.List;

import io.realm.Realm;

/**
 * Created by adrian on 21/01/2017.
 */

public class PlacesListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public interface OnPlaceListItemClickListener {
        void onPlaceListItemClicked(Place place);
    }

    protected List<ListItem> mDataSet;

    protected List<ListItem> mEmptyDataSet;

    protected OnPlaceListItemClickListener mOnPlaceListItemClickListener;

    private Realm mRealmInstance;

    public PlacesListAdapter(List<ListItem> dataSet, List<ListItem> emptyDataSet,  OnPlaceListItemClickListener listener) {
        this.mDataSet = dataSet;
        this.mEmptyDataSet = emptyDataSet;
        this.mOnPlaceListItemClickListener = listener;
        this.mRealmInstance = Realm.getDefaultInstance();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        int layout = viewType == ListItem.TYPE_PLACE? R.layout.place_list_item : R.layout.empty_list_item;
        View view = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);

        return viewType == ListItem.TYPE_PLACE ? new PlaceViewHolder(view) : new EmptyItemViewHolder(view);
    }

    @Override
    public int getItemViewType(int position) {
        return this.mDataSet == null? ListItem.TYPE_EMPTY : this.mDataSet.get(position) instanceof Place ? ListItem.TYPE_PLACE : ListItem.TYPE_EMPTY;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        int viewType = holder.getItemViewType();

        if(viewType == ListItem.TYPE_EMPTY) {

            EmptyItemViewHolder emptyItemViewHolder = (EmptyItemViewHolder) holder;
            EmptyListItem emptyItemModel = (EmptyListItem) this.mEmptyDataSet.get(position);

            emptyItemViewHolder.bindData(emptyItemModel);

        } else {

            PlaceViewHolder placeViewHolder = (PlaceViewHolder) holder;

            final Place place = (Place) this.mDataSet.get(position);

            this.mRealmInstance.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    Place queriedPlace = mRealmInstance.where(Place.class).equalTo("id", place.getId()).findFirst();
                    if(queriedPlace != null) {
                        place.setIsFavourite(queriedPlace.getIsFavourite());
                        place.setToVisit(queriedPlace.getToVisit());
                    }
                }
            });

            placeViewHolder.bindData(place, this.getOnItemClickListener(place));
        }
    }

    @Override
    public int getItemCount() {
        return this.mDataSet == null? this.mEmptyDataSet == null? 0 : this.mEmptyDataSet.size() : this.mDataSet.size();
    }

    public void swapDataSet(List<ListItem> dataSet) {
        this.mDataSet = dataSet;
        this.notifyDataSetChanged();
    }

    protected View.OnClickListener getOnItemClickListener(final Place place) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mOnPlaceListItemClickListener != null) {
                    mOnPlaceListItemClickListener.onPlaceListItemClicked(place);
                }
            }
        };
    }
}
