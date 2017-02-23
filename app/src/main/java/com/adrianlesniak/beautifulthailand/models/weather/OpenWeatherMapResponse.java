package com.adrianlesniak.beautifulthailand.models.weather;

import com.adrianlesniak.beautifulthailand.models.ApiCallResponse;

/**
 * Created by adrian on 22/02/2017.
 */

public class OpenWeatherMapResponse implements ApiCallResponse {

    public int cod;

    @Override
    public boolean isSuccessful() {
        return cod >=200 || cod < 300;
    }
}
