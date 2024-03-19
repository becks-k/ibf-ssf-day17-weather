package ibf.ssf.day17.weather.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import ibf.ssf.day17.weather.model.Weather;
import ibf.ssf.day17.weather.service.WeatherService;

@Controller
@RequestMapping
public class WeatherController {
    
    @Autowired
    WeatherService weatherService;

    @GetMapping("/result")
    public ModelAndView getWeather(@RequestParam String q) {
        
        List<Weather> results = weatherService.search(q);
        ModelAndView mav = new ModelAndView("result");

        mav.setStatus(HttpStatusCode.valueOf(200));
        mav.addObject("q", q);
        mav.addObject("results", results);

        return mav;
    }
}
