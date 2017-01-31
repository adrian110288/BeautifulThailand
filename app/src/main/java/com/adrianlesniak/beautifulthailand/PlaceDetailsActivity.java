package com.adrianlesniak.beautifulthailand;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.adrianlesniak.beautifulthailand.models.Photo;
import com.adrianlesniak.beautifulthailand.models.Place;
import com.adrianlesniak.beautifulthailand.utilities.BTUriProvider;
import com.adrianlesniak.beautifulthailand.views.BTTextView;
import com.adrianlesniak.beautifulthailand.views.MapCard;
import com.adrianlesniak.beautifulthailand.views.OpeningHoursCard;
import com.adrianlesniak.beautifulthailand.views.TelephoneCard;
import com.google.maps.PendingResult;
import com.google.maps.PlaceDetailsRequest;
import com.google.maps.model.PlaceDetails;
import com.squareup.picasso.Picasso;

public class PlaceDetailsActivity extends CloseableActivity {

    public static final String BUNDLE_PLACE = "place";

    private Place mPlace;

    private ImageView mPlacePhotoView;

    private BTTextView mPlaceNameView;

    private TelephoneCard mTelephoneCard;

    private OpeningHoursCard mOpeningHoursCard;

    private MapCard mMapCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_details);

        this.setup();
    }

    @Override
    protected void setup() {
        super.setup();

        this.mPlace = this.getIntent().getParcelableExtra(BUNDLE_PLACE);

        this.mPlacePhotoView = (ImageView) findViewById(R.id.place_photo_view);
        this.mPlacePhotoView.setVisibility(this.mPlace.getPhotosList() != null && this.mPlace.getPhotosList().size() > 0? View.VISIBLE : View.GONE);
        if(this.mPlace.getPhotosList() != null && this.mPlace.getPhotosList().size() > 0) {

            Photo photo = this.mPlace.getPhotosList().get(0);

            Picasso.with(this).
                    load(BTUriProvider.getUriForPhoto(this, photo)).
                    into(this.mPlacePhotoView);
        }

        this.mPlaceNameView = (BTTextView) findViewById(R.id.place_name_view);
        this.mPlaceNameView.setText(this.mPlace.getName());

        this.mTelephoneCard = (TelephoneCard) findViewById(R.id.card_telephone);

        this.mMapCard = (MapCard) findViewById(R.id.card_map);

        this.mOpeningHoursCard = (OpeningHoursCard) findViewById(R.id.card_opening_hours);

        PlaceDetailsRequest placeDetailsRequest = new PlaceDetailsRequest(BTApplication.getGeoApiContext());
        placeDetailsRequest.placeId(this.mPlace.getPlaceId()).setCallback(new PendingResult.Callback<PlaceDetails>() {
            @Override
            public void onResult(final PlaceDetails result) {

                PlaceDetailsActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mTelephoneCard.setTelephoneNo(result.formattedPhoneNumber);
                        mOpeningHoursCard.setOpeningHours(result.openingHours);
                        mMapCard.setAddress(result.formattedAddress);
                        mMapCard.setLocation(result.geometry.location.lat, result.geometry.location.lng);
                    }
                });

            }

            @Override
            public void onFailure(Throwable e) {

            }
        });
    }

    @Override
    public void onSecondaryToolbarButtonClicked() {
        super.onSecondaryToolbarButtonClicked();
    }
}
