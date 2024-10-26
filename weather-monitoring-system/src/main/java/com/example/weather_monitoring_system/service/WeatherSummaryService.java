package com.example.weather_monitoring_system.service;




import com.example.weather_monitoring_system.Model.DailyWeatherSummary;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Service
public class WeatherSummaryService {
    private Map<LocalDate, DailyWeatherSummary> dailySummaries = new HashMap<>();

   /* public void updateDailySummary(float temperature, String condition) {
        LocalDate today = LocalDate.now();
        DailyWeatherSummary summary = dailySummaries.getOrDefault(today, new DailyWeatherSummary(today));
        summary.update(temperature, condition);
        dailySummaries.put(today, summary);
    }*/
   public void updateDailySummary(LocalDate date, float temperature, String condition) {
       DailyWeatherSummary summary = dailySummaries.getOrDefault(date, new DailyWeatherSummary(date));
       summary.update(temperature, condition);
       dailySummaries.put(date, summary);
   }



    public DailyWeatherSummary getDailySummary(LocalDate date) {
        return dailySummaries.get(date);
    }
}
