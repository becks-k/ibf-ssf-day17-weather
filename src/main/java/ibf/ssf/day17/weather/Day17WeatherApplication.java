package ibf.ssf.day17.weather;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import ibf.ssf.day17.weather.model.Weather;
import ibf.ssf.day17.weather.service.WeatherService;

@SpringBootApplication
public class Day17WeatherApplication implements CommandLineRunner{

	@Autowired
	WeatherService weatherService;

	public static void main(String[] args) {
		SpringApplication.run(Day17WeatherApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		//Map<String, String> weatherResult = weatherService.search("Melbourne");
		// DEBUG
		// weatherResult.entrySet().stream().forEach(e -> System.out.println(e.getKey() + ":" + e.getValue()));
		// System.exit(0);
	}

}
