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

import com.adrianlesniak.beautifulthailand.R;
import com.adrianlesniak.beautifulthailand.apis.GoogleMapsApiHelper;
import com.adrianlesniak.beautifulthailand.cache.DistanceMatrixCache;
import com.adrianlesniak.beautifulthailand.cache.LocationCache;
import com.adrianlesniak.beautifulthailand.cache.NearbyPlacesCache;
import com.adrianlesniak.beautifulthailand.models.maps.DistanceMatrixRow;
import com.adrianlesniak.beautifulthailand.models.maps.Place;
import com.adrianlesniak.beautifulthailand.screens.details.PlaceDetailsActivity;
import com.adrianlesniak.beautifulthailand.screens.shared.EmptyAdapter;
import com.adrianlesniak.beautifulthailand.screens.shared.LoadingAdapter;
import com.adrianlesniak.beautifulthailand.screens.shared.LocationAwareActivity;
import com.adrianlesniak.beautifulthailand.screens.shared.LocationDependentFragment;
import com.adrianlesniak.beautifulthailand.utilities.PlaceComparator;

import java.util.Collections;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by adrian on 01/02/2017.
 */

public class NearbyFragment extends LocationDependentFragment implements OnPlaceClickListener {

    private int DEFAULT_SEARCH_RADIUS = 500;

    private RecyclerView mNearbyPlacesList;

    private RecyclerView.Adapter mAdapter;

    private Observer nearbyPlacesObserver = new Observer<List<Place>>() {
        @Override
        public void onSubscribe(Disposable d) {
            // Empty
        }

        @Override
        public void onNext(final List<Place> nearbyPlaces) {

            if(nearbyPlaces.isEmpty()) {
                mAdapter = new EmptyAdapter(getActivity(), getResources().getString(R.string.no_nearby_places_message));
                mNearbyPlacesList.setAdapter(mAdapter);
                return;
            }

            GoogleMapsApiHelper.getInstance(getContext())
                    .getDistanceToPlaces(LocationCache.getInstance().getLocationCache(), nearbyPlaces)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<List<DistanceMatrixRow.DistanceMatrixElement>>() {
                        @Override
                        public void onSubscribe(Disposable d) {
                            // Empty
                        }

                        @Override
                        public void onNext(List<DistanceMatrixRow.DistanceMatrixElement> distancesList) {

                            int distancesListCount = distancesList.size();
                            for(int index=0;index<distancesListCount;index++) {
                                DistanceMatrixCache.getInstance().putDistance(nearbyPlaces.get(index).placeId, distancesList.get(index));
                            }

                            Collections.sort(nearbyPlaces, new PlaceComparator());
                            NearbyPlacesCache.getsInstance().setCache(nearbyPlaces);

                            mAdapter = new NearbyPlacesAdapter(getActivity(), nearbyPlaces, NearbyFragment.this);
                            mNearbyPlacesList.setAdapter(mAdapter);
                        }

                        @Override
                        public void onError(Throwable e) {
                            e.printStackTrace();
                        }

                        @Override
                        public void onComplete() {
                            // Empty
                        }
                    });
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
    };

    private Observer isInThailandObserver = new Observer<Boolean>() {
        @Override
        public void onSubscribe(Disposable d) {

        }

        @Override
        public void onNext(Boolean value) {

            if(!value) {
                mAdapter = new EmptyAdapter(getActivity(), getResources().getString(R.string.error_not_in_thailand));
                mNearbyPlacesList.setAdapter(mAdapter);
                return;
            }

            GoogleMapsApiHelper
                    .getInstance(getContext())
                    .getNearbyPlaces(LocationCache.getInstance().getLocationCache(), DEFAULT_SEARCH_RADIUS)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(nearbyPlacesObserver);
        }

        @Override
        public void onError(Throwable e) {
            mAdapter = new EmptyAdapter(getActivity(), getResources().getString(R.string.error_loading_places_message));
            mNearbyPlacesList.setAdapter(mAdapter);
        }

        @Override
        public void onComplete() {

        }
    };

    @Override
    public void onResume() {
        super.onResume();

        if(!NearbyPlacesCache.getsInstance().isCacheEmpty()) {
            mAdapter = new NearbyPlacesAdapter(getActivity(), NearbyPlacesCache.getsInstance().getCache(), NearbyFragment.this);
            mNearbyPlacesList.setAdapter(mAdapter);
            return;
        }

        if(getActivity() instanceof LocationAwareActivity) {
            ((LocationAwareActivity) getActivity()).requestCurrentLocation();
        }
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
        this.mNearbyPlacesList.addOnScrollListener(new OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                setToolbarElevation(recyclerView.computeVerticalScrollOffset());
            }
        });

        this.mAdapter = new LoadingAdapter(getActivity());
        this.mNearbyPlacesList.setAdapter(this.mAdapter);
    }

    @Override
    public void onPlaceClicked(Place place) {

        Bundle detailsBundle = new Bundle();
        detailsBundle.putString(PlaceDetailsActivity.BUNDLE_PLACE_ID, place.placeId);

        Intent detailsIntent = new Intent(getContext(), PlaceDetailsActivity.class);
        detailsIntent.putExtras(detailsBundle);

        getActivity().startActivity(detailsIntent);
    }

    @Override
    protected void initializeApiCall(Location location) {
        GoogleMapsApiHelper.getInstance(getContext())
                .isInThailand(location)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(isInThailandObserver);
    }

}
