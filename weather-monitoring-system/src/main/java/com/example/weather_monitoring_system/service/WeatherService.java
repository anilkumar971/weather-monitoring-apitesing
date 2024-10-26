package com.example.weather_monitoring_system.service;



import com.example.weather_monitoring_system.Model.WeatherResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class WeatherService {

    private static final Logger logger = LoggerFactory.getLogger(WeatherService.class);

    @Value("${weather.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate;
    private final WeatherSummaryService weatherSummaryService;
    private final AlertService alertService;

    // List of metro cities in India
    private final List<String> cities = Arrays.asList("Delhi", "Mumbai", "Chennai", "Bangalore", "Kolkata", "Hyderabad");

    public WeatherService(RestTemplate restTemplate, WeatherSummaryService weatherSummaryService, AlertService alertService) {
        this.restTemplate = restTemplate;
        this.weatherSummaryService = weatherSummaryService;
        this.alertService = alertService;
    }

    @Scheduled(fixedRateString = "300000") // 5 minutes
    public void fetchWeatherData() {
        for (String city : cities) {
            try {
                WeatherResponse response = getWeather(city);
                if (response != null && response.getMain() != null) {
                    float temperatureCelsius = response.getMain().getTemp(); // Assuming this is in Celsius
                    String condition = response.getWeather()[0].getMain(); // Get the main weather condition

                    // Update daily summary
                    weatherSummaryService.updateDailySummary(LocalDate.now(), temperatureCelsius, condition);

                    // Check for alerts
                    alertService.checkTemperatureAlert(temperatureCelsius);
                    logger.info("Weather data fetched for city: {} - Temperature: {}°C, Condition: {}", city, temperatureCelsius, condition);
                } else {
                    logger.warn("Invalid weather response for city: {}", city);
                }
            } catch (Exception e) {
                logger.error("Error fetching weather data for city: {}", city, e);
            }
        }
    }

    public WeatherResponse getWeather(String city) {
        String url = String.format("http://api.openweathermap.org/data/2.5/weather?q=%s&appid=%s", city, apiKey);
        try {
            return restTemplate.getForObject(url, WeatherResponse.class);
        } catch (Exception e) {
            logger.error("Failed to fetch weather for city: {}", city, e);
            return null; // Return null to handle this case in fetchWeatherData
        }
    }
}

