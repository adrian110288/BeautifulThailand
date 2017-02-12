package com.adrianlesniak.beautifulthailand.screens.shared;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.adrianlesniak.beautifulthailand.R;
import com.adrianlesniak.beautifulthailand.models.RecentPlacesDistanceList;
import com.adrianlesniak.beautifulthailand.models.maps.DistanceMatrixElement;
import com.adrianlesniak.beautifulthailand.models.maps.DistanceMatrixResponse;
import com.adrianlesniak.beautifulthailand.models.maps.DistanceMatrixRow;
import com.adrianlesniak.beautifulthailand.models.maps.LatLng;
import com.adrianlesniak.beautifulthailand.models.maps.Photo;
import com.adrianlesniak.beautifulthailand.models.maps.Place;
import com.adrianlesniak.beautifulthailand.screens.nearby.OnPlaceClickListener;
import com.adrianlesniak.beautifulthailand.utilities.DistanceMatrixCache;
import com.adrianlesniak.beautifulthailand.utilities.MapsApiHelper;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by adrian on 25/01/2017.
 */

public class PlaceViewHolder extends RecyclerView.ViewHolder {

    private View mView;

    private Place mPlace;

    private TextView placeNameView;

    private ImageView placePhotoView;

    protected ImageButton addToFavouriteView;

    private TextView mDistanceTextView;

    private View mProgressView;

    public PlaceViewHolder(View itemView) {
        super(itemView);

        this.mView = itemView;
        this.placeNameView = (TextView) itemView.findViewById(R.id.place_name_view);
        this.placePhotoView = (ImageView) itemView.findViewById(R.id.place_photo_view);
        this.addToFavouriteView = (ImageButton) itemView.findViewById(R.id.place_add_to_fav);
        this.mDistanceTextView = (TextView) itemView.findViewById(R.id.distance_text_view);
        this.mProgressView = itemView.findViewById(R.id.progress_bar);
    }

    public void bindData(final Place place, final OnPlaceClickListener listener) {

        this.mPlace = place;

        this.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener != null) {
                    listener.onPlaceClicked(place);
                }
            }
        });

        this.placeNameView.setText(place.name);

        Photo photo = this.mPlace.photos != null && this.mPlace.photos.length > 0 ? this.mPlace.photos[0] : null;
        if(photo != null) {
            MapsApiHelper.getInstance(this.mView.getContext()).
                    loadPhoto(photo != null ? photo.photo_reference : null, mProgressView, this.placePhotoView);
        }


        DistanceMatrixElement distanceMatrixElement = DistanceMatrixCache.getInstance().getDistance(place.placeId);
        if(distanceMatrixElement != null) {
            this.mDistanceTextView.setText(distanceMatrixElement.distance.text);
        }

    }

// /        this.mPlaceListItemRemoveListener = listener;
//    }
//
//    @Override
//    public void onClick(View view) {
//
//        if(this.mPlace != null) {
//            if(view == addToFavouriteView) {
//
//            }
//
//
//            if(this.mPlaceListItemRemoveListener != null) {
//                this.mPlaceListItemRemoveListener.onPlaceListItemRemove(this.mPlace.placeId);
//            }
//        }
//    }   public void setOnPlaceListItemRemoveListener(DatabasePlacesListAdapter.OnPlaceListItemRemoveListener listener) {

}
