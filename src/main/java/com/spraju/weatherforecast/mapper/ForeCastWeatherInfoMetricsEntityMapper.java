package com.spraju.weatherforecast.mapper;

import com.spraju.weatherforecast.entity.LocationEntity;
import com.spraju.weatherforecast.entity.MainEntity;
import com.spraju.weatherforecast.entity.WeatherEntity;
import com.spraju.weatherforecast.entity.WindEntity;
import com.spraju.weatherforecast.models.CurrentWeatherInfoMetrics;
import com.spraju.weatherforecast.models.ForeCastWeatherInfoMetrics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
public class ForeCastWeatherInfoMetricsEntityMapper implements Mapper<ForeCastWeatherInfoMetrics, LocationEntity> {

    @Autowired
    MainIndicatorsEntityMapper mainIndicatorsEntityMapper;

    @Autowired
    WeatherSummaryEntityMapper weatherSummaryEntityMapper;

    @Autowired
    WindEntityMapper windEntityMapper;



    @Override
    public ForeCastWeatherInfoMetrics transform(LocationEntity data, Long unixTimeStamp) {
        ForeCastWeatherInfoMetrics metrics = new ForeCastWeatherInfoMetrics();
        metrics.setLocationName(data.getName());
        List<WeatherEntity> weathers = data.getWeathers();
        List<MainEntity> mains = data.getMains();
        List<WindEntity> winds = data.getWinds();
        List<CurrentWeatherInfoMetrics> currentWeatherInfoMetrics = IntStream.range(0, weathers.size()).
                mapToObj(i ->
                        new CurrentWeatherInfoMetrics(data.getName(), mainIndicatorsEntityMapper.transform(mains.get(i), data.getCurrentMain().getUnixTimeStamp()), Collections.singletonList(weatherSummaryEntityMapper.transform(weathers.get(i), data.getCurrentWeather().getUnixTimeStamp())), windEntityMapper.transform(winds.get(i), data.getCurrentWind().getUnixTimeStamp()), unixTimeStamp, convertToLocalDateTime(data.getCurrentMain().getUnixTimeStamp())))
                .collect(Collectors.toList());
        metrics.setForeCastWeatherInfoMetrics(currentWeatherInfoMetrics);
        return metrics;
    }
}
