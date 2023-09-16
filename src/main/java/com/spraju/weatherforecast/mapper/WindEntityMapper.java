package com.spraju.weatherforecast.mapper;

import com.spraju.weatherforecast.entity.WindEntity;
import com.spraju.weatherforecast.models.Wind;
import org.springframework.stereotype.Component;

@Component
public class WindEntityMapper implements Mapper<Wind, WindEntity> {

    @Override
    public Wind transform(WindEntity data,Long unixTimeStamp) {
        Wind wind = new Wind();
        wind.setUnixTimeStamp(unixTimeStamp);
        wind.setDeg(data.getDeg());
        //windEntity.setGust(data.getGust());
        wind.setSpeed(data.getSpeed());
        return wind;
    }
}
