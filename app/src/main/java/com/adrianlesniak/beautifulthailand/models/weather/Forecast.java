package com.adrianlesniak.beautifulthailand.models.weather;

import com.google.gson.annotations.SerializedName;

/**
 * Created by adrian on 22/02/2017.
 */

public class Forecast {

    public int dt;

    public Main main;

    public Weather weather;

    public Clouds clouds;

    public Wind wind;

    public Rain rain;

    public Snow snow;

    @SerializedName("dt_txt")
    public String dtTxt;
}
