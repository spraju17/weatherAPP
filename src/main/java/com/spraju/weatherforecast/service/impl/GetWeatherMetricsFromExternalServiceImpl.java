package com.spraju.weatherforecast.service.impl;

import com.spraju.weatherforecast.configuration.WeatherApiClientConfig;
import com.spraju.weatherforecast.controller.WeatherController;
import com.spraju.weatherforecast.entity.LocationEntity;
import com.spraju.weatherforecast.handler.LogHandler;
import com.spraju.weatherforecast.mapper.*;
import com.spraju.weatherforecast.models.CurrentWeatherInfoMetrics;
import com.spraju.weatherforecast.models.ForeCastWeatherInfoMetrics;
import com.spraju.weatherforecast.repository.LocationRepository;
import com.spraju.weatherforecast.service.GetWeatherMetricsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Locale;
import java.util.Optional;

@Service("WeatherMetricsFromExternalService")
public class GetWeatherMetricsFromExternalServiceImpl implements GetWeatherMetricsService {

    private static final Logger logger = LoggerFactory.getLogger(GetWeatherMetricsFromExternalServiceImpl.class);

    @Autowired
    LogHandler logHandler;

    @Autowired
    RestTemplate whetherApiClient;

    @Autowired
    WeatherApiClientConfig weatherApiClientConfig;

    @Autowired
    LocationRepository locationRepository;

    @Autowired
    CurrentWeatherInfoMetricsMapper currentWeatherInfoMetricsMapper;

    @Autowired
    ForeCastWeatherInfoMetricsMapper foreCastWeatherInfoMetricsMapper;

    @Autowired
    MainIndicatorsMapper mainIndicatorsMapper;

    @Autowired
    WeatherSummaryMapper weatherSummaryMapper;

    @Autowired
    WindMapper windMapper;


    @Override
    @Transactional
    public ForeCastWeatherInfoMetrics getForeCastWeatherInfo(String location) {
        try {
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(weatherApiClientConfig.getBaseUrl());
            builder.path(weatherApiClientConfig.getForecastWhether());

            builder.queryParam("q", location);
            builder.queryParam("appid", weatherApiClientConfig.getApiKey());

            ResponseEntity<ForeCastWeatherInfoMetrics> foreCastWeatherInfoMetricsResponseEntity = whetherApiClient.getForEntity(builder.toUriString(),
                    ForeCastWeatherInfoMetrics.class);
            ForeCastWeatherInfoMetrics response = foreCastWeatherInfoMetricsResponseEntity.getBody();
            response.setLocationName(location);
            LocationEntity locationEntity = foreCastWeatherInfoMetricsMapper.transform(response, null);
            Optional<LocationEntity> entityOptional = locationRepository.findById(location);
            entityOptional.map(x->
            {
                x.setWeathers(locationEntity.getWeathers());
                x.setMains(locationEntity.getMains());
                x.setWinds(locationEntity.getWinds());
                return x;
            }).orElseThrow(()-> new RuntimeException("s"));
            return response;
        }catch (Exception e){
            logHandler.logEvent("Error in fetching forecast weather details from external API exception: "+ e.getStackTrace().toString(), logger);
            throw e;
        }


    }

    @Override
    public CurrentWeatherInfoMetrics getCurrentWeatherInfo(String location, Long unixTimeStamp) {

        try {
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(weatherApiClientConfig.getBaseUrl());
            builder.path(weatherApiClientConfig.getCurrentWhether());

            builder.queryParam("q", location);
            builder.queryParam("appid", weatherApiClientConfig.getApiKey());

            ResponseEntity<CurrentWeatherInfoMetrics> weatherInfoMetricsEntity = whetherApiClient.getForEntity(builder.toUriString(),
                    CurrentWeatherInfoMetrics.class);
            CurrentWeatherInfoMetrics response = weatherInfoMetricsEntity.getBody();
            response.setLocationName(location);
            Long currentTimeStamp = weatherInfoMetricsEntity.getBody().getCurrentEpochTimeStamp();
            LocationEntity locationEntity = currentWeatherInfoMetricsMapper.transform(response, weatherInfoMetricsEntity.getBody().getCurrentEpochTimeStamp());

            return weatherInfoMetricsEntity.getBody();
        }catch (Exception e){
            logHandler.logEvent("Error in fetching current weather details from external API exception: "+ e.getStackTrace().toString(), logger);
            throw e;
        }


    }

}
