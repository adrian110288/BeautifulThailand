package com.adrianlesniak.beautifulthailand.screens.nearby;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.OnScrollListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.adrianlesniak.beautifulthailand.R;
import com.adrianlesniak.beautifulthailand.apis.GoogleMapsApiHelper;
import com.adrianlesniak.beautifulthailand.cache.DistanceMatrixCache;
import com.adrianlesniak.beautifulthailand.cache.NearbyPlacesCache;
import com.adrianlesniak.beautifulthailand.models.maps.Place;
import com.adrianlesniak.beautifulthailand.screens.details.PlaceDetailsActivity;
import com.adrianlesniak.beautifulthailand.screens.shared.EmptyAdapter;
import com.adrianlesniak.beautifulthailand.screens.shared.LocationAwareActivity;
import com.adrianlesniak.beautifulthailand.screens.shared.LocationDependentFragment;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by adrian on 01/02/2017.
 */

public class NearbyFragment extends LocationDependentFragment implements OnPlaceClickListener, NearbyContract.View {

    private int DEFAULT_SEARCH_RADIUS = 500;

    @BindView(R.id.progress_bar)
    public ProgressBar mProgressBar;

    @BindView(R.id.places_list)
    public RecyclerView mPlacesList;

    private NearbyPresenter mPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.mPresenter = new NearbyPresenter(GoogleMapsApiHelper.getInstance(getContext()), NearbyPlacesCache.getInstance(), DistanceMatrixCache.getInstance(), this);
        this.mPresenter.setSearchRadius(DEFAULT_SEARCH_RADIUS);
    }

    @Override
    public void onResume() {
        super.onResume();

        this.mPresenter.loadPlaces();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_places, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ButterKnife.bind(this, view);

        this.mPlacesList.setLayoutManager(new LinearLayoutManager(getContext()));
        this.mPlacesList.addOnScrollListener(new OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                setToolbarElevation(recyclerView.computeVerticalScrollOffset());
            }
        });
    }

    @Override
    public void onPlaceClicked(Place place) {

        Bundle detailsBundle = new Bundle();
        detailsBundle.putParcelable(PlaceDetailsActivity.BUNDLE_PLACE, place);

        Intent detailsIntent = new Intent(getContext(), PlaceDetailsActivity.class);
        detailsIntent.putExtras(detailsBundle);

        getActivity().startActivity(detailsIntent);
    }

    @Override
    protected void initializeApiCall(Location location) {
        this.mPresenter.checkIsInThailand(location);
    }

    @Override
    public void showPlaces(List<Place> places) {

        if(places.isEmpty()) {
            if(getActivity() instanceof LocationAwareActivity) {
                ((LocationAwareActivity) getActivity()).requestCurrentLocation();
            }

            return;
        }

        setAdapterWithPlaces(places);
    }

    @Override
    public void showNearbyPlaces(List<Place> nearbyPlaces) {

        if(nearbyPlaces.isEmpty()) {
            showEmptyAdapter();
            return;
        }

        setAdapterWithPlaces(nearbyPlaces);
    }

    @Override
    public void showLoading() {
        this.mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void dismissLoading() {
        this.mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void setIsInThailand(boolean isInThailand) {

        if(!isInThailand) {
            this.showEmptyAdapter();

            return;
        }

        mPresenter.loadNearbyPlaces();
    }

    private void showEmptyAdapter() {
        RecyclerView.Adapter adapter = new EmptyAdapter(getActivity(), getResources().getString(R.string.error_loading_places_message));
        mPlacesList.setAdapter(adapter);
    }

    private void setAdapterWithPlaces(List<Place> places) {
        NearbyPlacesAdapter mAdapter = new NearbyPlacesAdapter(getActivity(), places, this);
        this.mPlacesList.setAdapter(mAdapter);
    }
}
