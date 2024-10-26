package com.example.weather_monitoring_system.Model;




import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Main {
    private float temp;

    @JsonProperty("feels_like")
    private float feelsLike; // Perceived temperature in Centigrade

    private int pressure;
    private int humidity;

    // Getter for temperature in Celsius
    public float getTemp() {
        return temp - 273.15f; // Convert from Kelvin to Celsius
    }

    // Setter for temperature
    public void setTemp(float temp) {
        this.temp = temp;
    }

    // Getter for feels like temperature in Celsius
    public float getFeelsLike() {
        return feelsLike - 273.15f; // Convert from Kelvin to Celsius
    }

    // Setter for feels like temperature
    public void setFeelsLike(float feelsLike) {
        this.feelsLike = feelsLike;
    }

    // Getter for pressure
    public int getPressure() {
        return pressure;
    }

    // Setter for pressure
    public void setPressure(int pressure) {
        this.pressure = pressure;
    }

    // Getter for humidity
    public int getHumidity() {
        return humidity;
    }

    // Setter for humidity
    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }
}
