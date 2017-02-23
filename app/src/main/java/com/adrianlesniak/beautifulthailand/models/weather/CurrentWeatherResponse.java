package com.adrianlesniak.beautifulthailand.models.weather;

import java.util.List;

/**
 * Created by adrian on 15/02/2017.
 */

public class CurrentWeatherResponse extends OpenWeatherMapResponse{

    public Coordinates coord;

    public List<Weather> weather;

    public String base;

    public Main main;

    public Wind wind;

    public Clouds clouds;

    public int dt;

    public Sys sys;

    public int id;

    public String name;
}
