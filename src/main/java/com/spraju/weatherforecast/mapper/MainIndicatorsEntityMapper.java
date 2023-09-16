package com.spraju.weatherforecast.mapper;

import com.spraju.weatherforecast.entity.MainEntity;
import com.spraju.weatherforecast.models.Main;
import org.springframework.stereotype.Component;

@Component
public class MainIndicatorsEntityMapper implements Mapper<Main, MainEntity> {

    @Override
    public Main transform(MainEntity data, Long unixTimeStamp) {
        Main main = new Main();
        main.setUnixTimeStamp(unixTimeStamp);
        main.setHumidity(data.getHumidity());
        main.setFeels_like(data.getFeels_like());
        main.setPressure(data.getPressure());
        main.setTemp_max(data.getTemp_max());
        main.setTemp_min(data.getTemp_min());
        main.setTemp(data.getTemp());
        return main;
    }
}

