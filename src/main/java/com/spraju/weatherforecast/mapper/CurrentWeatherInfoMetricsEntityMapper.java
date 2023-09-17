package com.spraju.weatherforecast.mapper;

import com.spraju.weatherforecast.entity.LocationEntity;
import com.spraju.weatherforecast.models.CurrentWeatherInfoMetrics;
import com.spraju.weatherforecast.models.Weather;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component("CurrentWeatherInfoMetricsEntityMapper")
public class CurrentWeatherInfoMetricsEntityMapper implements Mapper<CurrentWeatherInfoMetrics, LocationEntity> {

    @Autowired
    MainIndicatorsEntityMapper mainIndicatorsEntityMapper;

    @Autowired
    WeatherSummaryEntityMapper weatherSummaryEntityMapper;

    @Autowired
    WindEntityMapper windEntityMapper;

    @Override
    public CurrentWeatherInfoMetrics transform(LocationEntity data, Long currentEpochTimeStamp) {
        CurrentWeatherInfoMetrics currentWeatherInfoMetrics = new CurrentWeatherInfoMetrics();
        currentWeatherInfoMetrics.setCurrentEpochTimeStamp(data.getCurrentMain().getUnixTimeStamp());
        currentWeatherInfoMetrics.setLocationName(data.getName().toUpperCase(Locale.ROOT));
        currentWeatherInfoMetrics.setWeatherSummary(Collections.singletonList(weatherSummaryEntityMapper.transform(data.getCurrentWeather(), data.getCurrentWeather().getUnixTimeStamp())));
        currentWeatherInfoMetrics.setWind(windEntityMapper.transform(data.getCurrentWind(), data.getCurrentWind().getUnixTimeStamp()));
        currentWeatherInfoMetrics.setMainIndicators(mainIndicatorsEntityMapper.transform(data.getCurrentMain(), data.getCurrentMain().getUnixTimeStamp()));
        return currentWeatherInfoMetrics;
    }
}

