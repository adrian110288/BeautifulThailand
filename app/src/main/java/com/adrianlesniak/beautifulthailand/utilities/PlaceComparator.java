package com.adrianlesniak.beautifulthailand.utilities;

import com.adrianlesniak.beautifulthailand.cache.DistanceMatrixCache;
import com.adrianlesniak.beautifulthailand.models.maps.DistanceMatrixRow;
import com.adrianlesniak.beautifulthailand.models.maps.Place;

import java.util.Comparator;

/**
 * Created by adrian on 12/02/2017.
 */

public class PlaceComparator implements Comparator<Place> {

    @Override
    public int compare(Place place1, Place place2) {

        DistanceMatrixRow.DistanceMatrixElement distanceMatrixElementToPlace1 = DistanceMatrixCache.getInstance().getDistance(place1.placeId);
        DistanceMatrixRow.DistanceMatrixElement distanceMatrixElementToPlace2 = DistanceMatrixCache.getInstance().getDistance(place2.placeId);

        if(distanceMatrixElementToPlace1 == null || distanceMatrixElementToPlace2 == null) {
            return -1;
        }

        if(distanceMatrixElementToPlace1.distance.value == distanceMatrixElementToPlace2.distance.value) {
            return 0;
        }

        return distanceMatrixElementToPlace1.distance.value > distanceMatrixElementToPlace2.distance.value ? 1 : -1;
    }
}
