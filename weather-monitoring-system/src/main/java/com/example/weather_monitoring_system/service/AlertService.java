package com.example.weather_monitoring_system.service;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicInteger;

@Service
public class AlertService {
  //  @Value("${weather.alert.temperature.threshold}")
   // private float temperatureThreshold;

    @Value("${temperatureThreshold}")
    private float temperatureThreshold;

   // @Value("${weather.alert.consecutive.threshold}")
  //  private int consecutiveThreshold;

    @Value("${alert.consecutiveThreshold}")
    private int consecutiveThreshold;

    private AtomicInteger alertCounter = new AtomicInteger(0);

    public boolean checkTemperatureAlert(float currentTemperature) {
        if (currentTemperature > temperatureThreshold) {
            alertCounter.incrementAndGet();
            if (alertCounter.get() >= consecutiveThreshold) {
                // Trigger alert
                System.out.println("Alert: Temperature exceeded threshold of " + temperatureThreshold + "°C!");
                return true;
            }
        } else {
            alertCounter.set(0); // Reset counter if threshold not breached
        }
        return false;
    }
}
