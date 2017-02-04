package com.adrianlesniak.beautifulthailand.screens.home;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.adrianlesniak.beautifulthailand.models.LatLng;
import com.adrianlesniak.beautifulthailand.models.PlacesSearchResponse;
import com.adrianlesniak.beautifulthailand.R;
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

public class HomeFragment extends Fragment{

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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        this.mNearbyPlacesList = (RecyclerView) view.findViewById(R.id.nearby_places_list);
        this.mNearbyPlacesList.setLayoutManager(new LinearLayoutManager(getContext()));

        this.mAdapter = new LoadingAdapter(getActivity());
        this.mNearbyPlacesList.setAdapter(this.mAdapter);


        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity)getContext(), new String[] { Manifest.permission.ACCESS_FINE_LOCATION }, BT_PERMISSION_REQUEST_FINE_LCOATION);

            return;
        }

        LatLng latLng = new LatLng(13.737188, 100.523218);
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
                            mAdapter = new NearbyPlacesAdapter(getActivity(), result.results);
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

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//
//

//
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case BT_PERMISSION_REQUEST_FINE_LCOATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    this.requestCurrentLocation();

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }
        }
    }

    public void requestCurrentLocation() {
//        this.mLocationManager.requestSingleUpdate(LocationManager.GPS_PROVIDER, this.mLocationListenerAdapter, null);


    }

//    private LocationListenerAdapter mLocationListenerAdapter = new LocationListenerAdapter() {
//        @Override
//        public void onLocationChanged(Location location) {
//
//            MapsApiHelper.getNearbyPlaces(null, DEFAULT_SEARCH_RADIUS, onNearbyPlacesCallback);
//            mLocationManager.removeUpdates(this);
//        }
//    };

}
