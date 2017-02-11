package com.adrianlesniak.beautifulthailand.utilities;

import android.content.Context;
import android.widget.ImageView;

import com.adrianlesniak.beautifulthailand.R;
import com.adrianlesniak.beautifulthailand.models.maps.DistanceMatrixResponse;
import com.adrianlesniak.beautifulthailand.models.maps.LatLng;
import com.adrianlesniak.beautifulthailand.models.maps.Place;
import com.adrianlesniak.beautifulthailand.models.maps.PlaceDetails;
import com.adrianlesniak.beautifulthailand.models.maps.PlaceDetailsResponse;
import com.adrianlesniak.beautifulthailand.models.maps.PlacesSearchResponse;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;
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

                if(!NearbyPlacesCache.getsInstance().isCacheEmpty()) {
                    emitter.onNext(NearbyPlacesCache.getsInstance().getCache());
                    emitter.onComplete();
                    return;
                }

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

                            NearbyPlacesCache.getsInstance().
                                    setCache(placesList);

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

                            PlaceDetailsCache.getInstance().
                                    addPlaceDetails(placeDetailsResponse.result);

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

    public void loadPhoto(String photoReference, int maxWidth, ImageView target) {

        String photoUrlRequest = URL_BASE + "place/photo?maxwidth=" + String.valueOf(maxWidth) + "&photoreference=" + photoReference + "&key=" + API_KEY;

        Picasso.with(this.mContext)
            .load(photoUrlRequest)
            .fit()
            .centerCrop()
            .into(target);
    }

    public Single<DistanceMatrixResponse> getDistanceToPlace(final LatLng currentLocation, final LatLng placeLocation) {

        return Single.create(new SingleOnSubscribe<DistanceMatrixResponse>() {
            @Override
            public void subscribe(final SingleEmitter<DistanceMatrixResponse> emitter) throws Exception {

                String distanceMatrixUrl = URL_BASE + "distancematrix/json?units=imperial&origins=" + currentLocation.lat +  "," + currentLocation.lng + "&destinations=" + placeLocation.lat + "," + placeLocation.lng + "&key=" + API_KEY;

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
                        emitter.onSuccess(distanceMatrixResponse);
                    }
                });
            }
        });

    }
}
