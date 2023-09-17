package com.spraju.weatherforecast.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ForeCastWeatherInfoMetrics {
    @JsonProperty("list")
    private List<CurrentWeatherInfoMetrics> foreCastWeatherInfoMetrics;
    @JsonIgnoreProperties
    private String locationName;
}
