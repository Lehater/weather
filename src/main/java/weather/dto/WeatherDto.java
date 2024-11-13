package main.java.weather.dto;

import java.util.ArrayList;

public class WeatherDto {
    private String yandexJsonResponse;
    private int currentTemp;
    private String avgTempForPeriod;
    private ArrayList<String> avgTempPeriods;

    public WeatherDto(String yandexJsonResponse, int currentTemp) {
        this.yandexJsonResponse = yandexJsonResponse;
        this.currentTemp = currentTemp;
        this.avgTempPeriods = new ArrayList<>();
    }

    public String getYandexJsonResponse() {
        return yandexJsonResponse;
    }

    public int getCurrentTemp() {
        return currentTemp;
    }

    public String getAvgTempForPeriod() {
        return avgTempForPeriod;
    }

    public void setAvgTempForPeriod(String avgTempForPeriod) {
        this.avgTempForPeriod = avgTempForPeriod;
    }

    public ArrayList<String> getAvgTempPeriods() {
        return avgTempPeriods;
    }

    public void addAvgTempPeriod(String tempWithData) {
        this.avgTempPeriods.add(tempWithData);
    }
}
