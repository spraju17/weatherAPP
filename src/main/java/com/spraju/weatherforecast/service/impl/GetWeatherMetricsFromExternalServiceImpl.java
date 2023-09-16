package com.spraju.weatherforecast.service.impl;

import com.spraju.weatherforecast.configuration.WeatherApiClientConfig;
import com.spraju.weatherforecast.entity.LocationEntity;
import com.spraju.weatherforecast.mapper.CurrentWeatherInfoMetricsMapper;
import com.spraju.weatherforecast.models.CurrentWeatherInfoMetrics;
import com.spraju.weatherforecast.models.ForeCastWeatherInfoMetrics;
import com.spraju.weatherforecast.repository.LocationRepository;
import com.spraju.weatherforecast.service.GetWeatherMetricsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Optional;

@Service("WeatherMetricsFromExternalService")
public class GetWeatherMetricsFromExternalServiceImpl implements GetWeatherMetricsService {

    @Autowired
    RestTemplate whetherApiClient;

    @Autowired
    WeatherApiClientConfig weatherApiClientConfig;

    @Autowired
    LocationRepository locationRepository;

    @Autowired
    CurrentWeatherInfoMetricsMapper currentWeatherInfoMetricsMapper;

//    @Override
//    public ForeCastWeatherInfoMetrics getWhetherInfo(String location) {
//        ForeCastWeatherInfoMetrics weatherInfoMetrics =  getCurrentWhetherInfo(location);
//        //locationRepository.save(s)
//        return getCurrentWhetherInfo(location);
//    }
//
//    @Override
//    public ForeCastWeatherInfoMetrics getCurrentWhetherInfo(String location) {
//        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(weatherApiClientConfig.getBaseUrl());
//        builder.path(weatherApiClientConfig.getCurrentWhether());
//
//        builder.queryParam("q", location);
//        builder.queryParam("appid", weatherApiClientConfig.getApiKey());
//
//        ResponseEntity<ForeCastWeatherInfoMetrics> weatherInfoMetricsEntity = whetherApiClient.getForEntity(builder.toUriString(),
//                ForeCastWeatherInfoMetrics.class);
//        return weatherInfoMetricsEntity.getBody();
//    }

    @Override
    public ForeCastWeatherInfoMetrics getForeCastWeatherInfo(String location) {
        return null;
    }

    @Override
    public CurrentWeatherInfoMetrics getCurrentWeatherInfo(String location, Long unixTimeStamp) {

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(weatherApiClientConfig.getBaseUrl());
        builder.path(weatherApiClientConfig.getCurrentWhether());

        builder.queryParam("q", location);
        builder.queryParam("appid", weatherApiClientConfig.getApiKey());

        ResponseEntity<CurrentWeatherInfoMetrics> weatherInfoMetricsEntity = whetherApiClient.getForEntity(builder.toUriString(),
                CurrentWeatherInfoMetrics.class);
        LocationEntity locationEntity = currentWeatherInfoMetricsMapper.transform(weatherInfoMetricsEntity.getBody(), weatherInfoMetricsEntity.getBody().getCurrentEpochTimeStamp());
        locationRepository.save(locationEntity);
        return weatherInfoMetricsEntity.getBody();
    }

}
