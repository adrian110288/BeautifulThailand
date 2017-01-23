package com.adrianlesniak.beautifulthailand.models;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by adrian on 23/01/2017.
 */

public class Photo extends RealmObject implements Parcelable {

    @PrimaryKey
    private String photoReference;
    private int width;
    private int height;

    public Photo() {}

    public Photo(JSONObject photoData) throws JSONException {
        this.width = photoData.getInt("width");
        this.height = photoData.getInt("height");
        this.photoReference = photoData.getString("photo_reference");
    }

    public Photo(Parcel parcel) {
        this.width = parcel.readInt();
        this.height = parcel.readInt();
        this.photoReference = parcel.readString();
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public String getPhotoReference() {
        return this.photoReference;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.width);
        dest.writeInt(this.height);
        dest.writeString(this.photoReference);
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