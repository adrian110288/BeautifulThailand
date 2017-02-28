package com.adrianlesniak.beautifulthailand.models;

/**
 * Created by adrian on 28/02/2017.
 */

public class NullPlace implements NullableObject {
    @Override
    public boolean isNull() {
        return true;
    }
}
