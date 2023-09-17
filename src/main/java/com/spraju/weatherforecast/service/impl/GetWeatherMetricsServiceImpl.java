package com.spraju.weatherforecast.service.impl;

import com.spraju.weatherforecast.exception.WeatherDBOutDatedException;
import com.spraju.weatherforecast.exception.WeatherMetricsNotFoundDB;
import com.spraju.weatherforecast.models.CurrentWeatherInfoMetrics;
import com.spraju.weatherforecast.models.ForeCastWeatherInfoMetrics;
import com.spraju.weatherforecast.models.WeatherInfoMetrics;
import com.spraju.weatherforecast.service.GetWeatherMetricsCombinerService;
import com.spraju.weatherforecast.service.GetWeatherMetricsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
@Primary
public class GetWeatherMetricsServiceImpl implements GetWeatherMetricsCombinerService {

    @Value("${tolerence}")
    private Long tolerence;

    @Autowired
    @Qualifier("WeatherMetricsDBService")
    GetWeatherMetricsService weatherMetricsDBService;

    @Autowired
    @Qualifier("WeatherMetricsFromExternalService")
    GetWeatherMetricsService weatherMetricsFromExternalService;


    public ForeCastWeatherInfoMetrics getForeCastWeatherInfo(String location) throws WeatherMetricsNotFoundDB {
        return weatherMetricsDBService.getForeCastWeatherInfo(location);
    }

    public CurrentWeatherInfoMetrics getCurrentWeatherInfo(String location, Long unixTimeStamp) throws WeatherMetricsNotFoundDB, WeatherDBOutDatedException {
        return weatherMetricsDBService.getCurrentWeatherInfo(location, unixTimeStamp);
    }

    public WeatherInfoMetrics getWeatherInfoMetrics(String location) throws WeatherMetricsNotFoundDB, WeatherDBOutDatedException {
        CurrentWeatherInfoMetrics currentWeatherInfoMetrics = getCurrentWeatherInfo(location.toUpperCase(Locale.ROOT), getCurrentTimeInUnqix());
        ForeCastWeatherInfoMetrics foreCastWeatherInfoMetrics = getForeCastWeatherInfo(location.toUpperCase(Locale.ROOT));

        return new WeatherInfoMetrics(foreCastWeatherInfoMetrics, currentWeatherInfoMetrics);
    }

}
