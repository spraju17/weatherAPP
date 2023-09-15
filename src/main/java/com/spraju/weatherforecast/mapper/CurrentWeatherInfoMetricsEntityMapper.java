package com.spraju.weatherforecast.mapper;

import com.spraju.weatherforecast.entity.LocationEntity;
import com.spraju.weatherforecast.models.CurrentWeatherInfoMetrics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
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
        currentWeatherInfoMetrics.setLocationName(data.getName().toUpperCase(Locale.ROOT));
        currentWeatherInfoMetrics.setWeatherSummary(new ArrayList<>((Collection) weatherSummaryEntityMapper.transform(data.getWeathers().get(0), currentEpochTimeStamp)));
        currentWeatherInfoMetrics.setWind(windEntityMapper.transform(data.getCurrentWind(), currentEpochTimeStamp));
        currentWeatherInfoMetrics.setMainIndicators(mainIndicatorsEntityMapper.transform(data.getCurrentMain(), currentEpochTimeStamp));
        return currentWeatherInfoMetrics;
    }
}

