package com.spraju.weatherforecast.service.impl;

import com.spraju.weatherforecast.exception.WeatherDBOutDatedException;
import com.spraju.weatherforecast.exception.WeatherMetricsNotFoundDB;
import com.spraju.weatherforecast.models.CurrentWeatherInfoMetrics;
import com.spraju.weatherforecast.models.ForeCastWeatherInfoMetrics;
import com.spraju.weatherforecast.service.GetWeatherMetricsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public class GetWeatherMetricsServiceImpl implements GetWeatherMetricsService {

    @Value("${tolerence}")
    private Long tolerence;

    @Autowired
    @Qualifier("WeatherMetricsDBService")
    GetWeatherMetricsService weatherMetricsDBService;

    @Autowired
    @Qualifier("WeatherMetricsFromExternalService")
    GetWeatherMetricsService weatherMetricsFromExternalService;


    @Override
    public ForeCastWeatherInfoMetrics getForeCastWeatherInfo(String location) {
        return null;
    }

    @Override
    public CurrentWeatherInfoMetrics getCurrentWeatherInfo(String location, Long unixTimeStamp) throws WeatherMetricsNotFoundDB, WeatherDBOutDatedException {
        CurrentWeatherInfoMetrics currentWeatherInfoMetrics = null;
        try {
            currentWeatherInfoMetrics = weatherMetricsDBService.getCurrentWeatherInfo(location, getCurrentTimeInUnqix());
            if ((unixTimeStamp - currentWeatherInfoMetrics.getCurrentEpochTimeStamp()) > tolerence)
                throw new WeatherDBOutDatedException("outated");
        }catch (WeatherMetricsNotFoundDB | WeatherDBOutDatedException e){
            currentWeatherInfoMetrics = weatherMetricsFromExternalService.getCurrentWeatherInfo(location, getCurrentTimeInUnqix());
        }catch (Exception e){
            System.out.println("something went wrong");
            throw e;
        }


        return currentWeatherInfoMetrics;
    }

}
