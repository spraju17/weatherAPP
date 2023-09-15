package com.spraju.weatherforecast.mapper;

import com.spraju.weatherforecast.entity.MainEntity;
import com.spraju.weatherforecast.models.Main;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class MainIndicatorsMapper implements Mapper<MainEntity, Main> {

    @Override
    public MainEntity transform(Main data, Long unixTimeStamp) {
        MainEntity mainEntity = new MainEntity();
        mainEntity.setUnixTimeStamp(unixTimeStamp);
        mainEntity.setHumidity(data.getHumidity());
        mainEntity.setFeels_like(data.getFeels_like());
        mainEntity.setPressure(data.getPressure());
        mainEntity.setTemp_max(data.getTemp_max());
        mainEntity.setTemp_min(data.getTemp_min());
        mainEntity.setTemp(data.getTemp());
        return mainEntity;
    }
}
