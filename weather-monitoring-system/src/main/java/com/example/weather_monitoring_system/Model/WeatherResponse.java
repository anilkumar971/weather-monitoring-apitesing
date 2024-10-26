package com.example.weather_monitoring_system.Model;



import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Arrays;

public class WeatherResponse {
    private String name;
    private Main main;
    private WeatherCondition[] weather; // Array to hold weather conditions
    private long dt; // Time of data update

    // Constructor
    public WeatherResponse(String name, Main main, WeatherCondition[] weather, long dt) {
        this.name = name;
        this.main = main;
        this.weather = weather;
        this.dt = dt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Main getMain() {
        return main;
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public WeatherCondition[] getWeather() {
        return weather;
    }

    public void setWeather(WeatherCondition[] weather) {
        this.weather = weather;
    }

    public long getDt() {
        return dt;
    }

    public void setDt(long dt) {
        this.dt = dt;
    }

    // Override toString for better logging
    @Override
    public String toString() {
        return "WeatherResponse{" +
                "name='" + name + '\'' +
                ", main=" + main +
                ", weather=" + Arrays.toString(weather) +
                ", dt=" + dt +
                '}';
    }
}
