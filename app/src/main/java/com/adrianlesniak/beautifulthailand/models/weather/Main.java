package com.adrianlesniak.beautifulthailand.models.weather;

import com.google.gson.annotations.SerializedName;

/**
 * Created by adrian on 22/02/2017.
 */

public class Main {

    public float temp;

    public float pressure;

    public float humidity;

    @SerializedName("temp_min")
    public float tempMin;

    @SerializedName("temp_max")
    public float tempMax;

    @SerializedName("sea_level")
    public float seaLevel;

    @SerializedName("grnd_level")
    public float grndLevel;
}
