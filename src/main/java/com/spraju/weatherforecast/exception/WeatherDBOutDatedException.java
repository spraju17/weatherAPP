package com.spraju.weatherforecast.exception;

public class WeatherDBOutDatedException extends Exception {
    public WeatherDBOutDatedException(String message) {
        super(message);
    }

    public WeatherDBOutDatedException(String message, Throwable cause) {
        super(message, cause);
    }
}
