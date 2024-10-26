package com.example.weather_monitoring_system.Model;

public class WeatherCondition {
    private String main; // Main weather condition (e.g., Rain, Snow, Clear)

    private String description; // Description of the weather condition

    public String getMain() {
        return main;
    }

    public void setMain(String main) {
        this.main = main;
    }

    public String getDescription() { // Add this method for description
        return description;
    }

    public void setDescription(String description) { // Add this method for description
        this.description = description;
    }
}
