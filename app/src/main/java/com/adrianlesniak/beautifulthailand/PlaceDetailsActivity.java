package com.adrianlesniak.beautifulthailand;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class PlaceDetailsActivity extends AppCompatActivity {

    public static final String BUNDLE_PLACE = "place";

    private Place mPlace;

    private BTToolbar mToolbar;

    private ImageView mPlacePhotoView;

    private BTTextView mPlaceNameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_details);

        this.mPlace = this.getIntent().getParcelableExtra(BUNDLE_PLACE);

        this.mToolbar = (BTToolbar) findViewById(R.id.toolbar);
        this.mToolbar.setOnToolbarActionListener(new BTToolbar.OnToolbarActionListener() {
            @Override
            public void onPrimaryButtonClicked() {
                finish();
            }

            @Override
            public void onSecondaryButtonClicked() {

            }
        });

        this.mPlacePhotoView = (ImageView) findViewById(R.id.place_photo_view);
        this.mPlacePhotoView.setVisibility(this.mPlace.getPhotos() != null && this.mPlace.getPhotos().size() > 0? View.VISIBLE : View.GONE);
        if(this.mPlace.getPhotos() != null && this.mPlace.getPhotos().size() > 0) {

            String uri = "https://maps.googleapis.com/maps/api/place/photo?maxwidth=" + this.mPlace.getPhotos().get(0).getWidth() + "&photoreference=" + this.mPlace.getPhotos().get(0).getPhotoReference() + "&key=" + this.getString(R.string.api_key);

            Picasso.with(this).
                    load(Uri.parse(uri)).
                    into(this.mPlacePhotoView);
        }

        this.mPlaceNameView = (BTTextView) findViewById(R.id.place_name_view);
        this.mPlaceNameView.setText(this.mPlace.getName());
    }
}
