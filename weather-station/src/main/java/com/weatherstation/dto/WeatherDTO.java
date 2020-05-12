package com.weatherstation.dto;


import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class WeatherDTO {
    private String id;
    private double temp;
    private double pressure;
    private double humidity;
    private String description;
    private double windSpeed;
    private Integer windDeg;
    private String country;
    private double lat;
    private double lon;
    private String city;




}
