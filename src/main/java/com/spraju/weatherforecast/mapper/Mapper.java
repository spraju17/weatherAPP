package com.spraju.weatherforecast.mapper;

import java.sql.Timestamp;
import java.util.Date;

public interface Mapper <X, Y> {
    X transform(Y data, Long unixTimeStamp);
}
