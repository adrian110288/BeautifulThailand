package com.adrianlesniak.beautifulthailand;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.adrianlesniak.beautifulthailand.database.DatabaseHelper;
import com.adrianlesniak.beautifulthailand.models.EmptyItemModel;
import com.adrianlesniak.beautifulthailand.models.ListModel;
import com.adrianlesniak.beautifulthailand.models.Place;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by adrian on 21/01/2017.
 */

public class PlacesListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int TYPE_PLACE = 0;
    public static final int TYPE_EMPTY = 1;

    public interface OnPlaceListItemClickListener {
        void onPlaceListItemClicked(Place place);
    }

    private List<ListModel> mDataSet;

    private OnPlaceListItemClickListener mOnPlaceListItemClickListener;

    public PlacesListAdapter(List<ListModel> dataSet, OnPlaceListItemClickListener listener) {
        this.mDataSet = dataSet;
        this.mOnPlaceListItemClickListener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(viewType == TYPE_PLACE? R.layout.place_list_item : R.layout.empty_list_item, parent, false);

        return viewType == TYPE_PLACE ? new PlaceViewHolder(view) : new EmptyItemViewHolder(view);
    }

    @Override
    public int getItemViewType(int position) {
        return this.mDataSet.get(position) instanceof Place ? TYPE_PLACE : TYPE_EMPTY;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        int viewType = holder.getItemViewType();

        if(viewType == TYPE_EMPTY) {

            EmptyItemViewHolder emptyItemViewHolder = (EmptyItemViewHolder) holder;
            EmptyItemModel emptyItemModel = (EmptyItemModel) this.mDataSet.get(position);

            emptyItemViewHolder.bindData(emptyItemModel);

        } else {

            PlaceViewHolder placeViewHolder = (PlaceViewHolder) holder;

            final Place place = (Place) this.mDataSet.get(position);

            Place queriedPlace = DatabaseHelper.getInstance().getPlaceById(place.getId());
            if(queriedPlace != null) {
                place.setIsFavourite(queriedPlace.getIsFavourite());
                place.setToVisit(queriedPlace.getToVisit());
            }

            View.OnClickListener onItemClickListener = new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(mOnPlaceListItemClickListener != null) {
                        mOnPlaceListItemClickListener.onPlaceListItemClicked(place);
                    }
                }
            };

            placeViewHolder.bindData(place, onItemClickListener);
        }
    }

    @Override
    public int getItemCount() {
        return this.mDataSet == null? 0 : this.mDataSet.size();
    }

    public void swapDataSet(List<ListModel> dataSet) {
        this.mDataSet = dataSet;
        this.notifyDataSetChanged();
    }

    protected static final class EmptyItemViewHolder extends RecyclerView.ViewHolder {

        private ImageView mIconView;

        private TextView mTitleView;

        public EmptyItemViewHolder(View itemView) {
            super(itemView);

            this.mIconView = (ImageView) itemView.findViewById(R.id.empty_list_item_icon);
            this.mTitleView = (TextView) itemView.findViewById(R.id.empty_list_item_title);
        }

        public void bindData(EmptyItemModel data) {
            this.mIconView.setImageResource(data.getIconRes());
            this.mTitleView.setText(data.getTitle());
        }
    }

    protected static final class PlaceViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        protected View view;

        private Place mPlace;

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

        public void bindData(Place place, View.OnClickListener listener) {

            this.mPlace = place;

            this.view.setOnClickListener(listener);
            this.addToFavouriteView.setOnClickListener(this);
            this.addToVisitView.setOnClickListener(this);

            this.placeNameView.setText(place.getName());
            this.addToFavouriteView.setSelected(place.getIsFavourite());
            this.addToVisitView.setSelected(place.getToVisit());

            if(place.getPhotosList() != null && !place.getPhotosList().isEmpty()) {

                String uri = "https://maps.googleapis.com/maps/api/place/photo?maxwidth=" + place.getPhotosList().get(0).getWidth() + "&photoreference=" + place.getPhotosList().get(0).getPhotoReference() + "&key=" + this.view.getContext().getString(R.string.api_key);

                Picasso.with(this.view.getContext()).
                        load(Uri.parse(uri)).
                        fit().
                        centerCrop().
                        into(this.placePhotoView);
            }
        }

        @Override
        public void onClick(View view) {

            if(this.mPlace != null) {
                if(view == addToFavouriteView) {
                    Place updatedPlace = DatabaseHelper.getInstance().setPlaceFavourite(this.mPlace, !this.mPlace.getIsFavourite());
                    view.setSelected(updatedPlace.getIsFavourite());
                }
                else if(view == addToVisitView) {
                    Place updatedPlace = DatabaseHelper.getInstance().setPlaceToVisit(this.mPlace, !this.mPlace.getToVisit());
                    view.setSelected(updatedPlace.getToVisit());
                }
            }
        }
    }
}
