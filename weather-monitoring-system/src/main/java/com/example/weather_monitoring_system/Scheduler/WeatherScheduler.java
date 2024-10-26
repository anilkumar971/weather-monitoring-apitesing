package com.example.weather_monitoring_system.Scheduler;


import com.example.weather_monitoring_system.Model.WeatherResponse;
import com.example.weather_monitoring_system.service.WeatherService;
import com.example.weather_monitoring_system.service.WeatherSummaryService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import com.example.weather_monitoring_system.Model.WeatherResponse;
import com.example.weather_monitoring_system.service.WeatherService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class WeatherScheduler {

    private static final Logger logger = LoggerFactory.getLogger(WeatherScheduler.class);
    private final WeatherService weatherService;
    private final WeatherSummaryService weatherSummaryService;
    private final List<String> cities; // List of cities to fetch weather data for

    // Constructor injection of dependencies
    public WeatherScheduler(WeatherService weatherService, WeatherSummaryService weatherSummaryService) {
        this.weatherService = weatherService;
        this.weatherSummaryService = weatherSummaryService;
        this.cities = List.of("Delhi", "Mumbai", "Chennai", "Bangalore", "Kolkata", "Hyderabad"); // Replace with your actual city names Delhi,Mumbai,Chennai,Bangalore,Kolkata,Hyderabad
    }

    @Scheduled(fixedRateString = "300000") // 5 minutes
    public void fetchWeatherData() {
        for (String city : cities) {
            WeatherResponse weatherResponse = weatherService.getWeather(city);
            if (weatherResponse != null) {
                float temperature = weatherResponse.getMain().getTemp();
                String condition = weatherResponse.getWeather()[0].getDescription();

                // Set the date as today
                LocalDate date = LocalDate.now();
                // Call the method from the instance of WeatherSummaryService
                weatherSummaryService.updateDailySummary(date, temperature, condition);
                logger.info("Weather data updated for city: {}", city);
            } else {
                logger.warn("No weather data found for city: {}", city);
            }
        }
    }
}
