package com.adrianlesniak.beautifulthailand.screens.home;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.adrianlesniak.beautifulthailand.R;
import com.adrianlesniak.beautifulthailand.models.Place;
import com.adrianlesniak.beautifulthailand.screens.shared.PlaceViewHolder;

import java.util.Arrays;
import java.util.List;

/**
 * Created by adrian on 01/02/2017.
 */

public class NearbyPlacesAdapter extends RecyclerView.Adapter<PlaceViewHolder> {

    private Context mContext;

    private List<Place> mPlaces;

    private OnPlaceClickListener mPlaceClickListener;

    public NearbyPlacesAdapter(Context context, Place[] places, OnPlaceClickListener placeClickListener) {
        this.mContext = context;
        this.mPlaces = Arrays.asList(places);
        this.mPlaceClickListener = placeClickListener;
    }

    @Override
    public PlaceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(this.mContext).inflate(R.layout.list_item_place, parent, false);
        return new PlaceViewHolder(v);
    }

    @Override
    public void onBindViewHolder(PlaceViewHolder holder, int position) {
        holder.bindData(this.mPlaces.get(position), mPlaceClickListener);
    }

    @Override
    public int getItemCount() {
        return this.mPlaces != null? this.mPlaces.size() : 0;
    }
}
