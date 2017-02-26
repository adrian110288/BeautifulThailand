package com.adrianlesniak.beautifulthailand.screens.nearby;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.adrianlesniak.beautifulthailand.R;
import com.adrianlesniak.beautifulthailand.db.PlacesLocalDataSource;
import com.adrianlesniak.beautifulthailand.models.maps.Place;
import com.adrianlesniak.beautifulthailand.screens.shared.PlaceViewHolder;
import com.adrianlesniak.beautifulthailand.utilities.ObserverAdapter;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by adrian on 01/02/2017.
 */

public class NearbyPlacesAdapter extends RecyclerView.Adapter<PlaceViewHolder> {

    private Context mContext;

    private List<Place> mPlaces;

    private OnPlaceClickListener mPlaceClickListener;

    public NearbyPlacesAdapter(Context context, List<Place> places, OnPlaceClickListener placeClickListener) {
        this.mContext = context;
        this.mPlaces = places;
        this.mPlaceClickListener = placeClickListener;
    }

    @Override
    public PlaceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(this.mContext).inflate(R.layout.list_item_place, parent, false);
        return new PlaceViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final PlaceViewHolder holder, int position) {

        final Place place = this.mPlaces.get(position);

        PlacesLocalDataSource.getInstance(holder.itemView.getContext())
                .getFavouritePlaceById(place.placeId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ObserverAdapter() {
                    @Override
                    public void onNext(Object value) {
                        holder.addToFavouriteButton.setSelected(value != null);
                    }

                    @Override
                    public void onError(Throwable e) {
                        holder.addToFavouriteButton.setSelected(false);
                    }

                });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mPlaceClickListener != null) {
                    mPlaceClickListener.onPlaceClicked(place);
                }
            }
        });

        holder.addToFavouriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PlacesLocalDataSource.getInstance(holder.itemView.getContext())
                        .setPlaceFavourite(place, !holder.addToFavouriteButton.isSelected())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new ObserverAdapter() {
                            @Override
                            public void onNext(Object value) {
                                holder.addToFavouriteButton.setSelected((Boolean) value);
                            }
                        });
            }
        });

        holder.bindData(place);
    }

    @Override
    public int getItemCount() {
        return this.mPlaces != null? this.mPlaces.size() : 0;
    }
}
