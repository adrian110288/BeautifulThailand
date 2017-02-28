package com.adrianlesniak.beautifulthailand.models.maps;

import android.os.Parcel;
import android.os.Parcelable;

import com.adrianlesniak.beautifulthailand.models.NullableObject;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by adrian on 04/02/2017.
 */

public class Place implements Parcelable, NullableObject {

    @SerializedName("place_id")
    public String placeId;

    public String name;

    public Geometry geometry;

//    public URL icon;

    public float rating;

    public String types[];

//    @SerializedName("opening_hours")
//    public OpeningHours openingHours;

    public List<Photo> photos;

    public String vicinity;

    @SerializedName("permanently_closed")
    public boolean permanentlyClosed;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.placeId);
        dest.writeString(this.name);
        dest.writeParcelable(this.geometry, flags);
        dest.writeFloat(this.rating);
        dest.writeStringArray(this.types);
        dest.writeTypedList(this.photos);
        dest.writeString(this.vicinity);
        dest.writeByte(this.permanentlyClosed ? (byte) 1 : (byte) 0);
    }

    public Place() {
    }

    protected Place(Parcel in) {
        this.placeId = in.readString();
        this.name = in.readString();
        this.geometry = in.readParcelable(Geometry.class.getClassLoader());
        this.rating = in.readFloat();
        this.types = in.createStringArray();
        this.photos = in.createTypedArrayList(Photo.CREATOR);
        this.vicinity = in.readString();
        this.permanentlyClosed = in.readByte() != 0;
    }

    public static final Parcelable.Creator<Place> CREATOR = new Parcelable.Creator<Place>() {
        @Override
        public Place createFromParcel(Parcel source) {
            return new Place(source);
        }

        @Override
        public Place[] newArray(int size) {
            return new Place[size];
        }
    };

    @Override
    public boolean isNull() {
        return false;
    }
}
