package ibf.ssf.day17.weather.service;

import java.io.StringReader;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import ibf.ssf.day17.weather.model.Weather;
import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

@Service
public class WeatherService {
    
    // Establish base url
    public static final String BASE_URL = "https://api.openweathermap.org/data/2.5/weather";

    @Value("${weather.api.key}")
    private String apiKey;

    public List<Weather> search(String term) {
        
        // Build url with query parameters
        String url = UriComponentsBuilder
        .fromUriString(BASE_URL)
        .queryParam("q", term.replaceAll(" ", "+")) // If keying in hong kong, space turns into another char hence replace space with +
        .queryParam("appid", apiKey)
        .queryParam("units", "metric")
        .toUriString();

        // Build get RequestEntity
        RequestEntity<Void> req = RequestEntity
        .get(url)
        .accept(MediaType.APPLICATION_JSON)
        .build();

        RestTemplate template = new RestTemplate();
        
        // Get results of response
        ResponseEntity<String> resp; 
        try {
            resp = template.exchange(req, String.class);
        } catch (Exception ex) {
            ex.printStackTrace();
            // Returns an empty list for th:if and th:unless conditions in thymeleaf
            return List.of();
        }

        // DEBUG
        System.out.printf(">>>>>> Status code: %d\n", resp.getStatusCode().value());
        System.out.printf(">>>>>> Payload: %s\n", resp.getBody());

        // Process the body
        List<Weather> weatherList = new LinkedList<>();
        

        JsonReader reader = Json.createReader(new StringReader(resp.getBody()));
        JsonObject result = reader.readObject();
        JsonArray weather = result.getJsonArray("weather");
        for (int i = 0; i < weather.size(); i++) {
            JsonObject elem = weather.getJsonObject(i);
            String id = String.valueOf(elem.get("id"));
            String main = elem.getString("main");
            String description = elem.getString("description");
            String icon = elem.getString("icon");
            
            Weather weatherResult = new Weather(icon, main, description);
            weatherList.add(weatherResult);

        }

        // // Alternative
        // weather.getJsonArray("weather").stream()
        // .map(value -> value.asJsonObject())
        // .map(j -> new Weather(j.getString("main")), j.getString("description"), j.getString("icon"));

        return weatherList;

    }

}

// result -> JO
// weather -> JA
// id -> String, main -> String, description -> String, icon -> String