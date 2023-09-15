package com.spraju.weatherforecast.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CurrentWeatherInfoMetrics {
    @JsonProperty("name")
    private String locationName;
    @JsonProperty("main")
    private Main mainIndicators;
    @JsonProperty("weather")
    private List<Weather> weatherSummary;
    private Wind wind;
    @JsonProperty("dt")
    private Long currentEpochTimeStamp;
}
