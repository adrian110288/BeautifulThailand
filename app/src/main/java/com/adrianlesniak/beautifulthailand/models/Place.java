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

    private Location mLocation;

    private RealmList<Photo> photosList;

    private String formattedAddress;

    private String formattedPhoneNumber;

    private double rating;

    private String website;

    private boolean openNow;

    private boolean isFavourite;

    private boolean toVisit;

    public Place(){}

    public Place(JSONObject placeData) throws JSONException {

        this.id = placeData.getString("id");
        this.placeId = placeData.getString("place_id");
        this.name = placeData.getString("name");

        JSONObject geometry = placeData.getJSONObject("geometry");
        JSONObject location = geometry.getJSONObject("location");
        this.mLocation = new Location(location);

        JSONArray photosArray = placeData.has("photos")? placeData.getJSONArray("photos") : null;
        parsePhotos(photosArray);

        this.formattedAddress = placeData.has("formatted_address") ? placeData.getString("formatted_address") : null;

        this.formattedPhoneNumber = placeData.has("formatted_phone_number") ? placeData.getString("formatted_phone_number") : null;

//        this.rating = placeData.has("rating") ? placeData.getDouble("rating") : null;

        this.website = placeData.has("website") ? placeData.getString("website") : null;

        this.openNow = placeData.has("opening_hours") ? placeData.has("open_now") ? placeData.getBoolean("open_now") : false : false;

        this.isFavourite = false;
        this.toVisit = false;
    }

    public Place(Parcel parcel) {
        this.id = parcel.readString();
        this.placeId = parcel.readString();
        this.name = parcel.readString();

        this.photosList = new RealmList<>();

        parcel.readTypedList(this.photosList, Photo.CREATOR);

        this.formattedAddress = parcel.readString();

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

    public Location getLocation() {
        return this.mLocation;
    }

    public List<Photo> getPhotosList() {
        return this.photosList;
    }

    public String getAddress() {
        return this.formattedAddress;
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
        dest.writeString(this.formattedAddress);
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
