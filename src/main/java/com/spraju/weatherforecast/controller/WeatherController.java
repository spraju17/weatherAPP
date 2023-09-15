package com.spraju.weatherforecast.controller;

import com.spraju.weatherforecast.exception.WeatherDBOutDatedException;
import com.spraju.weatherforecast.exception.WeatherMetricsNotFoundDB;
import com.spraju.weatherforecast.models.CurrentWeatherInfoMetrics;
import com.spraju.weatherforecast.service.GetWeatherMetricsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WeatherController {

    @Autowired
    GetWeatherMetricsService whetherService;

    @GetMapping("/currentWeather")
    public CurrentWeatherInfoMetrics get() throws WeatherMetricsNotFoundDB, WeatherDBOutDatedException {
        return whetherService.getCurrentWeatherInfo("delhi", null);
        }

}
