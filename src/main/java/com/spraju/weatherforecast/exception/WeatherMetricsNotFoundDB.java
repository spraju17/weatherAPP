package com.spraju.weatherforecast.exception;

public class WeatherMetricsNotFoundDB extends Exception{
    public WeatherMetricsNotFoundDB(String message, Throwable cause) {
        super(message, cause);
    }

    public WeatherMetricsNotFoundDB(String message) {
        super(message);
    }
}
