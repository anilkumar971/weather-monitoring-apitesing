package com.example.weather_monitoring_system.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "weather")
public class WeatherEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String main;
    private double temp;
    private double feelsLike;
    private long dt;

    // Getters and Setters
}
