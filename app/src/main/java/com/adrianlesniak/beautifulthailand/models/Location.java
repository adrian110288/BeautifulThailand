package com.adrianlesniak.beautifulthailand.models;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

import io.realm.RealmObject;

/**
 * Created by adrian on 23/01/2017.
 */

public class Location extends RealmObject implements Parcelable {

    private double latitude;

    private double longitude;

    public Location() {}

    public Location(JSONObject locationData) throws JSONException {

        this.latitude = locationData.getDouble("lat");
        this.longitude = locationData.getDouble("lng");
    }

    public Location(Parcel parcel) {
        this.latitude = parcel.readDouble();
        this.longitude = parcel.readDouble();
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(float mLatitude) {
        this.latitude = mLatitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(float mLongitude) {
        this.longitude = mLongitude;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(this.latitude);
        dest.writeDouble(this.longitude);
    }

    public static final Parcelable.Creator<Location> CREATOR = new Parcelable.Creator<Location>() {
        public Location createFromParcel(Parcel in) {
            return new Location(in);
        }

        public Location[] newArray(int size) {
            return new Location[size];
        }
    };
}

