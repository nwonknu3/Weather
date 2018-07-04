package com.develogical;

import com.weather.Day;
import com.weather.Forecast;
import com.weather.Region;

import java.util.Date;
import java.util.HashMap;

public class Cacher implements MyForecaster {
    private MyForecaster delegate;
    HashMap<String, Forecast> cache= new HashMap<String, Forecast>();

    HashMap<String, Long> mapTimeForecast= new HashMap<String, Long>();


    int maxCache;
    int intervalCacheRefresh;

    public Cacher(MyForecaster delegate, int maxCache, int intervalCacheRefresh) {
        this.delegate = delegate;
        this.maxCache = maxCache;
        this.intervalCacheRefresh = intervalCacheRefresh;
    }


    @Override
    public Forecast forecastFor(Region region, Day day) {

        String lookUpValue = region.toString() + "_" + day.toString();

        if(cache.size() == maxCache) {
            cache.clear();
        }

        long currentTime = System.currentTimeMillis() % 1000;

        long timePassed = mapTimeForecast.get(lookUpValue) - currentTime;

        if (! cache.containsKey(lookUpValue) || timePassed >= intervalCacheRefresh) {


            Forecast forecast = delegate.forecastFor(region, day);
            cache.put(lookUpValue, forecast);

            mapTimeForecast.put(lookUpValue, System.currentTimeMillis() % 1000);

            return forecast;
        }

        return cache.get(lookUpValue);
    }


}
