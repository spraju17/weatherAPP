package com.spraju.weatherforecast.controller;

import com.spraju.weatherforecast.constants.ActionType;
import com.spraju.weatherforecast.exception.WeatherDBOutDatedException;
import com.spraju.weatherforecast.exception.WeatherMetricsNotFoundDB;
import com.spraju.weatherforecast.handler.LogHandler;
import com.spraju.weatherforecast.models.CurrentWeatherInfoMetrics;
import com.spraju.weatherforecast.models.ForeCastWeatherInfoMetrics;
import com.spraju.weatherforecast.models.PerfMetrics;
import com.spraju.weatherforecast.models.WeatherInfoMetrics;
import com.spraju.weatherforecast.service.GetWeatherMetricsService;
import com.spraju.weatherforecast.service.impl.GetWeatherMetricsServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

@Controller
public class WeatherController {

    private static final Logger logger = LoggerFactory.getLogger(WeatherController.class);

    @Autowired
    GetWeatherMetricsServiceImpl whetherService;

    @Autowired
    @Qualifier("WeatherMetricsDBService")
    GetWeatherMetricsService service;

    @Autowired
    LogHandler logHandler;

    @GetMapping("/wethear")
    @ResponseBody
    public WeatherInfoMetrics getWeather() throws WeatherMetricsNotFoundDB, WeatherDBOutDatedException {
        WeatherInfoMetrics weatherInfoMetrics = null;
        Long currentTime = System.currentTimeMillis();
        try {
            weatherInfoMetrics =  whetherService.getWeatherInfoMetrics("delhi");
            logHandler.logPerfMetrics(new PerfMetrics(ActionType.GetWeatherInfo.toString(), true,  System.currentTimeMillis()- currentTime), logger );
        }catch (Exception e){
            logHandler.logPerfMetrics(new PerfMetrics(ActionType.GetWeatherInfo.toString(), false,  System.currentTimeMillis()- currentTime), logger );
        }
        return  weatherInfoMetrics;
    }
    @GetMapping("/")
    public String weather(Model model , @RequestParam(name = "locationName", required = false) String locationName) throws WeatherMetricsNotFoundDB, WeatherDBOutDatedException {
        if (locationName != null) {
            model.addAttribute("foreCastWeatherInfoMetrics", whetherService.getWeatherInfoMetrics(locationName.toUpperCase(Locale.ROOT)).getForeCastWeatherInfoMetrics());
            model.addAttribute("currentWeatherInfoMetrics", whetherService.getWeatherInfoMetrics(locationName.toUpperCase(Locale.ROOT)).getCurrentWeatherInfoMetrics());
            return "weather";
        }
        return "weather";
    }

}
