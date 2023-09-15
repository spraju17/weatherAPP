package com.spraju.weatherforecast.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Wind {
    private Double speed;
    private Double deg;
    private Double gust;
    private Long unixTimeStamp;
}
