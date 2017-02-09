package com.adrianlesniak.beautifulthailand.screens.details;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.adrianlesniak.beautifulthailand.R;
import com.adrianlesniak.beautifulthailand.models.RecentPlacesDistanceList;
import com.adrianlesniak.beautifulthailand.models.maps.PlaceDetailsResponse;
import com.adrianlesniak.beautifulthailand.models.maps.Review;
import com.adrianlesniak.beautifulthailand.screens.reviews.PlaceReviewsActivity;
import com.adrianlesniak.beautifulthailand.utilities.MapsApiHelper;
import com.adrianlesniak.beautifulthailand.utilities.ObserverAdapter;
import com.adrianlesniak.beautifulthailand.views.BTPhotoCarousel;
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
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class PlaceDetailsActivity extends AppCompatActivity implements OnMapReadyCallback {

    public static final String BUNDLE_PLACE_ID = "place_id";

    @BindView(R.id.place_title_text_view) TextView mPlaceTitleTextView;

    @BindView(R.id.place_distance_text_view) TextView mPlaceDistanceTextView;

    @BindView(R.id.place_review_count) TextView mPlaceReviewCount;

    @BindView(R.id.rating_text_view) TextView mPlaceRatingTextView;

    @BindView(R.id.photo_carousel) BTPhotoCarousel mBTPhotoCarousel;

    @BindView(R.id.address_text_view) TextView mAddressTextView;

    private SupportMapFragment mMapFragment;

    private PlaceDetailsResponse mPlaceDetailsResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_place_details);

        ButterKnife.bind(this);

        this.mMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map_view_fragment);
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

                        //TODO Add loader

                        mPlaceDetailsResponse = (PlaceDetailsResponse) value;
                        mMapFragment.getMapAsync(PlaceDetailsActivity.this);

                        updateViews();
                    }

                    @Override
                    public void onError(Throwable e) {

                        //TODO Add error handling

                        super.onError(e);
                    }
                });
    }

    @OnClick(R.id.back)
    public void onBack(View view) {
        this.finish();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.setupMap(googleMap);
    }

    public void showReviews(View view) {

        Review[] reviews = this.mPlaceDetailsResponse.result.reviews;

        Intent reviewsIntent = new Intent(this, PlaceReviewsActivity.class);
        reviewsIntent.putParcelableArrayListExtra(PlaceReviewsActivity.BUNDLE_REVIEWS, new ArrayList<Parcelable>(Arrays.asList(reviews)));
        reviewsIntent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);

        overridePendingTransition(R.anim.side_up, R.anim.slide_down);
        startActivity(reviewsIntent);
    }

    private void updateViews() {
        this.mPlaceTitleTextView.setText(this.mPlaceDetailsResponse.result.name);
        this.mPlaceDistanceTextView.setText(RecentPlacesDistanceList.getInstance().get(this.mPlaceDetailsResponse.result.placeId).getFirstElement().distance.text);
        this.mPlaceReviewCount.setText(this.mPlaceDetailsResponse.result.reviews == null ? getResources().getString(R.string.no_reviews_message) : getResources().getQuantityString(R.plurals.number_of_place_reviews, this.mPlaceDetailsResponse.result.reviews.length, this.mPlaceDetailsResponse.result.reviews.length));
        this.mPlaceRatingTextView.setText(String.valueOf(this.mPlaceDetailsResponse.result.rating));
        this.mBTPhotoCarousel.setPhotos(getSupportFragmentManager(), this.mPlaceDetailsResponse.result.photos);
        this.mAddressTextView.setText(this.mPlaceDetailsResponse.result.formattedAddress);
    }

    private void setupMap(GoogleMap googleMap) {

        LatLng placeCoord = new LatLng(this.mPlaceDetailsResponse.result.geometry.location.lat, this.mPlaceDetailsResponse.result.geometry.location.lng);
        MarkerOptions markerOptions = new MarkerOptions()
                .draggable(false)
                .position(placeCoord);

        googleMap.addMarker(markerOptions );

        CameraUpdate cameraUpdate = CameraUpdateFactory
                .newLatLngZoom(markerOptions.getPosition(), 15);

        googleMap.moveCamera(cameraUpdate);
        googleMap.getUiSettings().setAllGesturesEnabled(false);
    }

}
