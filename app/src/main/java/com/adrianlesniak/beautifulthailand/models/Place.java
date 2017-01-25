package com.adrianlesniak.beautifulthailand.models;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by adrian on 23/01/2017.
 */

public class Place extends RealmObject implements Parcelable, ListItem {

    @PrimaryKey
    private String id;

    private String placeId;

    private String name;

    private RealmList<Photo> photosList;

    private boolean isFavourite;

    private boolean toVisit;

    public Place(){}

    public Place(JSONObject mPlaceData) throws JSONException {

        this.id = mPlaceData.getString("id");
        this.placeId = mPlaceData.getString("place_id");
        this.name = mPlaceData.getString("name");

        JSONArray photosArray = mPlaceData.has("photos")? mPlaceData.getJSONArray("photos") : null;
        parsePhotos(photosArray);

        this.isFavourite = false;
        this.toVisit = false;
    }

    public Place(Parcel parcel) {
        this.id = parcel.readString();
        this.placeId = parcel.readString();
        this.name = parcel.readString();

        this.photosList = new RealmList<>();

        parcel.readTypedList(this.photosList, Photo.CREATOR);

        this.isFavourite = parcel.readByte() != 0;
        this.toVisit = parcel.readByte() != 0;
    }

    private void parsePhotos(JSONArray photosArray) throws JSONException {

        if(photosArray != null) {

            this.photosList = new RealmList<>();

            for (int index = 0; index < photosArray.length(); index++) {

                Photo photo = new Photo(photosArray.getJSONObject(index));
                this.photosList.add(photo);
            }
        }
    }

    public String getId() {
        return this.id;
    }

    public String getPlaceId() {
        return this.placeId;
    }

    public String getName() {
        return this.name;
    }

    public List<Photo> getPhotosList() {
        return this.photosList;
    }

    public boolean getIsFavourite() {
        return this.isFavourite;
    }

    public void setIsFavourite(boolean favourite) {
        this.isFavourite = favourite;
    }

    public boolean getToVisit() {
        return this.toVisit;
    }

    public void setToVisit(boolean visit) {
        this.toVisit= visit;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.placeId);
        dest.writeString(this.name);
        dest.writeTypedList(this.photosList);
        dest.writeByte((byte)(this.isFavourite? 0x01 : 0x00));
        dest.writeByte((byte)(this.toVisit? 0x01 : 0x00));
    }

    public static final Parcelable.Creator<Place> CREATOR = new Parcelable.Creator<Place>() {
        public Place createFromParcel(Parcel in) {
            return new Place(in);
        }

        public Place[] newArray(int size) {
            return new Place[size];
        }
    };
}
