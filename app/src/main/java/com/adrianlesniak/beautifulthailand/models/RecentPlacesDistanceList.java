package com.adrianlesniak.beautifulthailand.models;

import com.adrianlesniak.beautifulthailand.models.maps.DistanceMatrixResponse;

import java.util.HashMap;

/**
 * Created by adrian on 06/02/2017.
 */

public class RecentPlacesDistanceList extends HashMap<String, DistanceMatrixResponse> {

    private static RecentPlacesDistanceList sInstance;

    private RecentPlacesDistanceList(){ }

    public static RecentPlacesDistanceList getInstance() {

        if(sInstance == null) {
            sInstance = new RecentPlacesDistanceList();
        }

        return sInstance;
    }
}
