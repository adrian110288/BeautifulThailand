package com.adrianlesniak.beautifulthailand;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;

import com.adrianlesniak.beautifulthailand.models.Place;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import io.realm.Realm;

public class HomeActivity extends ToolbarActivity implements
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LoaderManager.LoaderCallbacks<LocalPlacesResponse>, PlacesListAdapter.OnItemClickListener {

    private DrawerLayout mDrawerLayout;

    private RecyclerView mPlacesList;

    private PlacesListAdapter mAdapter;

    private GoogleApiClient mGoogleApiClient;

    private Realm mRealmInstance;

    private static final int BT_PERMISSION_REQUEST_COARSE_LCOATION = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        this.mRealmInstance = Realm.getDefaultInstance();

        setup();
    }

    @Override
    protected void setup() {
        super.setup();

        this.mDrawerLayout = (DrawerLayout) this.findViewById(R.id.drawer_layout);

        this.mPlacesList = (RecyclerView) this.findViewById(R.id.places_list);
        this.mPlacesList.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));

        this.mAdapter = new PlacesListAdapter(null, this, this.mRealmInstance);
        this.mPlacesList.setAdapter(this.mAdapter);

        if (this.mGoogleApiClient == null) {
            this.mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
    }

    protected void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
    }

    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        this.mRealmInstance.close();
    }

    @Override
    public void onConnected(Bundle connectionHint) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[] { Manifest.permission.ACCESS_COARSE_LOCATION }, BT_PERMISSION_REQUEST_COARSE_LCOATION);

            return;
        }

        this.getLastKnownLocation();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case BT_PERMISSION_REQUEST_COARSE_LCOATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    this.getLastKnownLocation();

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    private void getLastKnownLocation() {

        Location mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (mLastLocation != null) {

            Bundle locationBundle = new Bundle();
            locationBundle.putDouble(LocalPlacesLoader.BUNDLE_LAT, mLastLocation.getLatitude());
            locationBundle.putDouble(LocalPlacesLoader.BUNDLE_LNG, mLastLocation.getLongitude());

            getSupportLoaderManager().initLoader(1, locationBundle, this).forceLoad();
        }
    }

    @Override
    public Loader<LocalPlacesResponse> onCreateLoader(int id, Bundle args) {

        return new LocalPlacesLoader(this, args, 500);
    }

    @Override
    public void onLoadFinished(Loader<LocalPlacesResponse> loader, LocalPlacesResponse data) {
        this.mAdapter.swapDataSet(data.getResults());
    }

    @Override
    public void onLoaderReset(Loader<LocalPlacesResponse> loader) {

    }

    @Override
    public void onPrimaryToolbarButtonClicked() {
        super.onPrimaryToolbarButtonClicked();
        mDrawerLayout.openDrawer(Gravity.LEFT);
    }

    @Override
    public void onItemClicked(Place place) {

        Intent detailsIntent = new Intent(this, PlaceDetailsActivity.class);
        detailsIntent.putExtra(PlaceDetailsActivity.BUNDLE_PLACE, place);

        this.startActivity(detailsIntent);
    }
}
