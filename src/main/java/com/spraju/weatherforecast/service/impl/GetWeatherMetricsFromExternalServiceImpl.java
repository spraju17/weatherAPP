package com.spraju.weatherforecast.service.impl;

import com.spraju.weatherforecast.configuration.WeatherApiClientConfig;
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

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
            List<CurrentWeatherInfoMetrics> modifiedResponse = response.getForeCastWeatherInfoMetrics().stream().map(x -> {
                x.setDate(convertToLocalDateTime(x.getCurrentEpochTimeStamp()));
                return x;
            }).collect(Collectors.toList());
            response.setForeCastWeatherInfoMetrics(modifiedResponse);

            LocationEntity locationEntity = foreCastWeatherInfoMetricsMapper.transform(response, null);
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

    private LocalDateTime convertToLocalDateTime(Long unixTimestamp){
        // Convert Unix timestamp to milliseconds by multiplying by 1000
        Instant instant = Instant.ofEpochMilli(unixTimestamp * 1000);

        // Convert Instant to LocalDateTime using the default time zone (you can specify a different ZoneId if needed)
        LocalDateTime localDateTime = instant.atZone(ZoneId.systemDefault()).toLocalDateTime();

        return localDateTime;
    }

}
