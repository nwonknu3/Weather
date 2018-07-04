package com.develogical;

import com.weather.Day;
import com.weather.Forecast;
import com.weather.Region;

import java.util.HashMap;

public class Cacher implements MyForecaster {
    private MyForecaster delegate;
    HashMap<String, Forecast> cache= new HashMap<String, Forecast>();



    int maxCache;
    public Cacher(MyForecaster delegate, int maxCache, int intervalCacheInterval) {
        this.delegate = delegate;
        this.maxCache = maxCache;
    }


    @Override
    public Forecast forecastFor(Region region, Day day) {

        String lookUpValue = region.toString() + "_" + day.toString();

        if(cache.size() == maxCache) {
            cache.clear();
        }

        if (! cache.containsKey(lookUpValue)) {

            Forecast forecast = delegate.forecastFor(region, day);
            cache.put(lookUpValue, forecast);
            return forecast;
        }

        return cache.get(lookUpValue);
    }


}
