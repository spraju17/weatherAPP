package com.spraju.weatherforecast.service;

import com.spraju.weatherforecast.exception.WeatherDBOutDatedException;
import com.spraju.weatherforecast.exception.WeatherMetricsNotFoundDB;
import com.spraju.weatherforecast.models.CurrentWeatherInfoMetrics;
import com.spraju.weatherforecast.models.ForeCastWeatherInfoMetrics;

public interface GetWeatherMetricsService {

    public ForeCastWeatherInfoMetrics getForeCastWeatherInfo(String location);

    public CurrentWeatherInfoMetrics getCurrentWeatherInfo(String location, Long unixTimeStamp) throws WeatherMetricsNotFoundDB, WeatherDBOutDatedException;

}
