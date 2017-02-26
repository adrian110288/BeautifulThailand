package com.adrianlesniak.beautifulthailand.screens.favourites;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.adrianlesniak.beautifulthailand.R;
import com.adrianlesniak.beautifulthailand.db.PlacesLocalDataSource;
import com.adrianlesniak.beautifulthailand.models.maps.Place;
import com.adrianlesniak.beautifulthailand.screens.nearby.NearbyPlacesAdapter;
import com.adrianlesniak.beautifulthailand.screens.nearby.OnPlaceClickListener;
import com.adrianlesniak.beautifulthailand.screens.shared.BaseFragment;
import com.adrianlesniak.beautifulthailand.screens.shared.ToolbarFragment;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

/**
 * Created by adrian on 02/02/2017.
 */

public class FavouritesFragment extends ToolbarFragment implements OnPlaceClickListener {

    @BindView(R.id.places_list)
    public RecyclerView mPlacesList;

    private RecyclerView.Adapter mAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();

        PlacesLocalDataSource.getInstance(getContext())
                .getFavouritePlaces()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Place>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<Place> placesList) {

                        mAdapter = new NearbyPlacesAdapter(getContext(), placesList, FavouritesFragment.this);
                        mPlacesList.setAdapter(mAdapter);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
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
    }

    @Override
    public void onPlaceClicked(Place place) {

    }
}
