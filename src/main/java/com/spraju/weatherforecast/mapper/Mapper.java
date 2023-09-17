package com.spraju.weatherforecast.mapper;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

public interface Mapper <X, Y> {
    X transform(Y data, Long unixTimeStamp);

    public default LocalDateTime convertToLocalDateTime(Long unixTimestamp){
        // Convert Unix timestamp to milliseconds by multiplying by 1000
        Instant instant = Instant.ofEpochMilli(unixTimestamp * 1000);

        // Convert Instant to LocalDateTime using the default time zone (you can specify a different ZoneId if needed)
        LocalDateTime localDateTime = instant.atZone(ZoneId.systemDefault()).toLocalDateTime();

        return localDateTime;
    }
}
