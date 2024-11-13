package main.java.weather.integration;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.io.IOException;
import java.util.ArrayList;

import io.github.cdimascio.dotenv.Dotenv;
import main.java.weather.dto.WeatherDto;
import org.json.JSONArray;
import org.json.JSONObject;

public class YandexApiService {
    private final double lat;
    private final double lon;
    private final String yandexApiKey;
    private final String yandexApiAddress;

    public YandexApiService() {
        Dotenv dotenv = Dotenv.load();
        this.lat = Double.parseDouble(dotenv.get("PLACE_LAT", "0.0"));
        this.lon = Double.parseDouble(dotenv.get("PLACE_LON", "0.0"));
        this.yandexApiKey = dotenv.get("YANDEX_API_KEY");
        this.yandexApiAddress = dotenv.get("YANDEX_API_ADDRESS");
    }

    public WeatherDto getTemp(int limit) throws IOException, InterruptedException {
        String uri = yandexApiAddress + "?lat=" + lat + "&lon=" + lon;
        if (limit > 0) {
            uri += "&limit=" + limit;
        }

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(uri))
                .header("X-Yandex-API-Key", yandexApiKey)
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        // Парсинг ответа JSON
        JSONObject jsonResponse = new JSONObject(response.body());
        int currentTemp = jsonResponse.getJSONObject("fact").getInt("temp");
        main.java.weather.dto.WeatherDto weatherDto = new main.java.weather.dto.WeatherDto(jsonResponse.toString(),
                currentTemp);

        // Вычисление средней температуры и добавление в DTO
        getAvgTemp(jsonResponse, weatherDto);

        return weatherDto;
    }

    private void getAvgTemp(JSONObject jsonObject, main.java.weather.dto.WeatherDto dto) {
        ArrayList<Integer> avgTemps = new ArrayList<>();
        ArrayList<String> dates = new ArrayList<>();

        JSONArray forecasts = jsonObject.getJSONArray("forecasts");
        for (int i = 0; i < forecasts.length(); i++) {
            JSONObject forecast = forecasts.getJSONObject(i);
            String date = forecast.getString("date");
            int tempAvg = forecast.getJSONObject("parts").getJSONObject("day").getInt("temp_avg");

            avgTemps.add(tempAvg);
            dates.add(date);
            dto.addAvgTempPeriod(date + ": " + tempAvg);
        }

        // Установка средней температуры за весь период
        if (!avgTemps.isEmpty()) {
            double average = avgTemps.stream().mapToInt(Integer::intValue).average().orElse(0);
            String averageFormatted = String.format("%.1f", average);
            dto.setAvgTempForPeriod(averageFormatted + " (за период с " + dates.get(0) + " по " + dates.get(dates.size() - 1) + ")");
        }
    }
}
