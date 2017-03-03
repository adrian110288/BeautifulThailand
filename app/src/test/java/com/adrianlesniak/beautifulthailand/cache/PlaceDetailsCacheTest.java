package com.adrianlesniak.beautifulthailand.cache;

import com.adrianlesniak.beautifulthailand.models.maps.Place;
import com.adrianlesniak.beautifulthailand.models.maps.PlaceDetails;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.junit.Assert.*;

/**
 * Created by adrian on 03/03/2017.
 */
public class PlaceDetailsCacheTest {

    private final String TEST_PLACE_ID = "place_101";

    private PlaceDetailsCache mPlaceDetailsCache;

    @Before
    public void setUp() throws Exception {
        mPlaceDetailsCache = PlaceDetailsCache.getInstance();
    }

    @Test
    public void addPlaceDetails() throws Exception {

        PlaceDetails placeDetails = new PlaceDetails();
        placeDetails.placeId = TEST_PLACE_ID;

        mPlaceDetailsCache.addPlaceDetails(placeDetails);

        assertEquals(1, mPlaceDetailsCache.size());
    }

    @Test
    public void getPlaceDetails() throws Exception {

    }

    @Test
    public void isCached() throws Exception {

    }

}