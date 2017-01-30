package com.adrianlesniak.beautifulthailand.utilities;

import android.content.Context;
import android.net.Uri;

import com.adrianlesniak.beautifulthailand.R;
import com.adrianlesniak.beautifulthailand.models.Photo;

/**
 * Created by adrian on 29/01/2017.
 */

public class BTUriProvider {

    private static final String PHOTO_BASE = "https://maps.googleapis.com/maps/api/place/photo";

    public static Uri getUriForPhoto(Context c, Photo photo) {

        return Uri.parse(PHOTO_BASE).
                buildUpon().
                appendQueryParameter("maxwidth", String.valueOf(photo.getWidth())).
                appendQueryParameter("photoreference", photo.getPhotoReference()).
                appendQueryParameter("key", c.getString(R.string.api_key)).
                build();
    }
}
