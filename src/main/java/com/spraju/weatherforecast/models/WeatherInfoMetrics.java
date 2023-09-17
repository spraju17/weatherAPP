package com.spraju.weatherforecast.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class WeatherInfoMetrics {

    private ForeCastWeatherInfoMetrics foreCastWeatherInfoMetrics;

    private CurrentWeatherInfoMetrics currentWeatherInfoMetrics;
}
