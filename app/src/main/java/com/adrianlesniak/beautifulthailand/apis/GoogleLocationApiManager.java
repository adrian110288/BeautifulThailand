package com.adrianlesniak.beautifulthailand.apis;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;

/**
 * Created by adrian on 21/02/2017.
 */

public class GoogleLocationApiManager implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, ResultCallback<LocationSettingsResult> {

    public interface GoogleLocationApiCallback {



    }

    private GoogleApiClient mGoogleApiClient;

    private LocationRequest mLocationRequest;

    private GoogleLocationApiCallback mLocationCallback;

    private LocationSettingsRequest.Builder mLocationSettingRequestBuilder;

    public GoogleLocationApiManager(Context context) {
        this.mGoogleApiClient = new GoogleApiClient.Builder(context)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        this.mLocationRequest = new LocationRequest()
                .setNumUpdates(1)
                .setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);

        this.mLocationSettingRequestBuilder = new LocationSettingsRequest.Builder()
                .addLocationRequest(this.mLocationRequest);
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        PendingResult<LocationSettingsResult> result = LocationServices.SettingsApi.checkLocationSettings(this.mGoogleApiClient, this.mLocationSettingRequestBuilder.build());
        result.setResultCallback(this);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onResult(@NonNull LocationSettingsResult locationSettingsResult) {

        Status resultStatus = locationSettingsResult.getStatus();

        switch (resultStatus.getStatusCode()) {
            case LocationSettingsStatusCodes.SUCCESS: {

                break;
            }
            case LocationSettingsStatusCodes.RESOLUTION_REQUIRED: {
//                resultStatus.startResolutionForResult(this.mContext, Activity.REQUEST_CHECK_SETTINGS);
                break;
            }
            case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE: {

                break;
            }
        }

    }

    public void connect() {
        this.mGoogleApiClient.connect();
    }

    public void disconnect() {
        this.mGoogleApiClient.disconnect();
    }

    public void setLocationCallback(GoogleLocationApiCallback locationCallback) {
        this.mLocationCallback = locationCallback;
    }
}
