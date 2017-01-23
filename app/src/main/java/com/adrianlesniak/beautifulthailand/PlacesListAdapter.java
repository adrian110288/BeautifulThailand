package com.adrianlesniak.beautifulthailand;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.adrianlesniak.beautifulthailand.models.Place;
import com.squareup.picasso.Picasso;

import java.util.List;

import io.realm.Realm;

/**
 * Created by adrian on 21/01/2017.
 */

public class PlacesListAdapter extends RecyclerView.Adapter<PlacesListAdapter.PlaceViewHolder> {

    public interface OnItemClickListener {
        void onItemClicked(Place place);
    }

    private List<Place> mDataSet;

    private OnItemClickListener mOnItemClickListener;

    private Realm mRealmInstance;

    public PlacesListAdapter(List<Place> dataSet, OnItemClickListener listener, Realm realmInstance) {
        this.mDataSet = dataSet;
        this.mOnItemClickListener = listener;
        this.mRealmInstance = realmInstance;
    }

    @Override
    public PlaceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.place_list_item, parent, false);

        return new PlaceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PlaceViewHolder holder, int position) {

        final Place place = this.mDataSet.get(position);

        Place queriedPlace = this.mRealmInstance.where(Place.class).equalTo("id", place.getId()).findFirst();
        if(queriedPlace != null) {
            place.setIsFavourite(queriedPlace.getIsFavourite());
        }

        View.OnClickListener holderViewClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClicked(place);
                }
            }
        };

        View.OnClickListener addToFavouriteViewClickListener = new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                place.setIsFavourite(!place.getIsFavourite());

                mRealmInstance.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        realm.copyToRealmOrUpdate(place);
                        view.setSelected(place.getIsFavourite());
                    }
                });
            }
        };

        View.OnClickListener addToVisitViewClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: Add to visit
            }
        };

        holder.bindData(place);

        holder.view.setOnClickListener(holderViewClickListener);
        holder.addToFavouriteView.setOnClickListener(addToFavouriteViewClickListener);
        holder.addToVisitView.setOnClickListener(addToVisitViewClickListener);
    }

    @Override
    public int getItemCount() {
        return this.mDataSet == null? 0 : this.mDataSet.size();
    }

    public void swapDataSet(List<Place> dataSet) {
        this.mDataSet = dataSet;
        this.notifyDataSetChanged();
    }

    protected static final class PlaceViewHolder extends RecyclerView.ViewHolder {

        protected View view;

        private TextView placeNameView;

        private ImageView placePhotoView;

        protected ImageButton addToFavouriteView;

        protected ImageButton addToVisitView;

        public PlaceViewHolder(View itemView) {
            super(itemView);

            this.view = itemView;
            this.placeNameView = (TextView) itemView.findViewById(R.id.place_name_view);
            this.placePhotoView = (ImageView) itemView.findViewById(R.id.place_photo_view);
            this.addToFavouriteView = (ImageButton) itemView.findViewById(R.id.place_add_to_fav);
            this.addToVisitView = (ImageButton) itemView.findViewById(R.id.place_add_to_visit);
        }

        public void bindData(Place place) {

            this.placeNameView.setText(place.getName());
            this.addToFavouriteView.setSelected(place.getIsFavourite());

            if(place.getPhotosList() != null && !place.getPhotosList().isEmpty()) {

                String uri = "https://maps.googleapis.com/maps/api/place/photo?maxwidth=" + place.getPhotosList().get(0).getWidth() + "&photoreference=" + place.getPhotosList().get(0).getPhotoReference() + "&key=" + this.view.getContext().getString(R.string.api_key);

                Picasso.with(this.view.getContext()).
                        load(Uri.parse(uri)).
                        into(this.placePhotoView);
            }
        }
    }
}
