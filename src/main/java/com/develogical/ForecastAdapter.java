package com.develogical;


import com.weather.Day;
import com.weather.Forecast;
import com.weather.Forecaster;
import com.weather.Region;

import java.util.HashMap;


public class ForecastAdapter implements MyForecaster {
    @Override
    public Forecast forecastFor(Region region, Day day) {
        return new Forecaster().forecastFor(region, day);
    }
}
