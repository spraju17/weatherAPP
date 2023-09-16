package com.spraju.weatherforecast.service.impl;

import com.spraju.weatherforecast.entity.LocationEntity;
import com.spraju.weatherforecast.exception.WeatherDBOutDatedException;
import com.spraju.weatherforecast.exception.WeatherMetricsNotFoundDB;
import com.spraju.weatherforecast.mapper.CurrentWeatherInfoMetricsEntityMapper;
import com.spraju.weatherforecast.models.CurrentWeatherInfoMetrics;
import com.spraju.weatherforecast.models.ForeCastWeatherInfoMetrics;
import com.spraju.weatherforecast.repository.LocationRepository;
import com.spraju.weatherforecast.service.GetWeatherMetricsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Locale;
import java.util.Optional;

@Service("WeatherMetricsDBService")
public class GetWeatherMetricsDBServiceImpl implements GetWeatherMetricsService {

    @Autowired
    LocationRepository locationRepository;

    @Autowired
    CurrentWeatherInfoMetricsEntityMapper currentWeatherInfoMetricsEntityMapper;

    @Override
    public ForeCastWeatherInfoMetrics getForeCastWeatherInfo(String location) {
        return null;
    }

    @Override
    public CurrentWeatherInfoMetrics getCurrentWeatherInfo(String location, Long unixTimeStamp) throws WeatherMetricsNotFoundDB {
        Optional<LocationEntity> metrics = locationRepository.findById(location.toUpperCase(Locale.ROOT));
        CurrentWeatherInfoMetrics weatherMetrics = metrics.map(x-> currentWeatherInfoMetricsEntityMapper.transform(x, null)).orElseThrow(()-> new WeatherMetricsNotFoundDB(" not found in db"));

        return weatherMetrics;
    }
}
