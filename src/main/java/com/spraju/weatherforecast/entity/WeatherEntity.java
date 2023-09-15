package com.spraju.weatherforecast.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "weather")
@Data
public class WeatherEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String main;
    private String description;
    private String icon;
    private Long unixTimeStamp;
}
