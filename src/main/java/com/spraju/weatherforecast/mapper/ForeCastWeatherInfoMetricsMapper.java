package com.spraju.weatherforecast.mapper;

import com.spraju.weatherforecast.entity.LocationEntity;
import com.spraju.weatherforecast.entity.MainEntity;
import com.spraju.weatherforecast.entity.WeatherEntity;
import com.spraju.weatherforecast.entity.WindEntity;
import com.spraju.weatherforecast.models.ForeCastWeatherInfoMetrics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Component
public class ForeCastWeatherInfoMetricsMapper implements Mapper<LocationEntity, ForeCastWeatherInfoMetrics>{

    @Autowired
    MainIndicatorsMapper mainIndicatorsMapper;

    @Autowired
    WeatherSummaryMapper weatherSummaryMapper;

    @Autowired
    WindMapper windMapper;

    @Override
    public LocationEntity transform(ForeCastWeatherInfoMetrics data, Long unixTimeStamp) {
        LocationEntity locationEntity = new LocationEntity();
        locationEntity.setName(data.getLocationName().toUpperCase(Locale.ROOT));
        Long timestamp = data.getForeCastWeatherInfoMetrics().get(0).getCurrentEpochTimeStamp();
        List<WeatherEntity> forecastWeather = data.getForeCastWeatherInfoMetrics().stream().map(x -> x.getWeatherSummary().get(0)).map(x-> weatherSummaryMapper.transform(x,timestamp)).collect(Collectors.toList());
        locationEntity.setWeathers(forecastWeather);
        List<MainEntity> forecastMains = data.getForeCastWeatherInfoMetrics().stream().map(x -> mainIndicatorsMapper.transform(x.getMainIndicators(), x.getCurrentEpochTimeStamp())).collect(Collectors.toList());
        locationEntity.setMains(forecastMains);
        List<WindEntity> forecastWinds = data.getForeCastWeatherInfoMetrics().stream().map(x -> windMapper.transform(x.getWind(), x.getCurrentEpochTimeStamp())).collect(Collectors.toList());
        locationEntity.setWinds(forecastWinds);
        return locationEntity;
    }
}
