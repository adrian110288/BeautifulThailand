package com.adrianlesniak.beautifulthailand;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by adrian on 21/01/2017.
 */

public class LocalPlacesAdapter extends RecyclerView.Adapter<LocalPlacesAdapter.ViewHolder> {

    private List<Place> mDataSet;

    public LocalPlacesAdapter(List<Place> dataSet) {
        this.mDataSet = dataSet;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.place_list_item, parent, false);

        ViewHolder vh = new ViewHolder(view);

        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bindData(this.mDataSet.get(position));
    }

    @Override
    public int getItemCount() {
        return this.mDataSet == null? 0 : this.mDataSet.size();
    }

    public void swapDataSet(List<Place> dataSet) {
        this.mDataSet = dataSet;
        this.notifyDataSetChanged();
    }

    protected static final class ViewHolder extends RecyclerView.ViewHolder {

        private TextView mPlaceNameView;

        public ViewHolder(View itemView) {
            super(itemView);

            this.mPlaceNameView = (TextView) itemView.findViewById(R.id.place_name_view);
        }

        public void bindData(Place place) {
            this.mPlaceNameView.setText(place.getName());
        }
    }
}
