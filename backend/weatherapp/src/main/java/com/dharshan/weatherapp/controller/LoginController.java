package com.dharshan.weatherapp.controller;

import com.dharshan.weatherapp.model.Users;
import com.dharshan.weatherapp.security.JwtUtil;
import com.dharshan.weatherapp.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class LoginController {
    WeatherService weatherService;
    JwtUtil jwtUtil;
    @Autowired
    LoginController(WeatherService weatherService,JwtUtil jwtUtil){
        this.weatherService = weatherService;
        this.jwtUtil=jwtUtil;
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Users data){
        if(weatherService.validateLogin(data)){
            String token=jwtUtil.generateToken(data.getEmail());
            return ResponseEntity.ok().body(Map.of("token",token));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error","Invalid credentials"));
    }
    @PostMapping("/signin")
    public ResponseEntity<String> signUp(@RequestBody Users data){
        if(weatherService.createUser(data)!=null){
            System.out.println();
            return new ResponseEntity<>("The data is added",HttpStatus.OK);
        }
        return new ResponseEntity<>("data cannot be added",HttpStatus.NOT_FOUND);
    }
}
