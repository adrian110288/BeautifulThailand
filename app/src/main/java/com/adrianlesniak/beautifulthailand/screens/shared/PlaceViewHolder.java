package com.adrianlesniak.beautifulthailand.screens.shared;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.adrianlesniak.beautifulthailand.R;
import com.adrianlesniak.beautifulthailand.utilities.MapsApiHelper;
import com.google.maps.PendingResult;
import com.google.maps.model.Photo;
import com.google.maps.model.PhotoResult;
import com.google.maps.model.PlacesSearchResult;

/**
 * Created by adrian on 25/01/2017.
 */

public class PlaceViewHolder extends RecyclerView.ViewHolder {

    private View mView;

    private PlacesSearchResult mPlace;

    private TextView placeNameView;

    private ImageView placePhotoView;

    protected ImageButton addToFavouriteView;

    protected ImageButton addToVisitView;

    public PlaceViewHolder(View itemView) {
        super(itemView);

        this.mView = itemView;
        this.placeNameView = (TextView) itemView.findViewById(R.id.place_name_view);
        this.placePhotoView = (ImageView) itemView.findViewById(R.id.place_photo_view);
        this.addToFavouriteView = (ImageButton) itemView.findViewById(R.id.place_add_to_fav);
        this.addToVisitView = (ImageButton) itemView.findViewById(R.id.place_add_to_visit);
    }

    public void bindData(PlacesSearchResult place, View.OnClickListener listener) {

        this.mPlace = place;

        this.mView.setOnClickListener(listener);
//        this.addToFavouriteView.setOnClickListener(this);
//        this.addToVisitView.setOnClickListener(this);

        this.placeNameView.setText(place.name);
//        this.addToFavouriteView.setSelected(place.getIsFavourite());
//        this.addToVisitView.setSelected(place.getToVisit());

        Photo photo = this.mPlace.photos != null && this.mPlace.photos.length > 0 ? this.mPlace.photos[0] : null;

        if(photo != null) {
            MapsApiHelper.getPhoto(photo.photoReference, photo.width, new PendingResult.Callback<PhotoResult>() {
                @Override
                public void onResult(PhotoResult result) {
                    Bitmap photo = BitmapFactory.decodeByteArray(result.imageData, 0, result.imageData.length);
                    placePhotoView.setImageBitmap(photo);
                }

                @Override
                public void onFailure(Throwable e) {

                }
            });

        }



//        GeoApiContext geoApiContext = new GeoApiContext().setApiKey(this.mView.getResources().getString(R.string.api_key));
//
//        Location currentLocation = CurrentLocation.getInstance().getCurrentLocation();
//
//        String [] origin = new String[] { String.valueOf(currentLocation.getLatitude()) + "," + String.valueOf(currentLocation.getLongitude()) };
//        String [] destination= new String[] { String.valueOf(this.mPlace.getLocation().getLatitude()) + "," + String.valueOf(this.mPlace.getLocation().getLongitude()) };
//
//        DistanceMatrixApiRequest distanceRequest = DistanceMatrixApi.getDistanceMatrix(geoApiContext, origin, destination).
//                mode(TravelMode.WALKING).
//                units(Unit.METRIC);
//
//        distanceRequest.setCallback(new PendingResult.Callback<DistanceMatrix>() {
//            @Override
//            public void onResult(DistanceMatrix result) {
//            }
//
//            @Override
//            public void onFailure(Throwable e) {
//
//            }
//        });
    }

//    public void setOnPlaceListItemRemoveListener(DatabasePlacesListAdapter.OnPlaceListItemRemoveListener listener) {
//        this.mPlaceListItemRemoveListener = listener;
//    }
//
//    @Override
//    public void onClick(View view) {
//
//        if(this.mPlace != null) {
//            if(view == addToFavouriteView) {
//
//            }
//            else if(view == addToVisitView) {
//
//            }
//
//            if(this.mPlaceListItemRemoveListener != null) {
//                this.mPlaceListItemRemoveListener.onPlaceListItemRemove(this.mPlace.placeId);
//            }
//        }
//    }
}
