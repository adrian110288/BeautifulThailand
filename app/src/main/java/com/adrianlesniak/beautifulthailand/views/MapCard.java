package com.adrianlesniak.beautifulthailand.views;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.widget.TextView;

import com.adrianlesniak.beautifulthailand.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by adrian on 29/01/2017.
 */

public class MapCard extends DetailsCard implements OnMapReadyCallback {

    private TextView mAddressView;

    private MapView mMapView;

    private double mLat;

    private double mLng;

    public MapCard(Context context) {
        this(context, null);
    }

    public MapCard(Context context, AttributeSet attrs) {
        super(context, attrs);

        this.mAddressView = (TextView) findViewById(R.id.address_view);
        this.mMapView = (MapView) findViewById(R.id.map_view);

        this.mMapView.onCreate(new Bundle());
        this.mMapView.getMapAsync(this);
    }

    @Override
    protected int getCardLayoutResource() {
        return R.layout.card_map;
    }

    public void setAddress(String address) {
        this.mAddressView.setText(address);
    }

    public void setLocation(double lat, double lng) {
        this.mLat = lat;
        this.mLng = lng;
    }

    @Override
    public void onMapReady(GoogleMap map) {
        map.addMarker(new MarkerOptions().position(new LatLng(this.mLat, this.mLng)));
    }
}
