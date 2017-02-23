package com.adrianlesniak.beautifulthailand.models.weather;

import java.util.List;

/**
 * Created by adrian on 22/02/2017.
 */

public class FiveDayForecastResponse extends OpenWeatherMapResponse {

    public City city;

    public List<Forecast> list;
}
