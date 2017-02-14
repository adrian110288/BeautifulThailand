package com.adrianlesniak.beautifulthailand.screens.details;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.adrianlesniak.beautifulthailand.R;
import com.adrianlesniak.beautifulthailand.models.maps.DistanceMatrixElement;
import com.adrianlesniak.beautifulthailand.models.maps.PlaceDetails;
import com.adrianlesniak.beautifulthailand.models.maps.Review;
import com.adrianlesniak.beautifulthailand.screens.reviews.PlaceReviewsActivity;
import com.adrianlesniak.beautifulthailand.utilities.cache.DistanceMatrixCache;
import com.adrianlesniak.beautifulthailand.utilities.MapsApiHelper;
import com.adrianlesniak.beautifulthailand.utilities.cache.PlaceDetailsCache;
import com.adrianlesniak.beautifulthailand.views.BTPhotoCarousel;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class PlaceDetailsActivity extends AppCompatActivity implements OnMapReadyCallback, View.OnScrollChangeListener {

    public static final String BUNDLE_PLACE_ID = "place_id";

    @BindView(R.id.scroll_view) ScrollView mDetailsScrollView;

    @BindView(R.id.back_frame) FrameLayout mBackFrame;

    @BindView(R.id.place_title_text_view) TextView mPlaceTitleTextView;

    @BindView(R.id.place_distance_text_view) TextView mPlaceDistanceTextView;

    @BindView(R.id.place_review_count) TextView mPlaceReviewCount;

    @BindView(R.id.rating_text_view) TextView mPlaceRatingTextView;

    @BindView(R.id.photo_carousel) BTPhotoCarousel mBTPhotoCarousel;

    @BindView(R.id.telephone_text_view) TextView mTelephoneTextView;

    @BindView(R.id.website_text_view) TextView mWebsiteTextView;

    @BindView(R.id.address_text_view) TextView mAddressTextView;

    private PlaceDetails mPlaceDetails;

    private Observer<PlaceDetails> mPlaceDetailsObserver = new Observer<PlaceDetails>() {
        @Override
        public void onSubscribe(Disposable d) { }

        @Override
        public void onNext(PlaceDetails placeDetails) {

            //TODO Add loader

            PlaceDetailsCache.getInstance().
                    addPlaceDetails(placeDetails);

            mPlaceDetails = placeDetails;
            updateViews();
        }

        @Override
        public void onError(Throwable e) {
            //TODO Add error handling
        }

        @Override
        public void onComplete() {}
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_place_details);

        ButterKnife.bind(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        final String placeId = getIntent().getExtras().getString(BUNDLE_PLACE_ID);

        MapsApiHelper.getInstance(this)
                .getPlaceDetails(placeId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mPlaceDetailsObserver);

        this.mDetailsScrollView.setOnScrollChangeListener(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        this.mDetailsScrollView.setOnScrollChangeListener(null);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.setupMap(googleMap);
    }

    @OnClick(R.id.back)
    public void onBack(View view) {
        finish();
    }

    @OnClick(R.id.place_review_count)
    public void showReviews(View view) {

        Review[] reviews = this.mPlaceDetails.reviews;

        Intent reviewsIntent = new Intent(this, PlaceReviewsActivity.class);
        reviewsIntent.putParcelableArrayListExtra(PlaceReviewsActivity.BUNDLE_REVIEWS, new ArrayList<Parcelable>(Arrays.asList(reviews)));
        reviewsIntent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);

        overridePendingTransition(R.anim.side_up, R.anim.slide_down);
        startActivity(reviewsIntent);
    }

    @OnClick(R.id.telephone_text_view)
    public void dialNumber(View view) {

        if(this.mPlaceDetails.internationalPhoneNumber!= null) {

            String uri = "tel:" + this.mPlaceDetails.internationalPhoneNumber;
            Intent telephoneIntent = new Intent(Intent.ACTION_DIAL);
            telephoneIntent.setData(Uri.parse(uri));

            try {
                startActivity(telephoneIntent);
            } catch (ActivityNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    @OnClick(R.id.address_text_view)
    public void openMaps(View view) {

        if(this.mPlaceDetails.formattedAddress != null) {

            String uri = "geo:" + this.mPlaceDetails.geometry.location.lat + "," + this.mPlaceDetails.geometry.location.lng + "?q=" + this.mPlaceDetails.geometry.location.lat + "," + this.mPlaceDetails.geometry.location.lng;
            Intent mapIntent = new Intent(Intent.ACTION_VIEW);
            mapIntent.setData(Uri.parse(uri));
            mapIntent.setPackage("com.google.android.apps.maps");

            try {
                startActivity(mapIntent);
            } catch (ActivityNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    @OnClick(R.id.website_text_view)
    public void goToLink(View view) {

        if(this.mPlaceDetails.website != null) {
            Intent websiteIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(this.mPlaceDetails.website));
            startActivity(websiteIntent);
        }
    }

    @OnClick(R.id.navigate_button)
    public void navigateToPlace(View view) {

        //TODO Change this
        String uri = "http://maps.google.com/maps?saddr=13.7488,100.5286&daddr=" + this.mPlaceDetails.geometry.location.lat + "," + this.mPlaceDetails.geometry.location.lng;
        Intent navigateIntent = new Intent(Intent.ACTION_VIEW);
        navigateIntent.setData(Uri.parse(uri));

        try {
            startActivity(navigateIntent);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void updateViews() {
        this.mPlaceTitleTextView.setText(this.mPlaceDetails.name);

        DistanceMatrixElement distanceMatrixElement = DistanceMatrixCache.getInstance().getDistance(this.mPlaceDetails.placeId);
        if(distanceMatrixElement != null) {
            this.mPlaceDistanceTextView.setText(distanceMatrixElement.distance.text);
        }

        this.mPlaceReviewCount.setText(this.mPlaceDetails.reviews == null ? getResources().getString(R.string.no_reviews_message) : getResources().getQuantityString(R.plurals.number_of_place_reviews, this.mPlaceDetails.reviews.length, this.mPlaceDetails.reviews.length));
        this.mPlaceReviewCount.setEnabled(this.mPlaceDetails.reviews != null);

        this.mPlaceRatingTextView.setText(String.valueOf(this.mPlaceDetails.rating));

        if(this.mPlaceDetails.photos == null || this.mPlaceDetails.photos.length == 0) {
            this.mBTPhotoCarousel.setVisibility(View.GONE);
        } else {
            this.mBTPhotoCarousel.setPhotos(getSupportFragmentManager(), this.mPlaceDetails.photos);
        }

        this.mTelephoneTextView.setText(this.mPlaceDetails.internationalPhoneNumber != null ? this.mPlaceDetails.internationalPhoneNumber : getResources().getString(R.string.no_telephone_no_message));
        this.mTelephoneTextView.setEnabled(this.mPlaceDetails.internationalPhoneNumber != null);

        this.mWebsiteTextView.setText(this.mPlaceDetails.website != null ? this.mPlaceDetails.website : getResources().getString(R.string.no_website_message));
        this.mWebsiteTextView.setEnabled(this.mPlaceDetails.website != null);

        this.mAddressTextView.setText(this.mPlaceDetails.formattedAddress);
        this.mAddressTextView.setEnabled(this.mPlaceDetails.formattedAddress != null);

        int googlePlayAvailability = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(this);
        if(googlePlayAvailability != ConnectionResult.SERVICE_INVALID) {

            findViewById(R.id.map_container).setVisibility(View.VISIBLE);
            ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map_view_fragment))
                    .getMapAsync(PlaceDetailsActivity.this);
        }
    }

    private void setupMap(GoogleMap googleMap) {

        LatLng placeCoord = new LatLng(this.mPlaceDetails.geometry.location.lat, this.mPlaceDetails.geometry.location.lng);
        MarkerOptions markerOptions = new MarkerOptions()
                .draggable(false)
                .position(placeCoord);

        googleMap.addMarker(markerOptions );

        CameraUpdate cameraUpdate = CameraUpdateFactory
                .newLatLngZoom(markerOptions.getPosition(), 15);

        googleMap.moveCamera(cameraUpdate);

        googleMap.getUiSettings().setAllGesturesEnabled(false);
        googleMap.getUiSettings().setMapToolbarEnabled(false);
        googleMap.getUiSettings().setMyLocationButtonEnabled(false);
    }

    @Override
    public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
        ViewCompat.setElevation(this.mBackFrame, Math.min(scrollY/getResources().getDisplayMetrics().density, getResources().getDimensionPixelSize(R.dimen.card_elevation)));
    }
}
