package com.spraju.weatherforecast.repository;

import com.spraju.weatherforecast.entity.LocationEntity;
import com.spraju.weatherforecast.entity.MainEntity;
import com.spraju.weatherforecast.entity.WeatherEntity;
import com.spraju.weatherforecast.entity.WindEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface LocationRepository extends JpaRepository<LocationEntity, String> {
//    @Modifying
//    @Transactional
//    @Query(value = " INSERT INTO LocationEntity  (currentMain, currentWeather, currentWind, name) VALUES ( :currentMain, :currentWeather, :currentWind, :name) ", nativeQuery = true)
//    public void updateCurrentWeather(@Param("name") String locationName, @Param("currentMain") MainEntity currentMain, @Param("currentWeather") WeatherEntity currentWeather, @Param("currentWind") WindEntity currentWind);
}
