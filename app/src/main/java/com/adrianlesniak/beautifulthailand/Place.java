package com.adrianlesniak.beautifulthailand;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by adrian on 21/01/2017.
 */

public class Place implements Parcelable {

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

    public Place(Parcel parcel) {
        this.mId = parcel.readString();
        this.mPlaceId = parcel.readString();
        this.mName = parcel.readString();

        this.mPhotos = new ArrayList<>();
        parcel.readTypedList(this.mPhotos, Photo.CREATOR);
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mId);
        dest.writeString(this.mPlaceId);
        dest.writeString(this.mName);
        dest.writeTypedList(this.mPhotos);
    }

    public static final Parcelable.Creator<Place> CREATOR = new Parcelable.Creator<Place>() {
        public Place createFromParcel(Parcel in) {
            return new Place(in);
        }

        public Place[] newArray(int size) {
            return new Place[size];
        }
    };

    public static class Photo implements Parcelable {

        private int mWidth;

        private int mHeight;

        private String mPhotoReference;

        public Photo(JSONObject photoData) throws JSONException {
            this.mWidth = photoData.getInt("width");
            this.mHeight = photoData.getInt("height");
            this.mPhotoReference = photoData.getString("photo_reference");
        }

        public Photo(Parcel parcel) {
            this.mWidth = parcel.readInt();
            this.mHeight = parcel.readInt();
            this.mPhotoReference = parcel.readString();
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

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.mWidth);
            dest.writeInt(this.mHeight);
            dest.writeString(this.mPhotoReference);
        }

        public static final Parcelable.Creator<Photo> CREATOR = new Parcelable.Creator<Photo>() {
            public Photo createFromParcel(Parcel in) {
                return new Photo(in);
            }

            public Photo[] newArray(int size) {
                return new Photo[size];
            }
        };
    }
}
