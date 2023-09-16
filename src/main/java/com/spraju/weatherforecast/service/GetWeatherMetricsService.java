package com.spraju.weatherforecast.service;

import com.spraju.weatherforecast.exception.WeatherDBOutDatedException;
import com.spraju.weatherforecast.exception.WeatherMetricsNotFoundDB;
import com.spraju.weatherforecast.models.CurrentWeatherInfoMetrics;
import com.spraju.weatherforecast.models.ForeCastWeatherInfoMetrics;

public interface GetWeatherMetricsService {

    public ForeCastWeatherInfoMetrics getForeCastWeatherInfo(String location);

    public CurrentWeatherInfoMetrics getCurrentWeatherInfo(String location, Long unixTimeStamp) throws WeatherMetricsNotFoundDB, WeatherDBOutDatedException;

    default public Long getCurrentTimeInUnqix(){
        long currentTimeMillis = System.currentTimeMillis();

        // Convert milliseconds to seconds by dividing by 1000
        long currentTimestampSeconds = currentTimeMillis / 1000;

        return currentTimestampSeconds;
    }

}
