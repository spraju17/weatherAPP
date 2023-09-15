package com.spraju.weatherforecast.mapper;

import com.spraju.weatherforecast.entity.LocationEntity;
import com.spraju.weatherforecast.models.CurrentWeatherInfoMetrics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Date;
import java.util.Locale;


@Component
public class CurrentWeatherInfoMetricsMapper implements Mapper<LocationEntity, CurrentWeatherInfoMetrics> {

    @Autowired
    MainIndicatorsMapper mainIndicatorsMapper;

    @Autowired
    WeatherSummaryMapper weatherSummaryMapper;

    @Autowired
    WindMapper windMapper;

    @Override
    public LocationEntity transform(CurrentWeatherInfoMetrics data, Long currentEpochTimeStamp) {
        LocationEntity locationEntity = new LocationEntity();
        locationEntity.setName(data.getLocationName().toUpperCase(Locale.ROOT));
        locationEntity.setCurrentWeather(weatherSummaryMapper.transform(data.getWeatherSummary().get(0), currentEpochTimeStamp));
        locationEntity.setCurrentWind(windMapper.transform(data.getWind(), currentEpochTimeStamp));
        locationEntity.setCurrentMain(mainIndicatorsMapper.transform(data.getMainIndicators(), currentEpochTimeStamp));
        return locationEntity;
    }
}
