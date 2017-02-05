package com.adrianlesniak.beautifulthailand.screens.details;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.adrianlesniak.beautifulthailand.R;
import com.squareup.picasso.Picasso;

/**
 * Created by adrian on 29/01/2017.
 */

public class BTPlaceMapView extends LinearLayout {

    private TextView mAddressView;

    private ImageView mMapImageView;

    public BTPlaceMapView(Context context) {
        this(context, null);
    }

    public BTPlaceMapView(Context context, AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater.from(getContext()).inflate(R.layout.view_place_map, this);

        this.mAddressView = (TextView) findViewById(R.id.address_text_view);
        this.mMapImageView = (ImageView) findViewById(R.id.map_image);
    }

    public void setAddress(String address) {
        this.mAddressView.setText(address);
    }

    public void setLocation(double lat, double lng) {

        String mapPhotUrl = "https://maps.googleapis.com/maps/api/staticmap?center=" + String.valueOf(lat) + "," + String.valueOf(lng) + "&zoom=16&scale=1&size=640x640&key=" + getContext().getResources().getString(R.string.static_map_api_key);

        Picasso.with(getContext())
                .load(mapPhotUrl)
                .fit()
                .centerInside()
                .into(this.mMapImageView);
    }
}
