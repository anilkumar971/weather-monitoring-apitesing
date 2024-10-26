package com.example.weather_monitoring_system.repo;



import com.example.weather_monitoring_system.Model.WeatherResponse;
import com.example.weather_monitoring_system.entity.WeatherEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface WeatherRepository extends JpaRepository<WeatherEntity, Long> {
}

