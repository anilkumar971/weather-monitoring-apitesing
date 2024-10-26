package com.example.weather_monitoring_system.controller;



import com.example.weather_monitoring_system.Model.DailyWeatherSummary;
import com.example.weather_monitoring_system.Model.WeatherResponse;
import com.example.weather_monitoring_system.service.AlertService;
import com.example.weather_monitoring_system.service.WeatherService;
import com.example.weather_monitoring_system.service.WeatherSummaryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import com.example.weather_monitoring_system.Model.WeatherResponse;
import com.example.weather_monitoring_system.service.AlertService;
import com.example.weather_monitoring_system.service.WeatherService;
import com.example.weather_monitoring_system.service.WeatherSummaryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
@RestController
@RequestMapping("/weather")
@CrossOrigin(origins = "http://localhost:4200")// Base URL for better organization
public class WeatherController {

    private static final Logger logger = LoggerFactory.getLogger(WeatherController.class);
    private final WeatherService weatherService;
    private final AlertService alertService;
    private final WeatherSummaryService weatherSummaryService;

    // Constructor injection of services
    public WeatherController(WeatherService weatherService, AlertService alertService, WeatherSummaryService weatherSummaryService) {
        this.weatherService = weatherService;
        this.alertService = alertService;
        this.weatherSummaryService = weatherSummaryService;
    }

    @GetMapping("/{city}")
    public ResponseEntity<?> getWeather(@PathVariable String city) {
        if (city == null || city.trim().isEmpty()) {
            logger.warn("Invalid city name provided");
            return ResponseEntity.badRequest().body("Invalid city name provided"); // Return more informative message
        }

        try {
            WeatherResponse weatherResponse = weatherService.getWeather(city);
            if (weatherResponse != null) {
                // Access the temperature correctly
                float temperature = weatherResponse.getMain().getTemp(); // Assuming temperature is in Celsius

                // Check for temperature alerts
                boolean alertTriggered = alertService.checkTemperatureAlert(temperature);
                if (alertTriggered) {
                    logger.info("Temperature alert triggered for city: {}", city);
                }

                // Update daily weather summary
                weatherSummaryService.updateDailySummary(LocalDate.now(), temperature, weatherResponse.getWeather()[0].getDescription());
                logger.info("Weather summary updated for city: {}", city);

                return ResponseEntity.ok(weatherResponse);
            } else {
                logger.warn("Weather data not found for city: {}", city);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Weather data not found for city: " + city);
            }
        } catch (Exception e) {
            logger.error("Error fetching weather data for city: {}", city, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error while fetching weather data.");
        }
    }

    // Endpoint to retrieve daily summary for a specific date
    @GetMapping("/summary/{date}")
    public ResponseEntity<?> getDailySummary(@PathVariable String date) {
        try {
            LocalDate parsedDate = LocalDate.parse(date); // Ensure the date is in the correct format
            DailyWeatherSummary summary = weatherSummaryService.getDailySummary(parsedDate);
            return (summary != null)
                    ? ResponseEntity.ok(summary)
                    : ResponseEntity.status(HttpStatus.NOT_FOUND).body("No summary found for date: " + date); // Return 404 if no summary found
        } catch (DateTimeParseException e) {
            logger.error("Invalid date format: {}", date, e);
            return ResponseEntity.badRequest().body("Invalid date format. Please use 'YYYY-MM-DD'.");
        } catch (Exception e) {
            logger.error("Error fetching daily summary for date: {}", date, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error while fetching daily summary.");
        }
    }
}

