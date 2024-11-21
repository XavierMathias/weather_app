package weatherapp.demo.WeatherController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import weatherapp.demo.DTO.WeatherDTO;
import weatherapp.demo.WeatherService.WeatherService;

@Controller
public class WeatherController {

    @Autowired
    private final WeatherService weatherService;

    public WeatherController(WeatherService weatherService){
        this.weatherService = weatherService;
    }
    @GetMapping("/")
    public String showWeatherForm(){
        return "weather";
    }

    @PostMapping("/getWeather")
    public String getWeather(@RequestParam("city") String city, Model model){
        System.out.println("City received: " + city);
        model.addAttribute("cityName", city);
        WeatherDTO weatherDTO = weatherService.fetchWeather(city);


        model.addAttribute("cityName", weatherDTO.getName());
        model.addAttribute("description", weatherDTO.getWeather().get(0).getDescription());
        model.addAttribute("temperature", weatherDTO.getMain().getTemp());
        model.addAttribute("windSpeed", weatherDTO.getWind().getSpeed());
        model.addAttribute("humidity", weatherDTO.getMain().getHumidity());

        return "weather"; // Return the weather.html page with data

    }

    /*@GetMapping("/getweather")
    public String getWeather(@RequestParam String city){
        String response = weatherService.getCurrentWeather(city);

        return response;
    }*/

}
