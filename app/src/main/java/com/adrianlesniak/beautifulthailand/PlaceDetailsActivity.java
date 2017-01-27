package com.adrianlesniak.beautifulthailand;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.adrianlesniak.beautifulthailand.models.Place;
import com.adrianlesniak.beautifulthailand.views.BTTextView;
import com.squareup.picasso.Picasso;

public class PlaceDetailsActivity extends CloseableActivity {

    public static final String BUNDLE_PLACE = "place";

    private Place mPlace;

    private ImageView mPlacePhotoView;

    private BTTextView mPlaceNameView;

    private BTTextView mPlaceAddressView;

    private BTTextView mPlacePhoneNoView;

    private BTTextView mPlaceRatingView;

    private BTTextView mPlaceWebsiteView;

    private BTTextView mPlaceOpenNowView;

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

            String uri = "https://maps.googleapis.com/maps/api/place/photo?maxwidth=" + this.mPlace.getPhotosList().get(0).getWidth() + "&photoreference=" + this.mPlace.getPhotosList().get(0).getPhotoReference() + "&key=" + this.getString(R.string.api_key);

            Picasso.with(this).
                    load(Uri.parse(uri)).
                    into(this.mPlacePhotoView);
        }

        this.mPlaceNameView = (BTTextView) findViewById(R.id.place_name_view);
        this.mPlaceNameView.setText(this.mPlace.getName());

        this.mPlaceAddressView = (BTTextView) findViewById(R.id.place_address_text);
        this.mPlaceAddressView.setText(this.mPlace.getAddress());
    }

    @Override
    public void onSecondaryToolbarButtonClicked() {
        super.onSecondaryToolbarButtonClicked();
    }
}
