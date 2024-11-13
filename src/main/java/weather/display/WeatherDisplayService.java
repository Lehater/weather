package main.java.weather.display;


import main.java.weather.dto.WeatherDto;

public class WeatherDisplayService {
    public void printWeatherData(WeatherDto weatherDto) {
        // Печать полученного JSON-ответа
        System.out.println("Полученный JSON ответ: ");
        System.out.println(weatherDto.getYandexJsonResponse());

        // Печать температуры из DTO
        System.out.println("Температура (fact.temp): " + weatherDto.getCurrentTemp());
    }

    public void printAverageTemperature(WeatherDto weatherDto) {
        // Печать средней температуры из DTO
        System.out.println("Средняя температура за период: " + weatherDto.getAvgTempForPeriod());
        System.out.println("Средние температуры по дням: ");
        for (String avgTemp : weatherDto.getAvgTempPeriods()) {
            System.out.println(avgTemp);
        }
    }
}