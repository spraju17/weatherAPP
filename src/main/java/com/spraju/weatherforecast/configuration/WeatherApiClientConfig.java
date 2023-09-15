package com.spraju.weatherforecast.configuration;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "service.whether-api")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WeatherApiClientConfig {
    private String baseUrl;
    private String currentWhether;
    private String forecastWhether;
    private Long readTimeoutMs;
    private Long connectTimeoutMs;
    private String apiKey;
}
