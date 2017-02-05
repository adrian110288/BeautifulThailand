package com.adrianlesniak.beautifulthailand.utilities;

import android.content.Context;
import android.widget.ImageView;

import com.adrianlesniak.beautifulthailand.R;
import com.adrianlesniak.beautifulthailand.models.LatLng;
import com.adrianlesniak.beautifulthailand.models.PlaceDetailsResponse;
import com.adrianlesniak.beautifulthailand.models.PlacesSearchResponse;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.io.IOException;

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

    private MapsApiHelper(Context context) {
        this.mContext = context;
        this.API_KEY = mContext.getResources().getString(R.string.api_key);
        this.mClient = new OkHttpClient();
    }

    public static MapsApiHelper getInstance(Context context) {

        if(sInstance == null) {
            sInstance = new MapsApiHelper(context);
        }

        return sInstance;
    }

    public Observable getNearbyPlaces(final LatLng latLng, final int radius) {

        return Observable.create(new ObservableOnSubscribe<PlacesSearchResponse>() {
            @Override
            public void subscribe(final ObservableEmitter<PlacesSearchResponse> emitter) throws Exception {

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

                        Gson gson = new Gson();
                        PlacesSearchResponse placesSearchResponse = gson.fromJson(response.body().string(), PlacesSearchResponse.class);

                        emitter.onNext(placesSearchResponse);
                        emitter.onComplete();
                    }
                });
            }
        });
    }

    public Observable getPlaceDetails(final String placeId) {

        return Observable.create(new ObservableOnSubscribe<PlaceDetailsResponse>() {
            @Override
            public void subscribe(final ObservableEmitter<PlaceDetailsResponse> emitter) throws Exception {

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

                        Gson gson = new Gson();
                        PlaceDetailsResponse placeDetailsResponse = gson.fromJson(response.body().string(), PlaceDetailsResponse.class);

                        emitter.onNext(placeDetailsResponse);
                        emitter.onComplete();
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

}
