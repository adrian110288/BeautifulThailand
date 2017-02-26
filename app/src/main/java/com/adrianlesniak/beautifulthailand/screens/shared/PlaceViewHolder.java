package com.adrianlesniak.beautifulthailand.screens.shared;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.adrianlesniak.beautifulthailand.R;
import com.adrianlesniak.beautifulthailand.apis.GoogleMapsApiHelper;
import com.adrianlesniak.beautifulthailand.cache.DistanceMatrixCache;
import com.adrianlesniak.beautifulthailand.models.maps.DistanceMatrixRow;
import com.adrianlesniak.beautifulthailand.models.maps.Photo;
import com.adrianlesniak.beautifulthailand.models.maps.Place;
import com.adrianlesniak.beautifulthailand.screens.nearby.OnPlaceClickListener;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by adrian on 25/01/2017.
 */

public class PlaceViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.place_name_view)
    public TextView placeNameView;

    @BindView(R.id.place_photo_view)
    public ImageView placePhotoView;

    @BindView(R.id.place_add_to_fav)
    public ImageButton addToFavouriteButton;

    @BindView(R.id.distance_text_view)
    public TextView mDistanceTextView;

    @BindView(R.id.duration_text_view)
    public TextView mDurationTextView;

    public PlaceViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void bindData(final Place place) {

        this.placeNameView.setText(place.name);

        Photo photo = place.photos != null && !place.photos.isEmpty() ? place.photos.get(0) : null;
        if(photo != null) {
            GoogleMapsApiHelper.getInstance(this.itemView.getContext()).
                    loadPhoto(this.itemView.getContext(), photo.photo_reference, null, this.placePhotoView);
        }

        DistanceMatrixRow.DistanceMatrixElement distanceMatrixElement = DistanceMatrixCache.getInstance().getDistance(place.placeId);
        if(distanceMatrixElement != null) {
            this.mDistanceTextView.setText(distanceMatrixElement.distance.text);
            this.mDurationTextView.setText(distanceMatrixElement.duration.text);
        }

    }

}
