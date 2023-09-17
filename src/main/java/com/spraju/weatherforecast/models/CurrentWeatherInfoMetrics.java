package com.spraju.weatherforecast.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
public class CurrentWeatherInfoMetrics {
    @JsonIgnoreProperties
    private String locationName;
    @JsonProperty("main")
    private Main mainIndicators;
    @JsonProperty("weather")
    private List<Weather> weatherSummary;
    private Wind wind;
    @JsonProperty("dt")
    private Long currentEpochTimeStamp;
    @JsonIgnoreProperties
    private LocalDateTime date;
}
