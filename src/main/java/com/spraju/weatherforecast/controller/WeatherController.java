package com.spraju.weatherforecast.controller;

import com.spraju.weatherforecast.exception.WeatherDBOutDatedException;
import com.spraju.weatherforecast.exception.WeatherMetricsNotFoundDB;
import com.spraju.weatherforecast.models.CurrentWeatherInfoMetrics;
import com.spraju.weatherforecast.service.GetWeatherMetricsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Logger;

@RestController
public class WeatherController {

    @Autowired
    GetWeatherMetricsService whetherService;

    @GetMapping("/currentWeather")
    public CurrentWeatherInfoMetrics get() throws WeatherMetricsNotFoundDB, WeatherDBOutDatedException {
        try {
            return whetherService.getCurrentWeatherInfo("delhi", 1694837180l);
        } catch (Exception e) {
            Logger.getLogger()
            throw new RuntimeException(e);
        }
    }

}
