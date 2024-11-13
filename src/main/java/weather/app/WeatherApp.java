package main.java.weather.app;

import main.java.weather.display.WeatherDisplayService;
import main.java.weather.dto.WeatherDto;
import main.java.weather.integration.YandexApiService;

import java.io.IOException;

public class WeatherApp {
    public static void main(String[] args) {
        YandexApiService integrationService = new YandexApiService();
        WeatherDisplayService displayService = new WeatherDisplayService();
        try {
            // Получение данных о погоде
            WeatherDto weatherDto = integrationService.getTemp(7);

            // Вывод данных на экран
            displayService.printWeatherData(weatherDto);
            displayService.printAverageTemperature(weatherDto);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}