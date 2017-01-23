package com.adrianlesniak.beautifulthailand;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class PlaceDetailsActivity extends CloseableActivity {

    public static final String BUNDLE_PLACE = "place";

    private Place mPlace;

    private ImageView mPlacePhotoView;

    private BTTextView mPlaceNameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_details);

        this.mPlace = this.getIntent().getParcelableExtra(BUNDLE_PLACE);

        setup();
    }

    @Override
    protected void setup() {
        super.setup();

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
    }

    @Override
    public void onSecondaryToolbarButtonClicked() {
        super.onSecondaryToolbarButtonClicked();
    }
}
