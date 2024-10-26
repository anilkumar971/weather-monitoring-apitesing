package com.example.weather_monitoring_system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@EnableScheduling
public class WeatherMonitoringSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(WeatherMonitoringSystemApplication.class, args);
	}

}
