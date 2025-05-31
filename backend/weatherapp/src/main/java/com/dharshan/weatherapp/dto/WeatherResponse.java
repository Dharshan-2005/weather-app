package com.dharshan.weatherapp.dto;

import lombok.Data;

@Data
public class WeatherResponse {
    private String city;
    private double temp;
    private int humidity;
    private double windSpeed;
}
