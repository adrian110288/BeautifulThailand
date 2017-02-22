package com.adrianlesniak.beautifulthailand.screens.shared;

import com.adrianlesniak.beautifulthailand.cache.LocationCache;

/**
 * Created by adrian on 15/02/2017.
 */

public abstract class LocationDependentFragment extends ToolbarFragment implements LocationCache.OnLocationUpdateListener {

    @Override
    public void onStart() {
        super.onStart();
        LocationCache.getInstance().setOnLocationUpdateListener(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        LocationCache.getInstance().removeOnLocationUpdateListener(this);
    }
}
