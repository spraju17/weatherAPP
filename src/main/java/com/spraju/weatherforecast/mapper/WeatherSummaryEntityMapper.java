package com.spraju.weatherforecast.mapper;

import com.spraju.weatherforecast.entity.WeatherEntity;
import com.spraju.weatherforecast.models.Weather;
import org.springframework.stereotype.Component;

@Component
public class WeatherSummaryEntityMapper implements Mapper<Weather, WeatherEntity> {

    @Override
    public Weather transform(WeatherEntity data, Long unixTimeStamp) {
        Weather weather = new Weather();
        weather.setUnixTimeStamp(unixTimeStamp);
        weather.setDescription(data.getDescription());
        weather.setIcon(data.getIcon());
        weather.setMain(data.getMain());
        return weather;
    }
}
