package com.develogical;

import com.weather.Day;
import com.weather.Forecast;
import com.weather.Region;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.*;

public class PutYourTestCodeInThisDirectoryTest {
    @Test
    public void getsForecastFromDelegateIfDoesNotAlreadyKnow() throws Exception {
        MyForecaster delegate = mock(MyForecaster.class);
        when(delegate.forecastFor(Region.LONDON, Day.MONDAY)).thenReturn(new Forecast("hot", 32));
        int maxCache = 5;
        MyForecaster myforecaster = new Cacher(delegate, maxCache);

        Forecast forecast = myforecaster.forecastFor(Region.LONDON, Day.MONDAY);
        assertEquals("hot", forecast.summary());
        assertEquals(32, forecast.temperature());

        verify(delegate).forecastFor(Region.LONDON, Day.MONDAY);
    }

    @Test
    public void getsForecastFromCacheIfDoesAlreadyKnow() throws Exception {
        MyForecaster delegate = mock(MyForecaster.class);
        when(delegate.forecastFor(Region.LONDON, Day.MONDAY)).thenReturn(new Forecast("hot", 32));

        int maxCache = 5;
        MyForecaster myforecaster = new Cacher(delegate, maxCache);

        Forecast forecast = myforecaster.forecastFor(Region.LONDON, Day.MONDAY);
        assertEquals("hot", forecast.summary());
        assertEquals(32, forecast.temperature());

        Forecast forecastCached = myforecaster.forecastFor(Region.LONDON, Day.MONDAY);

        assertEquals("hot", forecastCached.summary());
        assertEquals(32, forecastCached.temperature());

        verify(delegate, times(1)).forecastFor(Region.LONDON, Day.MONDAY);
    }



    @Test
    public void getsForecastFromCacheIfExceedLimitCallTwoTimes() throws Exception {
        MyForecaster delegate = mock(MyForecaster.class);
        int maxCache = 2;

        MyForecaster myforecaster = new Cacher(delegate, maxCache);

        myforecaster.forecastFor(Region.BIRMINGHAM, Day.MONDAY);

        myforecaster.forecastFor(Region.LONDON, Day.MONDAY);

        myforecaster.forecastFor(Region.BIRMINGHAM, Day.MONDAY);

        verify(delegate, times(2)).forecastFor(Region.BIRMINGHAM, Day.MONDAY);
    }


    @Test
    public void getsForecastFromCacheIfExceedLimitCallTwoTimesOneCached() throws Exception {
        MyForecaster delegate = mock(MyForecaster.class);
        int maxCache = 2;
        MyForecaster myforecaster = new Cacher(delegate, maxCache);
        myforecaster.forecastFor(Region.BIRMINGHAM, Day.MONDAY);
        myforecaster.forecastFor(Region.LONDON, Day.MONDAY);
        myforecaster.forecastFor(Region.BIRMINGHAM, Day.MONDAY);
        myforecaster.forecastFor(Region.BIRMINGHAM, Day.MONDAY);
        verify(delegate, times(2)).forecastFor(Region.BIRMINGHAM, Day.MONDAY);
    }



    @Test
    public void getsForecastFromDelegateErasedOldCache() throws Exception {
        MyForecaster delegate = mock(MyForecaster.class);
        int maxCache = 2;




        MyForecaster myforecaster = new Cacher(delegate, maxCache);
        myforecaster.forecastFor(Region.BIRMINGHAM, Day.MONDAY);
        myforecaster.forecastFor(Region.LONDON, Day.MONDAY);
        myforecaster.forecastFor(Region.BIRMINGHAM, Day.MONDAY);
        myforecaster.forecastFor(Region.BIRMINGHAM, Day.MONDAY);
        verify(delegate, times(2)).forecastFor(Region.BIRMINGHAM, Day.MONDAY);

        




    }


}
