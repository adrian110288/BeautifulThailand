package com.adrianlesniak.beautifulthailand;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

/**
 * Created by adrian on 21/01/2017.
 */

public class LocalPlacesAdapter extends RecyclerView.Adapter<LocalPlacesAdapter.ViewHolder> {

    public interface OnItemClickListener {
        void onItemClicked(Place place);
    }

    private List<Place> mDataSet;

    private OnItemClickListener mOnItemClickListener;

    public LocalPlacesAdapter(List<Place> dataSet, OnItemClickListener listener) {
        this.mDataSet = dataSet;
        this.mOnItemClickListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.place_list_item, parent, false);

        ViewHolder vh = new ViewHolder(view);

        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        final Place place = this.mDataSet.get(position);

        Place queriedPlace = Realm.getDefaultInstance().where(Place.class).equalTo("id", place.getId()).findFirst();
        if(queriedPlace != null) {
            place.setIsFavourite(queriedPlace.getIsFavourite());
        }

        holder.bindData(place);
        holder.getView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClicked(place);
                }
            }
        });
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

        private Place mPlace;

        private View mView;

        private TextView mPlaceNameView;

        private ImageView mPlacePhotoView;

        private ImageButton mPlaceAddToFavView;

        private ImageButton mPlaceAddToVisit;

        public ViewHolder(View itemView) {
            super(itemView);

            this.mView = itemView;
            this.mPlaceNameView = (TextView) itemView.findViewById(R.id.place_name_view);
            this.mPlacePhotoView = (ImageView) itemView.findViewById(R.id.place_photo_view);
            this.mPlaceAddToFavView = (ImageButton) itemView.findViewById(R.id.place_add_to_fav);
            this.mPlaceAddToFavView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(mPlace != null) {

                        mPlace.setIsFavourite(!mPlace.getIsFavourite());

                        Realm.getDefaultInstance().executeTransaction(new Realm.Transaction() {
                            @Override
                            public void execute(Realm realm) {
                                realm.copyToRealmOrUpdate(mPlace);
                                mPlaceAddToFavView.setSelected(mPlace.getIsFavourite());
                            }
                        });

                    }
                }
            });
            this.mPlaceAddToVisit = (ImageButton) itemView.findViewById(R.id.place_add_to_visit);
            this.mPlaceAddToVisit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(mPlace != null) {
                        // TODO: Set place to visit
                    }

                }
            });
        }

        public void bindData(Place place) {

            this.mPlace = place;

            this.mPlaceNameView.setText(place.getName());

            if(place.getPhotosList() != null && place.getPhotosList().size() > 0) {

                String uri = "https://maps.googleapis.com/maps/api/place/photo?maxwidth=" + place.getPhotosList().get(0).getWidth() + "&photoreference=" + place.getPhotosList().get(0).getPhotoReference() + "&key=" + this.mView.getContext().getString(R.string.api_key);

                Picasso.with(this.mView.getContext()).
                        load(Uri.parse(uri)).
                        into(this.mPlacePhotoView);
            }

            this.mPlaceAddToFavView.setSelected(place.getIsFavourite());
        }

        public View getView() {
            return this.mView;
        }
    }
}
