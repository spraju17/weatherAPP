package com.spraju.weatherforecast.mapper;

import com.spraju.weatherforecast.entity.WindEntity;
import com.spraju.weatherforecast.models.Wind;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class WindMapper implements Mapper<WindEntity, Wind> {

    @Override
    public WindEntity transform(Wind data,Long unixTimeStamp) {
        WindEntity windEntity = new WindEntity();
        windEntity.setUnixTimeStamp(unixTimeStamp);
        windEntity.setDeg(data.getDeg());
        //windEntity.setGust(data.getGust());
        windEntity.setSpeed(data.getSpeed());
        return windEntity;
    }
}
