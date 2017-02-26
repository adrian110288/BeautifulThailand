package com.adrianlesniak.beautifulthailand.models.maps;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by adrian on 04/02/2017.
 */

public class Geometry implements Parcelable {
    public LatLng location;

    public static class LatLng implements Parcelable {

        public double lat;

        public double lng;

        /**
         * Construct a location with a latitude longitude pair.
         */
        public LatLng() {}
        public LatLng(double lat, double lng) {
            this.lat = lat;
            this.lng = lng;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeDouble(this.lat);
            dest.writeDouble(this.lng);
        }

        protected LatLng(Parcel in) {
            this.lat = in.readDouble();
            this.lng = in.readDouble();
        }

        public static final Creator<LatLng> CREATOR = new Creator<LatLng>() {
            @Override
            public LatLng createFromParcel(Parcel source) {
                return new LatLng(source);
            }

            @Override
            public LatLng[] newArray(int size) {
                return new LatLng[size];
            }
        };
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.location, flags);
    }

    public Geometry() {
    }

    protected Geometry(Parcel in) {
        this.location = in.readParcelable(LatLng.class.getClassLoader());
    }

    public static final Parcelable.Creator<Geometry> CREATOR = new Parcelable.Creator<Geometry>() {
        @Override
        public Geometry createFromParcel(Parcel source) {
            return new Geometry(source);
        }

        @Override
        public Geometry[] newArray(int size) {
            return new Geometry[size];
        }
    };
}
