package com.adrianlesniak.beautifulthailand;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;

import com.adrianlesniak.beautifulthailand.database.DatabaseHelper;
import com.adrianlesniak.beautifulthailand.models.Place;
import com.adrianlesniak.beautifulthailand.navigation.NavigationFragment;

public class HomeActivity extends ToolbarActivity implements LoaderManager.LoaderCallbacks<LocalPlacesResponse>, PlacesListAdapter.OnPlaceListItemClickListener, NavigationFragment.OnNavigationItemClickListener {

    private LocationManager mLocationManager;

    private int DEFAULT_SEARCH_RADIUS = 500;

    private DrawerLayout mDrawerLayout;

    private RecyclerView mPlacesList;

    private NavigationFragment mNavigationFragment;

    private PlacesListAdapter mAdapter;

    private static final int BT_PERMISSION_REQUEST_FINE_LCOATION = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_home);

        this.setup();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onRestart() {
        this.mAdapter.notifyDataSetChanged();
        super.onRestart();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    protected void setup() {
        super.setup();

        this.mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        this.mDrawerLayout = (DrawerLayout) this.findViewById(R.id.drawer_layout);

        this.mNavigationFragment = (NavigationFragment) getSupportFragmentManager().findFragmentById(R.id.navigation_fragment);
        this.mNavigationFragment.setOnNavigationItemClickedListener(this);

        this.mPlacesList = (RecyclerView) this.findViewById(R.id.places_list);
        this.mPlacesList.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));

        this.mAdapter = new PlacesListAdapter(null, null, this);
        this.mPlacesList.setAdapter(this.mAdapter);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[] { Manifest.permission.ACCESS_FINE_LOCATION }, BT_PERMISSION_REQUEST_FINE_LCOATION);

            return;
        }

        this.requestCurrentLocation();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        DatabaseHelper.getInstance().closeRealm();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
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
       this.mLocationManager.requestSingleUpdate(LocationManager.GPS_PROVIDER, this.mLocationListenerAdapter, null);
    }

    @Override
    public Loader<LocalPlacesResponse> onCreateLoader(int id, Bundle args) {
        return new LocalPlacesLoader(this, args, DEFAULT_SEARCH_RADIUS);
    }

    @Override
    public void onLoadFinished(Loader<LocalPlacesResponse> loader, LocalPlacesResponse data) {
        this.mAdapter.swapDataSet(data.getResults());
    }

    @Override
    public void onLoaderReset(Loader<LocalPlacesResponse> loader) { }

    @Override
    public void onPrimaryToolbarButtonClicked() {
        super.onPrimaryToolbarButtonClicked();
        mDrawerLayout.openDrawer(Gravity.LEFT);
    }

    @Override
    public void onPlaceListItemClicked(Place place) {

        Intent detailsIntent = new Intent(this, PlaceDetailsActivity.class);
        detailsIntent.putExtra(PlaceDetailsActivity.BUNDLE_PLACE, place);

        this.startActivity(detailsIntent);
    }

    @Override
    public void onNavigationItemClicked() {
        this.mDrawerLayout.closeDrawer(Gravity.LEFT);
    }

    private LocationListenerAdapter mLocationListenerAdapter = new LocationListenerAdapter() {
        @Override
        public void onLocationChanged(Location location) {

            CurrentLocation.getInstance().setCurrentLocation(location);

            Bundle locationBundle = new Bundle();
            locationBundle.putDouble(LocalPlacesLoader.BUNDLE_LAT, location.getLatitude());
            locationBundle.putDouble(LocalPlacesLoader.BUNDLE_LNG, location.getLongitude());

            getSupportLoaderManager().initLoader(1, locationBundle, HomeActivity.this).forceLoad();

            mLocationManager.removeUpdates(this);
        }
    };

}
