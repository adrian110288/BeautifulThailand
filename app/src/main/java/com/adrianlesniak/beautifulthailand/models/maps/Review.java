package com.adrianlesniak.beautifulthailand.models.maps;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by adrian on 08/02/2017.
 */

public class Review implements Parcelable {

    @SerializedName("author_name")
    public String authorName;

    @SerializedName("author_url")
    public String authorUrl;

    public String language;

    public int rating;

    public String text;

    public int time;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.authorName);
        dest.writeString(this.authorUrl);
        dest.writeString(this.language);
        dest.writeInt(this.rating);
        dest.writeString(this.text);
        dest.writeInt(this.time);
    }

    public Review() {}

    protected Review(Parcel in) {
        this.authorName = in.readString();
        this.authorUrl = in.readString();
        this.language = in.readString();
        this.rating = in.readInt();
        this.text = in.readString();
        this.time = in.readInt();
    }

    public static final Parcelable.Creator<Review> CREATOR = new Parcelable.Creator<Review>() {
        @Override
        public Review createFromParcel(Parcel source) {
            return new Review(source);
        }

        @Override
        public Review[] newArray(int size) {
            return new Review[size];
        }
    };
}
