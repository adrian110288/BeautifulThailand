package com.adrianlesniak.beautifulthailand.screens.nearby;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.adrianlesniak.beautifulthailand.R;
import com.adrianlesniak.beautifulthailand.models.maps.LatLng;
import com.adrianlesniak.beautifulthailand.models.maps.Place;
import com.adrianlesniak.beautifulthailand.models.maps.PlacesSearchResponse;
import com.adrianlesniak.beautifulthailand.screens.details.PlaceDetailsActivity;
import com.adrianlesniak.beautifulthailand.screens.shared.BaseFragment;
import com.adrianlesniak.beautifulthailand.screens.shared.EmptyAdapter;
import com.adrianlesniak.beautifulthailand.screens.shared.LoadingAdapter;
import com.adrianlesniak.beautifulthailand.utilities.MapsApiHelper;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by adrian on 01/02/2017.
 */

public class NearbyFragment extends BaseFragment implements OnPlaceClickListener{

    private int DEFAULT_SEARCH_RADIUS = 500;

    private static final int BT_PERMISSION_REQUEST_FINE_LCOATION = 1;

    private LocationManager mLocationManager;

    private RecyclerView mNearbyPlacesList;

    private RecyclerView.Adapter mAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.mLocationManager = (LocationManager) getContext().getSystemService(getContext().LOCATION_SERVICE);
    }

    @Override
    public void onResume() {
        super.onResume();

        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity)getContext(), new String[] { Manifest.permission.ACCESS_FINE_LOCATION }, BT_PERMISSION_REQUEST_FINE_LCOATION);

            return;
        }

        // TODO Change this
        LatLng latLng = new LatLng(13.7488, 100.5286);
        MapsApiHelper
                .getInstance(getContext())
                .getNearbyPlaces(latLng, DEFAULT_SEARCH_RADIUS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<PlacesSearchResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        // Empty
                    }

                    @Override
                    public void onNext(PlacesSearchResponse result) {

                        if(result.results.length > 0) {
                            mAdapter = new NearbyPlacesAdapter(getActivity(), result.results, NearbyFragment.this);
                        } else {
                            mAdapter = new EmptyAdapter(getActivity(), getResources().getString(R.string.no_nearby_places_message));

                        }

                        mNearbyPlacesList.setAdapter(mAdapter);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mAdapter = new EmptyAdapter(getActivity(), getResources().getString(R.string.error_loading_places_message));
                        mNearbyPlacesList.setAdapter(mAdapter);
                    }

                    @Override
                    public void onComplete() {
                        // Empty
                    }
                });
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_nearby, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.mNearbyPlacesList = (RecyclerView) view.findViewById(R.id.nearby_places_list);
        this.mNearbyPlacesList.setLayoutManager(new LinearLayoutManager(getContext()));

        this.mAdapter = new LoadingAdapter(getActivity());
        this.mNearbyPlacesList.setAdapter(this.mAdapter);
    }

    @Override
    public void onPlaceClicked(Place place) {

        Bundle detailsBundle = new Bundle();
        detailsBundle.putString(PlaceDetailsActivity.BUNDLE_PLACE_ID, place.placeId);

        Intent detailsIntent = new Intent(getContext(), PlaceDetailsActivity.class);
        detailsIntent.putExtras(detailsBundle);
//        detailsIntent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);

        getContext().startActivity(detailsIntent);
    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
//        switch (requestCode) {
//            case BT_PERMISSION_REQUEST_FINE_LCOATION: {
//                // If request is cancelled, the result arrays are empty.
//                if (grantResults.length > 0
//                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//
//                    this.requestCurrentLocation();
//
//                } else {
//
//                    // permission denied, boo! Disable the
//                    // functionality that depends on this permission.
//                }
//                return;
//            }
//        }
//    }

//    public void requestCurrentLocation() {
//        this.mLocationManager.requestSingleUpdate(LocationManager.GPS_PROVIDER, new LocationListenerAdapter() {
//            @Override
//            public void onLocationChanged(Location location) {
//                mLocationManager.removeUpdates(this);
//            }
//        }, null);
//    }

}
