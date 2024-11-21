package weatherapp.demo.WeatherController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import weatherapp.demo.WeatherService.WeatherService;

@RestController
@RequestMapping("/api")
public class WeatherController {

    @Autowired
    WeatherService weatherService = new WeatherService();

    @GetMapping("/getweather")
    public String getWeather(@RequestParam String city){
        String response = weatherService.getCurrentWeather(city);

        return response;
    }

}
