package com.adrianlesniak.beautifulthailand;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.adrianlesniak.beautifulthailand.models.EmptyItemModel;
import com.adrianlesniak.beautifulthailand.models.ListModel;
import com.adrianlesniak.beautifulthailand.models.Place;
import com.squareup.picasso.Picasso;

import java.util.List;

import io.realm.Realm;

/**
 * Created by adrian on 21/01/2017.
 */

public class PlacesListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int TYPE_PLACE = 0;
    public static final int TYPE_EMPTY = 1;

    public interface OnItemClickListener {
        void onItemClicked(Place place);
    }

    private List<ListModel> mDataSet;

    private OnItemClickListener mOnItemClickListener;

    private Realm mRealmInstance;

    public PlacesListAdapter(List<ListModel> dataSet, OnItemClickListener listener, Realm realmInstance) {
        this.mDataSet = dataSet;
        this.mOnItemClickListener = listener;
        this.mRealmInstance = realmInstance;
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
            ((EmptyItemViewHolder) holder).bindData((EmptyItemModel) this.mDataSet.get(position));

        } else {
            final Place place = (Place) this.mDataSet.get(position);

            // TODO: Refactor this adapter for favourite and to visit places. This method below does not have to be called in favourite or to visit activities

            this.mRealmInstance.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    Place queriedPlace = realm.where(Place.class).equalTo("id", place.getId()).findFirst();
                    if(queriedPlace != null) {
                        place.setIsFavourite(queriedPlace.getIsFavourite());
                        place.setToVisit(queriedPlace.getToVisit());
                    }
                }
            });

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

                    mRealmInstance.executeTransaction(new Realm.Transaction() {
                        @Override
                        public void execute(Realm realm) {

                            place.setIsFavourite(!place.getIsFavourite());

                            realm.copyToRealmOrUpdate(place);
                            view.setSelected(place.getIsFavourite());
                        }
                    });
                }
            };

            View.OnClickListener addToVisitViewClickListener = new View.OnClickListener() {
                @Override
                public void onClick(final View view) {

                    mRealmInstance.executeTransaction(new Realm.Transaction() {
                        @Override
                        public void execute(Realm realm) {

                            place.setToVisit(!place.getToVisit());

                            realm.copyToRealmOrUpdate(place);
                            view.setSelected(place.getToVisit());
                        }
                    });
                }
            };

            PlaceViewHolder placeViewHolder = (PlaceViewHolder) holder;

            placeViewHolder.bindData(place);

            placeViewHolder.view.setOnClickListener(holderViewClickListener);
            placeViewHolder.addToFavouriteView.setOnClickListener(addToFavouriteViewClickListener);
            placeViewHolder.addToVisitView.setOnClickListener(addToVisitViewClickListener);
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
    }
}
