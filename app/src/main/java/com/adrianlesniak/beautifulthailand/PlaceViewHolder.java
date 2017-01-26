package com.adrianlesniak.beautifulthailand;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.adrianlesniak.beautifulthailand.database.DatabaseHelper;
import com.adrianlesniak.beautifulthailand.models.Place;
import com.squareup.picasso.Picasso;

/**
 * Created by adrian on 25/01/2017.
 */

public class PlaceViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private View mView;

    private Place mPlace;

    private TextView placeNameView;

    private ImageView placePhotoView;

    protected ImageButton addToFavouriteView;

    protected ImageButton addToVisitView;

    protected DatabasePlacesListAdapter.OnPlaceListItemRemoveListener mPlaceListItemRemoveListener;

    public PlaceViewHolder(View itemView) {
        super(itemView);

        this.mView = itemView;
        this.placeNameView = (TextView) itemView.findViewById(R.id.place_name_view);
        this.placePhotoView = (ImageView) itemView.findViewById(R.id.place_photo_view);
        this.addToFavouriteView = (ImageButton) itemView.findViewById(R.id.place_add_to_fav);
        this.addToVisitView = (ImageButton) itemView.findViewById(R.id.place_add_to_visit);
    }

    public void bindData(Place place, View.OnClickListener listener) {

        this.mPlace = place;

        this.mView.setOnClickListener(listener);
        this.addToFavouriteView.setOnClickListener(this);
        this.addToVisitView.setOnClickListener(this);

        this.placeNameView.setText(place.getName());
        this.addToFavouriteView.setSelected(place.getIsFavourite());
        this.addToVisitView.setSelected(place.getToVisit());

        if(place.getPhotosList() != null && !place.getPhotosList().isEmpty()) {

            String uri = "https://maps.googleapis.com/maps/api/place/photo?maxwidth=" + place.getPhotosList().get(0).getWidth() + "&photoreference=" + place.getPhotosList().get(0).getPhotoReference() + "&key=" + this.mView.getContext().getString(R.string.api_key);

            Picasso.with(this.mView.getContext()).
                    load(Uri.parse(uri)).
                    fit().
                    centerCrop().
                    into(this.placePhotoView);
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

    public void setOnPlaceListItemRemoveListener(DatabasePlacesListAdapter.OnPlaceListItemRemoveListener listener) {
        this.mPlaceListItemRemoveListener = listener;
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

            if(this.mPlaceListItemRemoveListener != null) {
                this.mPlaceListItemRemoveListener.onPlaceListItemRemove(this.mPlace);
            }
        }
    }
}
