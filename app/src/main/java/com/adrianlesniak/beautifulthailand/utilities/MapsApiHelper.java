package com.adrianlesniak.beautifulthailand.utilities;

import android.content.Context;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

import com.adrianlesniak.beautifulthailand.R;
import com.adrianlesniak.beautifulthailand.models.maps.DistanceMatrixElement;
import com.adrianlesniak.beautifulthailand.models.maps.DistanceMatrixResponse;
import com.adrianlesniak.beautifulthailand.models.maps.LatLng;
import com.adrianlesniak.beautifulthailand.models.maps.Place;
import com.adrianlesniak.beautifulthailand.models.maps.PlaceDetails;
import com.adrianlesniak.beautifulthailand.models.maps.PlaceDetailsResponse;
import com.adrianlesniak.beautifulthailand.models.maps.PlacesSearchResponse;
import com.adrianlesniak.beautifulthailand.utilities.cache.DistanceMatrixCache;
import com.adrianlesniak.beautifulthailand.utilities.cache.NearbyPlacesCache;
import com.adrianlesniak.beautifulthailand.utilities.cache.PlaceDetailsCache;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by adrian on 01/02/2017.
 */

public class MapsApiHelper {

    private static MapsApiHelper sInstance;

    private static final String URL_BASE = "https://maps.googleapis.com/maps/api/";

    private Context mContext;

    private String API_KEY = null;

    private OkHttpClient mClient;

    private Gson mGson;

    private MapsApiHelper(Context context) {
        this.mContext = context;
        this.API_KEY = mContext.getResources().getString(R.string.google_maps_api_key);
        this.mClient = new OkHttpClient();
        this.mGson = new Gson();
    }

    public static MapsApiHelper getInstance(Context context) {

        if(sInstance == null) {
            sInstance = new MapsApiHelper(context);
        }

        return sInstance;
    }

    public Observable getNearbyPlaces(final LatLng latLng, final int radius) {

        return Observable.create(new ObservableOnSubscribe<List<Place>>() {
            @Override
            public void subscribe(final ObservableEmitter<List<Place>> emitter) throws Exception {

                String uri = URL_BASE + "place/nearbysearch/json?location=" + String.valueOf(latLng.lat) + "," + String.valueOf(latLng.lng) + "&radius=" + String.valueOf(radius) + "&key=" + API_KEY;

                Request request = new Request.Builder()
                        .url(uri)
                        .build();

                mClient.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        emitter.onError(e);
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {

                        PlacesSearchResponse placesSearchResponse = mGson.fromJson(response.body().string(), PlacesSearchResponse.class);

                        if(placesSearchResponse.isSuccessful()) {

                            List<Place> placesList = Arrays.asList(placesSearchResponse.results);

                            emitter.onNext(placesList);
                            emitter.onComplete();
                        } else {
                            emitter.onError(new Throwable(placesSearchResponse.status));
                        }

                    }
                });
            }
        });
    }

    public Observable getPlaceDetails(final String placeId) {

        return Observable.create(new ObservableOnSubscribe<PlaceDetails>() {
            @Override
            public void subscribe(final ObservableEmitter<PlaceDetails> emitter) throws Exception {

                if(PlaceDetailsCache.getInstance().isCached(placeId)) {
                    emitter.onNext(PlaceDetailsCache.getInstance().getPlaceDetails(placeId));
                    emitter.onComplete();
                    return;
                }

                String uri = URL_BASE + "place/details/json?placeid=" + placeId + "&key=" +API_KEY;

                Request request = new Request.Builder()
                        .url(uri)
                        .build();

                mClient.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        emitter.onError(e);
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {

                        PlaceDetailsResponse placeDetailsResponse = mGson.fromJson(response.body().string(), PlaceDetailsResponse.class);

                        if(placeDetailsResponse.isSuccessful()) {

                            emitter.onNext(placeDetailsResponse.result);
                            emitter.onComplete();
                        } else {
                            emitter.onError(new Throwable(placeDetailsResponse.status));
                        }


                    }
                });
            }
        });
    }

    public void loadPhoto(final String photoReference, final View progressView, final ImageView target) {

        if(photoReference == null) {
            progressView.setVisibility(View.GONE);
            target.setScaleType(ImageView.ScaleType.CENTER);
            target.setImageDrawable(target.getContext().getResources().getDrawable(R.drawable.ic_image, null));
            return;
        }

        target.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {

                int maxPhotoWidth = target.getMeasuredWidth();

                String photoUrlRequest = URL_BASE + "place/photo?maxwidth=" + String.valueOf(maxPhotoWidth) + "&photoreference=" + photoReference + "&key=" + API_KEY;

                Picasso.with(mContext)
                        .load(photoUrlRequest)
                        .fit()
                        .centerCrop()
                        .into(target, new com.squareup.picasso.Callback() {
                            @Override
                            public void onSuccess() {
                                if(progressView != null) {
                                    progressView.setVisibility(View.GONE);
                                }
                            }

                            @Override
                            public void onError() {
                                if(progressView != null) {
                                    progressView.setVisibility(View.GONE);
                                }
                            }
                        });

                target.getViewTreeObserver().removeOnPreDrawListener(this);


                return false;
            }
        });
    }

    public Observable<List<DistanceMatrixElement>> getDistanceToPlaces(final LatLng origin, final List<Place> destinations) {

        return Observable.create(new ObservableOnSubscribe<List<DistanceMatrixElement>>() {
            @Override
            public void subscribe(final ObservableEmitter<List<DistanceMatrixElement>> emitter) throws Exception {

                StringBuilder destinationsValue = new StringBuilder();

                final int destinationsCount = destinations.size();
                for(int index=0;index<destinationsCount;index++) {
                    destinationsValue.append(destinations.get(index).geometry.location.lat);
                    destinationsValue.append(",");
                    destinationsValue.append(destinations.get(index).geometry.location.lng);
                    destinationsValue.append(index<destinationsCount-1 ? "|" : "");
                }

                final String distanceMatrixUrl = URL_BASE + "distancematrix/json?units=imperial&origins=" + origin.lat +  "," + origin.lng + "&destinations=" + destinationsValue.toString() + "&key=" + API_KEY;

                Request request = new Request.Builder()
                        .url(distanceMatrixUrl)
                        .build();

                mClient.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        emitter.onError(e);
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {

                        DistanceMatrixResponse distanceMatrixResponse = mGson.fromJson(response.body().string(), DistanceMatrixResponse.class);

                        if(distanceMatrixResponse.isSuccessful()) {

                            List<DistanceMatrixElement> distancesList = Arrays.asList(distanceMatrixResponse.rows[0].elements);

                            emitter.onNext(distancesList);
                            emitter.onComplete();
                        } else {
                            emitter.onError(new Throwable(distanceMatrixResponse.status));
                        }
                    }
                });
            }
        });

    }
}
