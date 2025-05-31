package com.dharshan.weatherapp.controller;

import com.dharshan.weatherapp.dto.WeatherResponse;
import com.dharshan.weatherapp.service.WeatherService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class GetData {
    WeatherService weatherService;
    GetData(WeatherService weatherService){
        this.weatherService=weatherService;
    }
    @GetMapping("/weather")
    public ResponseEntity<?> getData(@RequestParam String city){
        WeatherResponse data=weatherService.getData(city);
        if(data!=null){
            return new ResponseEntity<>(data, HttpStatus.OK);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("the weather is not found");

    }
}
