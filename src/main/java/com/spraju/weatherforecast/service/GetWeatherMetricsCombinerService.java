package com.spraju.weatherforecast.service;

import com.spraju.weatherforecast.exception.WeatherDBOutDatedException;
import com.spraju.weatherforecast.exception.WeatherMetricsNotFoundDB;
import com.spraju.weatherforecast.models.WeatherInfoMetrics;

public interface GetWeatherMetricsCombinerService extends  GetWeatherMetricsService{
    public WeatherInfoMetrics getWeatherInfoMetrics(String location) throws WeatherMetricsNotFoundDB, WeatherDBOutDatedException;
}
