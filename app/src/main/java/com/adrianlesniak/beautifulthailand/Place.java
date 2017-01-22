package com.adrianlesniak.beautifulthailand;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by adrian on 21/01/2017.
 */

public class Place {

    private String mId;

    private String mPlaceId;

    private String mName;

    private List<Photo> mPhotos;

    public Place(JSONObject mPlaceData) throws JSONException {

        this.mId = mPlaceData.getString("id");
        this.mPlaceId = mPlaceData.getString("place_id");
        this.mName = mPlaceData.getString("name");

        JSONArray photosArray = mPlaceData.has("photos")? mPlaceData.getJSONArray("photos") : null;
        parsePhotos(photosArray);
    }

    private void parsePhotos(JSONArray photosArray) throws JSONException {

        if(photosArray != null) {

            this.mPhotos = new ArrayList<>(photosArray.length());

            for (int index = 0; index < photosArray.length(); index++) {

                Photo photo = new Photo(photosArray.getJSONObject(index));
                this.mPhotos.add(photo);
            }
        }
    }

    public String getId() {
        return this.mId;
    }

    public String getPlaceId() {
        return this.mPlaceId;
    }

    public String getName() {
        return this.mName;
    }

    public List<Photo> getPhotos() {
        return this.mPhotos;
    }

    public static class Photo {

        private int mWidth;

        private int mHeight;

        private String mPhotoReference;

        public Photo(JSONObject photoData) throws JSONException {
            this.mWidth = photoData.getInt("width");
            this.mHeight = photoData.getInt("height");
            this.mPhotoReference = photoData.getString("photo_reference");
        }

        public int getWidth() {
            return this.mWidth;
        }

        public int getHeight() {
            return this.mHeight;
        }

        public String getPhotoReference() {
            return this.mPhotoReference;
        }
    }
}
