package com.spraju.weatherforecast.repository;

import com.spraju.weatherforecast.entity.LocationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<LocationEntity, String> {
}
