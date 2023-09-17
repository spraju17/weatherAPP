package com.spraju.weatherforecast.service.impl;

import com.spraju.weatherforecast.controller.WeatherController;
import com.spraju.weatherforecast.entity.LocationEntity;
import com.spraju.weatherforecast.exception.WeatherDBOutDatedException;
import com.spraju.weatherforecast.exception.WeatherMetricsNotFoundDB;
import com.spraju.weatherforecast.handler.LogHandler;
import com.spraju.weatherforecast.mapper.*;
import com.spraju.weatherforecast.models.CurrentWeatherInfoMetrics;
import com.spraju.weatherforecast.models.ForeCastWeatherInfoMetrics;
import com.spraju.weatherforecast.repository.LocationRepository;
import com.spraju.weatherforecast.service.GetWeatherMetricsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

@Service("WeatherMetricsDBService")
public class GetWeatherMetricsDBServiceImpl implements GetWeatherMetricsService {

    private static final Logger logger = LoggerFactory.getLogger(GetWeatherMetricsDBServiceImpl.class);

    @Autowired
    MainIndicatorsMapper mainIndicatorsMapper;

    @Autowired
    WeatherSummaryMapper weatherSummaryMapper;

    @Autowired
    WindMapper windMapper;


    @Value("${tolerence}")
    private Long tolerence;

    @Autowired
    LogHandler logHandler;

    @Autowired
    LocationRepository locationRepository;

    @Autowired
    @Qualifier("WeatherMetricsFromExternalService")
    GetWeatherMetricsService weatherMetricsService;

    @Autowired
    CurrentWeatherInfoMetricsEntityMapper currentWeatherInfoMetricsEntityMapper;

    @Autowired
    CurrentWeatherInfoMetricsMapper currentWeatherInfoMetricsMapper;

    @Autowired
    ForeCastWeatherInfoMetricsMapper foreCastWeatherInfoMetricsMapper;

    @Autowired
    ForeCastWeatherInfoMetricsEntityMapper foreCastWeatherInfoMetricsEntityMapper;

    @Override
    public ForeCastWeatherInfoMetrics getForeCastWeatherInfo(String location) throws WeatherMetricsNotFoundDB {
        try {
            Optional<LocationEntity> metrics  = locationRepository.findById(location.toUpperCase(Locale.ROOT));
            ForeCastWeatherInfoMetrics foreCastWeatherInfoMetrics = null;
            try {
                if (metrics.get().getWeathers() == null || metrics.get().getWinds()== null || metrics.get().getMains()==null)
                    throw new WeatherMetricsNotFoundDB("forecast data not available in DB");
                foreCastWeatherInfoMetrics = metrics.map(x->foreCastWeatherInfoMetricsEntityMapper.transform(x, null)).orElseThrow(()-> new WeatherMetricsNotFoundDB(" forecast weather info found in db"));
            }catch (WeatherMetricsNotFoundDB e) {
                logHandler.logEvent("forecast weather for " + location + " not found in DB-- calling external API", logger);
                ForeCastWeatherInfoMetrics metrics1 = weatherMetricsService.getForeCastWeatherInfo(location);
                updateForeCastWeatherInfoDB(metrics, metrics1);
                locationRepository.flush();
                return  metrics1;
            } catch (Exception e){
                logHandler.logEvent("Forecast weather for " + location + " failed for both DB search and External API call ", logger);
                throw e;
            }
            if (foreCastWeatherInfoMetrics.getForeCastWeatherInfoMetrics().size()< 10){
                logHandler.logEvent("Forecast weather for " + location + " found in DB is outdated-- calling external API", logger);
                ForeCastWeatherInfoMetrics metrics1 = weatherMetricsService.getForeCastWeatherInfo(location);
                updateForeCastWeatherInfoDB(metrics, metrics1);
                locationRepository.flush();
                return metrics1;
            }else{
                logHandler.logEvent("Forecast weather for " + location + " found in DB is valid", logger);
                return foreCastWeatherInfoMetrics;
            }
        }catch (Exception e){
            logHandler.logEvent("Exception occured while fetching the forecast weather : "+ e.getStackTrace().toString(), logger);
            throw  e;
        }
    }

    @Override
    public CurrentWeatherInfoMetrics getCurrentWeatherInfo(String location, Long unixTimeStamp) throws WeatherMetricsNotFoundDB, WeatherDBOutDatedException {
        try {
            Optional<LocationEntity> metrics = locationRepository.findById(location);
            CurrentWeatherInfoMetrics weatherMetrics = null;
            try {
                weatherMetrics = metrics.map(x -> currentWeatherInfoMetricsEntityMapper.transform(x, null)).orElseThrow(() -> new WeatherMetricsNotFoundDB(" current weather info found in db"));
            } catch (WeatherMetricsNotFoundDB e) {
                logHandler.logEvent("Current weather for " + location + " not found in DB-- calling external API", logger);
                CurrentWeatherInfoMetrics x = weatherMetricsService.getCurrentWeatherInfo(location, null);
                locationRepository.save(currentWeatherInfoMetricsMapper.transform(x, null));
                return x;
            } catch (Exception e) {
                logHandler.logEvent("Current weather for " + location + " failed for both DB search and External API call ", logger);
                throw e;
            }
            if ((unixTimeStamp - weatherMetrics.getCurrentEpochTimeStamp()) > tolerence) {
                CurrentWeatherInfoMetrics currentWeatherInfoMetrics = weatherMetricsService.getCurrentWeatherInfo(location, null);
                logHandler.logEvent("Current weather for " + location + " found in DB is outdated-- calling external API", logger);
                LocationEntity locationEntity = metrics.get();
                locationEntity.setCurrentWeather(weatherSummaryMapper.transform(currentWeatherInfoMetrics.getWeatherSummary().get(0), currentWeatherInfoMetrics.getCurrentEpochTimeStamp()));
                locationEntity.setCurrentMain(mainIndicatorsMapper.transform(currentWeatherInfoMetrics.getMainIndicators(), currentWeatherInfoMetrics.getCurrentEpochTimeStamp()));
                locationEntity.setCurrentWind(windMapper.transform(currentWeatherInfoMetrics.getWind(), currentWeatherInfoMetrics.getCurrentEpochTimeStamp()));
                locationRepository.flush();
                return currentWeatherInfoMetrics;
            } else{
                logHandler.logEvent("Current weather for " + location + " found in DB is valid", logger);
                return weatherMetrics;
            }
        } catch (Exception e) {
            logHandler.logEvent("Exception occured while fetching the current weather : "+ e.getStackTrace().toString(), logger);
            throw e;
        }
    }

    private void updateForeCastWeatherInfoDB( Optional<LocationEntity> metrics, ForeCastWeatherInfoMetrics metrics1){
        metrics.map(x->{
            x.setWeathers(metrics1.getForeCastWeatherInfoMetrics().stream().map(y-> weatherSummaryMapper.transform(y.getWeatherSummary().get(0), null)).collect(Collectors.toList()));
            x.setWinds(metrics1.getForeCastWeatherInfoMetrics().stream().map(z->windMapper.transform(z.getWind(), null)).collect(Collectors.toList()));
            x.setMains(metrics1.getForeCastWeatherInfoMetrics().stream().map(t-> mainIndicatorsMapper.transform(t.getMainIndicators(), null)).collect(Collectors.toList()));
            return x;
        }).orElseThrow(()-> new RuntimeException("d"));
    }
}
