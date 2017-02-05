package com.adrianlesniak.beautifulthailand.screens.shared;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.adrianlesniak.beautifulthailand.R;
import com.adrianlesniak.beautifulthailand.models.Photo;
import com.adrianlesniak.beautifulthailand.models.Place;
import com.adrianlesniak.beautifulthailand.screens.home.OnPlaceClickListener;
import com.adrianlesniak.beautifulthailand.utilities.MapsApiHelper;

/**
 * Created by adrian on 25/01/2017.
 */

public class PlaceViewHolder extends RecyclerView.ViewHolder {

    private View mView;

    private Place mPlace;

    private TextView placeNameView;

    private ImageView placePhotoView;

    protected ImageButton addToFavouriteView;

    public PlaceViewHolder(View itemView) {
        super(itemView);

        this.mView = itemView;
        this.placeNameView = (TextView) itemView.findViewById(R.id.place_name_view);
        this.placePhotoView = (ImageView) itemView.findViewById(R.id.place_photo_view);
        this.addToFavouriteView = (ImageButton) itemView.findViewById(R.id.place_add_to_fav);
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
//        this.addToFavouriteView.setOnClickListener(this);

        this.placeNameView.setText(place.name);
//        this.addToFavouriteView.setSelected(place.getIsFavourite());

        Photo photo = this.mPlace.photos != null && this.mPlace.photos.length > 0 ? this.mPlace.photos[0] : null;

        if(photo != null) {
            MapsApiHelper.getInstance(this.mView.getContext()).loadPhoto(photo.photo_reference, photo.width, this.placePhotoView);
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
