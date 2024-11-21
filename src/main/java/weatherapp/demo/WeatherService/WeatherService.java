package weatherapp.demo.WeatherService;

import com.google.gson.Gson;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import weatherapp.demo.DTO.WeatherDTO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class WeatherService {

    private static final OkHttpClient client = new OkHttpClient();
    private static final Gson gson = new Gson();
    private final String apiKey = "4e171f449amshc29fd9d4effa237p1f8e3fjsnc0c02c1a928e";
    private final String urlHost = "open-weather13.p.rapidapi.com";
    private static final Logger logger = LoggerFactory.getLogger(WeatherService.class);


    private String cityBreakDown(String city){
        String[] token = city.split("\\s+");
        List<String> cityNameList = new ArrayList<>(Arrays.asList(token));

        String lastCityName = cityNameList.get(cityNameList.size()-1); // capturing the last element
        String query = "";

        // adding string city name in list to the url to complete url
        for (String word: cityNameList) {
            if(word.equals(lastCityName)){
                query = query.concat(word);
                break;
            } else {
                query = query.concat(word + "%" + "20");
            }
        }

        return query;

    }

    public WeatherDTO fetchWeather(String cityQuery) {
        String urlCity = "https://open-weather13.p.rapidapi.com/city/";
        System.out.println("I'm in the service class");
        String newUrl = urlCity.concat(cityBreakDown(cityQuery).concat("/EN"));

        try {

            Request request = new Request.Builder()
                    .url(newUrl)
                    .get()
                    .addHeader("x-rapidapi-key", apiKey)
                    .addHeader("x-rapidapi-host", urlHost)
                    .build();

            try (Response response = client.newCall(request).execute()) {
                if (response.isSuccessful() && response.body() != null) {
                    String apiResponse = response.body().string();
                    logger.info("API response received successfully: {}", apiResponse); // Log at INFO level
                    System.out.println("This is the api response: " + apiResponse);
                    return gson.fromJson(apiResponse, WeatherDTO.class); // Return it
                } else {
                    logger.error("API Error: {} - {}", response.code(), response.message()); // Log at ERROR level
                    throw new IOException("API Error: " + response.code() + " - " + response.message());
                }
            }
        } catch (IOException e) {
            // Log the error (replace with proper logging in real applications)
            System.err.println("Error while fetching weather: " + e.getMessage());
            throw new RuntimeException("Failed to fetch weather data", e);
        }
    }

    public String getCurrentWeather(String cityQuery) {
        String urlCity = "https://open-weather13.p.rapidapi.com/city/";
        System.out.println("I'm in the service class");
        String newUrl = urlCity.concat(cityBreakDown(cityQuery).concat("/EN"));

        try {

            Request request = new Request.Builder()
                    .url(newUrl)
                    .get()
                    .addHeader("x-rapidapi-key", apiKey)
                    .addHeader("x-rapidapi-host", urlHost)
                    .build();

            try (Response response = client.newCall(request).execute()) {
                if (response.isSuccessful() && response.body() != null) {
                    String responseBody = response.body().string(); // Read the response body once
                    logger.info("API response received successfully: {}", responseBody); // Log at INFO level
                    WeatherDTO weatherDTO = gson.fromJson(responseBody, WeatherDTO.class);

                    System.out.println("City: " + weatherDTO.getName());
                    System.out.println("Temperature: " + weatherDTO.getMain().getTemp());
                    System.out.println("Weather Description: " + weatherDTO.getWeather().get(0).getDescription());
                    System.out.println("Wind speed: " + weatherDTO.getWind().getSpeed());

                    return responseBody; // Return it
                } else {
                    logger.error("API Error: {} - {}", response.code(), response.message()); // Log at ERROR level
                    throw new IOException("API Error: " + response.code() + " - " + response.message());
                }
            }
        } catch (IOException e) {
            // Log the error (replace with proper logging in real applications)
            System.err.println("Error while fetching weather: " + e.getMessage());
            throw new RuntimeException("Failed to fetch weather data", e);
        }
    }
}

