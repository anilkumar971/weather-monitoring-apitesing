package com.example.weather_monitoring_system.Model;


import java.time.LocalDate;
public class DailyWeatherSummary {
    private LocalDate date;
    private float averageTemperature;
    private float maxTemperature;
    private float minTemperature;
    private String dominantCondition;
    private int temperatureCount;

    public DailyWeatherSummary(LocalDate date) {
        this.date = date;
        this.maxTemperature = Float.MIN_VALUE;
        this.minTemperature = Float.MAX_VALUE;
        this.temperatureCount = 0;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public float getAverageTemperature() {
        return averageTemperature;
    }

    public float getMaxTemperature() {
        return maxTemperature;
    }

    public float getMinTemperature() {
        return minTemperature;
    }

    public String getDominantCondition() {
        return dominantCondition;
    }

    // Method to update temperature statistics
    public void update(float temperature, String condition) {
        temperatureCount++;
        averageTemperature = ((averageTemperature * (temperatureCount - 1)) + temperature) / temperatureCount;

        if (temperature > maxTemperature) {
            maxTemperature = temperature;
            dominantCondition = condition; // Update dominant condition based on max temperature
        }
        if (temperature < minTemperature) {
            minTemperature = temperature;
        }
    }
}
