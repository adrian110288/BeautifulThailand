package com.adrianlesniak.beautifulthailand.screens.home;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
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

import com.adrianlesniak.beautifulthailand.utilities.LocationListenerAdapter;
import com.adrianlesniak.beautifulthailand.R;
import com.adrianlesniak.beautifulthailand.screens.shared.EmptyAdapter;
import com.adrianlesniak.beautifulthailand.screens.shared.LoadingAdapter;
import com.adrianlesniak.beautifulthailand.utilities.MapsApiHelper;
import com.google.maps.PendingResult;
import com.google.maps.model.LatLng;
import com.google.maps.model.PlacesSearchResponse;

/**
 * Created by adrian on 01/02/2017.
 */

public class HomeFragment extends Fragment {

    private int DEFAULT_SEARCH_RADIUS = 500;

    private static final int BT_PERMISSION_REQUEST_FINE_LCOATION = 1;

    private Context mContext;

    private LocationManager mLocationManager;

    private RecyclerView mNearbyPlacesList;

    private RecyclerView.Adapter mAdapter;

    public HomeFragment(){}

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.mLocationManager = (LocationManager) this.mContext.getSystemService(this.mContext.LOCATION_SERVICE);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.mNearbyPlacesList = (RecyclerView) view.findViewById(R.id.nearby_places_list);
        this.mNearbyPlacesList.setLayoutManager(new LinearLayoutManager(this.mContext));

        this.mAdapter = new LoadingAdapter(this.mContext);
        this.mNearbyPlacesList.setAdapter(this.mAdapter);

        if (ActivityCompat.checkSelfPermission(this.mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity)this.mContext, new String[] { Manifest.permission.ACCESS_FINE_LOCATION }, BT_PERMISSION_REQUEST_FINE_LCOATION);

            return;
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        this.requestCurrentLocation();
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

        LatLng latLng = new LatLng(13.737188, 100.523218);
        MapsApiHelper.getNearbyPlaces(latLng, DEFAULT_SEARCH_RADIUS, onNearbyPlacesCallback);
    }

    private LocationListenerAdapter mLocationListenerAdapter = new LocationListenerAdapter() {
        @Override
        public void onLocationChanged(Location location) {

            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
            MapsApiHelper.getNearbyPlaces(latLng, DEFAULT_SEARCH_RADIUS, onNearbyPlacesCallback);
            mLocationManager.removeUpdates(this);
        }
    };

    private PendingResult.Callback<PlacesSearchResponse> onNearbyPlacesCallback = new PendingResult.Callback<PlacesSearchResponse>() {
        @Override
        public void onResult(final PlacesSearchResponse result) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    if(result.results.length > 0) {
                        mAdapter = new NearbyPlacesAdapter(mContext, result.results);
                    } else {
                        mAdapter = new EmptyAdapter(mContext, getResources().getString(R.string.no_nearby_places_message));

                    }

                    mNearbyPlacesList.setAdapter(mAdapter);
                }
            });
        }

        @Override
        public void onFailure(Throwable e) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mAdapter = new EmptyAdapter(mContext, getResources().getString(R.string.error_loading_places_message));
                    mNearbyPlacesList.setAdapter(mAdapter);
                }
            });
        }
    };

}
