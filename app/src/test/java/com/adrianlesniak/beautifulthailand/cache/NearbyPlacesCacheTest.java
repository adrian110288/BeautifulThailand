package com.adrianlesniak.beautifulthailand.cache;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.junit.Assert.*;

/**
 * Created by adrian on 03/03/2017.
 */
public class NearbyPlacesCacheTest {

    private NearbyPlacesCache mNearbyPlacesCache;

    @Before
    public void setUp() throws Exception {
        this.mNearbyPlacesCache = NearbyPlacesCache.getInstance();
    }

    @Test
    public void clearCache() throws Exception {

        mNearbyPlacesCache.clearCache();

        assertEquals(0, mNearbyPlacesCache.getCache().size());
    }

    @Test
    public void isCacheEmpty() throws Exception {

        mNearbyPlacesCache.clearCache();

        assertTrue(mNearbyPlacesCache.getCache().size() == 0);

    }

}