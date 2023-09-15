package com.spraju.weatherforecast.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.awt.image.BufferedImage;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Weather {
    private Integer id;
    private String main;
    private String description;
    private String icon;
    private Long unixTimeStamp;
}
