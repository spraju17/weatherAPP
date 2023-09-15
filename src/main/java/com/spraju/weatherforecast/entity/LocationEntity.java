package com.spraju.weatherforecast.entity;

import com.spraju.weatherforecast.models.Main;
import com.spraju.weatherforecast.models.Weather;
import com.spraju.weatherforecast.models.Wind;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "location")
@Data
public class LocationEntity {
    @Id
    private String name;
    @OneToOne(cascade = CascadeType.ALL)
    private MainEntity currentMain;
    @OneToOne(cascade = CascadeType.ALL)
    private WeatherEntity currentWeather;
    @OneToOne(cascade = CascadeType.ALL)
    private WindEntity currentWind;
    @OneToMany(cascade = CascadeType.ALL)
    private List<MainEntity> mains;
    @OneToMany(cascade = CascadeType.ALL)
    private List<WeatherEntity> weathers;
    @OneToMany(cascade = CascadeType.ALL)
    private List<WindEntity> winds;
}
