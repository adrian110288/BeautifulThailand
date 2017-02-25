package com.adrianlesniak.beautifulthailand.db;

import android.provider.BaseColumns;

/**
 * Created by adrian on 24/02/2017.
 */

public class BTContract {

    private BTContract() {}

    public static class Place implements BaseColumns {
        public static final String TABLE_NAME = "favourite_places";
        public static final String COLUMN_NAME_LAT_LNG = "location";
        public static final String COLUMN_NAME_PLACE_NAME = "name";
        public static final String COLUMN_NAME_ICON = "icon";
        public static final String COLUMN_NAME_PLACE_ID = "place_id";
        public static final String COLUMN_NAME_RATING = "rating";
        public static final String COLUMN_NAME_TYPES = "types";
        public static final String COLUMN_NAME_VICINITY = "vicinity";
        public static final String COLUMN_NAME_CLOSED = "permanently_closed";
    }

    public static class Photo implements BaseColumns {
        public static final String TABLE_NAME = "photos";
        public static final String COLUMN_NAME_HEIGHT = "height";
        public static final String COLUMN_NAME_WIDTH = "width";
        public static final String COLUMN_NAME_PHOTO_REFERENCE = "photo_reference";
        public static final String COLUMN_NAME_PLACE_ID = "place_id";
    }
}
