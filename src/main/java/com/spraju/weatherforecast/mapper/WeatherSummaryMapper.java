package com.spraju.weatherforecast.mapper;

import com.spraju.weatherforecast.entity.WeatherEntity;
import com.spraju.weatherforecast.models.Weather;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.Date;

@Component
public class WeatherSummaryMapper implements Mapper<WeatherEntity, Weather> {

    @Override
    public WeatherEntity transform(Weather data, Long unixTimeStamp) {
        WeatherEntity weatherEntity = new WeatherEntity();
        weatherEntity.setUnixTimeStamp(unixTimeStamp);
        weatherEntity.setDescription(data.getDescription());
        weatherEntity.setIcon(data.getIcon());
        weatherEntity.setMain(data.getMain());
        return weatherEntity;
    }
}


