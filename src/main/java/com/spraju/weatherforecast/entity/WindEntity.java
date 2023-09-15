package com.spraju.weatherforecast.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "wind")
@Data
public class WindEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private double speed;
    private double deg;
    private double gust;
    private Long unixTimeStamp;
}
