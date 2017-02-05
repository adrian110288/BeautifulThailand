package com.adrianlesniak.beautifulthailand.screens.details;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.adrianlesniak.beautifulthailand.R;
import com.adrianlesniak.beautifulthailand.models.PlaceDetailsResponse;
import com.adrianlesniak.beautifulthailand.utilities.MapsApiHelper;
import com.adrianlesniak.beautifulthailand.utilities.ObserverAdapter;
import com.adrianlesniak.beautifulthailand.views.BTPhotoCarousel;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class PlaceDetailsActivity extends AppCompatActivity {

    public static final String BUNDLE_PLACE_ID = "place_id";

    public ImageButton mBackButton;

    public TextView mPlaceTitleTextView;

    private TextView mPlaceReviewCount;

    public TextView mPlaceRatingTextView;

    public BTPhotoCarousel mBTPhotoCarousel;

    public BTPlaceMapView mBTPlaceMapView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_place_details);

        this.mPlaceTitleTextView = (TextView) findViewById(R.id.place_title_text_view);
        this.mPlaceReviewCount = (TextView) findViewById(R.id.place_review_count);
        this.mPlaceRatingTextView = (TextView) findViewById(R.id.rating_text_view);
        this.mBTPhotoCarousel = (BTPhotoCarousel) findViewById(R.id.photo_carousel);
        this.mBTPlaceMapView = (BTPlaceMapView) findViewById(R.id.place_map_view);

        this.mBackButton = (ImageButton) findViewById(R.id.back);
    }

    @Override
    protected void onResume() {
        super.onResume();

        final String placeId = getIntent().getExtras().getString(BUNDLE_PLACE_ID);

        MapsApiHelper.getInstance(this)
                .getPlaceDetails(placeId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ObserverAdapter() {
                    @Override
                    public void onNext(Object value) {
                        super.onNext(value);

                        PlaceDetailsResponse placeDetailsResponse = (PlaceDetailsResponse) value;

                        mPlaceTitleTextView.setText(placeDetailsResponse.result.name);
                        mPlaceReviewCount.setText(placeDetailsResponse.result.reviews == null ? getResources().getString(R.string.no_reviews_message) : getResources().getQuantityString(R.plurals.number_of_place_reviews, placeDetailsResponse.result.reviews.length, placeDetailsResponse.result.reviews.length));
                        mPlaceRatingTextView.setText(String.valueOf(placeDetailsResponse.result.rating));
                        mBTPhotoCarousel.setPhotos(getSupportFragmentManager(), placeDetailsResponse.result.photos);
                        mBTPlaceMapView.setAddress(placeDetailsResponse.result.formattedAddress);
                        mBTPlaceMapView.setLocation(placeDetailsResponse.result.geometry.location.lat, placeDetailsResponse.result.geometry.location.lng);
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                    }
                });
    }

    public void onBack(View view) {
        this.finish();
    }

}
