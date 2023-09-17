package com.spraju.weatherforecast.controller;

import com.spraju.weatherforecast.constants.ActionType;
import com.spraju.weatherforecast.exception.WeatherDBOutDatedException;
import com.spraju.weatherforecast.exception.WeatherMetricsNotFoundDB;
import com.spraju.weatherforecast.handler.LogHandler;
import com.spraju.weatherforecast.models.CurrentWeatherInfoMetrics;
import com.spraju.weatherforecast.models.ForeCastWeatherInfoMetrics;
import com.spraju.weatherforecast.models.PerfMetrics;
import com.spraju.weatherforecast.models.WeatherInfoMetrics;
import com.spraju.weatherforecast.service.GetWeatherMetricsService;
import com.spraju.weatherforecast.service.impl.GetWeatherMetricsServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WeatherController {

    private static final Logger logger = LoggerFactory.getLogger(WeatherController.class);

    @Autowired
    GetWeatherMetricsServiceImpl whetherService;

    @Autowired
    @Qualifier("WeatherMetricsDBService")
    GetWeatherMetricsService service;

    @Autowired
    LogHandler logHandler;

    @GetMapping("/currentWeather")
    public CurrentWeatherInfoMetrics get() throws WeatherMetricsNotFoundDB, WeatherDBOutDatedException {
        Long currentTime = System.currentTimeMillis();
        try {
            CurrentWeatherInfoMetrics currentWeatherInfoMetrics = whetherService.getCurrentWeatherInfo("delhi", 1694837180l);
            logHandler.logPerfMetrics(new PerfMetrics(ActionType.GetCurrentWeather.toString(), true,  System.currentTimeMillis()- currentTime), logger );
            return currentWeatherInfoMetrics;
        } catch (Exception e) {
            logHandler.logPerfMetrics(new PerfMetrics(ActionType.GetCurrentWeather.toString(), false,  System.currentTimeMillis()- currentTime, e), logger );
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/foreCastWeather")
    public ForeCastWeatherInfoMetrics getForeCastWeather() throws WeatherMetricsNotFoundDB, WeatherDBOutDatedException {
        Long currentTime = System.currentTimeMillis();
        try {
            ForeCastWeatherInfoMetrics foreCastWeatherInfoMetrics = whetherService.getForeCastWeatherInfo("delhi");
            logHandler.logPerfMetrics(new PerfMetrics(ActionType.GetForeCastWeather.toString(), true,  System.currentTimeMillis()- currentTime), logger );
            return foreCastWeatherInfoMetrics;
        } catch (Exception e) {
            logHandler.logPerfMetrics(new PerfMetrics(ActionType.GetCurrentWeather.toString(), false,  System.currentTimeMillis()- currentTime, e), logger );
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/wethear")
    public WeatherInfoMetrics getWeather() throws WeatherMetricsNotFoundDB, WeatherDBOutDatedException {
        WeatherInfoMetrics weatherInfoMetrics = null;
        Long currentTime = System.currentTimeMillis();
        try {
            weatherInfoMetrics =  whetherService.getWeatherInfoMetrics("delhi");
            logHandler.logPerfMetrics(new PerfMetrics(ActionType.GetWeatherInfo.toString(), true,  System.currentTimeMillis()- currentTime), logger );
        }catch (Exception e){
            logHandler.logPerfMetrics(new PerfMetrics(ActionType.GetWeatherInfo.toString(), false,  System.currentTimeMillis()- currentTime), logger );
        }
        return  weatherInfoMetrics;
    }
}
