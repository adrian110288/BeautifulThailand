package com.adrianlesniak.beautifulthailand.models.maps;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by adrian on 04/02/2017.
 */

public class Photo implements Parcelable {

    public int height;

    @SerializedName("html_attributions")
    public String[] htmlAttributions;

    @SerializedName("photo_reference")
    public String photo_reference;

    public int width;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.height);
        dest.writeStringArray(this.htmlAttributions);
        dest.writeString(this.photo_reference);
        dest.writeInt(this.width);
    }

    public Photo() {
    }

    protected Photo(Parcel in) {
        this.height = in.readInt();
        this.htmlAttributions = in.createStringArray();
        this.photo_reference = in.readString();
        this.width = in.readInt();
    }

    public static final Parcelable.Creator<Photo> CREATOR = new Parcelable.Creator<Photo>() {
        @Override
        public Photo createFromParcel(Parcel source) {
            return new Photo(source);
        }

        @Override
        public Photo[] newArray(int size) {
            return new Photo[size];
        }
    };
}
