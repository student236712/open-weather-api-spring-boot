package com.weatherstation;

import com.weatherstation.dto.ToScatter;
import com.weatherstation.model.*;
import com.weatherstation.repo.WeatherRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@org.springframework.stereotype.Controller
public class Controller {
    private Thread worker;
    @Value("${apiKeyValue}")
    private String apiKeyValue;
    private WeatherRepo weatherRepo;

    @Autowired
    public Controller(WeatherRepo weatherRepo) {
        this.weatherRepo = weatherRepo;
    }

    @GetMapping("/")
    public String startPage(Model model) {
//        model.addAttribute("lat", 49.296930);
//        model.addAttribute("lon", 19.956937);
//        model.addAttribute("name", "Zakopane");


        return "Meteogram";
    }

    @GetMapping("/weather/{cityName}")
    public String getAlbumsByAuthor(@Value("${apiKeyValue}") String apiKeyValue,
                                    Model model, RedirectAttributes redirectAttributes, @PathVariable String cityName) {

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        ToScatter toScatter = new ToScatter();
        HttpEntity httpEntity = new HttpEntity(httpHeaders);

        ResponseEntity<Example> exchange =
                restTemplate.exchange("https://api.openweathermap.org/data/2.5/forecast?q=" + cityName + "&units=metric&APPID=" + apiKeyValue,
                        HttpMethod.GET,
                        httpEntity,
                        Example.class);
        List<com.weatherstation.model.List> listOfWeather = exchange.getBody().getList();
        // Accumulate names into a List
        //List<String> list = people.stream().map(Person::getName).collect(Collectors.toList());
        List<Double> rainFall = listOfWeather.stream().map(com.weatherstation.model.List::getMain).map(Main::getTemp).collect(Collectors.toList());
        List<Rain> rainList = listOfWeather.stream().map(com.weatherstation.model.List::getRain).collect(Collectors.toList());
        List<Optional<Rain>> optionalList = null;
         optionalList.addAll(rainList.forEach(rain -> {}));
        //List<Double> rainFall = rainList.stream().ma.forEach();

        List<Optional<Rain>> optionalList = Arrays.asList(Optional.of())
        for (com.weatherstation.model.List element : listOfWeather) {

            LocalDateTime date = LocalDateTime.parse(element.getDtTxt().substring(0, 10) + 'T' + element.getDtTxt().substring(11));

            Optional<Rain> rain = Optional.ofNullable(element.getRain());
            Optional<Snow> snow = Optional.ofNullable(element.getSnow());

            Double rain3h = 0.0;
            Double snow3h = 0.0;
            if (rain.isPresent()) rain3h = rain.get().get3h();
            if (snow.isPresent()) snow3h = snow.get().get3h();
            Double windDeg = element.getWind().getDeg();
            Double windSpeed = element.getWind().getSpeed();
            String iconURL = "http://openweathermap.org/img/wn/" + element.getWeather().get(0).getIcon() + "@2x.png";
            String weatherDescription = element.getWeather().get(0).getDescription();
            Double temp = element.getMain().getTemp();
            Integer humidity = element.getMain().getHumidity();
            Double pressure = element.getMain().getPressure();
            Double feels_like = (Double) element.getMain().getAdditionalProperties().get("feels_like");


        }
        City city = exchange.getBody().getCity();
        Double lat = city.getCoord().getLat();
        Double lon = city.getCoord().getLon();
        String name = city.getName();
        toScatter.setRainfall(rainFall);
        // redirectAttributes.addFlashAttribute("weatherDTO", weatherDTO);
        List<String> images = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            images.add("http://openweathermap.org/img/wn/10d@2x.png");
        }
        model.addAttribute("images", images);
        model.addAttribute("toScatter", toScatter);
        return "Scatter";
    }

//    @GetMapping("/showScatter")
//    public String showScatter(Model model, @ModelAttribute(value = "weatherDTO") WeatherDTO weatherDTO) {
//        List<WeatherDTO> weatherDTOList = weatherRepo.findAll();
//        ArrayList<Double> temp = new ArrayList<>();
//        weatherDTOList.forEach(element -> temp.add(element.getTemp()));
//
//
//        ArrayList<Double> hum = new ArrayList<>();
//        weatherDTOList.forEach(element -> hum.add(element.getHumidity()));
//        StatisticsOperations statisticsOperations = new StatisticsOperations();
//        Map<Integer, Double> surveyMap = new LinkedHashMap<>();
//        temp.forEach(element -> surveyMap.put(temp.indexOf(element), element.doubleValue()));
//        Map<Integer, Double> surveyMap2 = new LinkedHashMap<>();
//        hum.forEach(element -> surveyMap2.put(hum.indexOf(element), element.doubleValue()));
//        Map<String, Object> mapToMap = new LinkedHashMap<>();
//        mapToMap.put("name", weatherDTO.getCity());
//        mapToMap.put("lat", weatherDTO.getLat());
//        mapToMap.put("lon", weatherDTO.getLon());
//
//        model.addAttribute("meanRate", statisticsOperations.computeMean(temp));
//        model.addAttribute("minRate", statisticsOperations.minValue(temp));
//        model.addAttribute("maxRate", statisticsOperations.maxValue(temp));
//        model.addAttribute("scatterTitle", "temp");
//        model.addAttribute("scriptToMap", "https://code.highcharts.com/mapdata/countries/pl/pl-all.js");
//        model.addAttribute("mapDestination", weatherDTO.getCountry());
//
//        model.addAttribute("surveyMap", surveyMap);
//        model.addAttribute("surveyMap2", surveyMap2);
//        model.addAttribute("mapDescription", mapToMap);
//
//        return "Scatter";
//    }


}